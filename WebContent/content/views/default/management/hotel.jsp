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
					酒店列表<a href="addHotel"><span class="badge navbar-right">添加&nbsp;+</span></a>
				</h3>
			</div>
			<div class="list-group">
				<div class="list-group-item">
					<p class="list-group-item-text">
					<table class="table table-striped" id="talble1">
							<tr>
								<th>酒店名</th>
								<th>电话</th>
								<th>地址</th>
								<th>描述</th>
								<th></th>
							</tr>
							<c:forEach var="hotel" items="${list }" varStatus="loop">
								<c:choose>
									<c:when test="${loop.index%2==0 }">
										<tr >
									</c:when>
									<c:otherwise>
										<tr >
									</c:otherwise>
								</c:choose>
									<td style="width:10%"><a href="./getHotel?id=${hotel.hotelID }">${hotel.name }</a></td>
									<td style="width:10%">${hotel.phoneNum }</td>
									<td style="width:10%">${fn:substring(hotel.address, 0, 8)}.....</td>
									<td>${hotel.description }</td>
								<td><a href="./updateHotel?id=${hotel.hotelID }"><span class="badge">修改</span></a></td>
								<td><a onclick="return confirm('确定删除该新闻么？')" href="./deleteHotel?id=${hotel.hotelID }">
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