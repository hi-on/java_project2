<%@page import="net.hue.bean.CategoryBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.CategoryDao"%>
<%@page import="net.hue.bean.MemberBean"%>
<%@page import="net.hue.dao.MemberDao"%>
<%@page import="net.hue.dao.CartDao"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴:캠핑마켓</title>
<link rel="icon" type="image/png" href="./images/logomain.png">
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">

<!-- ************************* 동작 스크립트 ********************* -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script type="text/javascript">
	
	jQuery(document).ready(function(){
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
		if(session.getAttribute("memno") == null){%>
		location.href ="<%=request.getContextPath()%>/member_loin.net";
		<%			
		}
		else{
		%>
			location.href = "<%=request.getContextPath()%>/showCart.net";	<%-- 수정 필요 --%>
		<%	
		}
		%>
	}
</script>
<!-- ************************ 경계선 ************************** -->

</head>
<body id="main">
 <div id="wrap">
  <header id="header" class="header">
<!-- 상단 sec01 -->
   <div class="sec01wrap">
   	<div class="-frame">
   	 <div class="sec01">
   	  <div class="-left">
   	   <ul class="menu">
   	    <li class="-layerCSwrap text">
   	     <a href="noticeCenter_notice.net">고객센터</a>
   	     <div class="layerCS">
   	      <ul class="CS-boardinfo">
   	       <li class="record-">
   	        <a href="noticeCenter_notice.net">공지사항</a>
   	       </li>
   	       <li class="record-">
   	        <a href="noticeCenter_review.net">포토후기</a>
   	       </li>
   	       <li class="record-">
   	        <a href="noticeCenter_product_QnA.net">QnA</a>
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
        <%
          MemberDao mdao = MemberDao.getInstance();
						
          String memid = (String)session.getAttribute("memid");
						
          if(memid == null){ // 로그인을 하지 않은 상태
        %>

        <li><a href="member_loin.net">로그인</a></li>
        <li><a href="member_join.net">회원가입</a></li>
        <li class="statelogoff">
         <a href="javascript:gotoCart()">
	   		장바구니
          <span class="bsCount">
           (<span id="user_basket_quantity" class="user_basket_quantity">
           0
           </span>)
          </span>
	     </a>
        </li>
        <%							
          } else { // 로그인 상태
          MemberBean mbean = mdao.getMemberById(memid);
        %>
        <li><a>환영합니다!&nbsp&nbsp<strong><%=mbean.getName()%></strong>&nbsp님</a></li>
        <li><a href="member_log_out.net">로그아웃</a></li>
        <li><a href="member_mypage_list.net">마이페이지</a></li>
	    <li class="statelogoff">
         <a href="javascript:gotoCart()">
	   		장바구니
          <span class="bsCount">
           (<span id="user_basket_quantity" class="user_basket_quantity">
	       <%
             int result = 0;
             if(session.getAttribute("memno") != null){
             int memno = (int)session.getAttribute("memno");
             CartDao ctdao = CartDao.getInstance();
             result = ctdao.countItemInCart(memno);	
             }
           %>
           <%=result%>

           </span>)
          </span>
	     </a>
        </li>
	    <%
	      if(memid.equals("admin")){
        %>
	     <li><a href="admin_top.net">관리자페이지</a></li>
	    <%	
	      }
	    %>
	    
	    <%
	      }
	    %>
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
 	    <!-- <li><a href="https://www.jogunshop.com/shop/idinfo.html?type=new&amp;mem_type=person&amp;first=">JOIN</a></li>
 	    <li><a href="./login/loginView.jsp">LOGIN</a></li> -->
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
  
<%-- 카테고리탭 S --%>
   <div class="categorymenuwrap">
    <div class="-frame">
     <table class="hd2">
	  <tr>
	   <td>
	    <div class="categorymenu">
		 <li class="-d1">
          <a href="#" class="allmenubtn" style="padding-left: 0px;"></a>
        
		<%
			CategoryDao cdao = CategoryDao.getInstance();	
			ArrayList<CategoryBean> clist = cdao.getAllCategory();
				
			String beforeLname = "";
			
			for(int i = 0; i<clist.size() ;i++){
				CategoryBean cbean = clist.get(i);
				
				if(cbean.getSstep() == 1 || cbean.getSname() == null){
		%>
		  		 </ul>
		 		</span>
		<% 
				}
			
				
				if(beforeLname.equals(cbean.getLname()) == false && (cbean.getSstep() == 1 || cbean.getSname() == null) && cbean.getLstep() != 0){// 대분류 명일때
		%>
					<span class="tmenu"> <a href="showLargeCategory.net?lcno=<%=cbean.getLno()%>"><%=cbean.getLname() %></a>
						<ul class="depth2" style="display: none;">
		<%	
				}
				
				if(cbean.getSname() != null){%>
					<li><a href="showSmallCategory.net?lcno=<%=cbean.getLno()%>&scno=<%=cbean.getSno()%>"><%=cbean.getSname() %></a></li>
		<%	
				}

				beforeLname = cbean.getLname();
			}
		%>
		  </li>
         </div>
        </td> 
  	   </tr>
	  </table>
     </div>
	</div>
<%-- 카테고리탭 E --%>

   </header>