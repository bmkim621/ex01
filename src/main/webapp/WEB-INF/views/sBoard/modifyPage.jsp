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
					
						<form action="modifyPage" method="post" id="f1">
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
						
							<!-- 수정할 때 post 방식으로 보내기 위해서 번호랑 페이지번호 같이 보내기(페이지 있는 목록으로 이동해야되니까 페이지 번호도 같이 실어서 보내야 함)-->
							<input type="hidden" name="bno" value="${boardVO.bno }" >
							<input type="hidden" name="page" value="${cri.page }">
							<input type="hidden" name="searchType" value="${cri.searchType }">
							<input type="hidden" name="keyword" value="${cri.keyword }">
						</form>
					
					</div>
			</div>
		</div>
	</div>
</section>

<%@ include file="../include/footer.jsp" %>