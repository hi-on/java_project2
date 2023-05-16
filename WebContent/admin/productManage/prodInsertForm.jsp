<%@page import="net.hue.bean.CategoryBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.CategoryDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ include file="../admin_include/top_admin.jsp"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<script type="text/javascript" src="./js/jquery.js"></script>
<script type="text/javascript">
	var opcount = 1; // 전달할 추가한 옵션의 개수(최소는 1개)
	var opArr = [];
	opArr.push(1);
	
	$(document).ready(function(){
		$("select[name='selLargeCategory']").change(function(){
			var choicedNo = $(this).val(); // 선택된 것의 lno 값
		
			$.ajax({
				url : "returnSCategory.net",
				dataType : "json",
				data :{
					lno : choicedNo
				},
				success : function(responseData){		
					
					var len =Object.keys(responseData.ITEMS).length;
					var temp = "<option value='-1'>소분류를 선택해주세요</option>";
					
					for(i = 0; i < len ; i++){
						temp += "<option value=" + JSON.stringify(responseData.ITEMS[i].sno) + ">" + (JSON.stringify(responseData.ITEMS[i].sname)).replace(/"/g,"") + "</option>"			
					}
					if(len == 0){
						$("select[name='selSmallCategory']").html("<option value='-1'>소분류가 존재하지 않습니다</option>");
						//$("select[name='selSmallCategory']").parent().parent().hide();
					}else{
						$("select[name='selSmallCategory']").parent().parent().show();
						$("select[name='selSmallCategory']").html(temp);
					}
				}
			});
		});
		
		$('#optionAdd').live("click", function(){
			$(this).parent().append("<span><input type='text' value='' size='5' name='opn_" + (++opcount) + "'> <input type='text' value='' size='5' name='stock_" + opcount +"'> <input type='button' value='-' class='deleteopt'> <br></span>");
			opArr.push(opcount);
		});
		
		
		$('.deleteopt').live("click", function(){
			//name속성 뒤에 붙은 값을 분리해서 이걸 배열에서 제거하자.
			var temp = $(this).prev().attr("name"); // stock_2
			var tempArr = temp.split('_');
			//alert(tempArr[1] + "삭제");
			for(var i = 0; i < opArr.length; i++) {
				  if(opArr[i] == tempArr[1])  {
					  opArr.splice(i, 1);
				    i--;
				    break;
				  }
			}
			//alert(opArr);
			//alert($(this).parent().prev().children().attr("name"));
			
			$(this).parent().remove();
		});
	});
	
	function check(){
		if($("select[name='selLargeCategory']").val() == "선택안함"){
			alert("대분류를 선택해주세요");
			return false;
		}
		
		if($("input[name='name']").val() == ""){
			alert("상품명을 입력해주세요");
			$('#name').val('').focus();
			return false;
		}
		
		if($("input[name='oriprice']").val() == ""){
			alert("정가를 입력해주세요");
			return false;
		}
		
		if($("input[name='discprice']").val() == ""){
			alert("할인가를 입력해주세요");
			return false;
		}

		
		if(isNaN($("input[name='stock_1']").val())){
			alert("재고는 숫자만 입력 가능합니다.");
			return false;
		
			
		}
		
		//alert(opcount);
		var temp = "";
		
		for(var i = 0; i < opArr.length; i++){
			temp += opArr[i] + ",";
		}
		document.f.opnums.value = temp;
		//alert(temp);
		//alert(document.f.opnums.value);
		//return false;
		
		//null로 보내면 안되므로
		//alert($("select[name='selSmallCategory']").val());
		
		
		for(var i = 0; i<opArr.length; i++){
			if($("input[name='opn_"+ opArr[i] +"']").val() == ""){
				alert("빈칸없이 옵션명을 입력해야 합니다.");
				$("input[name='opn_"+ opArr[i] +"']").focus();
				return false;
			}
			
			if($("input[name='stock_"+ opArr[i] +"']").val() == ""){
				alert("빈칸없이 재고수량을 입력해야 합니다.");
				$("input[name='stock_"+ opArr[i] +"']").focus();
				return false;	
			}
			
			if(isNaN($("input[name='stock_"+ opArr[i] +"']").val())){
				alert("재고수량은 숫자만 입력가능합니다.");
				$("input[name='stock_"+ opArr[i] +"']").focus().select();
				return false;
				
			
			}	
		}
		
		document.f.submit();
	}
</script>

<form name="f" action="prodInsertProc.net" method="post" enctype="multipart/form-data">
 <table class="producRegister">
  <caption>
   <strong>상품등록</strong>
  </caption>
  <tr>
   <td>대분류</td>
   <td>
    <select name="selLargeCategory">
     <option value="선택안함" selected>선택안함</option>
	  <c:forEach var="p" items="${lcateList}">
	   <c:if test="${p.lno != -1}">
	    <option value="${p.lno}">${p.lname}</option>
	   </c:if>
	  </c:forEach>
	 </select>	 	
	</td>	
		 
   <tr>
	<td>소분류</td>
	<td>
	 <select name="selSmallCategory">
	  <option value="선택안함">대분류를 먼저 선택해주세요</option>
     </select>	 	
	</td>
    <tr>
     <td>이름</td>
     <td><input type="text" name="name" placeholder="상품이름을 입력하세요."></td>
    </tr>
    <tr>
     <td>정가</td>
     <td><input type="text" name="oriprice" placeholder="?????">원</td>
    </tr>
    <tr>
     <td>할인판매가</td>
     <td><input type="text" name="discprice" placeholder="?????">원</td>
    </tr>
    <tr>
     <td>상품설명</td>
     <td>
      <textarea name="info" placeholder="?????"></textarea>
     </td>
    </tr>
    <tr>
     <td>상품이미지</td>
     <td><input type="file" name="mainImg"></td>
    </tr>
    <tr>
     <td>설명이미지1</td>
     <td><input type="file" name="detailImg1"></td>
    </tr>
    <tr>
     <td>설명이미지2</td>
     <td><input type="file" name="detailImg2"></td>
    </tr>
    <tr>
     <td>설명이미지3</td>
     <td><input type="file" name="detailImg3"></td>
    </tr>
    <tr>
     <td>설명이미지4</td>
     <td><input type="file" name="detailImg4"></td>
    </tr>
		
		 
    <tr class="produc_option">
     <td>
     	옵션/재고
     </td>
     <td> 
      <span>
       <input type='text' value='S' size='5' placeholder='사이즈 등' name="opn_1">
       <input type='text' value='10' size='5' placeholder='재고수량' name="stock_1">
       <input type="button" value="추가" id="optionAdd"><br>
      </span>	
     </td>
    </tr>
		 
    <tr class="produc_submit">
     <td colspan="2" align="center">
      <input type="hidden" value="" name="opnums">
      <input type="button" value="상품등록" onClick="check()">
      <input type="button" value="취소" onClick="location.href='admin_product.net'">
     </td>
    </tr>
   </table>
</form>
 

