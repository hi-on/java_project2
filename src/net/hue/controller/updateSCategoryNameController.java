package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class updateSCategoryNameController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int sno = Integer.parseInt(request.getParameter("sno"));
		String i = request.getParameter("i");	
		String scname = request.getParameter("scname" + i);
		
		System.out.println("sno:" + sno);
		System.out.println("i:" + i);
		System.out.println("scname:" + scname);
		
		CategoryDao cdao = CategoryDao.getInstance();
		int cnt = cdao.updateSCategoryName(sno, scname);
		
		
		if(cnt > 0){
			out.println("<script>");
			out.println("alert('소분류 수정 성공');");
			out.println("location='admin_category.net';");
			out.println("</script>");	
		}
		else{
			out.println("<script>");
			out.println("alert('소분류 수정 실패');");
			out.println("location='admin_category.net';");
			out.println("</script>");
		}
		
		return null;
	}

}
