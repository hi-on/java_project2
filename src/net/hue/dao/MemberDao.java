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

import net.hue.bean.MemberBean;

public class MemberDao {
	private Connection conn = null;

	private static MemberDao instance;

	public static MemberDao getInstance() {
		if (instance == null) {
			instance = new MemberDao();
		}
		return instance;
	}

	private MemberDao(){
		try {
			/* Context.xml을 살펴봐서 설정을 읽어본다 */
			Context initContext = new InitialContext();
			/* 내가 설정한 Context.xml 정보가 comp/env 이런 폴더안에 저장됨 */
			Context envContext = (Context) initContext.lookup("java:comp/env"); // java:comp/env 에 설정 정보가 저장되는건 내가 임의로 수정할 수 없음.
			/* 위 폴더가서 jbdc/OracleDb 이름으로 설정한 것을가져와라 */
			DataSource ds = (DataSource) envContext.lookup("jdbc/OracleDB");
			// 사용자가 사이트에 접속하면 컨넥션 객체를 얻음. 그리고 이 컨넥션 객체를 가지고 로그인을 하고 자시고 하는거임. 등등의 DB작업
			conn = ds.getConnection(); // 설정 정보를 가지고 계정에 접송해서 Connection 
			System.out.println("생성자에서 conn :" + conn);
		} catch (NamingException e) {
			System.out.println("CategoryDao 생성자에서 컨넥션 객체를 얻다가 오류 발생");
		} catch (SQLException e) {
			System.out.println("CategoryDao 생성자에서 컨넥션 객체를 얻다가 오류 발생");
		}
	}
	
	public int insertMember(MemberBean mbean) { // 회원가입
	
		int cnt = 0;
		
		PreparedStatement ps = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = "insert into member (no,id,password,name,zip,addr1,addr2"
					+ ",mobile1,mobile2,mobile3,email,gender,memdate) "
					+ "values(memseq.nextval,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			ps = conn.prepareStatement(sql);

			ps.setString(1, mbean.getId());;
			ps.setString(2, mbean.getPassword());
			ps.setString(3, mbean.getName());
			ps.setString(4, mbean.getZip());
			ps.setString(5, mbean.getAddr1());
			ps.setString(6, mbean.getAddr2());
			ps.setString(7, mbean.getMobile1());
			ps.setString(8, mbean.getMobile2());
			ps.setString(9, mbean.getMobile3());
			ps.setString(10, mbean.getEmail());
			ps.setString(11, mbean.getGender());

			// 4. SQL문 실행
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("insertMember() 실행중 에러");
			System.out.println(e);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return cnt;
	}
		
	
		public MemberBean getMemberById(String memid) { // 메인화면열릴시 세션있는지 확인하기 위해 아이디 체크
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		MemberBean mbean = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "select * from member where id=? and state=1";
			ps = conn.prepareStatement(sql);

			ps.setString(1, memid);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();

			if (rs.next()) {
				mbean = new MemberBean();
				
				mbean.setNo(rs.getInt("no"));
				mbean.setId(rs.getString("id"));
				mbean.setPassword(rs.getString("password"));
				mbean.setName(rs.getString("name"));
				mbean.setZip(rs.getString("zip"));
				mbean.setAddr1(rs.getString("addr1"));
				mbean.setAddr2(rs.getString("addr2"));
				mbean.setMobile1(rs.getString("mobile1"));
				mbean.setMobile2(rs.getString("mobile2"));
				mbean.setMobile3(rs.getString("mobile3"));
				mbean.setEmail(rs.getString("email"));
				mbean.setGender(rs.getString("gender"));
			}
		} catch (SQLException e) {
			System.out.println("getMemberById() 실행중 에러");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return mbean;
	}

