<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

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
					
					<!-- 보내야할 것 : 글번호, 페이지번호, 키워드, 검색종류 -->
					<form action="" method="post" id="f1">
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
					</form>
			</div>
		</div>
	</div>
	
	
	<!-- 댓글 -->
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				
				<div class="box-body">
					<label>Writer</label>
					<input type="text" class="form-control" placeholder="Enter Userid" id="newReplyWriter">
					<label>Reply Text</label>
					<input type="text" class="form-control" placeholder="Enter Reply Text" id="newReplyText">
				</div>
				
				<div class="box-footer">
					<button class="btn btn-primary" id="btnReplyAdd">ADD REPLY</button>
				</div>
			</div>
			
			<ul class="timeline">
				<li class="time-label" id="repliesDiv">
					<span class="bg-green">Replies List</span>
				</li>
			</ul>
			
			<div class="text-center">
				<!-- 부트 스트랩 -->
				<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
			</div>
		</div>
	</div>
	
	<!-- 댓글 수정하는 모달창 -->
	<div id="modifyModal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- 상단에 X 버튼 -->
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				
				<div class="modal-body">	
					<p><input type="text" id="replytext" class="form-control"></p>	<!-- 댓글 내용 들어갈 부분 -->
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-info" id="btnReplyMod" data-rno="">Modify</button>	<!-- rno 들어갈 부분 -->
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</section>

