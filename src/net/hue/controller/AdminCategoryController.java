package net.hue.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.CartBean;
import net.hue.bean.CategoryBean;
import net.hue.dao.CategoryDao;

public class AdminCategoryController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		
		request.setCharacterEncoding("UTF-8");
		
		CategoryDao cdao = CategoryDao.getInstance();
		ArrayList<CategoryBean> list = cdao.getAllCategory();
		
		request.setAttribute("list", list);
     	
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./admin/categoryManage/categoryManage.jsp");
		return forward;
	}

}
