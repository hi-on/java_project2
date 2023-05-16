package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.dao.CategoryDao;

public class smallCategoryDownController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		int sno = Integer.parseInt(request.getParameter("sno"));
		int sstep = Integer.parseInt(request.getParameter("sstep")); // 해당 대분류의 순서
		
	 	CategoryDao cdao = CategoryDao.getInstance();	

		int totalRows = cdao.countScategory(lno);	// lno에 해당하는 소분류의 총 개수	
		int result = -1;

		if (totalRows != -1 && sstep < totalRows) { // cdao.countLcategory() 처리가 성공하였고, 수정하려는 행의 step이 마지막이 아닌 경우
			result = cdao.updateDownSStep(lno, sno, sstep);
		
			ActionForward forward=new ActionForward();
	  		forward.setRedirect(false);//새로운 매핑주소로 이동
	  	    forward.setPath("admin_category.net");//로그인 매핑주소로 이동
	  	    return forward;
	  	    }
		else if(totalRows != -1 && totalRows == sstep) {
			out.println("<script>");
			out.println("alert('소분류의 끝입니다.');");
			out.println("location='admin_category.net';");
			out.println("</script>");
		}
		return null;
	}
}
