<%@page import="net.hue.bean.MemberBean"%>
<%@page import="net.hue.dao.MemberDao"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form name="f" class="OrderList" action="admin_user.net">
 <table id="ManageSearch">
  <tr>
   <td></td>
   <td></td>
   <td>
    <input class="Managesearcharea" type="search" name="searchId" value="${find_name}" placeholder="아이디 입력">
    <input class="Managesearchbtn" type="submit" value="검색">
   </td>
  </tr>
 </table>
</form>

<table class="orderManage">
 <caption>
  <strong>회원관리</strong>
 </caption>
 <tr class="ManageMenu">
  <th>번호</th>
  <th>ID</th>
  <th>비밀번호</th>
  <th>이름</th>
  <th>주소</th>
  <th>전화번호</th>
  <th>이메일</th>
  <th>성별</th>
  <th>가입상태</th>
  <th>가입날짜</th>
  <th>탈퇴날짜</th>
 </tr>
	<c:if test="${!empty ulist}">
	<c:forEach var="m" items="${ulist}">
	<tr class="product_user_list">
		<td>${m.no}</td>
		<td>${m.id}</td>
		<td>${m.password}</td>
		<td>${m.name}</td>
		<td>(&nbsp;${m.zip}&nbsp;)&nbsp;${m.addr1}&nbsp;${m.addr2}</td>
		<td>${m.mobile1}-${m.mobile2}-${m.mobile3}</td>
		<td>${m.email}</td>
		<td>${m.gender}</td>
		<td>${m.state}</td>
		<td>${m.memdate}</td>
		<td>${m.deldate}</td>
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
      <a href="admin_user.net?page=${page-1}">[이전]</a>&nbsp;
     </c:if>
     
     <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="admin_user.net?page=${a}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>
       
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="admin_user.net?page=${page+1}">[다음]</a>
    </c:if>
   </c:if>
   
   <%-- 검색 후 페이징 --%>
   <c:if test="${(!empty find_field) && (!empty find_name)}"> <%--검색필드와 검색어가 있는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="admin_user.net?page=${page-1}&find_field=${find_field}&find_name=${find_name}">[이전]</a>&nbsp;
     </c:if> 
      
   <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="admin_user.net?page=${a}&find_field=${find_field}&find_name=${find_name}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>      
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="admin_user.net?page=${page+1}&find_field=${find_field}&find_name=${find_name}">[다음]</a>
    </c:if>
   </c:if> 
   </div>