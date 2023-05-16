<%@page import="java.text.DecimalFormat"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../admin_include/top_admin.jsp"%> 
<%--
<%
	request.setCharacterEncoding("UTF-8");
	String searchId = request.getParameter("searchId");
	
	OrderDao odao = OrderDao.getInstance();
	ArrayList<OrderBean> list = null;
	
	if(searchId == null){
		list = odao.getAllOrder(); 
		System.out.println(list.size() + "개1");
	}else{
		list = odao.getAllOrderById(searchId);
		System.out.println(list.size() + "개2");
	}
%>
 --%>
 <div class="OrderList">
<form method="get" action="admin_order.net">
 <table id="ManageSearch">
  <tr>
   <%-- <td></td>
   <td></td>--%>
   <td style="display:none;">
    <select name="find_field">
    <option value="name" selected
    <c:if test="${find_field == 'o.name'}">${'selected'}</c:if>>
          상품명</option>   
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
	<c:if test="${!empty olist}">
	<c:forEach var="o" items="${olist}">
	<tr>
		<td>${o.no}</td>
		<td>${o.id}</td>
		<td>${o.name}</td>
		<td>${o.opname}</td>
		<td><fmt:formatNumber pattern="###,###" value="${o.price}"/>원</td>
		<td>${o.qty}</td>
		<td><fmt:formatNumber pattern="###,###" value="${o.qty * o.price}"/>원</td>
		<td>${o.rv_zip} ${o.rv_addr1} ${o.rv_addr2}</td>		
		<td>${o.receiver}</td>
		<td>${o.time}</td>
		<td style="height: 70px;">
		<c:set var="fullPath" value="/HueCamping/admin/product_images/${o.mainimgn}" />
			<img src="${fullPath}" style="max-width: 70px; max-height:70px;">
		</td>
		<td>
		<c:url var="deleteUrl" value="orderDelete.net">
			<c:param name="pno" value="${o.pno}" />
		</c:url>
		<a href="${deleteUrl}">삭제</a></td>
	</tr>
	</c:forEach>
	</c:if>
</table>

<%-- 검색 전후 페이징 --%>
  <div id="bList_paging">
   <%--검색 전 페이징 --%>
   <c:if test="${(empty find_field) && (empty find_name)}"> <%--검색필드와 검색어가 없는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="admin_order.net?page=${page-1}">[이전]</a>&nbsp;
     </c:if>
     
     <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="admin_order.net?page=${a}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>
       
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="admin_order.net?page=${page+1}">[다음]</a>
    </c:if>
   </c:if>
   
   <%-- 검색 후 페이징 --%>
   <c:if test="${(!empty find_field) && (!empty find_name)}"> <%--검색필드와 검색어가 있는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="admin_order.net?page=${page-1}&find_field=${find_field}&find_name=${find_name}">[이전]</a>&nbsp;
     </c:if> 
      
   <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="admin_order.net?page=${a}&find_field=${find_field}&find_name=${find_name}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>      
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="admin_order.net?page=${page+1}&find_field=${find_field}&find_name=${find_name}">[다음]</a>
    </c:if>
   </c:if> 
   </div>