	public MemberBean getMemberByInfo(String id) {// 로그인
	

		PreparedStatement ps = null;
		ResultSet rs = null;

		MemberBean mbean = null;

		try {
			// 3. SQL 작상 및 분석
			String sql = "select * from member where id=? and state=1";
			ps = conn.prepareStatement(sql);

			ps.setString(1, id);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();
			
			if (rs.next()) {
				mbean = new MemberBean();
				
				mbean.setNo(rs.getInt("no"));
				mbean.setId(rs.getString("id"));
				mbean.setPassword(rs.getString("password"));
				mbean.setName(rs.getString("name"));
				mbean.setZip(rs.getString("zip"));
				mbean.setAddr1(rs.getString("addr1"));
				mbean.setAddr2(rs.getString("addr2"));
				mbean.setMobile1(rs.getString("mobile1"));
				mbean.setMobile2(rs.getString("mobile2"));
				mbean.setMobile3(rs.getString("mobile3"));
				mbean.setEmail(rs.getString("email"));
				mbean.setGender(rs.getString("gender"));
			}
		} catch (SQLException e) {
			System.out.println("getMemberByInfo() 실행중 에러");
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return mbean;
	}
	

	public MemberBean idCheck(String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		MemberBean m = null;
		
		try {
			String sql = "select * from member where id='"+id+"'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);  // 검색 쿼리문 수행해서 결과 회원정보를 rs에 저장함
			if(rs.next()) {
				m = new MemberBean();
				m.setId(rs.getString("id"));
			}
		}catch (SQLException e) {
			System.out.println("idCheck() 실행중 에러");
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			}catch (SQLException e) {
				System.out.println(e);
			}
		}
		return m;
	}
	
