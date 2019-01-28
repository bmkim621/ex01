<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Home Page</h3>
				</div>
				
				<!-- 본문 들어갈 부분 -->
				<div class="box-body">
					${result }
				</div>
				<!-- 본문 끝 -->
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp" %>