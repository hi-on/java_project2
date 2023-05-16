<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
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
     <a href="noticeCenter_product_QnA.net"><strong>QnA</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

  <div class="guideLink_title">QnA</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="noticeCenter_notice.net">공지사항</a></li>
     <li><a href="noticeCenter_product_QnA.net"><strong style="color: #222;">QnA</strong></a></li>
     <li><a href="noticeCenter_FAQ.net">FAQ</a></li> 
     <li><a href="noticeCenter_review.net">포토후기</a></li>  
    </ul>
   </div>
   
<%-- 본문 --%>
   <div class=notice_main>  
    <div class="notice_cont">
     <table>
      <tr class="title">
       <th width="6%">번호</th>
       <th width="13%"></th>
       <th width="50%">제목</th>
       <th width="14%">작성자</th>
       <th width="17%">작성일</th>
      </tr>
      
      <c:if test="${!empty blist}"> <%--검색 전후 게시물 목록 있을 때 --%>
      <c:forEach var="b" items="${blist}">    
      <tr>
        <td align="center">
         ${b.board_no}
        </td>
               
        <td class="product 1">
         <a href="#">
          <img id="asd" src="./images/logomain.png" style="max-width: 70px; max-height: 70px;">
         </a>
        </td>     
        <td align="center">
        <a href="noticeCenter_product_QnA_view.net?board_no=${b.board_no}&page=${page}&state=view">
        ${b.board_title}</a>
        </td>
        <td align="center">${b.board_name}</td>
        <td align="center">${b.board_date}</td>
      </tr>
      </c:forEach>
       </c:if>
      
      <c:if test="${empty blist}">
      <tr>
       <th colspan="5">게시물 목록이 없습니다!</th>
      </tr>
     </c:if>
    </table>  
   </div>   
  
  <%-- 검색 전후 페이징 --%>
  <div id="bList_paging">
   <%--검색 전 페이징 --%>
   <c:if test="${(empty find_field) && (empty find_name)}"> <%--검색필드와 검색어가 없는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="noticeCenter_product_QnA.net?page=${page-1}">[이전]</a>&nbsp;
     </c:if>
     
     <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="noticeCenter_product_QnA.net?page=${a}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>
       
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="noticeCenter_product_QnA.net?page=${page+1}">[다음]</a>
    </c:if>
   </c:if>
   
   <%-- 검색 후 페이징 --%>
   <c:if test="${(!empty find_field) && (!empty find_name)}"> <%--검색필드와 검색어가 있는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="noticeCenter_product_QnA.net?page=${page-1}&find_field=${find_field}&find_name=${find_name}">[이전]</a>&nbsp;
     </c:if> 
      
   <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="noticeCenter_product_QnA.net?page=${a}&find_field=${find_field}&find_name=${find_name}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>      
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="noticeCenter_product_QnA.net?page=${page+1}&find_field=${find_field}&find_name=${find_name}">[다음]</a>
    </c:if>
   </c:if> 
   </div>
   
  <%-- 글쓰기 버튼 --%>
    <div class="searchArea">
     <table>
      <tr>
       <td>
        <div class="insert_cont">
         <input class="productQnA_write" type="button" value="글쓰기" onclick="location='noticeCenter_product_QnA_write.net?page=${page}';">
        </div>
       
        <%-- 검색 버튼 --%>                        
        <div class="productQnA_searchBar">
        <form method="get" action="noticeCenter_product_QnA.net" >
         <select name="find_field">  
           <option value="board_title"
           <c:if test="${find_field == 'board_title'}">${'selected'}</c:if>>
           	제목</option>
           
           <option value="board_cont"
           <c:if test="${find_field == 'board_cont'}">${'selected'}</c:if>>내용</option>
           
           <option value="board_name"
           <c:if test="${find_field == 'board_name'}">${'selected'}</c:if>>작성자</option>           
         </select>
                	
          <input type="search" name="find_name" id="find_name" value="${find_name}">
          <input type="submit" class="product_searchbtn" value="Search">
          </form>
          </div>
      	      	
       </td>
      </tr>
   	 </table>
    </div>    
    
  </div> 
 </div>
</div>
<jsp:include page="../include/footer.jsp" />