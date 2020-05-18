package address.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Member {

	/* �Ϲ����� ���̵� private String username; */

	/* �ѹ����Ҷ� id or memeberid(mId) */
	private int id; // PK
	private String name; // �̸�
	private String phone; // ��ȭ��ȣ (������ ���� ���� ������ String���� ����)
	private String address; // �ּ�

	// �׷� : ģ��, ȸ��, �б�, ����
	// private String group; --> �̷��� ����� ������ ������ X

	// getter, setter �����
	private GroupType groupType;

	@Override
	public String toString() {
		return id + ". " + name; // �� �������� ��µǵ��� ��, object�� ������ �ִ� �޼���
	}

	// ���̵����� ������
	@Builder
	public Member(int id, String name, String phone, String address, GroupType groupType) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.groupType = groupType;
	}

}
