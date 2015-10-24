/**
 * 初次加载时请调用：Ajax.init();
 */
var Ajax = {
	currentDialog: null,
	init: function(target){
		$(target).find("a[target=div]").each(function(){
			var $This = $(this);
			var url = $This.attr('href');
			var rel = $This.attr('rel');
			$This.click(function(event){
				Ajax.getHtml(url, rel);
				event.preventDefault();
				return false;
			});
		});
		$(target).find("a[target=ajax]").each(function(){
			var $This = $(this);
			var url = $This.attr('href');
			var reloadRel = $This.attr('reload');
			
			$This.click(function(event){
				event.preventDefault();
				Ajax.getJson(url, reloadRel);
			});
		});
		$(target).find("a[target=dialog]").each(function(){
			var $This = $(this);
			var url = $This.attr('href');
			var title = $This.attr('data-title') || '请给 <a> 设置 data-title 属性';
			var cssClass = $This.attr('data-Class') || '';
			var width = $This.attr('data-width') || BootstrapDialog.SIZE_NORMAL;
			
			$This.click(function(event){
				event.preventDefault();
				Ajax.currentDialog = BootstrapDialog.show({
					title: title,
					size: width,
					cssClass: cssClass,
					message: $('<div><div class="loading"></div></div>').load(url)
		        });
			});
		});
	},
	searchForm: function(form){
		var $form = $(form);
		var url = $form.attr("action");
		var rel = $form.attr("rel");
		var data = $form.serializeArray();
		Ajax.getHtml(url, rel, data);
	},
	refresh: function(rel){
		var form = $("#" + rel).find("form:first");
		Ajax.searchForm(form);
	},
	getHtml: function(url, rel, data){
		$.ajax({
			url: url,
			type: 'post',
			data: data,
			dataType: 'html',
			success: function(html){
				var container = $("#" + rel);
				CB.htmlCB(container, html);
			}
		});
	},
	getJson: function(url, reloadRel){
		$.ajax({
			url: url,
			type: 'post',
			dataType: 'json',
			success: function(json){
				CB.ajaxCB(json);
				CB.reloadCB(json, reloadRel);
			}
		});
	},
	//提交带文件表单，依赖 jquery.ajaxsubmit.js插件
	iframeSubmit: function(form){
		var $form = $(form);
		
	    $form.ajaxSubmit(function(data, textStatus, xhr){
	    	//$form.html(data);
	    	CB.htmlCB($form, data);
	    });
		return false;
	},
	normalSubmit: function(form){
		var $form = $(form);
		var url = $form.attr("action");
		var data = $form.serializeArray();
		var rel = $form.attr("rel");
		var dataResult = $form.attr("data-result");
		if(dataResult != undefined){
			$("#" + dataResult).html("");
		}
		var $submit = $form.find("button:submit,input:submit");
		$submit.attr("disabled", "disabled");
		$.ajax({
			url: url,
			type: 'post',
			dataType: 'html',
			data: data,
			success: function(html){
				$submit.removeAttr("disabled");
				try{
					var json = JSON.parse(html);
					CB.ajaxCB(json);
				}catch(e){
					var $container;
					if(rel == undefined){
						$container = $form;
					}else{
						$container = $("#" + rel);
					}
					if(dataResult != undefined){
						$container = $("#" + dataResult);
					}
					CB.htmlCB($container, html);
				}
			}
		});
		return false;
	}
};

/**
 * 回调函数
 */
var CB = {
	ajaxCB: function(json){
		if(json.statusCode == 301){
			alert("会话超时，请重新登录");
			window.location.href = "login.jsp";
			return;
		}
		if(json.msg){
			alert(json.msg);
		}
	},
	reloadCB: function(json, reloadRel){
		if(json.statusCode == 200){
			var form = $("#" + reloadRel).find("form:first");
			Ajax.searchForm(form);
		}
	},
	htmlCB: function(container, html){
		try{
			var json = html;
			if(typeof(html) == "string"){
				json = JSON.parse(html);
			}
			CB.ajaxCB(json);
			return;
		}catch(e){
			$container = $(container);
			$container.html(html);
			UI.init($container);
		}
	}
};


/**
 * 分页
 */
var Pagination = {
	init: function(target){
		$(target).find(".c_pagination").each(function(){
			var $This = $(this);
			var pageNum = parseInt($This.attr("pageNum"));
			var pageSize = parseInt($This.attr("pageSize"));
			var totalRow = parseInt($This.attr("totalRow"));
			var showNum = $This.attr("showNum") == undefined ? 6 : parseInt($This.attr("showNum"));
			var rel = $This.attr("rel");
			
			if(totalRow <= 0){
				return;
			}
			
			var preNum = pageNum - 1;
			var pre = '<li><a href="javascript:void(0)" target="pagination" num="' + preNum + '">&laquo;</a></li>';
			if(pageNum == 1){
				pre = '<li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>';
			}
			$This.append(pre);
			var totalPage = parseInt(totalRow / pageSize) + (totalRow % pageSize == 0 ? 0 : 1);
			for(var i = 1; i <= totalPage; i++){
				
				if(showNum < totalPage){
					
					if(i == (totalPage - 1)){
						$This.append('<li class="disabled"><a href="javascript:void(0);">...</a></li>');
						continue;
					}else if(i > showNum - 1 && i != totalPage ){
						continue;
					}
				}
				
				var li = '<li><a href="javascript:void(0)" target="pagination" num="' + i + '">' + i + '</a></li>';
				if(pageNum == i){
					li = '<li href="javascript:void(0)" class="active"><a target="pagination" num="' + i + '">' + i + '</a></li>';
				}
				$This.append(li);
			}
			
			var nextNum = pageNum + 1;
			var next = '<li><a href="javascript:void(0)" target="pagination" num="' + nextNum +'">&raquo;</a></li>';
			
			if(pageNum == totalPage){
				next = '<li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>';
			}
			$This.append(next);
			
			var extra = "<span style='margin-left:10px; line-height:34px;'>第 <span class='text-danger'>" + pageNum + "</span>/<span class='text-primary'>" + totalPage + "</span> 页</span>";
			$This.append(extra);
			
			Pagination._initClick($This, rel);
		});
	},
	_initClick: function(container, rel){
		var $form = $("#" + rel).find("#pager:first");
		
		$(container).find("a[target=pagination]").each(function(){
			$This = $(this);
			var pageNum = $This.attr("num");
			$This.on("click", function(event){
				event.preventDefault();
				var $input = $form.find("input[name=pageNum]:first");
				$input.val(pageNum);
				Ajax.searchForm($form);
			});
		});
	}
};

