package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CartDao;

public class deleteFromCartController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int itemno = Integer.parseInt(request.getParameter("itemno"));

		CartDao ctdao = CartDao.getInstance();
		int cnt = ctdao.deleteItem(itemno);
		
		String url = "";
		
		if(cnt > 0){
			out.println("<script>");
			out.println("alert('장바구니 삭제 성공');");
			out.println("location='showCart.net';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('장바구니 삭제 실패');");
			out.println("location='showCart.net';");
			out.println("</script>");
		}
		
		return null;
	}

}
