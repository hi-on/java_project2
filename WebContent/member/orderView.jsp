<%@page import="java.text.DecimalFormat"%>
<%@page import="net.hue.bean.CartBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.CartDao"%>
<%@page import="net.hue.dao.MemberDao"%>
<%@page import="net.hue.bean.MemberBean"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500&display=swap" rel="stylesheet">
<jsp:include page="../include/header.jsp" />

<script type="text/javascript">
	
	$(document).ready(function(){
		$("input[name='addressInputWay']").change(function(){
			var v = $("input[name='addressInputWay']:checked").val();
			
			if(v == 1){
				<%--<%
				String memid = (String) session.getAttribute("memid");
				MemberDao mdao = MemberDao.getInstance();
				MemberBean mbean = mdao.getMemberById(memid);
				%>--%>
				$("input[name='name']").val("${mbean.name}");
				$("input[name='mobile1']").val("${mbean.mobile1}");
				$("input[name='mobile2']").val("${mbean.mobile2}");
				$("input[name='mobile3']").val("${mbean.mobile3}");
				$("input[name='zip']").val("${mbean.zip}");
				$("input[name='addr1']").val("${mbean.addr1}");
				$("input[name='addr2']").val("${mbean.addr2}");
				
			}
			else if(v == 2){
				$("input[name='name']").val("");
				$("input[name='mobile1']").val("");
				$("input[name='mobile2']").val("");
				$("input[name='mobile3']").val("");
				$("input[name='zip']").val("");
				$("input[name='addr1']").val("");
				$("input[name='addr2']").val("");
			}	
		}).change();
		
	});
	
	function finishWithPay(){
		if($("input[name='name']").val() == ""){
			alert("받는 분의 성함을 입력해주세요");
			$("input[name='name']").focus();
			return;
		}
		
		if($("input[name='mobile1']").val() == ""){
			alert("전화번호를 입력해주세요");
			$("input[name='mobile1']").focus();
			return;
		}
		
		if($("input[name='mobile2']").val() == ""){
			alert("전화번호를 입력해주세요");
			$("input[name='mobile2']").focus();
			return;
		}
		
		if($("input[name='mobile3']").val() == ""){
			alert("전화번호를 입력해주세요");
			$("input[name='mobile3']").focus();
			return;
		}
		
		if($("input[name='zip']").val() == ""){
			alert("주소를 입력해주세요");
			return;
		}
		
		if($("input[name='addr2']").val() == ""){
			alert("상세주소를 입력해주세요");
			$("input[name='addr2']").focus();
			return;
		}
		
		if(isNaN($("input[name='mobile1']").val())){
			alert("전화번호는 숫자만 입력가능합니다.");
			$("input[name='mobile1']").select();
			return;
		}
		
		if(isNaN($("input[name='mobile2']").val())){
			alert("전화번호는 숫자만 입력가능합니다.");
			$("input[name='mobile2']").select();
			return;
		}
		
		if(isNaN($("input[name='mobile3']").val())){
			alert("전화번호는 숫자만 입력가능합니다.");
			$("input[name='mobile3']").select();
			return;
		}
		
		document.f.submit();
	}
</script>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	var newJquery = $.noConflict(true); // 다른 라이브러리랑 겹칠때 이렇게 해주면 됨.
	function openZipSearch() {
		new daum.Postcode({
			oncomplete: function(data) {
				newJquery('[name=zip]').val(data.zonecode); // 우편번호 (5자리)
				newJquery('[name=addr1]').val(data.address);
				newJquery('[name=addr2]').val(data.buildingName);
				newJquery('[name=addr2]').focus();
			}
		}).open();
	}
</script>

<%--
	String[] rowcheck = request.getParameterValues("rowcheck");
	CartDao ctdao = CartDao.getInstance();
	int ctno = -1;
	ArrayList<CartBean> ctlist = new ArrayList<CartBean>();
	
	String madedToTheOneRowcheck = "";
	
	if(rowcheck != null){ // 장바구니를 통해 들어왔을 경우
		for(String no: rowcheck){
			ctlist.add(ctdao.getItem(Integer.parseInt(no)));
		}
	
		//다음페이지로 넘길때 rowcheck를 한번 더 사용하기 위해 하나의 문자열로 만듬.
		if(rowcheck.length == 1){
			madedToTheOneRowcheck = rowcheck[0] + ",";	
		}
		else if(rowcheck.length > 1){
			madedToTheOneRowcheck = "" + rowcheck[0];
			for(int i = 1; i< rowcheck.length; i++){
				madedToTheOneRowcheck += "," + rowcheck[i];
			}
		}	
	}	
	else{ // 바로 구매하기 버튼을 눌러 들어온 경우
		ctno = Integer.parseInt(request.getParameter("ctno")); // 장바구니에 담긴 상품의 번호를 들고와야 함.
		ctlist.add(ctdao.getItem(ctno));
	}
--%>

