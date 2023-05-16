<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../admin_include/top_admin.jsp"%> 

<div class="ProductList">
<form method="get" action="admin_product.net">
 <table id="ManageSearch">
  <tr>
   <%--<td></td>
   <td></td>--%>
   <td style="display:none;">
    <select name="find_field">
     <option value="name" selected
     <c:if test="${find_field == 'pbean.name'}">${'selected'}</c:if>>상품명</option>
    </select>
   </td>
   <td>
    <input class="Managesearcharea" type="search" name="find_name" value="${find_name}" placeholder="상품명 입력">
    <input class="Managesearchbtn" type="submit" value="검색">
   </td>
  </tr>
 </table>
</form>
</div>

<table class="productManage">
 <caption>
  <strong>상품관리</strong>
 </caption>
 <tr>
  <td colspan="9"><button class="product_reg" onClick="location.href='prodInsertForm.net'">상품등록</button></td>
 </tr>
 <tr class="ManageMenu">
  <th>번호</th>
  <th>대분류</th>
  <th>소분류</th>
  <th>상품명</th>
  <th>정가</th>
  <th>할인가</th>
  <th></th>
  <th></th>
  <th>상품이미지</th>
 </tr>
	<c:forEach var="pbean" items="${plist}">
	<tr class="product_manage_list">
		<td>${pbean.no}</td>
		<td>${pbean.lcname}</td>
		<td>${pbean.scname }</td>
		<td><a href="getProduct_detail_ok.net?no=${pbean.no}">${pbean.name}</a></td>
		<td><fmt:formatNumber pattern="###,###" value="${pbean.oriprice}"/>원</td>
		<td><fmt:formatNumber pattern="###,###" value="${pbean.discprice}"/>원</td>
		<td><a href="#?pno=${pbean.no}">수정</a></td>
		<td><a href="prodDelete.net?pno=${pbean.no}">삭제</a></td>
		<td>
			<c:set var="rContext" value="/HueCamping/admin/product_images/"/>
			<c:set var="fullPath" value="${rContext}${pbean.mainImgN}" />
			<img src="${fullPath}" style="max-height: 70px; max-width: 70px;">		
		</td>
	</tr>
	</c:forEach>
</table>

<%-- 검색 전후 페이징 --%>
  <div id="bList_paging">
   <%--검색 전 페이징 --%>
   <c:if test="${(empty find_field) && (empty find_name)}"> <%--검색필드와 검색어가 없는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="admin_product.net?page=${page-1}">[이전]</a>&nbsp;
     </c:if>
     
     <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="admin_product.net?page=${a}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>
       
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="admin_product.net?page=${page+1}">[다음]</a>
    </c:if>
   </c:if>
   
   <%-- 검색 후 페이징 --%>
   <c:if test="${(!empty find_field) && (!empty find_name)}"> <%--검색필드와 검색어가 있는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="admin_product.net?page=${page-1}&find_field=${find_field}&find_name=${find_name}">[이전]</a>&nbsp;
     </c:if> 
      
   <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="admin_product.net?page=${a}&find_field=${find_field}&find_name=${find_name}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>      
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="admin_product.net?page=${page+1}&find_field=${find_field}&find_name=${find_name}">[다음]</a>
    </c:if>
   </c:if> 
   </div>