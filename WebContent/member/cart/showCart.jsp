<%@page import="java.text.DecimalFormat"%>
<%@page import="net.hue.bean.CartBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.CartDao"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500&display=swap"
	rel="stylesheet">
<jsp:include page="../../include/header.jsp" />

<style type="text/css">
/* 5 */
.custom-btn {
	background: #000;
	color: #fff;
	line-height: 42px;
	padding: 0;
	border: none;
	font-size: 15px;
	cursor: pointer;
}
</style>

<style type="text/css">
#cartTable {
	margin: auto;
	margin-top: 50px;
	text-align: center;
	width: 800px;
}

#cartTable p {
	margin: 10px;
}

#cartTable>caption {
	font-size: 19px;
}

#cartTable a:hover {
	color: #C2674B;
}
</style>

<!-- jquery 파일 로드 해주는건 top_user.jsp에 위치하여 생략 -->
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('.update')
								.live(
										"click",
										function() {
											if (isNaN($(this).parent().prev()
													.find('input').val())) {
												alert("숫자만 입력 가능합니다.");
												$(this).parent().prev().find(
														'input').select();
												return;
											}

											if ($(this).parent().prev().find(
													'input').val() == "") {
												alert("숫자를 입력하세요.");
												$(this).parent().prev().find(
														'input').focus();
												return;
											}

											var cartItemNum = $(this).parent()
													.parent().parent().find(
															'input:eq(0)')
													.val();
											var qty = $(this).parent().prev()
													.find('input').val(); //수량

											location.href = "updateCartItem.net?cartItemNum="
													+ cartItemNum
													+ "&qty="
													+ qty;
										});

					});

	function allCheck() {
		ac = document.f.allcheck;
		rc = document.f.rowcheck;

		if (typeof (rc.length) == 'undefined') { // 체크박스가 1개일 때 자꾸 오류났는데 이렇게 하니까 해결됨
			if (rc.checked) {
				rc.checked = false;
			} else {
				rc.checked = true;
			}
		} else {
			if (ac.checked == true) {
				for (var i = 0; i < rc.length; i++) {
					rc[i].checked = true;
				}
			} else {
				for (var i = 0; i < rc.length; i++) {
					rc[i].checked = false;
				}
			}
		}
	}

	function gotoOrder() {
		rc = document.f.rowcheck;

		if (typeof (rc.length) == 'undefined' && rc.checked) {
			document.f.submit();
			return;
		}

		isChecked = false;

		for (var i = 0; i < rc.length; i++) {
			if (rc[i].checked) {
				isChecked = true;
				break;
			}
		}

		if (isChecked == false) {
			alert("결제할 상품을 선택하세요.");
			return;
		}

		document.f.submit();
	}
</script>
<form name=f action="orderView.net">
	<table id="cartTable">
		<caption>
			<strong>장바구니</strong>
			<hr>
		</caption>
		<tr>
			<td><input type="checkbox" name="allcheck" onClick="allCheck()"></td>
			<td>상품이미지</td>
			<td>상품명</td>
			<td>선택옵션</td>
			<td>단가</td>
			<td>수량</td>
			<td>소계금액</td>
			<td>삭제</td>
		</tr>
		<c:forEach items="${ctlist}" var="ctbean">
			<tr>
				<td><input type="checkbox" name="rowcheck" value="${ctbean.no}"></td>
				<c:set var="fullPath"
					value="${pageContext.request.contextPath}/admin/product_images/${ctbean.mainimgn}" />
				<td><img src="${fullPath}"
					style="max-height: 100px; max-width: 100px;"></td>
				<td>${ctbean.pname}</td>
				<td>${ctbean.opname}</td>
				<td><fmt:formatNumber pattern="###,###" value="${ctbean.oneprice}"/>원</td>
				<td>
					<p>
						<input type="text" value="${ctbean.qty}"
							style="width: 30px; height: 20px; text-align: center;">
					</p>
					<p>
						<input type="button" value="수정" class="update"
							style="font-size: 5px; text-align: center;">
					</p>
				</td>
				<td><fmt:formatNumber pattern="###,###" value="${ctbean.oneprice * ctbean.qty}"/>원</td>
				<td><a href="deleteFromCart.net?itemno=${ctbean.no}">삭제</a></td>
			</tr>
		</c:forEach>

		<tr>
			<c:if test="${empty ctlist}">
				<td colspan="8"><br>
				<br>장바구니에 상품이 존재하지 않습니다.</td>
			</c:if>
			<c:if test="${not empty ctlist}">
				<td colspan="8"><br>
				<br>
				<input type="button" value="선택상품 주문하기" class="custom-btn"
					onClick="gotoOrder()" style="width: 433px; height: 50px"></td>
			</c:if>
		</tr>
	</table>
</form>

<jsp:include page="../../include/footer.jsp" />