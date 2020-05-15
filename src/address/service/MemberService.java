package address.service;


import java.util.List;

import address.dao.MemberDao;
import address.model.Member;

public class MemberService {

	// �̱��� ����� : �����ڸ����, private���� ����
	private MemberService() {
	}

	private static MemberService instance = new MemberService();

	public static MemberService getInstance() {
		return instance;
	}

	private MemberDao memberDao = MemberDao.getInstance();

	public int �ּҷ��߰�(Member member) {

		/* �ټ����� ������ �߰��� �� ���� �� */

		// 3. DAO�� �����ؼ� �߰��Լ� ȣ��(Member) ����
		return memberDao.�߰�(member);

	}
	
	public List<Member> ��ü���(){
		return memberDao.��ü���();
	}
}
