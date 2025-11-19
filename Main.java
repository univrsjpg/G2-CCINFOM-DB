import model.AllergenModel;
import controller.AllergenController;
import view.AllergenView;

import model.PetAllergenModel;
import view.PetAllergenView;
import controller.PetAllergenController;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            int petId = 1; // temp for testing

            PetAllergenModel paModel = new PetAllergenModel();
            PetAllergenView paView = new PetAllergenView(null);
            PetAllergenController paController =
                    new PetAllergenController(paView, paModel);

            AllergenView view = new AllergenView(petId);
            AllergenModel model = new AllergenModel();
            new AllergenController(view, model, paController);

            view.setVisible(true);
        });
    }
}
