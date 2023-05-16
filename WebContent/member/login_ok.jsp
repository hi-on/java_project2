<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="net.hue.bean.MemberBean" %>
<%@ page import="net.hue.dao.MemberDao" %>

<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String password = request.getParameter("password");

	MemberDao mdao = MemberDao.getInstance();
	MemberBean mbean = mdao.getMemberByInfo(id);
	
	String url = "";
	
	if(mbean != null){ // 회원
		String memid = mbean.getId();
		int memno = mbean.getNo();
		
		// 세션 아이디 , 식별자 저장
		session.setAttribute("memid", memid);
		session.setAttribute("memno", memno);
		
	if(memid.equals("admin")){ // 아이디가 admin 일때
		url = request.getContextPath() + "/main/index.jsp";
	%><script>
	alert("<%=mbean.getId()%>" + "회원님 로그인에 성공하셨습니다.");
	location.href="<%=url%>";</script><%
	}else{
		url = request.getContextPath() + "/main/index.jsp";
	%><script>
	alert("<%=mbean.getId()%>" + "회원님 로그인에 성공하셨습니다.");
	location.href="<%=url%>";</script><%
	}
	}else{ // mbean !=null 이 아니라면 비회원
		url = request.getContextPath() + "/member/login.jsp";
	%><script>
	alert("아이디와 비밀번호를 다시 확인해주세요.");
	location.href="<%=url%>";</script>
	<% } %>