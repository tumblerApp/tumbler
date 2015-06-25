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
					<span>反馈列表</span>
				</h3>
			</div>
			
			<div class="list-group">
				<div class="list-group-item">
					<p class="list-group-item-text">
					<table class="table table-striped" id="talble1">
							<tr>
								<th>内容</th>
								<th>电话</th>
								<th></th>
							</tr>
							<c:forEach var="feedback" items="${list }" varStatus="loop">
								<c:choose>
									<c:when test="${loop.index%2==0 }">
										<tr >
									</c:when>
									<c:otherwise>
										<tr >
									</c:otherwise>
								</c:choose>
									<td><a href="./getFeedback?id=${feedback.feedbackID }">${fn:substring(feedback.content, 0, 8)}.....</a></td>
									<td>${feedback.phoneNum }</td>
								<td><a onclick="return confirm('确定删除该反馈么？')"
									href="./deleteFeedback?id=${feedback.feedbackID }"><span
										class="badge">删除</span></a></td>
							</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<jsp:include page='footer.jsp' />