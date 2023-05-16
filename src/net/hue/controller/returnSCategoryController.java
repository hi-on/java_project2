package net.hue.controller;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.hue.bean.CategoryBean;
import net.hue.dao.CategoryDao;

public class returnSCategoryController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));

		CategoryDao cdao = CategoryDao.getInstance();
		ArrayList<CategoryBean> scateList = cdao.getOnlySmallCategory(lno);
		
		JSONObject jsonList = new JSONObject();
		JSONArray itemList = new JSONArray();
		
		for(CategoryBean cbean : scateList){
			JSONObject tempJson = new JSONObject();
			tempJson.put("sno", cbean.getSno());
			tempJson.put("sname", cbean.getSname());
			itemList.add(tempJson);
		}
		
		jsonList.put("ITEMS", itemList);
		out.print(jsonList);
		
		return null;
	}
}
