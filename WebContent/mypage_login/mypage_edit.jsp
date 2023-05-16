<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<jsp:include page="../include/header.jsp" />


<script type="text/javascript" src="./js/jquery.js"></script>
<script>/* 회원가입 검증 script */
	function editCheck() {
		
	var fm = document.frm;
	
	var pw = $("#password").val();
	var num = pw.search(/[0-9]/g);
	var eng = pw.search(/[a-z]/ig);
	var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	
	//아이디
	if(fm.id.value.trim()==""){
		alert("아이디를 입력해주세요!");
		fm.id.focus();
		return false;
	}else if(fm.id.value.length<4 || fm.id.value.length>16){
		alert("아이디를 4~16자 사이로 입력하세요!");
		fm.id.focus();
		return false;
	}else if(fm.password.value==""){
		alert("비밀번호를 입력해주세요.");
		fm.password.focus();
		return false;
	}else if(fm.password.value.length<8 || fm.id.value.length>16){
		alert("비밀번호를 8~16자 사이로 입력하세요!.");
		fm.password.focus();
		return false;
	}else if(pw.search(/\s/) != -1){
		fm.password.focus();
		alert("비밀번호는 공백 없이 입력해주세요.");
		return false;
	}else if(num < 0 || eng < 0 || spe < 0 ){
		fm.password.focus();
		alert("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
		return false;
	}else if(fm.delete_repwd.value==""){
		alert("비밀번호 확인을 입력해주세요!");
		fm.delete_repwd.focus();
		return false;
	}else if(fm.password.value != fm.delete_repwd.value){
		alert("비밀번호가 일치하지 않습니다.");
		fm.delete_repwd.value="";
		fm.delete_repwd.focus();
		return false;
	}else if(fm.name.value==""){
		alert("이름을 입력해주세요.");
		fm.name.value="";
		fm.name.focus();
		return false;
	}else if(fm.zip.value==""){
		alert("주소를 입력해주세요.");
		return false;
	}else if(fm.addr2.value==""){
		alert("상세주소를 입력해주세요.");
		fm.addr2.value="";
		fm.addr2.focus();
		return false;
	}else if(fm.mobile2.value==""){
		alert("전화번호 중간자리를 입력해주세요.");
		fm.mobile2.value="";
		fm.mobile2.focus();
		return false;
	}else if(fm.mobile2.value.length<3 || fm.mobile2.value.length>4){
		alert("전화번호 중간자리는 3~4글자 사이로 입력해주세요.");
		fm.mobile2.value="";
		fm.mobile2.focus();
		return false;
	}else if(fm.mobile3.value==""){
		alert("전화번호 마지막자리를 입력해주세요.");
		fm.mobile3.value="";
		fm.mobile3.focus();
		return false;
	}else if(fm.mobile3.value.length<3 || fm.mobile3.value.length>4){
		alert("전화번호 마지막자리는 3~4글자 사이로 입력해주세요.");
		fm.mobile3.value="";
		fm.mobile3.focus();
		return false;
	}else if(fm.email.value==""){
		alert("이메일주소를 입력해주세요.");
		fm.email.value="";
		fm.email.focus();
		return false;
	}
}
	//아이디 중복검색
	function id_check(){
		$('#idcheck').hide();  // idcheck영역을 숨김
		$id=$.trim($('#id').val());
		if($id.length<4){
			$newtxt='<font color="red" size="2"><b>아이디는 4자 이상이어야 합니다.</b></font>';
			$('#idcheck').text('');  // idcheck 아이디 영역 문자 초기화
			$('#idcheck').show();  // idcheck 영역 보이게 함
			$('#idcheck').append($newtxt);  // idcheck 영역 문자 끝에 문자 추가
			$('#id').val('').focus();
			return false;
		}
		
		if($id.length>12){
			$newtxt='<font color="red" size="2"><b>아이디는 12자 이하여야 합니다.</b></font>';
			$('#idcheck').text('');  // idcheck 아이디 영역 문자 초기화
			$('#idcheck').show();  // idcheck 영역 보이게 함
			$('#idcheck').append($newtxt);  // idcheck 영역 문자 끝에 문자 추가
			$('#id').val('').focus();
			return false;
		}
		
		// 아이디를 4자 이상 12자 이하로 입력했을 때 정규표현식으로 유효성 검증
		if(!(validate_userid($id))){
			$newtxt='<font color="red" size="2"><b>아이디는 영문소문자, 숫자, _조합만 가능합니다.</b></font>';
			$('#idcheck').text('');  // idcheck 아이디 영역 문자 초기화
			$('#idcheck').show();  // idcheck 영역 보이게 함
			$('#idcheck').append($newtxt);  // idcheck 영역 문자 끝에 문자 추가
			$('#id').val('').focus();
			return false;
		}
	}

	// 아이디 정규표현식
	function validate_userid($id){
		var pattern = new RegExp(/^[a-z0-9_]+$/);  // 아이디를 영문소문자와 숫자, _조합만 가능하게 함.
		return pattern.test($id);  // 아이디 정규표현식 검사
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
     <a href="member_mypage_edit.net"><strong>회원정보수정</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->  
<!-- 우편번호 다음 API 링크  -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	/* var newJquery = $.noConflict(true); // 다른 라이브러리랑 겹칠때 이렇게 해주면 됨. */
	function openZipSearch() {
		new daum.Postcode({
			oncomplete: function(data) {
				jQuery('[name=zip]').val(data.zonecode); // 우편번호 (5자리)
				jQuery('[name=addr1]').val(data.address);
				jQuery('[name=addr2]').val(data.buildingName);
				jQuery('[name=addr2]').focus();
			}
		}).open();
	}
</script>
<!-- 카테고리 S -->
  <div class="menupackage">
   <div class="listtopimg">
    <p class="banner"></p>
   </div>
   <div class="titleArea">
    <h2><span>회원정보 수정</span></h2>
   </div>
   <ul class="menucategory">
    <li style="display:;"><a href="member_mypage_edit.net"><strong style="color: #222;">회원정보수정</strong></a></li>
    <li style="display:;"><a href="member_mypage_del.net">회원탈퇴</a></li>
    <li style="display:;"><a href="member_mypage_order.net">주문내역</a></li>
   </ul>
  </div>
<!-- 카테고리 E -->
 
 <!-- 회원정보수정 S -->
  <div class="d1-join">
   <div class="titleArea center">
    <h2>회원정보 수정</h2>
   </div>
   <form action="member_mypage_edit_ok.net" id="frm" name="frm" method="post" onsubmit="return editCheck();">
    <div class="member-join">
     <h3>수정정보</h3>
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
          <input type="text" id="id_mp" name="id"
          fw-label="아이디" class="inputTypeText" readonly value="${mbean.id}">
          <br><span id="idshow">아이디는 변경하실 수 없습니다!</span>
         </td>
        </tr>
        <tr>
         <th scope="row">
         	이름
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td class="placeholder">
          <span id="nameContents">
           <input type="text" name="name" id="name" maxlength="20" value="${mbean.name}">
          </span>
         </td>
        </tr>
        <tr>
         <th scope="row">
         	주소
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td>
          <input type="text" id="zip" name="zip" readonly value="${mbean.zip}"/>
          <input type="button" class="btnNormal" id="btnNormal" name="btnNormal" value="우편번호" onclick="openZipSearch()"></p>
          <input id="addr1" name="addr1" type="text" readonly value="${mbean.addr1}"/> 기본주소<br/>
          <input id="addr2" name="addr2" type="text"  value="${mbean.addr2}"/> 상세주소
         </td>
        </tr>
        <tr>
         <th scope="row">
         	휴대전화
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td>
          <div class="multiForm mobile">
           <select id="mobile1" name="mobile1">
            <option value="010">010</option>
            <option value="011">011</option>
           </select>
           -
           <input type="text" id="mobile2" name="mobile2" maxlength="4" value="${mbean.mobile2}">
           -
           <input type="text" id="mobile3" name="mobile3" maxlength="4" value="${mbean.mobile3}">
           <!--
           <button type="button" class="btnBasic gBlank5" id="btn_action_verify_mobile"
          onclick="">인증번호 받기</button>
          </div>  
          <div class="verify">
          <input type="text" id="verify_mobile" name="verify_mobile" maxlength="6">
           <button type="button" class="btnBasic gBlank5" id="btn_verify_mobile_confirm"
           onclick="">인증번호 확인</button>
          </div>
          -->
          </div>
         </td>
        </tr>
        <tr>
         <th>
         	이메일
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td>
          <input type="email" id="email" name="email" value="${mbean.email}">
         </td>
        </tr>
        <tr>
         <th>
         	성별
         </th>
         <td>
          <div class="multiForm gender">
           <select id="gender" name="gender">  
           	<option value="-">-</option>
            <option value="남">남</option>
            <option value="여">여</option>
           </select>
          </div>
         </td>
        </tr>
       </tbody>
      </table>
     </div>	
    </div>
    
    <div class="member-join">
     <h3>비밀번호를 입력하시면 수정이 완료됩니다.</h3>
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
          <input type="password" id="password" name="password" autocomplete="off"
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
      <button type="submit" class="btn_JoinSubmit">회원수정</button>
     </div>
    </div>
   </form>
  </div>
<!-- 회원정보수정 E -->
 
 
 
 
 
 </div> <%-- contents --%>
</div> <%-- -frame --%>
<jsp:include page="../include/footer.jsp" />