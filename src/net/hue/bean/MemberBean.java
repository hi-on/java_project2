package net.hue.bean;

public class MemberBean {

	int no; // 식별자
	String id; // 아이디
	String password; // 패스워드
	String name; // 이름
	String zip; // 우편번호
	String addr1; // 주소
	String addr2; // 상세주소
	String mobile1; // 핸드폰 앞
	String mobile2; // 핸드폰 중
	String mobile3; // 핸드폰 뒤
	String email; //이메일
	String gender; // 성별
	int state; // 회원여부
	String memdate;
	String deldate;
	
	//페이징(쪽 나누기) 관련 변수
		private int startrow;//시작행 번호
		private int endrow;//끝행 번호
		
		//검색 기능 관련 변수 
		private String find_field;//검색필드
		private String find_name;//검색어

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getMobile3() {
		return mobile3;
	}
	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMemdate() {
		return memdate;
	}
	public void setMemdate(String memdate) {
		this.memdate = memdate.substring(0,10);
	}
	public String getDeldate() {
		return deldate;
	}
	public void setDeldate(String deldate) {
		if(deldate != null) {
			this.deldate = deldate.substring(0,10);
		}
	}
	
	public int getStartrow() {
		return startrow;
	}
	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}
	public int getEndrow() {
		return endrow;
	}
	public void setEndrow(int endrow) {
		this.endrow = endrow;
	}
	public String getFind_field() {
		return find_field;
	}
	public void setFind_field(String find_field) {
		this.find_field = find_field;
	}
	public String getFind_name() {
		return find_name;
	}
	public void setFind_name(String find_name) {
		this.find_name = find_name;
	}	
}