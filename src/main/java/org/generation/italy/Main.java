package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	private static final String URL = "jdbc:mysql://localhost:3306/nations";
	private static final String USER = "root";
	private static final String PSW = "root";
	
	public static void main(String[] args) {
		nations();
	}
	
	private static void nations() {
      try(Connection con = DriverManager.getConnection(URL, USER, PSW)) {
			
			final String sql = " SELECT countries.country_id AS id, countries.name AS nations, regions.name AS regions, continents.name AS continents "
					         + " FROM countries "
					         + " JOIN regions "
					         + "  ON countries.region_id = regions.region_id "
					         + " JOIN continents "
					         + "  ON regions.region_id = continents.continent_id "
					         + " WHERE countries.name LIKE ?"
					         + " ORDER BY countries.name ";
			
			try(PreparedStatement ps = con.prepareStatement(sql)){
				
				System.out.print("Inserisci nazione: ");
				Scanner sc = new Scanner(System.in);
				String nts = sc.nextLine();
				sc.close();
				
				ps.setString(1, nts);
				
				try(ResultSet rs = ps.executeQuery()){
					
					while(rs.next()) {
						
						final int id = rs.getInt(1);
						final String countries = rs.getString(2);
						final String regions = rs.getString(3);
						final String continents = rs.getString(4);
						
						System.out.println("Id: " + id + " - " + "Nazione: " + countries + " - "
								            + "Regione: " +  regions + " - " + "Continente: " + continents
								          );
					}
				}
			}
			
		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}

