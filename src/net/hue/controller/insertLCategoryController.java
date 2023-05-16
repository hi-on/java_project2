package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class insertLCategoryController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		String lcname = request.getParameter("lcname");
		
		
		CategoryDao cdao = CategoryDao.getInstance();
		int cnt = cdao.insertLCategory(lcname);
		
		String msg = "";
		
		if(cnt > 0){
			msg = "대분류 추가 성공";		
		}
		else{
			msg = "대분류 추가 실패";	
		}
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("location='admin_category.net';");
		out.println("</script>");
		return null;
	}
}
