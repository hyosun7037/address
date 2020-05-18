package address.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Member {

	/* 일반적인 아이디 private String username; */

	/* 넘버링할때 id or memeberid(mId) */
	private int id; // PK
	private String name; // 이름
	private String phone; // 전화번호 (연산할 일이 없기 때문에 String으로 생성)
	private String address; // 주소

	// 그룹 : 친구, 회사, 학교, 가족
	// private String group; --> 이렇게 만들면 도메인 설정이 X

	// getter, setter 만들기
	private GroupType groupType;

	@Override
	public String toString() {
		return id + ". " + name; // 이 형식으로 출력되도록 함, object가 가지고 있는 메서드
	}

	// 더미데이터 생성자
	@Builder
	public Member(int id, String name, String phone, String address, GroupType groupType) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.groupType = groupType;
	}

}
