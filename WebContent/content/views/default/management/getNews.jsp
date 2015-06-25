<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page='header.jsp' />
<div class="row clearfix">
<jsp:include page='menu.jsp' />
	<div class="col-md-10 column">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					标题：&nbsp;<font color="blue">${notice.title }</font><a href="./news"> <span class="badge navbar-right">返回</span></a>
				</h3>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<div class="col-sm-12">
						<p class="col-sm-12  control-p">${notice.content }</p>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<div class="col-sm-12">
						<img alt="新闻图片" src="..${notice.path }">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page='footer.jsp' />