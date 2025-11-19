package controller;

import model.AllergenModel;
import view.AllergenView;

import java.awt.event.*;
import java.util.List;

public class AllergenController implements ActionListener {
    private final AllergenModel model;
    private final AllergenView view;

    public AllergenController(AllergenView view, AllergenModel model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
        refreshAllergenTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add":
                addAllergen();
                break;
            case "Delete":
                deleteAllergen();
                break;
            case "Edit":
                editAllergen();
                break;
            case "View Food with Allergen":
                viewAllergenFoodStock();
                break;
        }
    }

    private void addAllergen() {
        String desc = view.getDescriptionInput().trim();
        if (desc.isEmpty()) {
            view.displayMessage("Please enter an allergen description.");
            return;
        }

        if (model.allergenExists(desc)) {
            view.displayMessage("This allergen already exists!");
            return;
        }

        model.addAllergen(desc);
        view.displayMessage("Allergen added successfully!");
        view.clearDescriptionInput();
        refreshAllergenTable();
    }

    private void deleteAllergen() {
        int allergenId = view.getSelectedAllergenId();
        if (allergenId < 0) {
            view.displayMessage("Please select an allergen to delete.");
            return;
        }

        model.deleteAllergen(allergenId);
        view.displayMessage("Allergen deleted successfully.");
        refreshAllergenTable();
    }

    private void editAllergen() {
        int allergenId = view.getSelectedAllergenId();
        if (allergenId < 0) {
            view.displayMessage("Please select an allergen to edit.");
            return;
        }

        String newDesc = view.promptForNewDesc();
        if (newDesc == null || newDesc.trim().isEmpty()) {
            view.displayMessage("Edit cancelled or invalid input.");
            return;
        }

        newDesc = newDesc.trim();

        if (model.allergenExists(newDesc)) {
            view.displayMessage("Another allergen with this description already exists!");
            return;
        }

        model.editAllergen(allergenId, newDesc);
        view.displayMessage("Allergen updated successfully!");
        refreshAllergenTable();
    }

    private void refreshAllergenTable() {
        List<String[]> allergens = model.listAllergensTable();
        view.setAllergenData(allergens);
    }

    public void viewAllergenFoodStock() {

        int allergenId = view.getSelectedAllergenId();

        if (allergenId < 0) {
            view.displayMessage("Please select an allergen first.");
            return;
        }

        List<String> foods = model.linkAllergensToFoodStock(allergenId);

        if (foods.isEmpty()) {
            view.displayLinkedFoods("No foods contain this allergen.");
            return;
        }

        StringBuilder sb = new StringBuilder("Foods containing this allergen:\n\n");
        for (String f : foods) {
            sb.append("• ").append(f).append("\n");
        }

        view.displayLinkedFoods(sb.toString());
    }
}
