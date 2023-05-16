<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<script src="../js/jquery.js"></script>
<jsp:include page="../include/header.jsp" />
<script>
 function okcancellist_btn_check(){//등록 버튼 내용 없을 때 경고창
	if($.trim($('#board_title').val())==''){
		 alert('제목을 입력하세요!');
		 $('#board_title').val('').focus();
		 return false;
	}
	if($.trim($('#board_name').val())==''){
		 alert('작성자를 입력하세요!');
		 $('#board_name').val('').focus();
		 return false;
	}
	if($.trim($('#board_pwd').val())==''){
		 alert('비밀번호를 입력하세요!');
		 $('#board_pwd').val('').focus();
		 return false;
	}
	if($.trim($('#board_cont').val())==''){
		 alert('글내용을 입력하세요!');
		 $('#board_cont').val('').focus();
		 return false;
	}
 }
</script>
<%-- 게시물 내용 보기 --%>
<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="../main/index.jsp">Home</a>
    </li>
    <li>
     <a href="noticeCenter_product_QnA.net"><strong>QnA</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

  <div class="guideLink_title">QnA</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="noticeCenter_notice.net">공지사항</a></li>
     <li><a href="noticeCenter_product_QnA.net"><strong style="color: #222;">QnA</strong></a></li>
     <li><a href="noticeCenter_FAQ.net">FAQ</a></li> 
     <li><a href="noticeCenter_review.net">포토후기</a></li>  
    </ul>
   </div>
   
<%-- 본문 --%>
<div id="bCont_wrap">
 <table id="bCont_t">
  <tr>
   <th class="proQnAth" scope="row" >제목</th>
   <td>${bc.board_title}</td>
  </tr>
  <tr >
   <th class="proQnAth" scope="row" >작성자</th>
   <td>${bc.board_name}</td>
  </tr>
  <tr >
   <td colspan="2" style="font-weight:bold; border-bottom: none;">작성일:${bc.board_date}</td>
  </tr>
  <tr>
   <td colspan="2" class="b_cont">${bc.board_cont}</td>
  </tr>
 </table>

<div id="bCont_btn">
 <input class="product_QnA_btn" type="button" value="수정" onclick="location=
	   'noticeCenter_product_QnA_view.net?board_no=${bc.board_no}&page=${page}&state=edit';" />
 <input class="product_QnA_btn" type="button" value="삭제" onclick="location=
	   'noticeCenter_product_QnA_view.net?board_no=${bc.board_no}&page=${page}&state=del';" />	   
 <input class="product_QnA_btn" type="button" value="목록" onclick="location='noticeCenter_product_QnA.net?page=${page}';" />
 
</div>
</div>

<%--댓글 창(답변 창) 구역--%>
<div id="bReply_wrap">
<%-- <form method="post" action="product_QnA_reply.jsp"
onsubmit="return okcancellist_btn_check();">
--%>

<%-- <input type="hidden" name="board_ref" value="${bc.board_ref}" >
<input type="hidden" name="board_step" value="${bc.board_step}" >
<input type="hidden" name="board_level" value="${bc.board_level}" >
--%>

<input type="hidden" name="page" value="${page}" >

<table id="bReply_t">
 <tr>
  <th class="proQnAth" width="10%">작성자</th>
  <th class="proQnAth" width="80%">댓글내용</th>
  <th class="proQnAth" width="10%">작성일</th> 
 </tr>

 
 <c:forEach var="rb" items="${replist}"> 
 <tr> 
  <td align="center" width="10%">${rb.reply_name}</td>
  <td width="80%" style="padding-left: 10px;">${rb.reply_cont}</td>
  <td align="center" width="10%">${rb.reply_date}</td> 
</tr>

 </c:forEach>

</table> 
 
 <input class="product_QnA_btn" type="button" value="댓글" onclick="location=
	  'noticeCenter_product_QnA_view.net?board_no=${bc.board_no}&page=${page}&state=reply';" />

<%-- <tr>
  <th>작성자</th>
  <td><input name="reply_name" id="reply_name" size="14" ></td>
 </tr>
 
 <tr>
  <th>댓글 제목</th>
  <td><input name="reply_title" id="reply_title" size="20" ></td>  
 </tr>
 
 <tr>
  <th>비밀번호</th>
  <td><input type="password" name="reply_pwd" id="reply_pwd" size="14" ></td>
 </tr>
 
 <tr>
  <th>댓글 내용</th>
  <td><textarea name="reply_cont" id="reply_cont" rows="10" cols="30"></textarea></td>
 </tr>--%>

<%-- <div id="replybtn">
 <input type="submit" value="댓글 등록">
</div>--%>


<%-- </form>--%>

</div>




</div> 
</div>
<jsp:include page="../include/footer.jsp" />