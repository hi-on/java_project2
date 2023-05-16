package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.OrderDao;

public class orderDeleteController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int pno = Integer.parseInt(request.getParameter("pno"));
		
		OrderDao odao = OrderDao.getInstance();
		int cnt = odao.deleteorder(pno);
		
		if(cnt > 0) {
			out.println("<script>");
			out.println("alert('주문목록 삭제 완료되었습니다.');");
			out.println("location='admin_order.net';");
			out.println("</script>");
		}
		return null;
	}

}
