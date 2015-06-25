<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page='header.jsp' />
<script type="text/javascript">
	// Popup window code
	function popupWindow(url) {
		popupWindow = window
				.open(
						url,
						'popUpWindow',
						'height=600,width=600,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
	}
</script>
<div class="row clearfix">
	<jsp:include page='menu.jsp' />
	<div class="col-md-10 column">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					列表管理<a href="addScenic"><span class="badge navbar-right">添加&nbsp;+</span></a>
				</h3>
			</div>
			<form action="searchScenic">
			<div class="panel-heading">
				<select name="searchCategory" id="searchCategory">
						<option value="nothing">请选择类别</option>
						<option value="scenic">景点</option>
						<option value="hotel">酒店</option>
						<option value="food">美食</option>
						<option value="shopping">购物</option>
						<option value="game">娱乐</option>
						<option value="information">资讯</option>
				</select>
				<button class="badge navbar-right" type="submit">查询</button>
			</div>
			</form>
			<div class="list-group">
				<div class="list-group-item">
					<p class="list-group-item-text">
					<table class="table table-striped" id="talble1">
							<tr>
								<th>名称</th>
								<th>类别</th>
								<th>电话</th>
								<th>地址</th>
								<th>描述</th>
								<th></th>
							</tr>
							<c:forEach var="scenic" items="${list }" varStatus="loop">
								<c:choose>
									<c:when test="${loop.index%2==0 }">
										<tr >
									</c:when>
									<c:otherwise>
										<tr >
									</c:otherwise>
								</c:choose>
								<td style="width:10%"><a href="./getScenic?id=${scenic.scenicID }">${scenic.name }</a></td>
								<td style="width:5%">
									<c:if test="${scenic.category == 'scenic'}">景点</c:if>
									<c:if test="${scenic.category == 'hotel'}">酒店</c:if>
									<c:if test="${scenic.category == 'food'}">美食</c:if>
									<c:if test="${scenic.category == 'shopping'}">购物</c:if>
									<c:if test="${scenic.category == 'game'}">娱乐</c:if>
									<c:if test="${scenic.category == 'information'}">资讯</c:if>
								</td>
								<td style="width:10%">${scenic.phoneNum }</td>
								<td style="width:10%">
									<c:if test="${scenic.address == null}"></c:if>
									<c:if test="${scenic.address != null}">
										<c:if test="${scenic.address == ''}">${scenic.address}</c:if>
										<c:if test="${scenic.address != ''}">${fn:substring(scenic.address, 0, 8)}.....</c:if>
									</c:if>
								</td>
								<td>${scenic.description }</td>
								<td><a href="./updateScenic?id=${scenic.scenicID }"><span class="badge">修改</span></a></td>
								<td><a onclick="return confirm('确定删除该新闻么？')" href="./deleteScenic?id=${scenic.scenicID }">
								<span class="badge">删除</span></a></td>
								</tr>
							</c:forEach>
					</table>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<jsp:include page='footer.jsp' />