	// 아이디 찾기
	public MemberBean findId(MemberBean m) {
		MemberBean mid = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from member where name='"+m.getName()+"' and email='"
					+m.getEmail()+"' and state=1";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				mid = new MemberBean();
				mid.setId(rs.getString("id"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			}catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		return mid;
	}
	
	
	// 비밀번호 찾기
	public MemberBean findPwd(MemberBean m) {
		MemberBean mpwd = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from member where id='"+m.getId()+"' and name='"
					+m.getName()+"' and email='"+m.getEmail()+"' and state=1";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				mpwd = new MemberBean();
				mpwd.setPassword(rs.getString("password"));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			}catch (SQLException e) {
				System.out.println(e);
			}
		}
		return mpwd;
	}
	
	//회원 탈퇴
	public int delMember(MemberBean mbean) {
		int  cnt = -1;
		
		PreparedStatement ps = null;
		
		try {
			String sql= "update member set state=2 ,deldate=sysdate where id=? And password=? And email=?";
			ps= conn.prepareStatement(sql);
			
			ps.setString(1, mbean.getId());
			ps.setString(2, mbean.getPassword());
			ps.setString(3, mbean.getEmail());
			
			cnt = ps.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("회원탈퇴 SQL문 실패");
		}finally {
			try {
				if(ps != null) ps.close();
				}catch(SQLException e) {
				System.out.println("회원탈퇴 수행시 오라클 접속종료 실패");
			}//catch
		}//finally
		return cnt;
	}

	public int updateMember(MemberBean mbean, int mno) { //회원정보 수정시

		int cnt = 0;
		
		PreparedStatement ps = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = "update member set id=?,name=?,zip=?,addr1=?,addr2=?"
					+ ",mobile1=?,mobile2=?,mobile3=?,email=?,gender=?"
					+ " where no=? and password=?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, mbean.getId());;
			ps.setString(2, mbean.getName());
			ps.setString(3, mbean.getZip());
			ps.setString(4, mbean.getAddr1());
			ps.setString(5, mbean.getAddr2());
			ps.setString(6, mbean.getMobile1());
			ps.setString(7, mbean.getMobile2());
			ps.setString(8, mbean.getMobile3());
			ps.setString(9, mbean.getEmail());
			ps.setString(10, mbean.getGender());
			ps.setInt(11, mno);
			ps.setString(12, mbean.getPassword());

			// 4. SQL문 실행
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("updateMember() 실행중 에러");
			System.out.println(e);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return cnt;
	}

	public MemberBean contmember(int mno) {
		
		MemberBean mbean = new MemberBean();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 3. SQL 작상 및 분석
			String sql = "select * from member where no=?";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, mno);
			
			// 4. SQL문 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				mbean.setNo(rs.getInt("no"));
				mbean.setId(rs.getString("id"));
				mbean.setPassword(rs.getString("password"));
				mbean.setName(rs.getString("name"));
				mbean.setZip(rs.getString("zip"));
				mbean.setAddr1(rs.getString("addr1"));
				mbean.setAddr2(rs.getString("addr2"));
				mbean.setMobile1(rs.getString("mobile1"));
				mbean.setMobile2(rs.getString("mobile2"));
				mbean.setMobile3(rs.getString("mobile3"));
				mbean.setEmail(rs.getString("email"));
				mbean.setGender(rs.getString("gender"));
				mbean.setState(rs.getInt("state"));
				mbean.setMemdate(rs.getString("memdate"));
			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("allMember() SQL문 실행중 오류 발생");
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				System.out.println("접속 종료 실패");
			}
		}
		return mbean;
	}
	
	/*
	// admin페이지에 사용자 목록
			public ArrayList<MemberBean> allMember() {
			
			ArrayList<MemberBean> list = new ArrayList<MemberBean>();
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				// 3. SQL 작상 및 분석
				String sql = "select * from member order by no asc";
				ps = conn.prepareStatement(sql);
				
				// 4. SQL문 실행
				rs = ps.executeQuery();
				
				while(rs.next()) {
					MemberBean mbean = new MemberBean();
					
					mbean.setNo(rs.getInt("no"));
					mbean.setId(rs.getString("id"));
					mbean.setPassword(rs.getString("password"));
					mbean.setName(rs.getString("name"));
					mbean.setZip(rs.getString("zip"));
					mbean.setAddr1(rs.getString("addr1"));
					mbean.setAddr2(rs.getString("addr2"));
					mbean.setMobile1(rs.getString("mobile1"));
					mbean.setMobile2(rs.getString("mobile2"));
					mbean.setMobile3(rs.getString("mobile3"));
					mbean.setEmail(rs.getString("email"));
					mbean.setGender(rs.getString("gender"));
					mbean.setState(rs.getInt("state"));
					mbean.setMemdate(rs.getString("memdate"));
					mbean.setDeldate(rs.getString("deldate"));
					
					list.add(mbean);
				}

			} catch (SQLException e) {
				System.out.println(e);
				System.out.println("allMember() SQL문 실행중 오류 발생");
			} finally {
				try {
					if (ps != null)
						ps.close();
				} catch (SQLException e) {
					System.out.println("접속 종료 실패");
				}
			}
			return list;
		}// allMember()
		*/
			
	//검색 전후 유저 목록
	public int getListCount(MemberBean findR) {

		int count=0;
		
		PreparedStatement pt = null;
		ResultSet rs=null;
		try {
		
			String sql="select count(no) from member ";
			if(findR.getFind_field()==null) {
				//검색 전 개수(검색 필드 내용 없을 때)
				sql+="";
			}else if(findR.getFind_field().equals("id")) {
				sql=" where id like ?";//글제목이랑 같을 때
			}
			
			pt=conn.prepareStatement(sql);
			
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
	public List<MemberBean> getReviewList(int page, int limit, MemberBean findR) {
		List<MemberBean> ulist=new ArrayList<>();
		PreparedStatement pt = null;
		ResultSet rs=null;
		
		try {
			
			String sql="select * from (select rowNum rNum, no, id, password, name, zip, "
					+ "addr1, addr2, mobile1, mobile2, mobile3, email, gender, state, "
					+ "memdate, deldate from (select * from member ";
			if(findR.getFind_field() == null) {//검색전
				sql+="";
			}else if(findR.getFind_field().equals("id")) {
				sql+=" where id like ?";
			}
			sql+=" order by no asc)) where rNum>=? and rNum<=?";
			/* 페이징과 검색관련 쿼리문,  rowNum컬럼은 오라클에서 테이블 생성시 추가되는 컬럼으로 최초 레코드 저장
			 * 시 일련 번호값이 알아서 저장된다. rNum은 rowNum 컬럼의 별칭명이다.
			 */
			
			pt=conn.prepareStatement(sql);
			
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
				MemberBean u=new MemberBean();
				u.setNo(rs.getInt("no"));
				u.setId(rs.getString("id"));
				u.setPassword(rs.getString("password"));
				u.setName(rs.getString("name"));
				u.setZip(rs.getString("zip"));
				u.setAddr1(rs.getString("addr1"));
				u.setAddr2(rs.getString("addr2"));
				u.setMobile1(rs.getString("mobile1"));
				u.setMobile2(rs.getString("mobile2"));
				u.setMobile3(rs.getString("mobile3"));
				u.setEmail(rs.getString("email"));
				u.setGender(rs.getString("gender"));
				u.setState(rs.getInt("state"));
				u.setMemdate(rs.getString("memdate"));
				u.setDeldate(rs.getString("deldate"));
				
				ulist.add(u);//컬렉션에 추가
			}
			
		}catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pt != null) pt.close();				
			}catch(Exception e) {e.printStackTrace();}
		}
		return ulist;
	}


}
