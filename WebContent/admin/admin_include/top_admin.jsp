<%@page import="net.hue.dao.CartDao"%>
<%@page import="net.hue.dao.MemberDao"%>
<%@page import="net.hue.bean.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="icon" type="image/png" href="./images/logomain.png">
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">

<script type="text/javascript" src="<%=request.getContextPath()%>../../js/jquery.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
 		//카테고리 펼쳐지고 접히기
		jQuery(".tmenu").live("mouseenter", function() {
		   jQuery(this).find(".depth2").slideDown("fast");
		});
		jQuery(".tmenu").live("mouseleave", function() {
		   jQuery(this).find(".depth2").slideUp("fast");
		});
	});
	
	function gotoCart(){
		<%
		if(session.getAttribute("mno") == null){%>
			location.href ="<%=request.getContextPath()%>/member_loin.net";
		<%			
		}
		else{
		%>
			location.href = "<%=request.getContextPath()%>/user/cart/showCart.jsp";	
		<%	
		}
		%>
	}
</script>

<header id="header" class="header">
   <div class="sec01wrap">
   	<div class="-frame">
   	 <div class="sec01">
   	  <div class="-left">
   	   <ul class="menu">
   	    <li class="-layerCSwrap text">
   	     <a href="#">고객센터</a>
   	     <div class="layerCS">
   	      <ul class="CS-boardinfo">
   	       <li class="record-">
   	        <a href="noticeCenter_notice.net">공지사항</a>
   	       </li>
   	       <li class="record-">
   	        <a href="noticeCenter_review.net">포토후기</a>
   	       </li>
   	       <li class="record-">
   	        <a href="noticeCenter_product_QnA.net">상품문의</a>
   	       </li>
   	       <li class="record-">
   	        <a href="noticeCenter_FAQ.net">FAQ</a>
   	       </li>
   	      </ul>
   	     </div>
   	    </li>
   	   </ul>
   	  </div>
   	 
   	  <div class="-right">
   	   <ul class="menu">
   	    <li class="statelogoff">
   	     <a>환영합니다!&nbsp&nbsp<strong>관리자</strong>&nbsp님</a>
   	    </li>
   	    <li class="statelogoff">
   	     <a href="member_log_out.net">로그아웃</a>
   	    </li>
   	    <li class="statelogoff">
   	     <a href="admin_top.net">관리자페이지</a>
   	    </li>
   	    <li>
         <form action="showSearchResult.jsp" method="post" name="search">
          <div class="searcharea"> 
           <input id="search" name="search"> 
            <a href="javascript:search_submit();" class="searhBtn">
             <div class="searchbtn">
<%-- 돋보기 설명.. --%>	 <i class="xi-search"></i>
             </div> 
            </a>
           </div>
          </form>
         </li>
   	   </ul> 	 
   	  </div>
     </div>  
    </div>
   </div>
   
   <div class="clear"></div>

   <%--상단 sec2 --%>
   <div class="-frame">
    <div class="sec02">
     <div class="ex_top_left">
     </div>
     <div id="logo" class="logoarea">
      <ul class="logo">
       <li class="banner-logo">
        <a href="member_main.net"> 
         <img src="./images/logomain.png" alt="Camp Market" >
        </a>
       </li>
      </ul>
     </div>
     <div class="ex_top_right"></div>
    </div>
   </div>

<!-- <hr> -->
 <div class="categorymenuwrap">
  <div class="-frame">
   <table class="hd2">
    <tr>
      <td>
<!-- 카테고리 -->
       <div class="cateWrap"><!-- 절대주소 사용해야함.  -->
        <span class="tmenu">
         <a href="admin_category.net">카테고리 관리</a>
        </span> 

        <span class="tmenu"> 
         <a href="admin_product.net">상품관리</a>
        </span> 

        <span class="tmenu"> 
         <a href="admin_order.net">주문내역</a>
        </span> 

        <span class="tmenu"> 
         <a href="admin_user.net">회원관리</a>
        </span> 
       </div>
      </td>
     </tr>
     <tr>
      <td colspan="3"></td>
     </tr>
    </table>
    </div>
    </div>
    
</header>