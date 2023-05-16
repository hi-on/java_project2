package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class memeber_del_okController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("delete_id");
		String password = request.getParameter("delete_pwd");
		String email = request.getParameter("delete_email");
		
		MemberDao mdao = MemberDao.getInstance();
		MemberBean mbean = new MemberBean();
		
		mbean.setId(id); mbean.setPassword(password);
		mbean.setEmail(email);
		
		int cnt = mdao.delMember(mbean);
		
		if(cnt > 0) {
			session.invalidate(); // 세션 설정 삭제
			
			out.println("<script>");
			out.println("alert('정상적으로 회원 탈퇴 되었습니다.');");
			out.println("location='member_main.net';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('입력된 회원정보가 없습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
		}
		
		return null;
	}

}
