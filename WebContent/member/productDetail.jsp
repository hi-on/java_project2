<%@page import="net.hue.bean.CategoryBean"%>
<%@page import="net.hue.dao.CategoryDao"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="net.hue.bean.StockBean"%>
<%@page import="net.hue.dao.StockDao"%>
<%@page import="net.hue.bean.ProductBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.ProductDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../include/header.jsp" />

<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500&display=swap" rel="stylesheet">


<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$(".plus").click(function() {
			var num = $(".numBox").val();
			var plusNum = Number(num) + 1;
			
			$(".numBox").val(plusNum);
		});

		$(".minus").click(function() {
			var num = $(".numBox").val();
			var minusNum = Number(num) - 1;
			
			if(minusNum <= 0) {
				$(".numBox").val(num);
			} 
			else {
				$(".numBox").val(minusNum);          
			}
		});
	});
	
	function addCart(pno){
		if($('#optionSelect').val() == ""){
			//alert($('#optionSelect').val());
			alert("옵션을 선택해주세요");
			return;
		}
		
        <c:if test="${empty sessionScope.memno}">
         <c:url var="loginUrl" value="/member_loin.net" />
         alert("로그인을 먼저 해야합니다!");
         location.href = "<c:out value='${loginUrl}'/>";
        </c:if>
 
		var qty = $(".numBox").val();
		var opname = $('#optionSelect').val();
		
		var count = (($('select option:selected').text()).split("(")[1]).split("개")[0]; // 재고
		
		if(parseInt(count) < parseInt(qty)){
			alert("재고수량을 확인 후 다시 시도하세요");
			return;
		}
		
		<c:if test="${not empty sessionScope.memno}">
		 location.href = "addCart.net?pno=" + pno + "&qty=" + qty + "&opname=" + opname; 
		</c:if>
	}
	
	function addCartSkipShow(pno){
		
		if($('#optionSelect').val() == ""){
			//alert($('#optionSelect').val());
			alert("옵션을 선택해주세요");
			return;
		}
		
		<c:if test="${empty sessionScope.memno}">
         <c:url var="loginUrl" value="/member_loin.net" />
         alert("로그인을 먼저 해야합니다!");
         location.href = "<c:out value='${loginUrl}'/>";
        </c:if>
		
		var qty = $(".numBox").val();
		var opname = $('#optionSelect').val();
		
		var count = (($('select option:selected').text()).split("(")[1]).split("개")[0]; // 재고
		
		if(parseInt(count) < parseInt(qty)){
			alert("재고수량을 넘어서는 주문 시도는 불가능 합니다");
			return;
		}
		
		<c:if test="${not empty sessionScope.memno}">
		 location.href = "orderSkipCart.net?pno=" + pno + "&qty=" + qty + "&opname=" + opname;  
		</c:if>
	}
</script>

<div class="-frame">

<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="member_main.net">Home</a>
    </li>
    <li>
     <a href="#">${Lc}</a>
    </li>
    <li>
     <a href="#"><strong>${Sc}</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

<div id="detailTopArea">
<table class="detailArea">
<fmt:formatNumber pattern="###,###" value="${yourNumber}" />
 <tr>
  <td>
   <c:set var="fullPath" value="/HueCamping/admin/product_images/${prodMainImg}" />
   <img id="mainImg" src="${fullPath}">
  </td>
  <td id="detailTopRight">
   <table>
    <tr>
     <td>
      <h3>${prodName }</h3>
      <img src="./images/depart_today.png">
     </td>
    </tr>
    <tr>
     <td>
      <span id="info">${prodInfo }</span>
     </td>
    </tr>
    <tr>
     <td class="detail_t">정가</td>
     <td class="detail_c">
      <span style="text-decoration:line-through">
       <fmt:formatNumber pattern="###,###" value="${prodOriPri}"/>
      </span>원
     </td>
    </tr>
    <tr>
     <td class="detail_t">할인가</td>
     <td class="detail_c">
     <fmt:formatNumber pattern="###,###" value="${prodDisPri}"/> 원
     </td>
    </tr>
    <tr>
     <td class="detail_t">배송비</td>
     <td class="detail_c">3,000원 (50,000원 이상 구매 시 무료)</td>
    </tr>
    
    <tr>
     <td class="detail_optiont">옵션선택</td>
     <td class="detail_option">
      <select id="optionSelect">
       <option value="">옵션선택</option>
       
       <c:forEach var="c" items="${slist}">
        <option value="${c.opname}" >
         ${c.opname}	
         
         <c:set var="cnt" value="optcnt" />
         <c:choose>
          <c:when test="${c.count == 0 }">
           (품절)
          </c:when>
          <c:otherwise>
           (${c.count}개 남음)
          </c:otherwise>
         </c:choose>
						
        </option>
       </c:forEach>
       
      </select>
     </td>
    </tr>
    
    <tr>
     <td class="detail_t">수량선택</td>
     <td class="detail_count">
      <button type="button" class="minus">-</button>
      <input type="number" class="numBox" min="1" max="999" size="10" value="1" readonly="readonly" >
      <button type="button" class="plus">+</button>
     </td>
    </tr>
    
    <tr class="detail_btn">
     <td><input type="button" value="바로구매" class="buy_btn" onClick="addCartSkipShow(${prodNo})"></td>
     <td><input type="button" value="장바구니" class="addcart_btn" onClick="addCart(${prodNo})"></td>
     <td><input type="button" value="관심상품" class="addcart_btn" onClick="addCart(${prodNo})"></td>
    </tr>
   </table>
  </td>
 </tr>
</table>
</div>
<table id="detailMiddleArea">
 <tr>
  <td class="product_detail">
   <strong>Product Detail</strong>
  </td>
 </tr>

 <tr>
  <td class="detailImg">
   <c:if test="${prodDetailImg1 ne null }">
    <c:set var="detailImg_fullPath1" value="/HueCamping/admin/product_images/${prodDetailImg1}" />
    <img src="${detailImg_fullPath1}">
   </c:if>
  </td>
 </tr>

 <tr>
  <td class="detailImg">
   <c:if test="${prodDetailImg2 ne null }">
    <c:set var="detailImg_fullPath2" value="/HueCamping/admin/product_images/${prodDetailImg2}" />
    <img src="${detailImg_fullPath2}">
   </c:if>
  </td>
 </tr>

 <tr>
  <td class="detailImg">
   <c:if test="${prodDetailImg3 ne null }">
    <c:set var="detailImg_fullPath3" value="/HueCamping/admin/product_images/${prodDetailImg3}" />
    <img src="${detailImg_fullPath3}">
   </c:if>
  </td>
 </tr>

 <tr>
  <td class="detailImg">
   <c:if test="${prodDetailImg4 ne null }">
    <c:set var="detailImg_fullPath4" value="/HueCamping/admin/product_images/${prodDetailImg4}" />
    <img src="${detailImg_fullPath4}">
   </c:if>
  </td>
 </tr>
 
</table>
</div>
<jsp:include page="../include/footer.jsp" />
