<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Board List All</h3>
				</div>
				
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
							      	<td><a href='${pageContext.request.contextPath}/board/read?bno=${boardVO.bno }'>${boardVO.title }</a></td>
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
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li><a href="#">${idx }</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp" %>