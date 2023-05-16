<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<jsp:include page="../include/header.jsp" />

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
   <form action="" id="frm" name="frm" method="post">
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
          fw-label="아이디" class="inputTypeText" placeholder="(영문소문자/숫자, 4~16자)">
          <span id="idMsg" class="error">아이디를 입력해 주세요.</span>
          <%-- 중복확인 버튼 없이 여기서 바로 유효성 검증 힘들면 그냥 확인버튼으로 교체  --%>
          <label style="display: none;">(영문소문자/숫자, 4~16자)</label>
         </td>
        </tr>
        <tr>
         <th scope="row">
         	비밀번호
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td class="placeholder">
          <input type="password" id="delete_pwd" name="delete_pwd" autocomplete="off"
          fw-filter="isFill&isFill&isMin[4]&isMax[16]&isIdentify" maxlength="16"
          fw-label="비밀번호" class="inputTypeText" placeholder="(영문 대소문자/숫자/특수문자 중 3가지 이상 조합, 8자~16자)">
          <div class="typeUpper" style="display: none;">
           <div class="content">
            <strong class="txtWarn">※ 비밀번호 입력 조건</strong>
            <ul class="typeDash gBlank10">
             - 대소문자/숫자/특수문자 중 3가지 이상 조합, 8자~16자
             <br>
             - 입력 가능 특수문자 
             <br>
             ~ ` ! @ # $ % ^ ( ) _ - = { } [ ] | ; : < > , . ? /
             <br>
             - 공백 입력 불가능                                    
            </ul>
           </div>
          </div>
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
          fw-label="비밀번호 확인" class="inputTypeText" placeholder="(영문 대소문자/숫자/특수문자 중 3가지 이상 조합, 8자~16자)">
          <span id="pwdConfirmMsg"></span>
         </td>
        </tr>
        <tr>
         <th>
         	이메일
         	<img alt="필수" src="https://img.echosting.cafe24.com/skin/base/common/ico_required_blue.gif">
         </th>
         <td>
          <input id="delete_email" name="delete_email" fw_filter="isFill&isEmail" type="text">
          <span id="emailMsg"></span>
         </td>
        </tr>
        
       </tbody>
      </table>
     </div>
     <div class="ec-base-button gColumn">
      <a href="/" class="btn_JoinCancle sizeL">취소</a>
      <a href="#" class="btn_JoinSubmit sizeL" onclick="">회원탈퇴</a>
     </div>
    </div>
   </form>
  </div>
<!-- 회원탈퇴 E -->


</div> <%-- contents --%>
</div> <%-- -frame --%>
<jsp:include page="../include/footer.jsp" />