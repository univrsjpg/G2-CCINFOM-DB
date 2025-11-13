package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class PetInfoModel {

	public Pet getPetInfo(int pet_id) {
		String qry = String.format("SELECT * FROM pet WHERE pet_id = %d", pet_id);

		try (Connection conn = DBConnector.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry)) {
			
			rs.next();
			String name = rs.getString("pet_name");
			String species = rs.getString("species");
			String gender = rs.getString("gender");
			int age = rs.getInt("age");
			double weight = rs.getDouble("weight");

			Pet pet = new Pet(pet_id, name, species, gender, age, weight);
			return pet;

		} catch (SQLException e) {
			e.printStackTrace();

			return new Pet();
		}
	}

	public static class Pet {
		private int id;
		private String name;
		private String species;
		private String gender;
		private int age;
		private double weight;

		public Pet(){}

		public Pet(int id, String name, String species, String gender, int age, double weight) {
			this.id = id;
			this.name = name;
			this.species = species;
			this.gender = gender;
			this.age = age;
			this.weight = weight;
		}

		// Getters
		public String getName() { return name; }
		public String getSpecies() { return species; }
		public String getGender() { return gender; }
		public int getAge() { return age; }
		public double getWeight() { return weight; }
	}
	
}