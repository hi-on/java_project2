<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="net.hue.bean.MemberBean"%>
<%@ page import="net.hue.dao.MemberDao"%>

<%
	request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="mbean" class="net.hue.bean.MemberBean"/>
<jsp:setProperty name="mbean" property="*" />

<%
	MemberDao mdao = MemberDao.getInstance();
	int cnt = mdao.insertMember(mbean); // DB에 회원등록
	
	String url = "";

	if(cnt > 0){
		url = "login.jsp";

%>
	<script>
		alert("<%=mbean.getName()%>님, " + "회원이 되신걸 환영합니다.");
		location.href = "<%=url%>";
	</script>
<%
	}else{
%>
	<script>
		alert("회원가입에 실패하였습니다. 다시시도해주세요.");
		location.href="./join.jsp";
	</script>
<% 
	}
%>

	 