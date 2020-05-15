package address.test;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

// DB 연결
public class DBConnectionTest {
	
	@Test
	public void getConnection() {

		try {
			// 이 클래스는 DriverManager가 관리
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cos","bitc5600");

		} catch (Exception e) {
			System.out.println("DB 연결 실패 : " + e.getMessage());
		}

	}
}
