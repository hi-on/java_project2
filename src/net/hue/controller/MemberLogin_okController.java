package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class MemberLogin_okController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		MemberDao mdao = MemberDao.getInstance();
		MemberBean mbean = new MemberBean();
		mbean.setId(id); mbean.setPassword(password);
		mbean = mdao.getMemberByInfo(id);
		
		if(mbean == null) { // 회원이 아니면
			out.println("<script>");
			out.println("alert('가입 안된 회원입니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else { // 아이디가 맞는데 비번이 다를경우
			if(!mbean.getPassword().equals(password)) {
				out.println("<script>");
				out.println("alert('비번이 다릅니다.');");
				out.println("history.go(-1);");
				out.println("</script>");
		}else {
			String memid = mbean.getId();
			int memno = mbean.getNo();
			String menm = mbean.getName();
			
			//세션 아이디, 식별자 저장
			HttpSession session = request.getSession();
			session.setAttribute("memid", memid);
			session.setAttribute("memno", memno);
			out.println("<script>");
			out.println("alert('"+menm+"님 환영합니다.');");
			out.println("location='member_main.net';");
			out.println("</script>");
			}
		}
		return null;
	}
}