<%-- <c:choose>
 <c:when test="${empty rowcheck}">
  <c:set var="ctno" value="${ctno}"/>
  <c:set var="ctlist" value="${ctlist}"/>
 </c:when>
 
 <c:otherwise> 
  <c:forEach var="no" items="${rowcheck}">
   <c:set var="ctlist" value="${status.ctlist.add(ctdao.getItem(no))}"/>
  </c:forEach>
  <c:choose>
   <c:when test="${fn:length(rowcheck)==1}">
    <c:set var="madedToTheOneRowcheck" value="${rowcheck[0]}+','"/>
   </c:when>
   <c:when test="${fn:length(rowcheck)>1}">
    <c:set var="madedToTheOneRowcheck" value="${''+rowcheck[0]}"/>
    <c:forEach var="i" items="${fn:length(rowcheck)}" begin="1">
     <c:set var="madedToTheOneRowcheck" value="${madedToTheOneRowcheck+','+rowcheck[i]}"/>
    </c:forEach>
   </c:when>
  </c:choose>
 </c:otherwise>
</c:choose>--%>

<form name=f action="orderProc.net">
	<table id="orderTable">
		<caption>
			<strong>결제할 상품 정보</strong>
			<hr>
		</caption>
		<tr>
			<td>상품이미지</td>
			<td>상품명</td>
			<td>선택옵션</td>
			<td>단가</td>
			<td>수량</td>
			<td>소계금액</td>
		</tr>
		
		<%--<%
		DecimalFormat decFormat = new DecimalFormat("###,###");
		int totalPrice = 0;
		for(CartBean ctbean : ctlist){
			totalPrice += ctbean.getOneprice()*ctbean.getQty();
		%>--%>

		<c:forEach var="ctbean" items="${ctlist}" varStatus="status">		
			<tr>	
				<%-- <td><%=ctbean.getNo()%></td> --%>
				<%--
				String fullPath = request.getContextPath() + "/admin/product_images/" + ctbean.getMainimgn();
				/* System.out.println(fullPath); */
				--%>
				<%-- <td>${ctbean.no}</td>--%>
				
				<c:set var="fullPath" value="${pageContext.request.contextPath}/admin/product_images/${ctbean.mainimgn}"/>
				<td><img src="${fullPath}"  style="max-height: 100px; max-width: 100px;"></td>
				<td>${ctbean.pname}<%--=ctbean.getPname()--%></td>
				<td>${ctbean.opname}<%--=ctbean.getOpname()--%></td>
				<td><fmt:formatNumber pattern="###,###" value="${ctbean.oneprice}"/>원</td>
				<%--%=decFormat.format(ctbean.getOneprice())--%>
				<td>${ctbean.qty}<%--=ctbean.getQty()--%></td>
				<td>
				<fmt:formatNumber pattern="###,###" value="${ctbean.oneprice*ctbean.qty}"/>원</td>
				<%--=decFormat.format(ctbean.getOneprice()*ctbean.getQty())원</td>--%>
				
			</tr>
		
		<%--
		}
		--%>
		</c:forEach>
		<tr>
			<td colspan="8"> <b>결제하실 총 금액은 총 <strong><fmt:formatNumber pattern="###,###" value="${totalPrice}"/></strong>원 입니다.</b></td>
			<%--=decFormat.format(totalPrice)--%>
		</tr>
		
   </table>
  <div class="d1-join">
  <div class="member-join">
  <div class="ec-base-table typeWrite">
  <table id="deliveryInfoTable">
   <caption style="color: #222;">
    <strong>배송지 정보</strong>
    <hr>
   </caption>
   <tr>
    <th>배송지 선택</th>
    <td class="placeholder"><input type="radio" name="addressInputWay" value="1" checked>주문자 정보와 동일 &nbsp&nbsp&nbsp <input type="radio" name="addressInputWay" value="2">새로운 배송지 작성</td>
   </tr>
   <tr>
    <th>받는분 성명</th>
    <td class="placeholder">
     <span id="nameContents">
      <input type="text" name="name" id="name">
     </span>
    </td>
   </tr>
   <tr>
    <th valign="top">연락처</th>
    <td class="placeholder">
     <input type="text" maxlength="3" name="mobile1" id="mobile1"> - <input type="text" maxlength="4" name="mobile2" id="mobile2"> - <input type="text" maxlength="4" name="mobile3"  id="mobile3">
    </td>
   </tr>
   <tr>
    <th valign="top">배송지주소</th>
    <td class="placeholder">
	 <p>
	  <input type="text" name="zip"  id="zip" readonly>
      <input type="button" value="우편번호" class="btnNormal" id="btnNormal" name="btnNormal"  onclick="openZipSearch()">
     </p>
     <input type="text" name="addr1"  id="addr1" readonly>  기본주소
	 <input type="text" name="addr2"  id="addr2">  상세주소
    </td>
   </tr>
   <tr>	
    <td colspan="8">
     <input type="hidden" name="ctno" value="${ctno}">
     <input type="hidden" name="rowcheck" value="${madedToTheOneRowcheck}">
     <input type="button" value="결제하기" class="custom-btn" onClick="finishWithPay()" style="width:433px; height:50px">
    </td>
   </tr>
  </table>
  </div>
  </div>
 </div>
</form>
<jsp:include page="../include/footer.jsp" />