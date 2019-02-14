<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">REGISTER NEW BOARD</h3>
				</div>
					
					<!-- 첨부파일 사용할 때는 반드시 enctype -->
					<form role="form" method="post" action="register" enctype="multipart/form-data">
						<div class='box-body'>
							<div class="form-group">
								<label>Title</label>
								<input type="text" name="title" class="form-control" placeholder="Enter Title">
							</div>
							
							<div class="form-group">
								<label>Content</label>
								<textarea rows="5" class="form-control" name="content" placeholder="Enter Content"></textarea>
							</div>
							
							<div class="form-group">
								<label>Writer</label>
								<input type="text" name="writer" class="form-control" placeholder="Enter Writer">
							</div>
							
							<!-- 첨부파일 들어갈 곳 -->
							<div class="form-group">
								<label>Image File</label>
								<input type="file" name="imageFiles" class="form-control" placeholder="이미지를 선택하세요" multiple="multiple">
							</div>
						</div>
						
						<!-- box-footer부분 -->
						<div class="box-footer">
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</form>
				
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp" %>