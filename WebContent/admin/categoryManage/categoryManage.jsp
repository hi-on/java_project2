
<%--<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.CategoryDao"%>
<%@page import="net.hue.bean.CategoryBean"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../admin_include/top_admin.jsp"%>--%>

<%@ include file="../admin_include/top_admin.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<%
	CategoryDao cdao = CategoryDao.getInstance();
	ArrayList<CategoryBean> list = cdao.getAllCategory();
%>
--%>


<script type="text/javascript" src="./js/jquery.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$('#showForm').live("click", function(){
			$(this).parent().parent().next().toggle();
			$(this).parent().parent().next().find('input[type="text"]').val("");
			$(this).parent().parent().next().find('input[type="text"]').focus();
		});
		
		$('#cancel').live("click", function(){
			$(this).parents('.updateCateTd').toggle(); //가장 먼저 찾아진 것 1곳에만 작용함
			/* $(this).parent().parent().next().find('input[type="text"]').val("");
			$(this).parent().parent().next().find('input[type="text"]').focus(); */
		});
	});
	
	function insertSmallCategory(lno){
		var scname = window.prompt("추가할 소분류를 입력하세요", "");
		
		if(scname == ""){
			insertSmallCategory(lno);
		}
		else if(scname != null){
			document.insertSCateform.lno.value = lno;
			document.insertSCateform.scname.value = scname;
			document.insertSCateform.submit();
				}
	}
	
	function insertLargeCategory(){
		var lcname = window.prompt("추가할 대분류를 입력하세요", "");
		
		if(lcname == ""){
			insertSmallCategory();
		}
		else if(lcname != null){
			document.insertLCateform.lcname.value = lcname;
			document.insertLCateform.submit();
		}
	}
	
	function checkLCname(i){
		var temp = "input[name='lcname" + i +"']";	
		if($(temp).val() == ""){
			alert("대분류명을 입력해야 합니다.");			
			return false;
		}
	}
	
	function checkSCname(i){
		var temp = "input[name='scname" + i +"']";
		if($(temp).val() == ""){
			alert("소분류명을 입력해야 합니다.");			
			return false;
		}
	}
	
	
</script>

<!-- 눈에는 보여지지 않는 form -->
<form name="insertSCateform" action="insertSCategory.net">
 <input type=hidden name="lno" value="">
 <input type=hidden name="scname" value="">
</form>

<form name="insertLCateform" action="insertLCategory.net">
 <input type=hidden name="lcname" value="">
</form>

<table class="categoryManage">
	<caption>
		<strong>카테고리 관리</strong>
	</caption>
	
	<c:set var="beforeLname" value="" />

	<c:forEach var="cbean" items="${list}" varStatus="status">

    <c:if test="${!beforeLname.equals(cbean.lname) && cbean.lno != -1}">
        <tr class="lcategory">
            <td style="color:green;"><b>${beforeLname = cbean.lname}</b></td>
            <td><a href="#" id="showForm">수정</a></td>
            <td><a href="deleteLCategory.net?lno=${cbean.lno}&lstep=${cbean.lstep}">삭제</a></td>
            <td><a href="javascript:insertSmallCategory(${cbean.lno})" id="showInsertSForm">+</a></td>
            <td><a href="largeCategoryUp.net?lno=${cbean.lno}&lstep=${cbean.lstep}">▲</a> | <a href="largeCategoryDown.net?lno=${cbean.lno}&lstep=${cbean.lstep}">▼</a></td>
        </tr>
        <tr class="updateCateTd" style="display: none;">
            <td colspan="5"> 
                <form action="updateLCategoryName.net">
                    <table>
                        <tr>
                            <td>대분류명 : </td>
                            <td>
                                <input type="text" name="lcname${status.index}" value="">
                                <input type="hidden" name="i" value="${status.index}">
                                <input type="hidden" name="lno" value="${cbean.lno}">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="수정" onClick="return checkLCname('${status.index}')">
                                <input type="button" value="취소" id="cancel">
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
    </c:if>
    
    <c:if test="${cbean.sname != null}">
        <tr class="scategory">
            <td>
                <img src="./images/level.gif" width="10" height="15">-${cbean.sname}
            </td>
            <td><a href="#" id="showForm">수정</a></td>
            <td><a href="deleteSCategory.net?sno=${cbean.sno}&sstep=${cbean.sstep}">삭제</a></td>
            <td></td>
            <td><a href="smallCategoryUp.net?lno=${cbean.lno}&sno=${cbean.sno}&sstep=${cbean.sstep}">△</a> | <a href="smallCategoryDown.net?lno=${cbean.lno}&sno=${cbean.sno}&sstep=${cbean.sstep}">▽</a></td>
        </tr>
        <tr class="updateCateTd" style="display: none;">
            <td colspan="5"> 
                <form action="updateSCategoryName.net">
                    <table>
                        <tr>
                            <td>소분류명 : </td>
                            <td>
                                <input type="text" name="scname${status.index}" value="">
                                <input type="hidden" name="i" value="${status.index}">
                                <input type="hidden" name="sno" value="${cbean.sno}">
				 </td>
				</tr>
				<tr>
				 <td colspan="2">
				  <input type="submit" value="수정" onClick="return checkSCname(${status.index})">
				  <input type="button" value="취소" id="cancel">
				 </td>
				</tr>
			   </table>
			  </form>
			 </td>
			</tr>
			
		<%--	
		<%
		}%>
		<%
		beforeLname = cbean.getLname();
	}
	%>
	--%>
	</c:if>
	<%-- ${beforeLname = cbean.lname} --%>
	</c:forEach>
	<tr>
	 <td>
	 </td>
	 <td colspan="2">
	  <a href="javascript:insertLargeCategory()">+ 대분류 추가</a>
	 </td>
	</tr>
</table>