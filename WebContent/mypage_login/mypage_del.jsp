<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<script type="text/javascript" src="./js/jquery.js"></script>
<jsp:include page="../include/header.jsp" />

<script>
	function delCheck() {
		
		var fm = document.frm;
		
		if(fm.delete_id.value.trim()==""){
			alert("아이디를 입력해주세요!");
			fm.delete_id.focus();
			return false;
	}else if(fm.delete_pwd.value==""){
		alert("비밀번호를 입력해주세요.");
		fm.delete_pwd.focus();
		return false;
	}else if(fm.delete_repwd.value==""){
		alert("비밀번호확인를 입력해주세요.");
		fm.delete_repwd.focus();
		return false;
	}else if(fm.delete_pwd.value != fm.delete_repwd.value){
		alert("비밀번호가 일치하지 않습니다.");
		fm.delete_repwd.value="";
		fm.delete_repwd.focus();
		return false;
	}else if(fm.delete_email.value==""){
		alert("이메일주소를 입력해주세요.");
		fm.delete_email.value="";
		fm.delete_email.focus();
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
     <a href="member_mypage_list.net">마이페이지</a>
    </li>
    <li>
     <a href="member_mypage_del.net"><strong>회원탈퇴</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->  

<!-- 카테고리 S -->
  <div class="menupackage">
   <div class="listtopimg">
    <p class="banner"></p>
   </div>
   <div class="titleArea">
    <h2><span>마이페이지</span></h2>
   </div>
   <ul class="menucategory">
    <li style="display:;"><a href="member_mypage_edit.net">회원정보수정</a></li>
    <li style="display:;"><a href="member_mypage_del.net"><strong style="color: #222;">회원탈퇴</strong></a></li>
    <li style="display:;"><a href="member_mypage_order.net">주문내역</a></li>
   </ul>
  </div>
<!-- 카테고리 E -->

<!-- 회원탈퇴 S -->
  <div class="d1-join">
   <div class="titleArea center">
    <h2>회원탈퇴</h2>
   </div>
   <form action="memeber_del_ok.net" id="frm" name="frm" method="post" onsubmit="return delCheck();">
    <div class="member-join">
     <h3>탈퇴기입내용</h3>
     <div class="ec-base-table typeWrite">
      <table border="1">
       <colgroup>
        <col style="width: 200px;">
        <col style="width: auto;">
       </colgroup>
       <tbody>
        <tr>
         <th scope="row">
         	아이디
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td class="placeholder">
          <input type="text" id="delete_id" name="delete_id"
          fw-filter="isFill&isFill&isMin[4]&isMax[16]&isIdentify"
          fw-label="아이디" class="inputTypeText" readonly value="${mbean.id}">
         </td>
        </tr>
        <tr>
         <th>
         	이메일
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td>
          <input id="delete_email" name="delete_email" fw_filter="isFill&isEmail" type="text" placeholder=" 이메일을 입력해주세요!">
          <span id="emailMsg"></span>
         </td>
        </tr>
        
       </tbody>
      </table>
     </div>
    </div>
    
    <div class="member-join">
     <h3>비밀번호를 입력하시면 탈퇴가 완료됩니다.</h3>
     <div class="ec-base-table typeWrite">
      <table border="1">
       <colgroup>
        <col style="width: 200px;">
        <col style="width: auto;">
       </colgroup>
       <tbody>
        <tr>
         <th scope="row">
         	비밀번호
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td class="placeholder">
          <input type="password" id="delete_pwd" name="delete_pwd" autocomplete="off"
          fw-filter="isFill&isFill&isMin[4]&isMax[16]&isIdentify" maxlength="16"
          fw-label="비밀번호" class="inputTypeText" placeholder=" 비밀번호를 입력해주세요!">
          <label style="display: none;">(영문 대소문자/숫자/특수문자 중 3가지 이상 조합, 8자~16자)</label>
         </td>
        </tr>
        <tr>
         <th scope="row">
         	비밀번호 확인
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td>
          <input type="password" id="delete_repwd" name="delete_repwd" autocomplete="off"
          fw-filter="isFill&isFill&isMin[4]&isMax[16]&isIdentify" maxlength="16"
          fw-label="비밀번호 확인" class="inputTypeText" placeholder=" 비밀번호 확인을 입력해주세요!">
          <span id="pwdConfirmMsg"></span>
         </td>
        </tr>
        
       </tbody>
      </table>
     </div>
     <div class="ec-base-button gColumn">
     <button type="reset" class="btn_JoinCancle">취소</button>
      <button type="submit" class="btn_JoinSubmit">회원탈퇴</button>
     </div>
    </div>
   </form>
  </div>
<!-- 회원탈퇴 E -->


</div> <%-- contents --%>
</div> <%-- -frame --%>
<jsp:include page="../include/footer.jsp" />