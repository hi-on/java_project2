<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<jsp:include page="../include/header.jsp" />
<script src="../js/jquery.js"></script>

<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="member_main.net">Home</a>
    </li>
    <li>
     <a href="noticeCenter_review.net">상품문의</a>
    </li>
    <li>
     <a href="product_QnA_cont.jsp"><strong>상품문의 등록</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

  <div class="guideLink_title">상품문의</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="notice.jsp">공지사항</a></li>
     <li><a href="product_QnA.jsp"><strong style="color: #222;">상품문의</strong></a></li>
     <li><a href="noticeCenter_FAQ.net">FAQ</a></li> 
     <li><a href="review.jsp">포토후기</a></li>  
    </ul>
   </div>
   
<%-- 본문 --%>
<div class="product_QnA_info">
 
  <a href="#상품페이지">
   <img class="product_QnA_thumbnail" src="../images/table.jpg" alt="상품 이미지">
  </a>
 
 <ul class="product_QnA_infocont">
  <li>
   <a href="#">홈파티 원형 LED 컵받침 테이블웨어 LED컵코스터</a>
  </li>
  <li class="price txt">5000원</li>
  <li class="seginfo_btn">
   <a href="#">상세보기</a></li>
 </ul> <!-- product_QnA_infocont E -->
</div> <!-- product_QnA_info E -->

<form>
<div class="product_QnA_cont">
 <table class="product_QnA_cont_table">
  <tr style="border-top:1px solid gray;">
   <th class="proQnAth" scope="row">제목</th>
   <td> ${title}야인시대 </td>
  </tr>
  <tr style="border-bottom:1px solid gray;">
   <th class="proQnAth" scope="row">아이디</th>
   <td>${id}</td>
  </tr>
  <tr>
   <td colspan="2">작성일:${date}</td>
  </tr>
  <tr >
   <td class="pro_QnA_contcols" colspan="2">
    ${cont}관리자용 사딸라!!사딸라!!
   </td>
  </tr>
 </table>
</div> <!-- product_QnA_cont E -->
</form>

<div class="okcancellist_btn"> 
 <input class="product_QnA_btn" type="submit" value="등록" onclick="#">
 <input class="product_QnA_btn" type="button" value="삭제" onclick="history.back();">
</div> <!-- okcancellist_btn E -->

<form>
<div class="repleplace">
 <textarea rows="10" cols="200">댓글창</textarea>
</div>

<div class="repleOK">
 <input class="product_QnA_btn" type="submit" value="댓글 등록" onclick="#내용보내기"> 
</div>
</form>

</div> <!-- content E -->
</div> <!-- frame E -->
<jsp:include page="../include/footer.jsp" />