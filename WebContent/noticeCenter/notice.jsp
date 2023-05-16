<%@page import="net.hue.bean.MemberBean"%>
<%@page import="net.hue.dao.MemberDao"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<jsp:include page="../include/header.jsp" />

<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="../main/index.jsp">Home</a>
    </li>
    <li>
     <a href="noticeCenter_notice.net"><strong>공지사항</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

  <div class="guideLink_title">공지사항</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="noticeCenter_notice.net"><strong style="color: #222;">공지사항</strong></a></li>
     <li><a href="noticeCenter_product_QnA.net">QnA</a></li>
     <li><a href="noticeCenter_FAQ.net">FAQ</a></li>  
     <li><a href="noticeCenter_review.net">포토후기</a></li> 
    </ul>
   </div>
   
<%--공지사항 본문 --%>
  <div class="notice_main">
   <div class="notice_cont">
     <table>
      <tr class="title">
       <th width="6%">번호</th>
       <th width="50%">제목</th>
       <th width="14%">글쓴이</th>
       <th width="17%">작성일</th>
       
      </tr>
      
      <tr>
       <td>2</td>
       <td class="notice_txt" ><a href="noticeCenter_notice_showResultSearch02.net">예상 배송 기간</a></td>     
       <td>Admin</td>
       <td>2023.03.03</td>       
     </tr>
     <tr>
      <td>1</td>
      <td class="notice_txt" ><a href="noticeCenter_notice_showResultSearch01.net">휴 캠프 소개</a></td>     
      <td>Admin</td>
      <td>2023.03.03</td>     
     </tr>
    </table>
   </div>
   
   <%
    MemberDao mdao = MemberDao.getInstance();
						
    String memid = (String)session.getAttribute("memid");
						
    if(memid == null || !memid.equals("admin")){ // 비로그인 or 관리자 외 로그인시
   %>
   <div class="searchArea">
   <form action="noticeCenter_notice.net" method="post" name="search">
    <div class="notice_search">
     <input class="notice_searchbox" type="text">
     <input class="notice_searchbtn" type="submit" value="Search">
    </div>
   </form>
   </div>
   <%							
    } else { // 로그인 상태
    MemberBean mbean = mdao.getMemberById(memid);
   %>
   
   <%
	if(memid.equals("admin")){
   %>
	<div class="searchArea">
	 <div class="insert_cont">
      <input type="button" value="글쓰기" onclick="location='noticeCenter_product_QnA_write.net';">
     </div>
    <form action="noticeCenter_notice.net" method="post" name="search">
     <div class="notice_search">
      <input class="notice_searchbox" type="text">
      <input class="notice_searchbtn" type="submit" value="Search">
     </div>
    </form>
   </div>
   <%	
	}
   %>
	
   <%
    }
   %>
  </div> 
 </div>
</div>

<jsp:include page="../include/footer.jsp" />