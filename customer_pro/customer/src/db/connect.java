package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//mysql접속을 위한 클래스
public class connect {

	public static Connection makeConnection(String db){
		String url = "jdbc:mysql://localhost/" + db ;
		String id = "root";
		String pass = "1234";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,id,pass);
		}catch (ClassNotFoundException e) {
			System.out.println("Drive not found");
		} catch (SQLException e) {
			System.out.println("Connection failed");
		}
		return con;
	}
	
	public static void main(String[] args) {
		Connection con = makeConnection("");
	}
}
