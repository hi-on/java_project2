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
     <a href="member_mypage_list.net"><strong>마이페이지</strong></a>
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
    <li style="display:;"><a href="member_mypage_del.net">회원탈퇴</a></li>
    <li style="display:;"><a href="member_mypage_order.net">주문내역</a></li>
   </ul>
  </div>
<!-- 카테고리 E -->



</div> <%-- contents --%>
</div> <%-- -frame --%>
<jsp:include page="../include/footer.jsp" />