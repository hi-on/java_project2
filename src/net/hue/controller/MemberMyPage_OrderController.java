package net.hue.controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.OrderBean;
import net.hue.dao.OrderDao;

public class MemberMyPage_OrderController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		String mid = (String) session.getAttribute("memid");
		
		OrderDao odao = OrderDao.getInstance();
		ArrayList<OrderBean> list = odao.getOrder(mid);
		
		request.setAttribute("list", list);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./mypage_login/mypage_orderlist.jsp");
		return forward;
	}

}
