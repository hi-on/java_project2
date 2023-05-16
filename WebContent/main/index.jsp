<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../include/header.jsp" />

<link rel="stylesheet" type="text/css" href="./css/imageslide.css" >
<script type="text/javascript" src="./js/imageslide.js"></script>

  <div id="-main">
<%-- 메인페이지 이미지 S --%>
   <div id="main_main">
    <div id="main_img">
     <div class="mySlideDiv fade active">
      <img src="./images/mainimage.jpg" width="1660" height="500" alt="메인이미지" >
     </div>
     <div class="mySlideDiv fade">
      <img src="./images/imageslid1.jpg" width="1660" height="500" alt="메인이미지" >
     </div>
     <div class="mySlideDiv fade">
      <img src="./images/imageslid2.jpg" width="1660" height="500" alt="메인이미지" >
     </div>
     <a class="prev" onclick="prevSlide()">&#10094;</a>
     <a class="next" onclick="nextSlide()">&#10095;</a>
    </div>
   </div>
<%-- 메인페이지 이미지 E --%> 
 
   <div class="clear"></div>
   
<%-- 메인배너 S --%>   
   <div class="mainSection">
	<div class="mainsec01">
	 <div class="-frame">
	  <div id="mainsec00" class="mainsec00">
	   <div class="-sort2">
	    <ul class="grid4 -zero">
	     <li class="item0">
	      <a href="#">
	       <img src="https://egura83.cafe24.com/web/upload/NNEditor/20211112/562c3e483171391818f9ac6f0afc77a8.jpg">
	      </a>
	     </li>
	     <li class="item1">
	      <a href="#">
	       <img src="https://egura83.cafe24.com/web/upload/NNEditor/20211112/fd7e4a8c923eb3abbbf24f26cd2b27e4.jpg">
	      </a>
	     </li>
	     <li class="item2">
	      <a href="#">
	       <img src="https://egura83.cafe24.com/web/upload/NNEditor/20211112/e70d9c88f2ac4c62cfa8fc284595c0db.jpg">
	      </a>
	     </li>
	     <li class="item3">
	      <a href="#">
	       <img src="./images/asd.jpg">
	      </a>
	     </li>
	    </ul>
	   </div>
	  </div>
	 </div>
	</div>
   </div>
<%-- 메인배너 E --%>  

<%-- 메인페이지 본문 --%>
<%-- 베스트 아이템 --%>
   <div class="product-1 mainSection">
    <div class="ec-base-product mainproduct main">
     <div class="-frame">
      <div class="productGroupTitle">
       <div class="-prdstitle">
        <span class="text" style="display: ;">
         BEST ITEM
         <p>가장 많은 사랑을 받은 상품입니다.</p>
        </span>
        <span style="display: none;">
         <img alt="BEST ITEM<p>가장 많은 사랑을 받은 상품입니다.</p>" >
        </span>
       </div>
      </div>
   <table id="mainList">
    <c:forEach var="pbean" items="${list}" varStatus="status">
        <c:set var="fullPath" value="${pageContext.request.contextPath}/admin/product_images/${pbean.mainImgN}" />
        <c:if test="${status.count ==1}">
            <tr>
        </c:if>
        <td>
            <table id="mainListInside">
                <tr>
                    <td class="thumnail_img">
                        <a href="getProduct_detail_ok.net?no=${pbean.no}">
                        <img src="${fullPath}" width="300px" height="300px"></a>
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
		</tr>
		<tr style="display:none;">
		</c:if>
	</c:forEach>
</table>
     </div>
    </div>
   </div>

 <div class="clear"></div>
 
 
<jsp:include page="../include/footer.jsp" />