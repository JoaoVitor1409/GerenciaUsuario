package admin;

import java.sql.*;

public class ConnectionFactory {
	public static Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost/Usuario?useTimezone=true&serverTimezone=America/Sao_Paulo";
		
		return DriverManager.getConnection(url, "root", "");
			
	}
}