<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jstl.jsp" %>
</script>
<section class="content-header">
    <h1>
    我的宝贝
    </h1>
</section>

<form id="pager" action="${currentUrl }" rel="main" method="post">
	<input type="hidden" name="pageNum" value="${pageNum }"/>
	<input type="hidden" name="pageSize" value="${pageSize }" />
</form>

<section class="content">
	<div>
		<a href="http://club.jd.com/myJdcomments/myJdcomments.action" target="_blank" class="btn btn-danger">导入京东</a>
		<a href="http://club.jd.com/myJdcomments/myJdcomments.action" target="_blank" class="btn btn-warning">导入淘宝</a>
		<a href="http://club.jd.com/myJdcomments/myJdcomments.action" target="_blank" class="btn btn-primary">导入拍拍</a>
		<a href="javascript:void(0);" id="beginShare" class="btn btn-info">开始分享</a>
		<img id="qrcode" style="width:80px;height:80px;display: none" src="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2868037146,1713855063&fm=58"/>
	</div>
	<div id="main_second" style="margin-top: 3em;">
		<table class="table table-striped table-hover">
			<tr>
				<th width="5%">序号</th>
				<th width="10%">图片</th>
				<th width="10%">购买商品</th>
				<th >购买时间</th>
			</tr>
			<c:forEach items="${list}" var="data" varStatus="s">
			<tr>
				<td>${s.index + 1 + (pageNum - 1) * pageSize }</td>
				<td><img width="100px" height="100px" src="${data.imageURL}"/></td>
				<td>${data.name }</td>
				<td>
					<fmt:formatDate value="${data.bought_at }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			</c:forEach>
		</table>
		
		<%@include file="/common/pagination.jsp" %>
		
	</div>
</section>
<script type="text/javascript">
$(function(){
	$("#beginShare").hover(function(){
		$("#qrcode").show();
	},function(){
		$("#qrcode").hide();
	});
});
</script>
