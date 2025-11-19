package model;

import util.DBConnector;
import java.util.ArrayList;
import java.sql.*;              // SQL Functionality
import java.time.*;             // Date Conversion,

public class WeightModel
{
    private Connection petTracker;

    public WeightModel()
    {
        try {
            petTracker = DBConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function takes in all values from weight_history of a given pet.
     * @param petId - Used to identify what pet the user is talking about .
     * @return WeightUnit[] - Array of all weights for that specific pet
     */
    public WeightUnit[] readAllWeight(int petId)
    {
        ArrayList<WeightUnit> weightList = new ArrayList<WeightUnit>(10);

        try {
            Statement statement = petTracker.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM weight_history " +
                                                     "WHERE pet_id = " + petId);
            while (rs.next())
            {
                int weightId = rs.getInt("weight_id");
                double currWeight = rs.getDouble("curr_weight");
                LocalDate date = rs.getDate("date").toLocalDate();

                weightList.add(new WeightUnit(weightId, petId, currWeight, date));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weightList.toArray(new WeightUnit[0]);
    }

    /**
     * This function records the weight entry in the Weight record with the Pet
     * weight and date. Weight ID is not included as it is automatically supplemented
     * by the SQL program.
     * @param petId        :
     * @param petWeight    :
     * @param loggedDate   :
     *
     */
    public void recordWeight(int petId, double petWeight, LocalDate loggedDate)
    {
        String query = "INSERT INTO weight_history (pet_id, curr_weight, date) VALUES" +
                "(?, ?, ?)";

        try {
            PreparedStatement statement = petTracker.prepareStatement(query);

            statement.setInt(1, petId);
            statement.setDouble(2, petWeight);
            statement.setDate(3, (java.sql.Date.valueOf(loggedDate)));

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateRecentWeight(double petWeight, int petId)
    {
        String qry = "UPDATE pet SET weight = " + petWeight + " WHERE pet_id = " + petId;

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(qry);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* subclass for weight */
    public class WeightUnit {

        public WeightUnit(int weightId, int petId, double currWeight, LocalDate date)
        {
            this.weightId = weightId;
            this.petId = petId;
            this.currWeight = currWeight;
            this.date = date;
        }

        // getters
        public int getWeightId() { return weightId; }
        public int getPetId() { return petId; }
        public double getCurrWeight() { return currWeight; }
        public LocalDate getDate() { return date; }

        private int weightId;
        private int petId;
        private double currWeight;
        private LocalDate date;
    }
}