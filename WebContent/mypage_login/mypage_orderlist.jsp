<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
     <a href="member_mypage_order.net"><strong>주문내역</strong></a>
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
    <li style="display:;"><a href="member_mypage_order.net"><strong style="color: #222;">주문내역</strong></a></li>
   </ul>
  </div>

<table class="orderManage">
 <caption>
  <strong>주문접수내역</strong>
 </caption>
 <tr class="ManageMenu">
  <th>번호</th>
  <th>주문자ID</th>
  <th>상품명</th>
  <th>옵션명</th>
  <th>단가</th>
  <th>수량</th>
  <th>총계</th>
  <th>배송지</th>
  <th>수령자명</th>
  <th>주문시간</th>
  <th>상품이미지</th>
  <th></th>
 </tr>
	<fmt:formatNumber pattern="###,###" value="${yourNumber}" />
	<c:if test="${!empty list}">
	<c:forEach var="o" items="${list}">
	<tr>
		<td>${o.no}</td>
		<td>${o.id}</td>
		<td>${o.name}</td>
		<td>${o.opname}</td>
		<td><fmt:formatNumber pattern="###,###" value="${o.price}"/>원</td>
		<td>${o.qty}</td>
		<td><fmt:formatNumber pattern="###,###" value="${o.qty * o.price}"/>원</td>
		<td>${o.rv_zip} ${o.rv_addr1} ${o.rv_addr2}</td>		<td>${o.receiver}</td>
		<td>${o.time}</td>
		<td>
	<c:set var="fullPath" value="/HueCamping/admin/product_images/${o.mainimgn}" />
			<img src="${fullPath}" style="max-width: 70px; max-height: 70px;">
		</td>
		<td>
		<c:url var="deleteUrl" value="orderDelete.net">
  <c:param name="pno" value="${o.pno}" />
</c:url>
<a href="${deleteUrl}">삭제</a>
</td>
	</tr>
	</c:forEach>
</c:if>
</table>
<jsp:include page="../include/footer.jsp" />