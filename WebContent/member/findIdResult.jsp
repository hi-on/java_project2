<%@page import="net.hue.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</script>
</head>
<body id="find_pop">
 <form id="member_search" name="idsearch" method="post">
  <c:choose>
  <c:when test="${memid ne null}">
   <div class = "container">
    <div class = "found-success">
     <div class ="found-id">
      <h4>회원님의 아이디는</h4>  
      <h3><strong>${memid}</strong></h3>
      <h4>입니다!</h4>
     </div> 
    </div>
    <div class = "found-login">
     <input type="button" class="btn_FindCancle" value="비밀번호 찾기" onClick ="location='member_find_pwd.net'"/>
     <input type="button" class="btn_FindSubmit" value="로그인" onClick ="javascript:linkToOpener('member_loin.net');"/>
    </div>
   </div>
  </c:when>
  
  <c:otherwise>
   <div class = "container">
    <div class = "found-fail">
     <h4>등록된 정보가 없습니다!</h4>  
    </div>
    <div class = "found-login">
     <input type="button" class="btn_FindCancle" value="다시 찾기" onClick="history.back()"/>
     <input type="button" class="btn_FindSubmit" value="회원가입" onClick="javascript:linkToOpener('member_join.net');"/>
    </div>
   </div>
  </c:otherwise>
  </c:choose>
  
 </form>
</body>
</html>