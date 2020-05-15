package address.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import address.db.DBConnection;
import address.db.DBUtils;
import address.model.GroupType;
import address.model.Member;

// DAO = Date Access Object�� DB connection����, ������� �ٸ� DAO�� �����
// static X
public class MemberDao {
	
	// new ���ϰ� private�� �����.
	private MemberDao() {}
		private static MemberDao instance = new MemberDao();
		public static MemberDao getInstance() {
			return instance;
	}	
		
	// IO�� database ���� �������� �� �̱� ������ static���� �ϸ� �ð��� ��ü�ȴ�.
		
		
	// DML�� return���� int, ���ϵǴ� ���� ����� ���� ����	, -1�̸� ����
	public int �߰�(Member member) { 
		
		// ������ �̸� memmber_seq ����Ŭ ���� enxtval ���ڸ� �޾ƿ�
		final String SQL = "INSERT INTO member(id, name, phone, address, groupType) "
						  + "VALUES(member_seq.nextval, ?, ?, ?, ?)";
		
		// ���� ���� (finally���� ���� ���ؼ�)
		Connection conn = null;
		PreparedStatement  pstmt = null;
		
		// Connection�� stack���� ����, �������� �����ϸ� ���ϰ� �ʹ� ����
		
		try {
			// 1. ��Ʈ�� ���� (�ٷιٷ� �ݾ������, ���� �ݾ������)
			conn = DBConnection.getConnection(); // ��Ʈ��
			
			// 2. ���۴ޱ� (?�� �� �� �ִ� ����)
			pstmt = conn.prepareStatement(SQL);
			
			// 3. ����ǥ �ϼ�
			pstmt.setString(1, member.getName()); //1��°�� �̸� �ҷ�����
			pstmt.setString(2, member.getPhone()); //2��°�� ��ȣ �ҷ�����
			pstmt.setString(3, member.getAddress()); //3��°�� �ּ� �ҷ�����
			pstmt.setString(4, member.getGroupType().toString()); //4��°�� �׷�ҷ�����
			
			// 4. �������� (flush + commit)
			int result = pstmt.executeUpdate();
			return result;
			
		} catch (Exception e) {
			System.out.println("�߰����� : " + e.getMessage()); // ���� �޼����� Ȯ���� �� ����
		 // System.out.println("�߰����� : " + e.printStackTrace()); // ���� �� ������ �� ��ô�ؼ� �˷��� (�� �� �ڼ���)
		}
		
		// ������ �����
		finally {
			DBUtils.close(conn, pstmt);
		}
		
		return -1;
	}
	
	public int ����(int id) { // PK
		return -1;
	}
	
	public int ����(Member m) {
		return -1;
	}
	
	
	// DQL�� return���� ResultSet == Cursor / null�� ���� ã�� ���� ��
	public Member �󼼺���(int id) { // PK
		return null;
	}
	
	public List<Member> ��ü���(){
		// ������ �̸� memmber_seq ����Ŭ ���� enxtval ���ڸ� �޾ƿ�
				final String SQL = "SELECT * FROM member ORDER BY id";
				
				// ���� ���� (finally���� ���� ���ؼ�)
				Connection conn = null;
				PreparedStatement  pstmt = null;
				ResultSet rs = null;
				List<Member> members = new ArrayList<>();
				// Connection�� stack���� ����, �������� �����ϸ� ���ϰ� �ʹ� ����
				
				try {
					// 1. ��Ʈ�� ���� (�ٷιٷ� �ݾ������, ���� �ݾ������)
					conn = DBConnection.getConnection(); // ��Ʈ��
					
					// 2. ���۴ޱ� (?�� �� �� �ִ� ����)
					pstmt = conn.prepareStatement(SQL);
					
					// 3. ����ǥ �ϼ�
					
					// 4. �������� (flush + rs�ޱ�)
					rs = pstmt.executeQuery(); // Ŀ���� ��� ������ ù��° ���� �ٷ� ���� ����Ŵ
					while(rs.next()) { // return ���� true, false
						members.add(Member.builder()
								.id(rs.getInt("id"))
								.name(rs.getString("name"))
								.phone(rs.getString("phone"))
								.address(rs.getString("address"))
								.groupType(GroupType.valueOf(rs.getString("groupType")))
								.build());
					}
					return members;
					
				} catch (Exception e) {
					System.out.println("�߰����� : " + e.getMessage()); // ���� �޼����� Ȯ���� �� ����
				 // System.out.println("�߰����� : " + e.printStackTrace()); // ���� �� ������ �� ��ô�ؼ� �˷��� (�� �� �ڼ���)
				}
				
				// ������ �����
				finally {
					DBUtils.close(conn, pstmt, rs);
				}
				
				return null;
	}
	
	public List<Member> �׷���(GroupType group){ // �����ο� �������� �ο��� �� O
		return null;
	}
}