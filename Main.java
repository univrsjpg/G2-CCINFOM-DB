import controller.MainMenuController1;
import model.MainMenuModel1;
import view.MainMenuView1;

// How to compile w/ WINDOWS: javac -cp ".;lib/mysql-connector-j-9.5.0.jar" Main.java controller/*.java model/*.java view/*.
// How to compile w/ LINUX: javac -cp ".:lib/mysql-connector-j-9.5.0.jar" Main.java controller/*.java model/*.java view/*.java

// Run w/ WINDOWS: java -cp ".;lib/mysql-connector-j-9.5.0.jar" Main
// Run w/ LINUX: java -cp ".:lib/mysql-connector-j-9.5.0.jar" Main

public class Main {
    public static void main(String[] args) {

        // Goes to FoodStock
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainMenuView1 view = new MainMenuView1();
            MainMenuModel1 model = new MainMenuModel1();
            new MainMenuController1(view, model);
            view.setVisible(true);
        });
    }
}
