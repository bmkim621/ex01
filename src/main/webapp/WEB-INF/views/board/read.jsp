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
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="Enter Title" value="${boardVO.title }" readonly="readonly">
						</div>
												
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" class="form-control" name="content" placeholder="Enter Content" readonly="readonly">${boardVO.content }</textarea>
						</div>
							
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="Enter Writer" value="${boardVO.writer }" readonly="readonly">
						</div>
					</div>
						
					<!-- box-footer부분 -->
					<div class="box-footer">
						<button type="submit" class="btn btn-primary" id="btnList">Go List</button>
						<button type="submit" class="btn btn-warning" id="btnModify">Modify</button>
						<button type="submit" class="btn btn-danger" id="btnRemove">Remove</button>
					</div>
					
					<!-- 삭제할 때 post 방식으로 보내기 위해서 번호 hidden으로 실어서 보냄 -->
					<form action="" method="post" id="f1">
						<input type="hidden" name="bno" value="${boardVO.bno }" >
					</form>
			</div>
		</div>
	</div>
</section>

<!-- 버튼 클릭했을 경우 해당 페이지로 이동하게 처리한다. -->
<script>
	$(function(){
		$("#btnList").click(function(){
			location.href = "${pageContext.request.contextPath}/board/listAll";
		})
		
		//Remove 버튼 클릭
		$("#btnRemove").click(function(){
			//id가 f1인 form을 submit으로 보내야함.
			//폼 태그의 action의 값을 바꿔준다.
			$("#f1").attr("action", "remove");
			//모든 태그의 속성을 제이쿼리에서 바꿀 수 있다. ex) get 방식으로 바꾸고 싶다. $("#f1).attr("method", "get");
			//폼 태그 보낸다.
			$("#f1").submit();
			
			//이 결과값을 받을 컨트롤러.
		})
		
		//수정버튼 클릭했을 경우
		$("#btnModify").click(function(){
			location.href = "${pageContext.request.contextPath}/board/modify?bno=${boardVO.bno }";
		})
	})
</script>
<%@ include file="../include/footer.jsp" %>