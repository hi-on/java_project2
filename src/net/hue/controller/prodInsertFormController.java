package net.hue.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.CategoryBean;
import net.hue.dao.CategoryDao;

public class prodInsertFormController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		CategoryDao cdao = CategoryDao.getInstance();
		ArrayList<CategoryBean> lcateList = cdao.getOnlyLargeCategory();
		ActionForward forward = new ActionForward();
		
		request.setCharacterEncoding("UTF-8");
		
		request.setAttribute("lcateList", lcateList);
		forward.setRedirect(false);
		forward.setPath("./admin/productManage/prodInsertForm.jsp");
		return forward;
	}

}
