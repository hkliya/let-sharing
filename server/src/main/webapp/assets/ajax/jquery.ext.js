(function($){
	$.UI = {
		scrollToBottom: function(containerId){
    		var body;
    		if(containerId == undefined){
    			body = $("body")[0];
    		}else{
    			body = $("#" + containerId)[0];
    		}
    		body.scrollTop = body.scrollHeight;
    	},
		load: function($a, callBack){
			var $div = $("#" + $a.attr("target"));
    		$div.html("");
    		var url = $a.attr("link") == "" ? $a.attr("href") : $a.attr("link");
    		$.HTTP.getHtml(url, function(html){
    			if(callBack != undefined && $.isFunction(callBack)){
					callBack(html);
				}else{
					$div.html(html);
				}
    		});
		},
		loadMore: function($a, callBack){
			$a.show();
			var pageNum = $a.attr("pageNum") == "" ? 1 : parseInt($a.attr("pageNum")) + 1;
			var url = $a.attr("link") == "" ? $a.attr("href") : $a.attr("link");
			if(url.indexOf("?") > 0){
				url = url + "&pageNum=" + pageNum;
			}else{
				url = url + "?pageNum=" + pageNum;
			}
			$.HTTP.getHtml(url, function(html){
				setTimeout(function(){
					$a.hide();
					if(callBack != undefined && $.isFunction(callBack)){
						if(html.trim() != ""){
							$a.attr("pageNum", pageNum);
						}
						callBack(html);
						return;
					}
					if(html.trim() == ""){
						$a.hide();
						return;
					}
					var $target = $("#" + $a.attr("target"));
					$target.append(html);
					$a.attr("pageNum", pageNum);
				}, 500);
			});
		},
		subForm: function(formId){
			$("#" + formId).submit();
		},
		scrollLoad: function($Link){
			var params = {isLoading: false, $a: $Link};
			function setLoaded(){
				params.isLoading = false;
			};
			function setLoading(){
				params.isLoading = true;
			}
			function isLoading(){
				return params.isLoading;
			}
			function load(){
				if($(document).scrollTop() + $(window).height() >= $(document).height()){
					console.log("is loading = " + params.isLoading);
					if(!params.isLoading){
			    		setLoading();
			    		console.log("load start");
			    		var $a = params.$a;
			    		$.UI.loadMore($a, function(html){
		    				if(html.trim() != ""){
		    					var $target = $("#" + $a.attr("target"));
		    					$target.append(html);
		    					$target.find(".scrollLoad").scrollLoading();
		    				}
		    				setTimeout(function(){
		    					console.log("load finish");
		    					setLoaded();
	    					}, 300);
		    			});
		    		}
			    }
			}
			window.onscroll = load;
		}
	};
	$.EVENT = {
    	submit: function(formId){
    		$("#" + formId).submit();
    	}
	};
	$.HTTP = {
		open: function(obj){
			var $me = $(obj);
			var url = $me.attr("href") || $me.attr("link");
			window.open(url); 
		},
		getHtml: function(url, callBack){
			$.HTTP._ajax('get', url, {}, 'html', callBack);
		},
		getJson: function(url, callBack){
			$.HTTP._ajax('get', url, {}, 'json', callBack);
		},
		subForm: function(form, callBack){
			var $form = $(form);
			var url = $form.attr("action");
			var data = $form.serializeArray();
			$.HTTP._ajax('post', url, data, 'json', callBack);
			return false;
		},
		//提交带文件表单，依赖 jquery.ajaxsubmit.js插件
		subMulForm: function(form, callBack){
			var $form = $(form);
		    $form.ajaxSubmit(function(data, textStatus, xhr){
		    	if(callBack != undefined && $.isFunction(callBack)){
		    		callBack($form, data);
				}
		    });
			return false;
		},
		_ajax: function(type, url, data, dataType, callBack){
			$.ajax({
				type: type,
				url: url,
				data: data,
				dataType: dataType,
				success: function(response){
					if(callBack != undefined && $.isFunction(callBack)){
						callBack(response);
					}
				}
			});
		}
	};
	$.CB = {
		reload: function(json){
			if(json.statusCode == 200){
				window.location.reload(false);
			}else{
				alert(json.msg);
			}
		}
	};
	
	$.Component = {
		pairStatus: function(selector, container){
			
			var $es = container == undefined ? $(selector) : $(container).find(selector);
			$es.unbind("click");
			$es.click(function(event){
				event.preventDefault();
				var $e = $(this);
				
				if($e.attr("disable") != undefined){
					return;
				}
				
				var currentStatus = $e.attr("display");
				var $p = $e.parent();
				$p.find(selector).each(function(){
					var $me = $(this);
					if($me.attr("display") == currentStatus){
						$me.hide();
						$me.attr("display", 'false');
					}else{
						$me.show();
						$me.attr("display", 'true');
					}
				});
				var callback = $e.attr("callback");
				if(callback != undefined){
					if(!$.isFunction(callback)){
						callback = eval('(' + callback + ')');
					}else{
						callback.call($e, $e);
					}
				}
				$.HTTP.getJson($e.attr("href"));
			});
			$es.each(function(){
				var $e = $(this);
				$e.hide();
				if($e.attr("display") == 'true'){
					$e.show();
				}
			});
		}	
	};
	
	$.UploadFile = {
		upload: function(inputId, postUrl, callbacks){
			var fd = new FormData();
			var input = document.getElementById(inputId);
	        fd.append(input.name, input.files[0]);
	        var xhr = new XMLHttpRequest();
	        
	        if(callbacks.start != undefined){
	        	callbacks.start.call($.UploadFile, input.files[0]);
	        }
	        
	        xhr.upload.addEventListener("progress", callbacks.progress || $.UploadFile.progress, false);
	        xhr.addEventListener("load", callbacks.load || $.UploadFile.load, false);
	        xhr.addEventListener("error", callbacks.error || $.UploadFile.error, false);
	        xhr.addEventListener("abort", callbacks.abort || $.UploadFile.abort, false);
	        xhr.open("POST", postUrl);
	        xhr.send(fd);
		},
		progress: function(evt){
			//正在上传，处理进度
			if (evt.lengthComputable) {
	          var percentComplete = Math.round(evt.loaded * 100 / evt.total);
	          console.log(percentComplete.toString() + '%');
	          //document.getElementById('progressBar').style.width = percentComplete.toString() + '%';
	        }
		},
		load: function(evt){
			//上传成功,服务器端返回响应时候触发
			var result = evt.target.responseText;
	        console.log(result);
		},
		error: function(){
			//上传错误
			alert("There was an error attempting to upload the file.");
		},
		abort: function(){
			//取消上传
			alert("The upload has been canceled by the user or the browser dropped the connection.");
		}
	};
	$.Browser = {
		isPC: function(){
			var userAgentInfo = navigator.userAgent;
		    var Agents = ["Android", "iPhone",
		                "SymbianOS", "Windows Phone",
		                "iPad", "iPod"];
		    var flag = true;
		    for (var v = 0; v < Agents.length; v++) {
		        if (userAgentInfo.indexOf(Agents[v]) > 0) {
		            flag = false;
		            break;
		        }
		    }
		    return flag;
		}	
	};
	$.PageBar = {
		//bar 需要属性： pageNum，totalPage， formId(分页formId)，itemNum(按钮数，optional)
		init: function(barId){
			var $bar = $("#" + barId);
			$.PageBar._createBar($bar);
			$.PageBar._initClick($bar);
		},
		_createBar: function($bar){
			var pageNum = parseInt($bar.attr("pageNum"));
			var totalPage = parseInt($bar.attr("totalPage"));
			
			var index = 1;
			var count = $bar.attr("itemNum") == undefined ? 5 : parseInt($bar.attr("itemNum"));
			if(pageNum <= count){
				index = 1;
			}else if(pageNum + count > totalPage){
				index = totalPage - count + 1;
			}else{
				index = pageNum - (count % 2 == 0 ? count/2 : (count - 1) / 2);
			}
			if(pageNum > count){
				var pageIndex = parseInt(pageNum) - 1;
				$bar.append('<li><a class="page_a" href="javascript:void(0);" pageNum="1">首页</a></li>');
				$bar.append('<li><a class="page_a" href="javascript:void(0);" pageNum="' + pageIndex + '">&lt;</a></li>');
			}
			var totalIndex = index + count - 1;
			totalIndex = totalIndex > totalPage ? totalPage : totalIndex;
			for(; index <= totalIndex; index++){
				if(pageNum == index){
					$bar.append('<b> ' + pageNum + ' </b>');
				}else{
					$bar.append('<li><a class="page_a" href="javascript:void(0);" pageNum="' + index + '">' + index + '</a></li>');
				}
			}
			if(pageNum < totalPage){
				var pageIndex = parseInt(pageNum) + 1;
				$bar.append('<li><a class="page_a" href="javascript:void(0);" pageNum="'+ pageIndex +'">&gt;</a></li>');
				$bar.append('<li><a class="page_a" href="javascript:void(0);" pageNum="' + totalPage + '">尾页</a></li>');
			}
		},
		_initClick: function($bar){
			var $form = $("#" + $bar.attr("formId"));
			$bar.find("a.page_a").each(function(){
				$This = $(this);
				var pageNum = $This.attr("pageNum");
				$This.click(function(){
					var $input = $form.find("input[name=pageNum]:first");
					$input.val(pageNum);
					$form.submit();
				});
			});
		}
	};
})(jQuery);

