<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>

<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Board List Search</h3>
				</div>
				<div class="box-body">
					<select name="searchType">
						<option value="n">---</option>
						<option value="t" ${cri.searchType == 't' ? 'selected' : '' }>Title</option>
						<option value="c" ${cri.searchType == 'c' ? 'selected' : '' }>Content</option>
						<option value="w" ${cri.searchType == 'w' ? 'selected' : '' }>Writer</option>
						<option value="tc" ${cri.searchType == 'tc' ? 'selected' : '' }>Title or Content</option>
						<option value="cw" ${cri.searchType == 'cw' ? 'selected' : '' }>Content or Writer</option>
						<option value="tcw" ${cri.searchType == 'tcw' ? 'selected' : '' }>Title or Content or Writer</option>
					</select>
					<input type="text" name="keyword" id="keywordInput" value="${cri.keyword }">
					<button id="btnSearch">Search</button>
					<button id="btnNewBoard">New Board</button>
				</div>
			</div>		
			
			<div class="box">
				<div class="box-body">
					<!-- 테이블 들어갈 곳 -->
					<table class="table table-hover">
						<thead>
						   	<tr>
						        	<th style="width:10px;">BNO</th>
						        	<th>TITLE</th>
							        <th>WRITER</th>
							        <th>REGDATE</th>
							        <th style="width:40px;">VIEWCNT</th>
						    	</tr>
						    </thead>
						    <tbody>
						    	<c:forEach items="${list }" var="boardVO">
						    		<tr>
								      	<td>${boardVO.bno }</td>
								      	<!-- 타이틀 눌렀을 때 상세정보 보기로 넘어가기 위해서 a태그 필요 -->
								      	<!-- 해당 글 읽기위한 글번호 bno와 Go List 버튼을 눌렀을 때 해당 페이지로 이동하기 위해서 페이지 번호 page 같이 실어서 보내야 함 -->
								      	<td><a href='${pageContext.request.contextPath}/sBoard/readPage?bno=${boardVO.bno }&page=${pageMaker.cri.page }'>${boardVO.title }</a></td>
								      	<td>${boardVO.writer }</td>
								      	<td><fmt:formatDate value="${boardVO.regdate }" pattern="yyyy-MM-dd HH:mm"/></td>  
								      	<td><span class='badge bg-blue'>${boardVO.viewcnt }</span></td>
						      		</tr>
						    	</c:forEach>
						   </tbody>
					</table>
				</div>
			
					<!-- 하단에 나오는 페이지 번호 -->
					<div class='box-footer'>
						<div class='text-center'>
							<ul class='pagination'>
								<!-- prev 버튼 달릴 지 판단 -->
								<c:if test="${pageMaker.prev }">
									<li><a href="${pageContext.request.contextPath }/sBoard/list?page=${pageMaker.startPage - 1 }&searchType=${cri.searchType }&keyword=${cri.keyword }">&laquo;</a></li>
								</c:if>
								<!-- 현재 선택한 페이지 -->
								<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
									<li ${pageMaker.cri.page == idx ? 'class="active"' : ''}>
										<a href="${pageContext.request.contextPath }/sBoard/list?page=${idx }&searchType=${cri.searchType }&keyword=${cri.keyword }">${idx }</a>
									</li>
								</c:forEach>
								<!-- next 버튼 -->
								<c:if test="${pageMaker.next }">
									<!-- 10번페이지까지 다음 버튼 누르면 첫 시작이 11번 페이지니까 마지막 페이지 + 1 해야 함 -->
									<li><a href="${pageContext.request.contextPath }/sBoard/list?page=${pageMaker.endPage + 1 }&searchType=${cri.searchType }&keyword=${cri.keyword }">&raquo;</a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
</section>

<script>
	$(function(){
		//Search 버튼
		$("#btnSearch").click(function(){
			//searchType, keyword 값 가져오기
			var searchType = $("select[name='searchType']").val();
			var keyword = $("#keywordInput").val();
			
			location.href = "${pageContext.request.contextPath}/sBoard/list?searchType=" + searchType + "&keyword=" + keyword;
			
		})
		
		//New Board 버튼
		$("#btnNewBoard").click(function(){
			location.href = "${pageContext.request.contextPath}/sBoard/register";
		})
		
	})
</script>
<%@ include file="../include/footer.jsp" %>