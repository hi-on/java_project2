<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="icon" type="image/png" href="./images/logomain.png">
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script type="text/javascript">
 function win_close(){
	 window.close();
 }
 
 function pwd_search() { 
	 	var fm = document.pwdfindscreen;

	 	if (fm.id.value=="") {
			alert("아이디를 입력해주세요");
			fm.id.value="";
			fm.id.focus();
			return;
		}else if(fm.name.value==""){
			alert("이름을 입력해주세요.");
			fm.name.value="";
			fm.name.focus();
			return false;
		}else if(fm.email.value==""){
			alert("이메일주소를 입력해주세요.");
			fm.email.value="";
			fm.email.focus();
			return false;
		}

	 fm.method = "post";
	 fm.action = "member_find_pwd_result.net"; //넘어간화면
	 fm.submit();
	 
 }
</script>
</head>
<body id="find_pop">
 <div class="find_mem">
  <div class="titleArea center">
   <h2>비밀번호 찾기</h2>
  </div>
  <form id="frm" name="pwdfindscreen" method="post">
   <div class="find_mem_id">
    <h3>비밀번호는 가입시 입력하신 아이디, 이메일을 통해 찾으실 수 있습니다.</h3>
    <div class="ec-base-table typeWrite">
     <table border="1">
      <tbody>
      <tr>
         <th scope="row">
         	아이디
         </th>
         <td class="placeholder">
          <input type="text" id="id" name="id"
          fw-label="아이디" class="inputTypeText" placeholder="(영문소문자/숫자, 4~16자)">
         </td>
        </tr>
       <tr>
        <th scope="row">
        	이름
        </th>
        <td class="placeholder">
         <span id="nameContents">
          <input type="text" name="name" id="name" maxlength="20" placeholder="가입시 사용한 이름을 입력하세요!">
         </span>
        </td>
       </tr>
       <tr>
        <th>
        	이메일
        </th>
        <td>
         <input type="email" id="email" name="email" placeholder="가입시 사용한 이메일을 입력하세요!">
        </td>
       </tr>
      </tbody>
     </table>
    </div>
    <div class="ec-base-button gColumn">
     <button type="button" class="btn_FindCancle" onClick="win_close()">취소</button>
     <button type="button" class="btn_FindSubmit" onClick="pwd_search()">찾기</button>
    </div>
   </div>
  </form>
 </div>

</body>
</html>