<!-- 템플릿 -->
<script id="template1" type="text/x-handlebars-template">
	{{#each.}}
	<li class="replyLi" data-rno="{{rno}}">
		<i class="fa fa-comments bg-blue"></i>
		
		<div class="timeline-item">
			<span class="item">
				<i class="fa fa-clock-o"></i>{{tempDate regdate}}
			</span>
			<h3 class="timeline-header">
				<strong>{{rno}}</strong> - {{replyer}}
			</h3>
			<div class="timeline-body">{{replytext}}</div>
			<div class="timeline-footer">
				<a class="btn btn-primary btn-xs btnModify" data-toggle="modal" data-target="#modifyModal" data-rno="{{rno}}">Modify</a>
				<a class="btn btn-danger btn-xs btnDelete">Delete</a>
			</div>
		</div>
	</li>
	{{/each}}
</script>

<!-- Handlerbars 사용 -->
<script>
	
	Handlebars.registerHelper("tempDate", function(value){
		var date = new Date(value);
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		
		return year + "-" + month + "-" + day; 
	})

	var bno = ${boardVO.bno };
	
	function getPageList(page){
		$.ajax({
			//컨트롤러에 있는 주소 replies/{bno}/{page}
			url: "${pageContext.request.contextPath}/replies/" + bno + "/" + page,
			type: "get",
			dataType: "json",
			success: function(json){
				console.log(json);
						
				//템플릿에 있는 li 태그가 사라져야 되므로(나자신 -> remove 사용)
				$(".replyLi").remove();
				
				var source = $("#template1").html();
				var f = Handlebars.compile(source);
				var result = f(json.list);
				
				$(".timeline").append(result);
				

				//pagination
				//새로고침 될 때 안에 있는 내용 날린다. => 댓글 작성 성공하면 다시 getPageList 호출하기 때문에
			 	$("#pagination").empty();
				
				for(var i = json.pageMaker.startPage ; i <= json.pageMaker.endPage ; i++){
					
					var liTag = $("<li>");
					var aTag = $("<a>").attr("href", "#").append(i);
					liTag.append(aTag);
					
					//시작하는 페이지가 선택한 페이지와 같으면
					if(i == json.pageMaker.cri.page){
						liTag.addClass("active");
					}
					
					$("#pagination").append(liTag);
				}
			}
		})	
	}
	
</script>

<!-- 버튼 클릭했을 경우 해당 페이지로 이동하게 처리한다. -->
<script>
	$(function(){
		//댓글 리스트 보이게 한다.
		getPageList(1);
		
		
		$("#btnList").click(function(){
//			location.href = "${pageContext.request.contextPath}/board/listPage";
			
			//form태그에 있는 값을 가져가고 싶으면? 그냥 get 방식으로 보내고, action의 위치를 listPage(이동하려는 주소값)로 넘김.
			$("#f1").attr("action", "list");
			$("#f1").attr("method", "get");
			$("#f1").submit();
		})
		
		//Remove 버튼 클릭
		$("#btnRemove").click(function(){
			//id가 f1인 form을 submit으로 보내야함.
			//폼 태그의 action의 값을 바꿔준다.
			$("#f1").attr("action", "removePage");
			//모든 태그의 속성을 제이쿼리에서 바꿀 수 있다. ex) get 방식으로 바꾸고 싶다. $("#f1).attr("method", "get");
			//폼 태그 보낸다.
			$("#f1").submit();
			
			//이 결과값을 받을 컨트롤러.
		})
		
		//수정버튼 클릭했을 경우
		$("#btnModify").click(function(){
			$("#f1").attr("action", "modifyPage");
			$("#f1").attr("method", "get");
			$("#f1").submit();
		})
		
		
		
		
		// ========================== 댓글 ==========================
		$("#btnReplyAdd").click(function(){
		
		//댓글 등록할 때 => 컨트롤러 안에 bno(위에 이미 있음), replyer, replytext 필요.
		var replyer = $("#newReplyWriter").val();
		var replytext = $("#newReplyText").val();
			
		//post 방식에서는 body에 실어서보내야 되기 때문에 변수로 만들어줌. {키:값, 키: 값, ..}
		var jsonBody = {bno: bno, replyer: replyer, replytext: replytext};
			
			//컨트롤러에 RequestBody(RestControlle에서난 RequestBody 사용함!) 있는 경우 => headers, JSON.stringify 반드시 사용해야 함.
			$.ajax({
				url: "${pageContext.request.contextPath}/replies/",
				type: "post",
				//stringify 사용할 때 headers도 같이 사용해야 함.
				headers:{
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": "POST"
				},
				
				//컨트롤러에 RequestBody가 있는 경우 stringify를 사용해야 함.
				//"{bno: bno, replyer: replyer, replytext: replytext}" String 방식으로 보내줌.
				data: JSON.stringify(jsonBody),	
				//컨트롤러에서 register return 값이 String이므로 text로 받아야함.
				dataType: "text",
				success: function(json){
					console.log(json);	
						
					if(json == "success"){
						alert("등록하였습니다.");		
						//등록한 후 댓글창 새로고침되도록 한다.
						getPageList(1);
						//작성 후 내용 비우기
						$("#newReplyWriter").val("");
						$("#newReplyText").val("");
					}
				}
			})
		})
		
		
		// 댓글 삭제(동적으로 버튼 생기니까 on 사용해야 함)
		$(document).on("click", ".btnDelete", function(){
			//삭제할 때 rno 필요하니까 가져온다.
			var rno = $(this).parents(".replyLi").attr("data-rno");
			
			$.ajax({
				url: "${pageContext.request.contextPath}/replies/" + rno,
				type: "delete",
				dataType: "text",
				success: function(json){
					console.log(json);
					
					if(json == "Success"){
						alert("삭제되었습니다.");
					}
					//리스트 갱신시킨다.
					getPageList(1);
				}
			})
		})
		
		
		// ====== 페이지 이동 : 동적으로 버튼 생기니까 on 사용해야 함. ======
		$(document).on("click", ".pagination a", function(e){
			 e.preventDefault();	//클릭 차단
			 
			 //내가 몇 번째 클릭하는지 어떻게 알지? => 일단 페이지 번호를 가지고 온다. <a>안에 페이지 번호 있으니까 this
			 var num = $(this).text();
			 getPageList(num);	
		})
		
		
		// ====== 댓글에 있는 수정버튼 : 동적으로 버튼 만들어지까 on 사용해야 함. ======
		$(document).on("click", ".btnModify", function(){
			//rno 가져오기
			var rno = $(this).attr("data-rno");
			$("#btnReplyMod").attr("data-rno", rno);
			
			//댓글 내용 가지고오기
			var replytext = $(this).parent().prev().html();
//			console.log(text);
			$("#replytext").val(replytext);
		})
		
		$(document).on("click", "#btnReplyMod", function(){
			var rno = $("#btnReplyMod").attr("data-rno");
			var replytext = $("#replytext").val();
			var jsonBody = {replytext: replytext};
			
			//ajax로 보냄.
			$.ajax({
				url: "${pageContext.request.contextPath}/replies/" + rno,
				type: "put",
				//보낼 데이터 => int rno와 vo(RequestBody있으니까 headers, stringify 필요)
				headers:{
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": "PUT"
				},
				data: JSON.stringify(jsonBody),	
				dataType: "text",
				success: function(json){
					console.log(json);
					
					if(json == "Success"){
						alert(rno + " 수정되었습니다.");
					}
					
					//수정되고 나서 창 안보이게 처리한다.
					$("#modifyModal").modal("hide");
					
					//리스트 갱신시킨다.
					getPageList(1);
				}
				
			})
		})
		
	})
</script>
<%@ include file="../include/footer.jsp" %>