package net.hue.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hue.bean.MemberBean;
import net.hue.dao.MemberDao;

public class MemberIDCheckController implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		// 브라우저에 출력되는 문자, 태그, 언어코딩 타입을 설정
		PrintWriter out = response.getWriter();  // 출력스트림 out 설정
		String id = request.getParameter("id");
	
		MemberDao mdao = MemberDao.getInstance();
		MemberBean db_id = mdao.idCheck(id);  // 아이디에 해당하는 회원정보를 오라클 DB로 부터 검색
		
		int result = -1;  // 중복아이디가 없는 경우 반환값
		if(db_id != null) {  // 중복아이디가 있는 경우
			result = 1;
		}
		out.println(result);  // 비동기식 프로그램은 화면전환이 없기 때문에 현재 화면에서 값 반환한다.
		return null;
	}

}
