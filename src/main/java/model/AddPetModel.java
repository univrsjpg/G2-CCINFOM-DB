package model;

import util.DBConnector;
import java.sql.*;
import java.util.*;

public class AddPetModel {

	public void addPet(String name, String species, String gender, int age) {
		String qry = "INSERT INTO pet (pet_name, species, gender, age) VALUES (?, ?, ?, ?)";

		try (Connection conn = DBConnector.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(qry)) {

			pstmt.setString(1, name);
			pstmt.setString(2, species);
			pstmt.setString(3, gender);
			pstmt.setInt(4, age);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}