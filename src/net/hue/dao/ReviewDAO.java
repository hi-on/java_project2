package net.hue.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.hue.bean.ReviewBean;
import net.hue.bean.ReviewReplyBean;

public class ReviewDAO {

	private Connection con = null;

	private static ReviewDAO instance;

	public static ReviewDAO getInstance() {
		if (instance == null) {
			instance = new ReviewDAO();
		}
		return instance;
	}

	public ReviewDAO() {
		try {
			/* Context.xml을 살펴봐서 설정을 읽어본다 */
			Context initContext = new InitialContext();
			/* 내가 설정한 Context.xml 정보가 comp/env 이런 폴더안에 저장됨 */
			Context envContext = (Context) initContext.lookup("java:comp/env"); // java:comp/env 에 설정 정보가 저장되는건 내가 임의로 수정할 수 없음.
			/* 위 폴더가서 jbdc/OracleDb 이름으로 설정한 것을가져와라 */
			DataSource ds = (DataSource) envContext.lookup("jdbc/OracleDB");
			// 사용자가 사이트에 접속하면 컨넥션 객체를 얻음. 그리고 이 컨넥션 객체를 가지고 로그인을 하고 자시고 하는거임. 등등의 DB작업
			con = ds.getConnection(); // 설정 정보를 가지고 계정에 접송해서 Connection 
			System.out.println("생성자에서 conn :" + con);
		} catch (NamingException e) {
			System.out.println("CategoryDao 생성자에서 컨넥션 객체를 얻다가 오류 발생");
		} catch (SQLException e) {
			System.out.println("CategoryDao 생성자에서 컨넥션 객체를 얻다가 오류 발생");
		}		
	}//ReviewDAO()생성자
	
	
	//리뷰 저장
	public void insertReview(ReviewBean r) {
		//int re=-1;
		PreparedStatement pt = null;
		String sql = null;
		
		try {
			
			sql="insert into review (board_no,board_name,board_title,board_pwd,board_cont,"
					+"board_date,board_file1) values(review_no_seq.nextval,?,?,?,?,sysdate,?)";
			
			pt=con.prepareStatement(sql);
			pt.setString(1,r.getBoard_name());
			pt.setString(2,r.getBoard_title());
			pt.setString(3,r.getBoard_pwd());
			pt.setString(4,r.getBoard_cont());
			/* 추가 */
			pt.setString(5,r.getBoard_file1());
			//pt.setString(6,r.getBoard_file2());
			//pt.setString(7,r.getBoard_file3());
			//pt.setInt(5,0);
			//pt.setInt(6,0);
							   
			 pt.executeUpdate();

		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		//return re;
	}


	
	//검색 전후 리뷰 개수
	public int getListCount(ReviewBean findR) {
		
		int count=0;
		
		PreparedStatement pt = null;
		ResultSet rs=null;
		try {
		
			String sql="select count(board_no) from review ";
			if(findR.getFind_field()==null) {
				//검색 전 개수(검색 필드 내용 없을 때)
				sql+="";
			}else if(findR.getFind_field().equals("board_title")) {
				sql=" where board_title like ?";//글제목이랑 같을 때
			}else if(findR.getFind_field().equals("board_cont")) {
				sql=" where board_cont like ?";//글내용이랑 같을 때
			}else if(findR.getFind_field().equals("board_name")) {
				sql=" where board_name like ?";//글내용이랑 같을 때
			}
			
			pt=con.prepareStatement(sql);
			
			if(findR.getFind_field()!=null) {
				//검색필드 내용 있을 때
				pt.setString(1, findR.getFind_name());
			}
			
			rs=pt.executeQuery();//쿼리문 수행 후 레코드 저장
			
			if(rs.next()) {
				count=rs.getInt(1);
			}
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return count;
	}
	
	
	//검색 목록
	public List<ReviewBean> getReviewList(int page, int limit, ReviewBean findR) {
		
		List<ReviewBean> rlist=new ArrayList<>();
		PreparedStatement pt = null;
		ResultSet rs=null;
		
		try {
			
			String sql="select * from (select rowNum rNum,board_no,board_name,board_title,"
					+ "board_date,board_hit,board_file1 from (select * from review ";
			if(findR.getFind_field() == null) {//검색전
				sql+="";
			}else if(findR.getFind_field().equals("board_title")) {
				sql+=" where board_title like ?";
			}else if(findR.getFind_field().equals("board_cont")) {
				sql+=" where board_cont like ?";
			}else if(findR.getFind_field().equals("board_name")) {
				sql+=" where board_name like ?";
			}
			sql+=" order by board_no desc)) where rNum>=? and rNum<=?";
			/* 페이징과 검색관련 쿼리문,  rowNum컬럼은 오라클에서 테이블 생성시 추가되는 컬럼으로 최초 레코드 저장
			 * 시 일련 번호값이 알아서 저장된다. rNum은 rowNum 컬럼의 별칭명이다.
			 */
			
			pt=con.prepareStatement(sql);
			
			int startrow=(page-1)*10+1;//읽기 시작할 행번호. 10이 한페이지 보여지는 목록개수
			int endrow=startrow+limit-1;//읽을 마지막 행번호
			
			if(findR.getFind_field() != null) {//검색하는 경우
				pt.setString(1,findR.getFind_name());
				pt.setInt(2,startrow);
				pt.setInt(3,endrow);
			}else {//검색하지 않는 경우
				pt.setInt(1,startrow);
				pt.setInt(2,endrow);
			}
			
			rs=pt.executeQuery();
			
			while(rs.next()) {//복수개의 레코드가 검색되는 경우는  while반복문으로 반복
				ReviewBean r=new ReviewBean();
				r.setBoard_no(rs.getInt("board_no"));
				r.setBoard_name(rs.getString("board_name"));
				r.setBoard_title(rs.getString("board_title"));
				r.setBoard_hit(rs.getInt("board_hit"));
				//r.setBoard_ref(rs.getInt("board_ref"));
				//r.setBoard_step(rs.getInt("board_step"));
				r.setBoard_date(rs.getString("board_date"));
				r.setBoard_file1(rs.getString("board_file1"));
				
				
				rlist.add(r);//컬렉션에 추가
			}
			
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();				
			}catch(Exception e) {e.printStackTrace();}
		}
		return rlist;
	}
	

	
	
	//게시판 삭제
	public void delBoard(int board_no) {
		
		PreparedStatement pt=null;
		String sql = null;
		
		try {
			sql="delete from review where board_no=?";
			pt=con.prepareStatement(sql);
			pt.setInt(1,board_no);
			pt.executeUpdate();
			
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}			
		}
	}//삭제 delBoard

	
	//글 내용 보기
	public ReviewBean getBoardView(int board_no) {
		ReviewBean rc=null;
		PreparedStatement pt = null;
		String sql = null;
		ResultSet rs=null;
		try {
			sql="select * from review where board_no=?";
			pt=con.prepareStatement(sql);
			pt.setInt(1, board_no);
			rs=pt.executeQuery();
			
			if(rs.next()) {
				rc=new ReviewBean();
				rc.setBoard_no(rs.getInt("board_no"));
				rc.setBoard_name(rs.getString("board_name"));
				rc.setBoard_title(rs.getString("board_title"));
				rc.setBoard_cont(rs.getString("board_cont"));
				rc.setBoard_pwd(rs.getString("board_pwd"));
				rc.setBoard_date(rs.getString("board_date"));
				rc.setBoard_hit(rs.getInt("board_hit")); 
				rc.setBoard_ref(rs.getInt("board_ref"));
				/* 추가*/
				rc.setBoard_file1(rs.getString("board_file1"));
				//rc.setBoard_file2(rs.getString("board_file2"));
				//rc.setBoard_file3(rs.getString("board_file3"));
				
				//rc.setBoard_step(rs.getInt("board_step"));
				//rc.setBoard_level(rs.getInt("board_level"));
			}
		}catch(Exception e) {e.printStackTrace();}
		finally{
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();					
			}catch(Exception e) {e.printStackTrace();}
		}		
		return rc;
	}

	
	
	//게시판 수정
	public int editBoard(ReviewBean eb) {

		int re=-1;//수정 실패시 반환값
		
		PreparedStatement pt=null;
		String sql=null;
		
		try {
			sql="update review set board_name=?,board_title=?,board_cont=? where board_no=?";
			
			pt=con.prepareStatement(sql);
			pt.setString(1,eb.getBoard_name());
			pt.setString(2,eb.getBoard_title());
			pt.setString(3,eb.getBoard_cont());
			pt.setInt(4,eb.getBoard_no());
			
			re=pt.executeUpdate();
			
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return re;
	}
	
	//조회수 증가
	public void updateHit(int board_no) {
		
		PreparedStatement pt=null;
		String sql = null;
		
		try {
			sql="update review set board_hit=board_hit+1 where board_no=?";
			pt=con.prepareStatement(sql);
			pt.setInt(1,board_no);
			pt.executeUpdate();
			
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	

	//댓글 저장
	public void replyBoard(ReviewReplyBean rb,int board_no) {
		
		PreparedStatement pt=null;
		String sql = null;
		try {
			
			sql="insert into reviewReply (reply_no,board_no,reply_name,reply_title,reply_pwd,reply_cont, "
					+" reply_date) values(reviewReply_no_seq.nextval,?,?,?,?,?, "
					+ "sysdate)";
					pt=con.prepareStatement(sql);
					pt.setInt(1, rb.getBoard_no());				
					pt.setString(2, rb.getReply_name());
					pt.setString(3, rb.getReply_title());
					pt.setString(4, rb.getReply_pwd());
					pt.setString(5, rb.getReply_cont());
					//pt.setInt(5, rb.getReply_ref());
					//pt.setInt(6, rb.getReply_step()+1);
					//pt.setInt(7, rb.getReply_level()+1);
			
			pt.executeUpdate();
			
		}catch(Exception e) {e.printStackTrace();
				System.out.println("저장");				}
		finally {
			try {
				if(pt != null) pt.close();
				
			}catch(Exception e) {e.printStackTrace();}
		}			
	}//replyBoard()
	
	//댓글보기
	public List<ReviewReplyBean> getReplyView(int board_no, int page) {
		
		List<ReviewReplyBean> replist=new ArrayList<>();
		PreparedStatement pt = null;
		ResultSet rs=null;
		String sql=null;
		try {
			sql="select reply_name,reply_cont,reply_date from reviewReply where board_no=? order by reply_no asc";
			pt=con.prepareStatement(sql);
			pt.setInt(1, board_no);
			rs=pt.executeQuery();
			
			while(rs.next()) {//복수개의 레코드가 검색되는 경우는  while반복문으로 반복
				ReviewReplyBean rrb=new ReviewReplyBean();
				//rrb.setReply_no(rs.getInt("reply_no"));
				rrb.setReply_name(rs.getString("reply_name"));
				rrb.setReply_cont(rs.getString("reply_cont"));
				rrb.setReply_date(rs.getString("reply_date"));
				//b.setBoard_hit(rs.getInt("board_hit"));
				//b.setBoard_ref(rs.getInt("board_ref"));
				//b.setBoard_step(rs.getInt("board_step"));
				//b.setBoard_level(rs.getInt("board_level"));
				//rrb.setReply_date(rs.getString("reply_date"));
				
				replist.add(rrb);//컬렉션에 추가
			}
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return replist;
	}//댓글 내용 보기


}



	






	


	
	
	
