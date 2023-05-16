<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../include/header.jsp" />

<script src="../js/jquery.js"></script>
<script>
	$(document).ready(function(){
		$('#banner_image').show();
	});
</script>

<c:choose>
 <c:when test="${empty plist}">
  <table id="mainList">
   <tr>
    <td>해당 카테고리에는 진열된 상품이 존재하지 않습니다.</td>
   </tr>
  </table>
 </c:when>
 
 <c:otherwise>
  <table id="mainList">
   <c:forEach items="${plist}" var="pbean" varStatus="status">
   <c:set var="fullPath" value="${pageContext.request.contextPath}/admin/product_images/${pbean.mainImgN}"/>
   <td>
    <table id="mainListInside">
     <tr>
      <td class="thumnail_img">
       <a href="getProduct_detail_ok.net?no=${pbean.no}"><img src="${fullPath}" style="max-height: 300px; max-width: 300px;"></a>
      </td>
     </tr>
     <tr>
      <td class="thumnail_prod">
       <h3>${pbean.name}</h3>
      </td>
     </tr>
     <tr>
      <td class="thumnail_price">
       <span style="text-decoration:line-through;" class="thumnail_price1">
       <fmt:formatNumber pattern="###,###" value="${pbean.oriprice}"/>원</span>
       <span class="thumnail_price2">
       <fmt:formatNumber pattern="###,###" value="${pbean.oriprice}"/>원</span>
      </td>
     </tr>
    </table>
   </td>
    <c:if test="${status.count % 4 == 0}">
     <tr>
    </c:if>
   </c:forEach>
  </table>
 </c:otherwise>

</c:choose>

<%--    23.05.12      --%>

<%-- 검색 전후 페이징 --%>
  <div id="bList_paging">
   <%--검색 전 페이징 --%>
   <c:if test="${(empty find_field) && (empty find_name)}"> <%--검색필드와 검색어가 없는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="showLargeCategory.net?lcno=${lcno}&page=${page-1}">[이전]</a>&nbsp;
     </c:if>
     
     <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="showLargeCategory.net?lcno=${lcno}&page=${a}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>
       
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="showLargeCategory.net?lcno=${lcno}&page=${page+1}">[다음]</a>
    </c:if>
   </c:if>
   
    
   <%-- 검색 후 페이징 --%>
   <c:if test="${(!empty find_field) && (!empty find_name)}"> <%--검색필드와 검색어가 있는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="showLargeCategory.net?page=${page-1}&find_field=${find_field}&find_name=${find_name}">[이전]</a>&nbsp;
     </c:if> 
       --%>
   <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="showLargeCategory.net?page=${a}&find_field=${find_field}&find_name=${find_name}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>      
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="showLargeCategory.net?page=${page+1}&find_field=${find_field}&find_name=${find_name}">[다음]</a>
    </c:if>
   </c:if> 
   
   </div>
   
   <%--    23.05.12      --%>
   
<jsp:include page="../include/footer.jsp" />