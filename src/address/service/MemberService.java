package address.service;


import java.util.List;

import address.dao.MemberDao;
import address.model.Member;

public class MemberService {

	// 싱글톤 만들기 : 생성자만들고, private으로 변경
	private MemberService() {
	}

	private static MemberService instance = new MemberService();

	public static MemberService getInstance() {
		return instance;
	}

	private MemberDao memberDao = MemberDao.getInstance();

	public int 주소록추가(Member member) {

		/* 다섯가지 로직은 추가할 때 쓰는 것 */

		// 3. DAO에 접근해서 추가함수 호출(Member) 전달
		return memberDao.추가(member);

	}
	
	public List<Member> 전체목록(){
		return memberDao.전체목록();
	}
}
