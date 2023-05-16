package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class MemberFind_ID_ResultController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();  // 출력 스트림 생성

		MemberDao mdao = MemberDao.getInstance();
		MemberBean mb = new MemberBean();
		ActionForward forward = new ActionForward();

		request.setCharacterEncoding("UTF-8");		
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		mb.setName(name); mb.setEmail(email);
		
		MemberBean mid = mdao.findId(mb);
		
		if(mid == null) {
			out.println("<script>");
			out.println("location='./member/findIdResult.jsp';");
			out.println("</script>");
		}else {
			request.setAttribute("memid", mid.getId());
			forward.setRedirect(false);
			forward.setPath("./member/findIdResult.jsp");
			return forward;
		}
		return null;
	}

}
