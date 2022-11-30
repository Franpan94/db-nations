package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
	private static final String URL = "jdbc:mysql://localhost:3306/db-aereoporto";
	private static final String USER = "root";
	private static final String PSW = "root";
	
	public static void main(String[] args) {
		
		query1();
		query2();
		query1();
		query1();
	}
	
	private static void query1() {
      try(Connection con = DriverManager.getConnection(URL, USER, PSW)) {
			
			
			
			final String sql = "SELECT * FROM roles";
			
			try(PreparedStatement ps = con.prepareStatement(sql)){
				try(ResultSet rs = ps.executeQuery()){
					
					while(rs.next()) {
						
						final int id = rs.getInt(1);
						final String name = rs.getString(2);
						
						System.out.println(id + " - " + name);
					}
				}
			}
			
		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