/**
 * 图片上传
 */
function MEditor(uploadFormId, targetId){
	var $Form = $("#" + uploadFormId);
	var $Target = $("#" + targetId);
	var ME = this;
	this.init = function(triggerBtnId){
		ME.bindSelectFileEvent(triggerBtnId);
		ME.bindUploadImgEvent();
	};
	this.bindSelectFileEvent = function(triggerBtnId){
		$("#" + triggerBtnId).click(function(){
			var $input = $Form.find("input[type=file]:first");
			$input.click();
		});
	};
	this.bindUploadImgEvent = function(){
		var $input = $Form.find("input[type=file]:first");
		$input.change(ME.uploadImg);
	};
	this.uploadImg = function(){
		$Form.ajaxSubmit(function(data, textStatus, xhr){
			if(data.url != undefined){
				var $block = ME.createBlcok(data.url);
				$Target.append($block);
			}
			var $input = $Form.find("input[type=file]:first");
			$input.val("");
	    });
	};
	this.createBlcok = function(imgUrl){
		var html = '<div style="position: relative; width: 100%;">'
			 + '<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
			 + '<img style="max-width:100%;">'
			 + '<textarea class="form-control" placeholder="图片说明" rows="2" ></textarea>'
			 + '</div>';
		var $block = $(html);
		var $img = $block.find('img:first');
		$img.attr("src", imgUrl);
		
		ME.initDeleteEvent($block);
		
		return $block;
	};
	this.initDeleteEvent = function($block){
		$block.find('button:first').click(function(){
			$block.remove();
		});
	};
	this.getContent = function(){
		var content = "";
		$Target.find("img").each(function(){
			var $img = $(this);
			content += $img[0].outerHTML;
			var $eara = $img.next();
			var val = $eara.val();
			if(val.trim() != ''){
				content += "<p>" + val + "</p>"
			}
		});
		return content;
	};
	this.setContentTo = function(inputId){
		var content = ME.getContent();
		$("#" + inputId).val(content);
	};
}

