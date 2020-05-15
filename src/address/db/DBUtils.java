package address.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 계속 쓸거니까 메서드로 만들어놓음
public class DBUtils {
	
	// Connection, PreparedStatement 종료
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : " + e.getMessage());
		}
	}

	// Connection, PreparedStatement, ResultSet 종료
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : " + e.getMessage());
		}
	}
}
