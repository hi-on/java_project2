package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class updateLCategoryNameController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		String i = request.getParameter("i");	
		String lcname = request.getParameter("lcname" + i);
		
		CategoryDao cdao = CategoryDao.getInstance();
		int cnt = cdao.updateLCategoryName(lno, lcname);
		
		if(cnt > 0){
			out.println("<script>");
			out.println("alert('대분류 수정 성공');");
			out.println("location='admin_category.net';");
			out.println("</script>");	
		}
		else{
			out.println("<script>");
			out.println("alert('대분류 수정 실패');");
			out.println("location='admin_category.net';");
			out.println("</script>");	
		}
		return null;
	}

}
