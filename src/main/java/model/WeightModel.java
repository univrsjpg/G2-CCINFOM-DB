package model;

import java.util.ArrayList;
import java.sql.*;              // SQL Functionality
import java.time.*;             // Date Conversion,

public class WeightModel
{
    private Connection petTracker;

    public WeightModel()
    {
        petTracker = createConnection();
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

    public void updateRecentWeight(double petWeight)
    {
        String query = "INSERT INTO pet (weight) VALUES (?)";

        try {
            PreparedStatement statement = petTracker.prepareStatement(query);

            statement.setDouble(1, petWeight);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
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

    // reminder to change this for sql once it works out
    private Connection createConnection() throws RuntimeException
    {
        Connection connection = null;
        try {
            // loading the JDBC Driver .. Change mariadb to mysql hyperlink when necessary
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("driver loaded");

            // this is for my laptop , change user and password args as needed ,
            // also the jdbc:mariadb
            connection = DriverManager.getConnection
                    ("jdbc:mariadb://localhost/pettracker", "celia", "Myckelyn29");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        System.out.println("It works!");
        return connection;
    }
}