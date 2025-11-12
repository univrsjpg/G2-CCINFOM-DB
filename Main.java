import model.AllergenModel;
import view.AllergenView;
import controller.AllergenController;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            AllergenView view = new AllergenView();         // create UI
            AllergenModel model = new AllergenModel();      // connect to DB
            new AllergenController(view, model);            // connect controller
            view.setVisible(true);                       // show the window
        });
    }
}
