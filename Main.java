import controller.FoodStockController;
import model.FoodStockModel;
import view.FoodStockView;

// How to compile javac -cp ".;lib/mysql-connector-j-9.5.0.jar" Main.java controller/*.java model/*.java view/*.
// Run java -cp ".;lib/mysql-connector-j-9.5.0.jar" Main

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            FoodStockView view = new FoodStockView();
            FoodStockModel model = new FoodStockModel();
            new FoodStockController(view, model);
            view.setVisible(true);
        });
    }
}
