<%@page import="net.hue.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="icon" type="image/png" href="./images/logomain.png">
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script type="text/javascript">
function linkToOpener(URL){
	if (window.opener && !window.opener.closed)
	window.opener.location = URL;
	window.close();
}
	
function pwdview(){
	var mem_pwd = document.getElementById('mem_pwd');
	
	if(mem_pwd.style.display != 'none'){
		mem_pwd.style.display = 'none';
	}else{
		mem_pwd.style.display = 'block';
	}
}
</script>
</head>
<body id="find_pop">
 <form id="member_search" name="idsearch" method="post">
   <div class = "container">
    <div class = "found-success">
     <div class ="found-id">
      <h4>회원님의 비밀번호는 </h4> 
      <h3>
       ${fn:substring(mempwd,0,5)}
       <c:forEach begin="1" end="${fn:length(mempwd)-5}">
       *
       </c:forEach>
      </h3>
      <h3 id="mem_pwd" style="display: none;">
       ${mempwd}
      </h3>
      <h4>입니다</h4>
	 </div>
    </div>
    <div class = "found-login">
     <input type="button" class="btn_FindCancle" value="전체보기" onClick ="pwdview()"/>
     <input type="button" class="btn_FindSubmit" value="로그인" onClick ="javascript:linkToOpener('member_loin.net');"/>
    </div>
   </div>

 </form>
</body>
</html>