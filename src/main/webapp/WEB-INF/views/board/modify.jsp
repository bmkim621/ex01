<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Home Page</h3>
				</div>
					<div class='box-body'>
					
						<form action="modify" method="post" id="f1">
							<div class="form-group">
								<label>Title</label>
								<input type="text" name="title" class="form-control" placeholder="Enter Title" value="${boardVO.title }">
							</div>
													
							<div class="form-group">
								<label>Content</label>
								<textarea rows="5" class="form-control" name="content" placeholder="Enter Content">${boardVO.content }</textarea>
							</div>
						
							
							<!-- box-footer부분 -->
							<div class="box-footer">
								<button type="submit" class="btn btn-warning" id="btnModify">Modify</button>
							</div>
						
							<!-- 수정할 때 post 방식으로 보내기 위해서 번호 hidden으로 실어서 보냄 -->
							<input type="hidden" name="bno" value="${boardVO.bno }" >
						</form>
					
					</div>

			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp" %>