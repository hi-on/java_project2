package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class MemberMyPage_EditController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		int mno = (int) session.getAttribute("memno");
		MemberDao mdao = MemberDao.getInstance();
		MemberBean mbean = mdao.contmember(mno);
		
		request.setAttribute("mbean", mbean);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./mypage_login/mypage_edit.jsp");
		return forward;
	}
}
