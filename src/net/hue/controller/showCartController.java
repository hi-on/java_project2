package net.hue.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.CartBean;
import net.hue.dao.CartDao;

public class showCartController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		request.setCharacterEncoding("UTF-8");
		
		int mno = (int) session.getAttribute("memno");
		CartDao ctdao = CartDao.getInstance();
		ArrayList<CartBean> ctlist = ctdao.getAllItem(mno);
		
		request.setAttribute("ctlist", ctlist);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./member/cart/showCart.jsp");
		return forward;
	}
}
