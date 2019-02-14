<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../include/header.jsp" %>

<style>
	div.item{
		width: 100px;
		float: left;
		margin: 5px;
		text-align: center;
		position: relative;
	}
	
	div.box-footer{
		clear: both;
	}

	button.btnDel{
		position: absolute;
		top: 10px;
		right: 10px;
	}
</style>

<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Home Page</h3>
				</div>
					<div class='box-body'>
					
						<!-- 파일 추가하고 싶을 때 form에 반드시 enctype 사용해야 함 -->
						<form action="modifyPage" method="post" id="f1" enctype="multipart/form-data">
							<div class="form-group">
								<label>Title</label>
								<input type="text" name="title" class="form-control" placeholder="Enter Title" value="${boardVO.title }">
							</div>
													
							<div class="form-group">
								<label>Content</label>
								<textarea rows="5" class="form-control" name="content" placeholder="Enter Content">${boardVO.content }</textarea>
							</div>
							
							
							<!-- 수정 시 새로운 첨부파일 추가하고 싶을 때 -->
							<div class="form-group">
								<label>Image File</label>
								<input type="file" name="addFiles" class="form-control" multiple="multiple">
							</div>
							
							<!-- 수정할 때 첨부 파일 들어오는 부분 -->
							<div class="form-group">
								<c:forEach var="file" items="${boardVO.files }">
									<div class="item">
										<img src="displayFile?filename=${file }">
										<span>${fn:substring(file, 51, -1)}</span>
										<button type="button" class="btnDel" data-src="${file }">X</button>
									</div>
								</c:forEach>
							</div>
							
							<!-- 삭제하려는 파일 들어갈 div -->
							<div id="delFileWrap"></div>
						
						
							<script>
								//image의 크기를 구하여 div.item의 크기 조정 [
								$(".item").each(function(i, obj){
									var img = new Image();
									img.onload = function(){  
										$(obj).css("width", this.width);
									}
									img.src = $(obj).find("img").attr("src");
									// ]
								})
								
								$(".btnDel").click(function(){
									var filename = $(this).attr("data-src");
									//삭제하고자 하는 파일이름이 담긴 새로운 input 태그를 만든다.
									var $input = $("<input>").attr("type", "hidden").attr("name", "delFiles").val(filename);
									//삭제하려는 파일이 들어가는 div에 input 태그들을 넣는다.
									$("#delFileWrap").append($input);
									$(this).parent().remove();
								})
							</script>						
							
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