package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLog_outController implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		session.invalidate(); // 세션 설정 삭제
		
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다!');");
		out.println("location='member_main.net';");
		out.println("</script>");
		return null;
	}
}