/**
 * 初始化UI，添加交互
 */
var UI = {
	init: function(target){
		target = target == undefined ? $(document) : target;
		Pagination.init(target);
		Ajax.init(target);
		LookUp.init(target);
	},
	initLoadingBar:function(){
		NProgress.configure({ easing: 'ease', speed: 1000 });
		$(document).ajaxStart(function(){
			//$.AMUI.progress.start();
			NProgress.start();
		});
		$(document).ajaxStop(function(){
			//$.AMUI.progress.done();
			NProgress.done();
		});
	}
};

var ValidForm = {
	_submit: function(form){
		var $form = $(form);
		var callback = $form.attr('callback');
		if(callback != undefined){
			var cbFun = eval("(" + callback + ")");
			cbFun(form);
		}
	},
	init: function(formId){
		var $form = $('#' + formId);
		$form.validator();
		$form.on('submit', function (e) {
			if (!e.isDefaultPrevented()) {
				ValidForm._submit(this);
			}
			return false;
		});
	},
	initAmaze: function(formId){
		$('#' + formId).validator({
			H5validation: false,
		    onValid: function(validity) {
		      $(validity.field).closest('.am-form-group').find('.am-alert').hide();
		    },
		    onInValid: function(validity) {
		      var $field = $(validity.field);
		      var $group = $field.closest('.am-form-group');
		      var $alert = $group.find('.am-alert');
		      // 使用自定义的提示信息 或 插件内置的提示信息
		      var msg = $field.data('validationMessage') || this.getValidationMessage(validity);
		      if (!$alert.length) {
		        $alert = $('<div class="am-alert am-alert-danger"></div>').hide().
		          appendTo($group);
		      }
		      $alert.html(msg).show();
		    }
		}).on('submit', function(e){
			if (!e.isDefaultPrevented()) {
				ValidForm._submit(this);
			}
			return false;
		});
	}
};

var LookUp = {
	init: function(target){
		$(target).find("a[target=lookUp]").click(function(event){
			var $This = $(this);
			
			var title = $This.attr("title");
			var height = $This.attr("height");
			var width = $This.attr("width");
			var url = $This.attr("href");
			var rel = "openDialogBody";
			
			var $dialog = $("#openedDialogDiv");
			
			//设置宽度、高度、标题
			var $settingDialog = $dialog.find("#c-setting-dialog");
			$settingDialog.css({'width': width + 'px', 'height': height + 'px'});
			var $title = $dialog.find("#openDialogTitle");
			$title.html(title);
			var bodyH = height - 40;
			$("#" + rel).css({'height': bodyH + "px"});
			
			$dialog.modal();
			
			Ajax.getHtml(url, rel);
			
			event.preventDefault();
		});
	},
	initSelect: function(containerId){
		var $Div = containerId == undefined ? $(document) : $("#" + containerId);
		var existedVals;
		$Div.find("[bringBack]").each(function(){
			var $This = $(this);
			var attrVal = $This.attr("bringBack");
			var target = attrVal.split("=")[0];
			var val = attrVal.split("=")[1];
			if(existedVals == undefined){
				var inputVal = $("[lookUp=" + target + "]").val();
				existedVals = "," + inputVal + ",";
			}
			if(existedVals.indexOf("," + val + ",") >= 0){
				$This.addClass("am-btn-success am-disabled");
			}
		});
	},
	bringBack: function(option, lookUpName){
		for(var key in option){
			var selector = lookUpName + "_" + key;
			var val = option[key];
			var $input = $("[lookUp=" + selector + "]");
			if($input[0].tagName.toLowerCase() == 'input'){
				$input.val(val);
			}else{
				$input.html(val);
			}
		}
		$("#openedDialogDiv").modal('close');
	},
	bringBackAppend: function(option, lookUpName, obj){
		var $This = $(obj);
		$This.addClass("am-btn-success am-disabled");
		for(var key in option){
			var selector = lookUpName + "_" + key;
			var val = option[key];
			var $input = $("[lookUp=" + selector + "]");
			
			if($input[0].tagName.toLowerCase() == 'input'){
				var originVal = $input.val();
				if(originVal != ""){
					originVal = originVal + ",";
				}
				$input.val(originVal+ val);
			}else{
				var originVal = $input.html();
				if(originVal != ""){
					originVal = originVal + ",";
				}
				$input.html(originVal+ val);
			}
		}
	},
	clear: function(targets){
		var targetArr = targets.split(",");
		for(var i = 0; i < targetArr.length; i++){
			var target = targetArr[i];
			var $Target = $("[lookUp=" + target + "]");
			
			if($Target[0].tagName.toLowerCase() == 'input'){
				$Target.val("");
			}else{
				$Target.html("");
			}
		}
	}
};
