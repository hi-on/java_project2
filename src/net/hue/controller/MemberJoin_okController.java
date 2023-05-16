package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.controller.ActionForward;
import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class MemberJoin_okController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		MemberBean mbean = new MemberBean();
		
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		String name=request.getParameter("name");
		String zip=request.getParameter("zip");
		String addr1=request.getParameter("addr1");
		String addr2=request.getParameter("addr2");
		String mobile1=request.getParameter("mobile1");
		String mobile2=request.getParameter("mobile2");
		String mobile3=request.getParameter("mobile3");
		String email=request.getParameter("email");
		String gender=request.getParameter("gender");
		
		mbean.setId(id); mbean.setPassword(password); mbean.setName(name);
		mbean.setZip(zip); mbean.setAddr1(addr1); mbean.setAddr2(addr2);
		mbean.setMobile1(mobile1); mbean.setMobile2(mobile2); mbean.setMobile3(mobile3);
		mbean.setEmail(email); mbean.setGender(gender);
		
		MemberDao mdao = MemberDao.getInstance();
		int cnt = mdao.insertMember(mbean);
		
		if(cnt > 0) {
			out.println("<script>");
			out.println("alert('"+name+"님 가입 축하드립니다.');");
			out.println("location='member_loin.net';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('회원가입에 실패하였습니다.');");
			out.println("location='member_join.net';");
			out.println("</script>");
		}
		return null;
	}
}
