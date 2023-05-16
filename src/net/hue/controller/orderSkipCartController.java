package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.dao.CartDao;

public class orderSkipCartController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		int mno = (int) session.getAttribute("memno");
		int pno = Integer.parseInt(request.getParameter("pno"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		String opname = request.getParameter("opname");
		
		CartDao ctdao = CartDao.getInstance();
		int cnt = ctdao.insertItem(mno, pno, qty, opname);
		int ctno = ctdao.getMaxCtno(mno);

		if (cnt > 0) {
			out.println("<script>");
			out.println("alert('주문정보 입력창으로 이동합니다.');");
			out.println("location='orderView.net?ctno="+ctno+"';");
			out.println("</script>");
	}
		return null;
	}

}
