package controller;

import model.AllergenModel;
import view.AllergenView;

import model.MainMenuModel2;
import view.MainMenuView2;

import java.awt.event.*;
import java.util.List;

import javax.swing.SwingUtilities;

public class AllergenController implements ActionListener {

    private final AllergenModel model;
    private final AllergenView view;
    private final PetAllergenController petAllergenController;

    public AllergenController(AllergenView view,
                              AllergenModel model,
                              PetAllergenController petAllergenController) {

        this.view = view;
        this.model = model;
        this.petAllergenController = petAllergenController;

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

            case "View Pet Allergy Record":
                petAllergenController.showPetRecord(view.getPetId());
                break;
                case "BackToMainMenu":
                backToMenu();
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
        view.displayMessage("Allergen added successfully.");
        view.clearDescriptionInput();
        refreshAllergenTable();
    }

    private void deleteAllergen() {
        int id = view.getSelectedAllergenId();
        if (id < 0) {
            view.displayMessage("Please select an allergen to delete.");
            return;
        }

        model.deleteAllergen(id);
        view.displayMessage("Allergen deleted successfully.");
        refreshAllergenTable();
    }

    private void editAllergen() {
        int id = view.getSelectedAllergenId();
        if (id < 0) {
            view.displayMessage("Please select an allergen to edit.");
            return;
        }

        String newDesc = view.promptForNewDesc();
        if (newDesc == null) return;

        newDesc = newDesc.trim();
        if (newDesc.isEmpty()) {
            view.displayMessage("Invalid input.");
            return;
        }

        if (model.allergenExists(newDesc)) {
            view.displayMessage("This allergen already exists!");
            return;
        }

        model.editAllergen(id, newDesc);
        view.displayMessage("Allergen updated successfully.");
        refreshAllergenTable();
    }

    private void refreshAllergenTable() {
        view.setAllergenData(model.listAllergensTable());
    }

    private void viewAllergenFoodStock() {
        int allergenId = view.getSelectedAllergenId();

        if (allergenId < 0) {
            view.displayMessage("Select an allergen.");
            return;
        }

        List<String> foods = model.linkAllergensToFoodStock(allergenId);

        if (foods.isEmpty()) {
            view.displayLinkedFoods("No foods contain this allergen.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String f : foods) sb.append("• ").append(f).append("\n");

        view.displayLinkedFoods(sb.toString());
    }

        private void backToMenu() {
        view.dispose();

        MainMenuView2 menu2View = new MainMenuView2();
        MainMenuModel2 menu2Model = new MainMenuModel2();
        new MainMenuController2(menu2View, menu2Model, view.getPetId());

        menu2View.setVisible(true);
    }
}
