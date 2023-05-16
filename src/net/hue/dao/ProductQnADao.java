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

import net.hue.bean.BoardBean;
import net.hue.bean.ReplyBean;

public class ProductQnADao {

	private Connection con = null;

	private static ProductQnADao instance;

	public static ProductQnADao getInstance() {
		if (instance == null) {
			instance = new ProductQnADao();
		}
		return instance;
	}
	
	public ProductQnADao() {
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
	}//ProductQnADAO()생성자
	
	
	//상품문의 글 저장
	public void insertProductQnA(BoardBean b) {
		
		PreparedStatement ps = null;
		String sql = null;
		try {	
			sql="insert into productQnA (board_no,board_name,board_title,board_pwd,board_cont, "
					+"board_date) values(productQnA_no_seq.nextval,?,?,?,?, "
					+ "sysdate)";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, b.getBoard_name());
			ps.setString(2, b.getBoard_title());
			ps.setString(3, b.getBoard_pwd());
			ps.setString(4, b.getBoard_cont());
		
			ps.executeUpdate();
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(ps != null) ps.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}//insertProductQnA


	//검색 전 후 레코드 개수
	public int getListCount(BoardBean findB) {
		int count=0;
		
		PreparedStatement pt = null;
		ResultSet rs=null;
		try {
		
			String sql="select count(board_no) from productQnA ";
			if(findB.getFind_field()==null) {
				//검색 전 개수(검색 필드 내용 없을 때)
				sql+="";
			}else if(findB.getFind_field().equals("board_title")) {
				sql+=" where board_title like ?";//글제목이랑 같을 때
			}else if(findB.getFind_field().equals("board_cont")) {
				sql+=" where board_cont like ?";//글내용이랑 같을 때
			}else if(findB.getFind_field().equals("board_name")) {
				sql+=" where board_name like ?";//글 작성자랑 같을 때
			}
			pt=con.prepareStatement(sql);
			
			if(findB.getFind_field()!=null) {
				//검색필드 내용 있을 때
				pt.setString(1, findB.getFind_name());
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
	
	
	//검색전후 목록
		public List<BoardBean> getBoardList(int page,int limit,BoardBean findB){
			List<BoardBean> blist=new ArrayList<>();
			PreparedStatement pt = null;
			ResultSet rs=null;
			try {
				
				String sql="select * from (select rowNum rNum,board_no,board_name,board_title,"
						+ "board_date from (select * from productQnA ";
						
				if(findB.getFind_field() == null) {//검색전
					sql+=" ";
				}else if(findB.getFind_field().equals("board_title")) {
					sql+=" where board_title like ?";
				}else if(findB.getFind_field().equals("board_cont")) {
					sql+=" where board_cont like ?";
				}else if(findB.getFind_field().equals("board_name")) {
					sql+=" where board_name like ?";//글 작성자랑 같을 때
				}
				sql+=" order by board_no desc)) where rNum>=? and rNum<=?";
				/* 페이징과 검색관련 쿼리문,  rowNum컬럼은 오라클에서 테이블 생성시 추가되는 컬럼으로 최초 레코드 저장
				 * 시 일련 번호값이 알아서 저장된다. rNum은 rowNum 컬럼의 별칭명이다.
				 */
				
				pt=con.prepareStatement(sql);
				
				int startrow=(page-1)*10+1;//읽기 시작할 행번호. 10이 한페이지 보여지는 목록개수
				int endrow=startrow+limit-1;//읽을 마지막 행번호
				
				if(findB.getFind_field() != null) {//검색하는 경우
					pt.setString(1,findB.getFind_name());
					pt.setInt(2,startrow);
					pt.setInt(3,endrow);
				}else {//검색하지 않는 경우
					pt.setInt(1,startrow);
					pt.setInt(2,endrow);
				}
				
				rs=pt.executeQuery();
				
				while(rs.next()) {//복수개의 레코드가 검색되는 경우는  while반복문으로 반복
					BoardBean b=new BoardBean();
					b.setBoard_no(rs.getInt("board_no"));
					b.setBoard_name(rs.getString("board_name"));
					b.setBoard_title(rs.getString("board_title"));
					//b.setBoard_hit(rs.getInt("board_hit"));
					//b.setBoard_ref(rs.getInt("board_ref"));
					//b.setBoard_step(rs.getInt("board_step"));
					//b.setBoard_level(rs.getInt("board_level"));
					b.setBoard_date(rs.getString("board_date"));
					
					blist.add(b);//컬렉션에 추가
				}
				
			}catch(Exception e) {e.printStackTrace();}
			finally {
				try {
					if(rs != null) rs.close();
					if(pt != null) pt.close();				
				}catch(Exception e) {e.printStackTrace();}
			}
			return blist;
		}//getBoardList()

		//게시판 글 내용 보기
		public BoardBean getBoardView(int board_no) {
			BoardBean bc=null;
			PreparedStatement pt = null;
			String sql = null;
			ResultSet rs=null;
			try {
				sql="select * from productQnA where board_no=?";
				pt=con.prepareStatement(sql);
				pt.setInt(1, board_no);
				rs=pt.executeQuery();
				
				if(rs.next()) {
					bc=new BoardBean();
					bc.setBoard_no(rs.getInt("board_no"));
					bc.setBoard_name(rs.getString("board_name"));
					bc.setBoard_title(rs.getString("board_title"));
					bc.setBoard_cont(rs.getString("board_cont"));
					bc.setBoard_pwd(rs.getString("board_pwd"));
					bc.setBoard_date(rs.getString("board_date"));
				}
			}catch(Exception e) {e.printStackTrace();}
			finally{
				try {
					if(rs != null) rs.close();
					if(pt != null) pt.close();					
					
				}catch(Exception e) {e.printStackTrace();}
			}		
			return bc;
		}//getBoardView()

		
		//게시판 저장 글 수정
		public int editProductQnA(BoardBean eb) {
			int re=-1;
			
			PreparedStatement pt = null;
			String sql = null;
			
			try {
				sql="update productQnA set board_name=?,board_title=?,board_cont=?,board_date=sysdate where board_no=?";
				pt=con.prepareStatement(sql);				
				pt.setString(1, eb.getBoard_name());
				pt.setString(2, eb.getBoard_title());
				pt.setString(3, eb.getBoard_cont());
				pt.setInt(4, eb.getBoard_no());
				
				re=pt.executeUpdate();//수정 쿼리문 수행후 성공한 레코드 행의 개수를 반환
				
				
			}catch(Exception e) {e.printStackTrace();}
			finally {
				try {				
					if(pt != null) pt.close();			
				}catch(Exception e) {e.printStackTrace();}
			}			
			
			return re;
		}//editProductQnA()
		
		
		//delBoard 게시판  글 삭제		
		public void delBoard(int board_no) {			
			
			PreparedStatement pt=null;
			String sql = null;
			
			try {
				sql="delete from productQnA where board_no=?";
				pt=con.prepareStatement(sql);
				pt.setInt(1,board_no);
				pt.executeUpdate();
				
			}catch(Exception e) {e.printStackTrace();}
			finally {
				try {
					if(pt != null) pt.close();
				}catch(Exception e) {e.printStackTrace();}			
			}
			
		}//delBoard

		
		//게시판 글 답글 기능(댓글 창)
		public void replyBoard(ReplyBean rb,int board_no) {
			//sql
			//값 저장
			//전체 값 저장
			PreparedStatement pt=null;
			String sql = null;
			try {
				//sql="update ReplyproductQnA set board_level=board_level+1 where board_ref=? and "
						//+" board_level>?";
				//pt=con.prepareStatement(sql);
				
				//pt.setInt(1, rb.getReply_ref());
				//pt.setInt(2, rb.getReply_level());
				//pt.executeUpdate();
				
				sql="insert into ReplyproductQnA (reply_no,board_no,reply_name,reply_title,reply_pwd,reply_cont, "
				+" reply_date) values(ReplyproductQnA_no_seq.nextval,?,?,?,?,?, "
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
				
			}catch(Exception e) {e.printStackTrace();}
			finally {
				try {
					if(pt != null) pt.close();
					
				}catch(Exception e) {e.printStackTrace();}
			}			
		}//replyBoard()

		//댓글 보기
		public List<ReplyBean> getReplyView(int board_no, int page) {
			List<ReplyBean> replist=new ArrayList<>();
			PreparedStatement pt = null;
			ResultSet rs=null;
			String sql=null;
			try {
				sql="select reply_name,reply_cont,reply_date from ReplyproductQnA where board_no=? order by reply_no asc";
				pt=con.prepareStatement(sql);
				pt.setInt(1, board_no);
				rs=pt.executeQuery();
				
				while(rs.next()) {//복수개의 레코드가 검색되는 경우는  while반복문으로 반복
					ReplyBean rrb=new ReplyBean();
					
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

		/*public ReplyBean getReplyView(int board_no) {
			ReplyBean rc=null;
			PreparedStatement pt = null;
			String sql = null;
			ResultSet rs=null;
			try {
				sql="select * from ReplyproductQnA where board_no=?";
				pt=con.prepareStatement(sql);
				pt.setInt(1, board_no);
				rs=pt.executeQuery();
				
				if(rs.next()) {
					rc=new ReplyBean();
					rc.setReply_no(rs.getInt("reply_no"));
					rc.setReply_name(rs.getString("reply_name"));
					rc.setReply_title(rs.getString("reply_title"));
					rc.setReply_cont(rs.getString("reply_cont"));
					rc.setReply_pwd(rs.getString("reply_pwd"));
					rc.setReply_date(rs.getString("reply_date"));
				}
			}catch(Exception e) {e.printStackTrace();}
			finally{
				try {
					if(rs != null) rs.close();
					if(pt != null) pt.close();					
					
				}catch(Exception e) {e.printStackTrace();}
			}		
			return rc;
		}*/

		

		
}
