package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class insertSCategoryController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		String scname = request.getParameter("scname");
		
		System.out.println("lno: " + lno);
		System.out.println("scname: " + scname);
		
		CategoryDao cdao = CategoryDao.getInstance();
		int cnt = cdao.insertSCategory(lno, scname);
		
		String msg = "";
		
		if(cnt > 0){
			msg = "소분류 추가 성공";		
		}
		else{
			msg = "소분류 추가 실패";	
		}
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("location='admin_category.net';");
		out.println("</script>");
		return null;
	}

}
