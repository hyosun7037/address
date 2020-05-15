package address.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// ��� ���Ŵϱ� �޼���� ��������
public class DBUtils {
	
	// Connection, PreparedStatement ����
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("DB����� ������ �߻� : " + e.getMessage());
		}
	}

	// Connection, PreparedStatement, ResultSet ����
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("DB����� ������ �߻� : " + e.getMessage());
		}
	}
}
