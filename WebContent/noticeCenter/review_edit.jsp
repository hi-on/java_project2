<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<jsp:include page="../include/header.jsp" />

<script src="../js/jquery.js"></script>
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
<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
<div id="expath" class="path">
   <ol>
    <li>
     <a href="member_main.net">Home</a>
    </li>
    <li>
     <a href="noticeCenter_review.net"><strong>포토후기</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

  <div class="guideLink_title">포토후기</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="noticeCenter_notice.net">공지사항</a></li>
     <li><a href="noticeCenter_product_QnA.net">QnA</a></li>
     <li><a href="noticeCenter_FAQ.net">FAQ</a></li> 
     <li><a href="noticeCenter_review.net"><strong style="color: #222;">포토후기</strong></a></li>   
    </ul>
   </div>
   
<%-- 본문 --%>

<div class="product_QnA_cont">
<form method="post" action="noticeCenter_review_edit.net?page=${page}" onsubmit="return okcancellist_btn_check();">
 <input type="hidden" name="board_no" value="${rc.board_no}">
 
 <table id="bCont_t">
  <tr>
   <th class="proQnAth" scope="row">제목</th>
   <td><input name="board_title" id="board_title" value="${rc.board_title}" /></td>
  </tr>
  
  <tr>
   <th class="proQnAth" scope="row">글쓴이</th>
   <td><input name="board_name" id="board_name" value="${rc.board_name}" /></td>
  </tr>
  
  <tr>
   <th class="proQnAth" scope="row">비밀번호</th>
   <td><input type="password" name="board_pwd" id="board_pwd" /></td>
  </tr> 
  
  <tr>
   <td class="pro_QnA_contcols" colspan="2">
    <textarea id="board_cont" name="board_cont">${rc.board_cont}</textarea>
   </td>
  </tr>
 </table>

<div class="okcancellist_btn">
 <input class="product_QnA_btn" type="submit" value="수정완료" />
 <input class="product_QnA_btn" type="reset" value="취소" onclick="$('#board_title').focus();"/>
 <input class="product_QnA_btn" type="button" value="목록" onclick="location='noticeCenter_review.net?page=${page}';">
</div>
        
</form>
</div>

<%-- 본문 --%>
 </div>
</div>
<jsp:include page="../include/footer.jsp" />













