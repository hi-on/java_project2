package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class MemberFind_pwd_ResultController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();  // 출력 스트림 생성
		MemberDao mdao = MemberDao.getInstance();
		MemberBean mb = new MemberBean();
		ActionForward forward = new ActionForward();
		
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		mb.setId(id); mb.setName(name); mb.setEmail(email);
		
		MemberBean mpwd = mdao.findPwd(mb);
		
		if(mpwd == null) {
			out.println("<script>");
			out.println("alert('회원정보를 찾을 수 없습니다!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			request.setAttribute("mempwd", mpwd.getPassword());
			forward.setRedirect(false);
			forward.setPath("./member/findPwdResult.jsp");
			
			return forward;
		}
		return null;
	}

}
