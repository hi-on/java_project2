package net.hue.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReviewWriteController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		request.setAttribute("page",page);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./noticeCenter/review_write.jsp");
		return forward;
	}
}