/**
 * 文件上传
 */
function FileUpload(){
	var ME = this;
	this.btn = undefined;
	this.input = undefined; 
	
	//1,选择文件
	this.fileSelect = function(clickBtnId, fileInputId){
		ME.btn = document.getElementById(clickBtnId);
		ME.input = document.getElementById(fileInputId);
		ME.btn.onclick = function(){
			ME.input.click();
    	};
    	return ME;
	};
	//2,上传文件
	this.fileUpload = function(postUrl){
		ME.input.onchange = function(){
    		var fd = new FormData();
	        fd.append(ME.input.name, ME.input.files[0]);
	        var xhr = new XMLHttpRequest();
	        
	        ME.start();
	        
	        xhr.upload.addEventListener("progress", ME.progress, false);
	        xhr.addEventListener("load", ME.load, false);
	        xhr.addEventListener("error", ME.error, false);
	        xhr.addEventListener("abort", ME.abort, false);
	        xhr.open("POST", postUrl);
	        xhr.send(fd);
    	};
    	return ME;
	};
	//3,事件回调
	this.setEventCB = function(callbacks){
		
		if(callbacks.start != undefined){
			ME.start = callbacks.start;
		}
		if(callbacks.progress != undefined){
			ME.progress = callbacks.progress;
		}
		if(callbacks.load != undefined){
			ME.load = callbacks.load;
		}
		return ME;
	}
	
	function isFun(fun){
		return startCB != undefined && typeof startCB == 'function'; 
	}
	
	//文件上传时间点事件
	//1，开始上传
	this.start = function(){
		var file = ME.input.files[0];
		if(file){
			var fileSize = 0;
	        if (file.size > 1024 * 1024){
	        	fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';  
	        }else{
	        	fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
	        }
	        console.log("name = " + file.name);
	        console.log("size = " + fileSize);
		}
	};
	//2，正在上传
	this.progress = function(evt){
		if (evt.lengthComputable) {
			var percentComplete = Math.round(evt.loaded * 100 / evt.total);
	        console.log(percentComplete.toString() + '%');
	    }
	};
	//3上传完成
	this.load = function(evt){
		var result = evt.target.responseText;
        console.log(result);
	};
	//4上传失败
	this.error = function(){
		alert("There was an error attempting to upload the file.");
	}
	//5取消上传
	this.abort = function(){
		alert("The upload has been canceled by the user or the browser dropped the connection.");
	}
}


/*表情*/
function Emoji(iconBtn, iconsAreaId, editAreaId, textAreaId){
	var $iconBtn = $("#" + iconBtn);
	var $iconsArea = $("#" + iconsAreaId);
	var $icons = $iconsArea.find("img");
	var $editArea = $("#" + editAreaId);
	var $target = $("#" + textAreaId);

	$iconBtn.click(function(){
		$iconsArea.slideToggle("fast");
	});

	$icons.click(function(){
		var $me = $(this);
		var $img = $("<img>");
		$img.attr("src", $me.attr("src"));
		$img.css("display", "inline");
		$editArea.append($img);
	});

	this.setText = function(){
		var text = $editArea.html();
		text = replaceScript(text);
		$target.val(text);
	}

	function replaceScript(str){
		var startTag = /<script/g;
		var endTag = /<\/script>/g;
		return str.replace(startTag, '&lt;script').replace(endTag, '&lt;/script&gt;');
	}
}