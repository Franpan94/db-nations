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
      try(Connection con = DriverManager.getConnection(URL, USER, PSW); Scanner sc = new Scanner(System.in)) {
			
			final String sql = " SELECT countries.country_id AS id, countries.name AS nations, regions.name AS regions, continents.name AS continents "
					         + " FROM countries "
					         + " JOIN regions "
					         + "  ON countries.region_id = regions.region_id "
					         + " JOIN continents "
					         + "  ON regions.continent_id = continents.continent_id "
					         + " WHERE countries.name LIKE ? "
					         + " ORDER BY countries.name ";
			
			try(PreparedStatement ps = con.prepareStatement(sql)){
				
				System.out.print("Inserisci nazione: ");
				String nts = sc.nextLine();
				
				ps.setString(1, '%' + nts + '%');
				
				
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
			
			System.out.println("");
			System.out.print("Scegli id: ");
			int ntsId = sc.nextInt();
			
			final String sql1 = " SELECT languages.language AS languageName "
			         + " FROM countries "
			         + " JOIN country_languages "
			         + "  ON countries.country_id = country_languages.country_id "
			         + " JOIN languages "
			         + "  ON country_languages.language_id = languages.language_id "
			         + " WHERE countries.country_id = ? ";
			
            try(PreparedStatement ps = con.prepareStatement(sql1)){
				
				ps.setInt(1, ntsId);
				
				try(ResultSet rs = ps.executeQuery()){
					
					while(rs.next()) {
						
						final String languages = rs.getString(1);
						
						System.out.print(languages + (!rs.isLast() ? ", " : ""));
					}
				}
			}
			
			
            System.out.println("");
			
			final String sql2 = " SELECT country_stats.year AS year, country_stats.population AS population, country_stats.gdp AS gdp "
			         + " FROM countries "
			         + " JOIN country_stats "
			         + "  ON countries.country_id = country_stats.country_id "
			         + " WHERE countries.country_id = ? ";
			
            try(PreparedStatement ps = con.prepareStatement(sql2)){
				
                ps.setInt(1, ntsId);
				
				try(ResultSet rs = ps.executeQuery()){
					
					if(rs.next()) {
						
						final String year = rs.getString(1);
						final String population = rs.getString(2);
						final String gdp = rs.getString(3);
						
						System.out.println("Statistiche recenti: " + "\nAnno: " + year + ", "  
						                   + "popolazione: " + population + ", " + "Gdp: "+ gdp + ", "
						                  );
					}
				}
			}
		
			
		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}