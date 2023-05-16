package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CartDao;

public class updateCartItemController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int no = Integer.parseInt(request.getParameter("cartItemNum"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		
		CartDao ctdao = CartDao.getInstance();
		int cnt = ctdao.updateItem(no, qty);
		
		if(cnt > 0){
			out.println("<script>");
			out.println("alert('장바구니 수정 성공');");
			out.println("location='showCart.net';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('장바구니 수정 실패');");
			out.println("location='showCart.net';");
			out.println("</script>");
		}
		return null;
	}

}
