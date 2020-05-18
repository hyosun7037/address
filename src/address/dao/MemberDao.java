package address.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import address.db.DBConnection;
import address.db.DBUtils;
import address.gui.MainFrame;
import address.model.GroupType;
import address.model.Member;

// DAO = Date Access Object는 DB connection해줌, 사람마다 다른 DAO를 써야함
// static X
public class MemberDao {
	
	// new 못하게 private로 만든다.
	private MemberDao() {} // 한번 new 하면 계속 똑같은 걸 쓸 수 있음
		private static MemberDao instance = new MemberDao();
		public static MemberDao getInstance() {
			return instance;
	}	
		
	// IO는 database 에서 가져오는 것 이기 때문에 static으로 하면 시간이 지체된다.
		
		
	// DML은 return값이 int, 리턴되는 값은 변경된 행의 갯수	, -1이면 오류
	public int 추가(Member member) { 
		
		// 시퀀스 이름 memmber_seq 오라클 변수 enxtval 숫자를 받아옴
		final String SQL = "INSERT INTO member(id, name, phone, address, groupType) "
						  + "VALUES(member_seq.nextval, ?, ?, ?, ?)";
		
		// 변수 선언 (finally에서 쓰기 위해서)
		Connection conn = null;
		PreparedStatement  pstmt = null;
		
		// Connection은 stack으로 관리, 전역으로 관리하면 부하가 너무 많음
		
		try {
			// 1. 스트림 연결 (바로바로 닫아줘야함, 직접 닫아줘야함)
			conn = DBConnection.getConnection(); // 스트림
			
			// 2. 버퍼달기 (?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			
			// 3. 물음표 완성
			pstmt.setString(1, member.getName()); //1번째는 이름 불러오기
			pstmt.setString(2, member.getPhone()); //2번째는 번호 불러오기
			pstmt.setString(3, member.getAddress()); //3번째는 주소 불러오기
			pstmt.setString(4, member.getGroupType().toString()); //4번째는 그룹불러오기
			
			// 4. 쿼리전송 (flush + commit)
			int result = pstmt.executeUpdate();
			return result;
			
		} catch (Exception e) {
			System.out.println("추가오류 : " + e.getMessage()); // 오류 메세지를 확인할 수 있음
		 // System.out.println("추가오류 : " + e.printStackTrace()); // 내가 모른 오류도 다 추척해서 알려줌 (좀 더 자세함)
		}
		
		// 무조건 실행됨
		finally {
			DBUtils.close(conn, pstmt);
			
		}
		
		return -1;
	}
	
	public int 삭제(int memberId) { // PK
		
		// 시퀀스 이름 memmber_seq 오라클 변수 enxtval 숫자를 받아옴
				final String SQL = "DELETE * FROM member WHERE id = ?";
				
				// 변수 선언 (finally에서 쓰기 위해서)
				Connection conn = null;
				PreparedStatement  pstmt = null;
				
				// Connection은 stack으로 관리, 전역으로 관리하면 부하가 너무 많음
				
				try {
					// 1. 스트림 연결 (바로바로 닫아줘야함, 직접 닫아줘야함)
					conn = DBConnection.getConnection(); // 스트림
					
					// 2. 버퍼달기 (?를 쓸 수 있는 버퍼)
					pstmt = conn.prepareStatement(SQL);
					
					// 3. 물음표 완성
					pstmt.setInt(1, memberId); //1번째는 이름 불러오기


					// 4. 쿼리전송 (flush + commit)
					int result = pstmt.executeUpdate();
					return result;
					
				} catch (Exception e) {
					System.out.println("삭제오류 : " + e.getMessage()); // 오류 메세지를 확인할 수 있음
				}
				
				// 무조건 실행됨
				finally {
					DBUtils.close(conn, pstmt);
				}
				
				return -1;
	}
	
