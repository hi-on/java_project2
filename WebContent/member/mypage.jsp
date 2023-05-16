<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<jsp:include page="../include/header.jsp" />

<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="../index.jsp">Home</a>
    </li>
    <li>
     <a href="cart.jsp"><strong>마이페이지</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->  
 
 
 <!-- 본문 내용 S -->
<div class="mypage_login">
 <h1>로그인</h1>
 <form>
  <fieldset>
   <input class="mypage_text" type="text" value="아이디">
   <input class="mypage_text" type="text" value="비밀번호">
   <input class="mypage_okbtn" type="button" value="확인" onclick="return logincheck();">
   <ul>
    <li class="findIdpwd_join"><a href="#" >아이디 찾기</a></li>
    <li class="findIdpwd_join"><a href="#" >비밀번호 찾기</a></li>
    <li class="findIdpwd_join"><a href="join.jsp" >회원가입</a></li>
   </ul>
   
   <table class="conv_login">
    <tr>
     <td class="login_img">
      <img src="../images/naver.png" width="50"
      height="50" ></td>
     <td>네이버 로그인</td>
   </table>
   <table class="conv_login">
    <tr>
     <td class="login_img"><img src="../images/google.png" width="50"
      height="50" ></td>
     <td>구 글 로그인</td>
   </table>
   <table class="conv_login">
    <tr>
     <td class="login_img"><img src="../images/kakaopay.png" width="50"
      height="50" ></td>
     <td>카카오 로그인</td>
   </table>
  </fieldset>
 </form>
</div> 
 
 <div class="mypage_notjoin_login">
  <h1>비회원 주문조회</h1>
  <form>
   <input class="mypage_text" type="text" value="이름">
   <input class="mypage_text" type="text" value="주문번호">
   <input class="mypage_text" type="text" value="비밀번호">
   <h4>비회원인 경우 주문조회만 가능</h4>
   <input class="mypage_okbtn" type="button" value="확인">
  </form>
 </div>
 <!-- 본문 내용 E -->
 
 
 </div> <%-- contents --%>
</div> <%-- -frame --%>
<jsp:include page="../include/footer.jsp" />