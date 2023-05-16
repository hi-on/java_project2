package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class deleteLCategoryController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		int lstep = Integer.parseInt(request.getParameter("lstep"));

		CategoryDao cdao = CategoryDao.getInstance();
		
		int cnt = cdao.countScategory(lno);
		
		String msg = "";
		if(cnt != 0){
			msg = "소분류가 존재하여 삭제가 불가합니다.";
		}
		else{
			int result = cdao.deleteLCategory(lno, lstep);
			
			if(result > 0){
				msg = "대분류 삭제 성공";		
			}
			else{
				msg = "대분류 삭제 실패";	
			}
		}
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("location='admin_category.net';");
		out.println("</script>");	
	
		return null;
	}
}