	public int 수정(Member member) {
		// 시퀀스 이름 memmber_seq 오라클 변수 enxtval 숫자를 받아옴
				final String SQL = "UPDATE member SET name=?, phone=?, address=?, groupType=? WHERE id=? ";
				
				// 변수 선언 (finally에서 쓰기 위해서)
				Connection conn = null;
				PreparedStatement  pstmt = null;
				
				try {
					// 1. 스트림 연결 (바로바로 닫아줘야함, 직접 닫아줘야함)
					conn = DBConnection.getConnection(); // 스트림
					
					// 2. 버퍼달기 (?를 쓸 수 있는 버퍼)
					pstmt = conn.prepareStatement(SQL);
					
					// 3. 물음표 완성
					pstmt.setString(1, member.getName()); //1번째는 이름 불러오기
					pstmt.setString(2, member.getPhone()); //2번째는 번호 불러오기
					pstmt.setString(3, member.getAddress()); //3번째는 주소 불러오기
					pstmt.setString(4, member.getGroupType().toString()); //4번째는 그룹불러오기
					pstmt.setInt(5, member.getId()); //4번째는 그룹불러오기
					
					// 4. 쿼리전송 (flush + commit)
					int result = pstmt.executeUpdate();
					return result;
					
				} catch (Exception e) {
					System.out.println("추가오류 : " + e.getMessage()); // 오류 메세지를 확인할 수 있음
				}
				
				// 무조건 실행됨
				finally {
					DBUtils.close(conn, pstmt);
					
				}
				
				return -1;
	}
	
	
	// DQL은 return값이 ResultSet == Cursor / null은 값을 찾지 못한 것
	public Member 상세보기(int id) { // PK
		
		final String SQL = "SELECT * FROM member WHERE id = ?";
		
		Connection conn = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		Member member = null;
		
		try {
			// 1. 스트림 연결 (바로바로 닫아줘야함, 직접 닫아줘야함)
			conn = DBConnection.getConnection(); // 스트림
			
			// 2. 버퍼달기 (?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			
			// 3. 물음표 완성
			pstmt.setInt(1, id);
			
			// 4. 쿼리전송 (flush + rs받기)
			rs = pstmt.executeQuery(); // 커서가 결과 집합의 첫번째 행의 바로 위를 가리킴
			if(rs.next()) { // return 값이 true, false
				member = Member.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.groupType(GroupType.valueOf(rs.getString("groupType")))
						.build();
			}
			return member;
			
		} catch (Exception e) {
			System.out.println("추가오류 : " + e.getMessage()); // 오류 메세지를 확인할 수 있음
		 // System.out.println("추가오류 : " + e.printStackTrace()); // 내가 모른 오류도 다 추척해서 알려줌 (좀 더 자세함)
		}
		
		// 무조건 실행됨
		finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	public List<Member> 전체목록(){
		// 시퀀스 이름 memmber_seq 오라클 변수 enxtval 숫자를 받아옴
				final String SQL = "SELECT * FROM member ORDER BY id";
				
				// 변수 선언 (finally에서 쓰기 위해서)
				Connection conn = null;
				PreparedStatement  pstmt = null;
				ResultSet rs = null;
				List<Member> members = new ArrayList<>();
				// Connection은 stack으로 관리, 전역으로 관리하면 부하가 너무 많음
				
				try {
					// 1. 스트림 연결 (바로바로 닫아줘야함, 직접 닫아줘야함)
					conn = DBConnection.getConnection(); // 스트림
					
					// 2. 버퍼달기 (?를 쓸 수 있는 버퍼)
					pstmt = conn.prepareStatement(SQL);
					
					// 3. 물음표 완성
					
					// 4. 쿼리전송 (flush + rs받기)
					rs = pstmt.executeQuery(); // 커서가 결과 집합의 첫번째 행의 바로 위를 가리킴
					while(rs.next()) { // return 값이 true, false
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
					System.out.println("추가오류 : " + e.getMessage()); // 오류 메세지를 확인할 수 있음
				 // System.out.println("추가오류 : " + e.printStackTrace()); // 내가 모른 오류도 다 추척해서 알려줌 (좀 더 자세함)
				}
				
				// 무조건 실행됨
				finally {
					DBUtils.close(conn, pstmt, rs);
				}
				
				return null;
	}
	
	public List<Member> 그룹목록(GroupType groupType){ // 도메인에 강제성을 부여할 수 O
		// 시퀀스 이름 memmber_seq 오라클 변수 enxtval 숫자를 받아옴
		final String SQL = "SELECT * FROM member WHERE groupType = ?";
		
		// 변수 선언 (finally에서 쓰기 위해서)
		Connection conn = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		// Connection은 stack으로 관리, 전역으로 관리하면 부하가 너무 많음
		
		try {
			// 1. 스트림 연결 (바로바로 닫아줘야함, 직접 닫아줘야함)
			conn = DBConnection.getConnection(); // 스트림
			
			// 2. 버퍼달기 (?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			
			// 3. 물음표 완성
			pstmt.setString(1, groupType.toString()); //groupType을 string으로 변환
			// 4. 쿼리전송 (flush + rs받기)
			rs = pstmt.executeQuery(); // 커서가 결과 집합의 첫번째 행의 바로 위를 가리킴
			while(rs.next()) { // return 값이 true, false
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
			System.out.println("그룹목록 : " + e.getMessage()); // 오류 메세지를 확인할 수 있음
		 // System.out.println("추가오류 : " + e.printStackTrace()); // 내가 모른 오류도 다 추척해서 알려줌 (좀 더 자세함)
		}
		
		// 무조건 실행됨
		finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return null;
	}
}
