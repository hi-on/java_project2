package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class member_mypage_edit_okController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
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
		
		int mno = (int) session.getAttribute("memno");
		
		mbean.setId(id); mbean.setPassword(password); mbean.setName(name);
		mbean.setZip(zip); mbean.setAddr1(addr1); mbean.setAddr2(addr2);
		mbean.setMobile1(mobile1); mbean.setMobile2(mobile2); mbean.setMobile3(mobile3);
		mbean.setEmail(email); mbean.setGender(gender);
		
		MemberDao mdao = MemberDao.getInstance();
		int cnt = mdao.updateMember(mbean,mno);
		
		if(cnt > 0) {
			session.invalidate(); // 세션 설정 삭제
			
			out.println("<script>");
			out.println("alert('수정된 정보로 로그인 해주세요.');");
			out.println("location='member_loin.net';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('비밀번호를 확인하세요.');");
			out.println("history.go(-1);");
			out.println("</script>");
		}
		return null;
	}
}
