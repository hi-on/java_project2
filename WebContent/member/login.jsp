<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<jsp:include page="../include/header.jsp" />
<script type="text/javascript" src="./js/jquery.js"></script>

<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="member_main.net">Home</a>
    </li>
    <li>
     <a href="member_loin.net"><strong>로그인</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->  
 
<script> /* 로그인 검증 */
	function loginCheck() {
	
	var lg = document.loginform;
	
	if(lg.id.value.trim()==""){
		alert("아이디를 입력해주세요");
		lg.id.focus();
		return false;
	}else if(lg.password.value==""){
		alert("비밀번호를 입력해주세요");
		lg.password.focus();
		return false;
	}
}

// 아이디/비번 찾기 팝업
	function find_ID(){
		var popupWidth = 700;
		var popupHeight = 600;

		var popupX = Math.ceil(( window.screen.width - popupWidth )/2);
		var popupY = Math.ceil(( window.screen.height - popupHeight )/2); 

		window.open('member_find_ID.net', '아이디 찾기', 'width=' + popupWidth + ',height=' + popupHeight + ',left='+ popupX + ', top='+ popupY);
    }
    
	function find_pwd(){
		var popupWidth = 700;
		var popupHeight = 600;

		var popupX = Math.ceil(( window.screen.width - popupWidth )/2);
		var popupY = Math.ceil(( window.screen.height - popupHeight )/2); 

		window.open('member_find_pwd.net', '비밀번호 찾기', 'width=' + popupWidth + ',height=' + popupHeight + ',left='+ popupX + ', top='+ popupY);
    }
</script>
 
<!-- 본문 내용 S -->
 <div class="d1_login">
  <div class="login_page">
   <div class="titleArea center">
    <h2>로그인</h2>
   </div>
   <form id="loginform" name="loginform" action="member_login_ok.net" method="post" onsubmit="return loginCheck();">
    <fieldset>
     <input class="login_field" type="text" id="id" name="id" placeholder="아이디" >
     <input class="login_field" type="password" id="password" name="password" placeholder="패스워드" autocapitalize="none" >
     <input class="login_okbtn" type="submit" value="로그인">
     <div class="findIdpwd_join">
      <a href="javascript:find_ID()" class="find_pop">아이디 찾기</a>
      <a href="javascript:find_pwd()" class="find_pop">비밀번호 찾기</a>
      <a href="member_join.net" >회원가입</a>
     </div>
   
     <div class="sns_login">
      <table class="conv_login">
       <tr>
        <td class="login_img">
         <a href="#none" class="snsLoginBtn btnNaver" onclick="">
          <span>네이버 로그인</span>
         </a>
        </td>
       </tr>
      </table>
      <table class="conv_login">
       <tr>
        <td class="login_img">
         <a href="#none" class="snsLoginBtn btnGoogle" onclick="">
          <span>구글 로그인</span>
         </a>
        </td>
       </tr>
      </table>
      <table class="conv_login">
       <tr>
        <td class="login_img">
         <a href="#none" class="snsLoginBtn btnKakao" onclick="">
          <span>카카오 로그인</span>
         </a>
        </td>
       </tr>
      </table>
     </div>
    </fieldset>
   </form>
  </div> 
  
  <div class="non_member_order">
   <div class="titleArea center">
    <h2>비회원 주문조회</h2>
   </div>
   <form>
    <input class="non_member_text" type="text" placeholder="이름">
    <input class="non_member_text" type="text" placeholder="주문번호">
    <input class="login_okbtn" type="button" value="확인">
    <h4>비회원인 경우 주문조회만 가능 합니다.</h4>
   </form>
  </div>
  </div>
 <!-- 본문 내용 E -->

 
 </div> <%-- contents --%>
</div> <%-- -frame --%>
<jsp:include page="../include/footer.jsp" />