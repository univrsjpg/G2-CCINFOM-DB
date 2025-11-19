package controller;

import view.MainMenuView2;
import model.MainMenuModel2;

import view.MainMenuView1;
import model.MainMenuModel1;

import view.PetInfoView;
import model.PetInfoModel;

import view.WeightView;
import model.WeightModel;

import view.AllergenView;
import model.AllergenModel;
import view.PetAllergenView;
import model.PetAllergenModel;

import model.foodEatenModel;
import view.foodEatenView;

import java.awt.event.*;


// The second main menu of the program, at this point the pet has been chosen.
public class MainMenuController2 implements ActionListener {
	private final MainMenuView2 view;

	private final int pet_id;

	// Initializes MainMenu2 by setting view and model and getting the pet_id.
	public MainMenuController2 (MainMenuView2 view, MainMenuModel2 model, int pet_id) {
		this.view = view;
		this.pet_id = pet_id;
		view.setPetName(model.getPetName(pet_id));
		this.view.addActionListener(this);
	}

	// Selects a method depending on the action performed by the user.
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch (command) {
			case "ViewWeight" -> viewWeight(pet_id);
			case "ViewPetInfo" -> viewPetInfo(pet_id);
			case "RecordIntake" -> recordIntake(pet_id);
			case "ViewAllergies" -> viewAllergies(pet_id);
			case "Back" -> goBack();
		}
	}

	// Allows the user to view pet weight
	private void viewWeight(int pet_id) {
		view.dispose();

		WeightView weightView = new WeightView();
		WeightModel weightModel = new WeightModel();
		new WeightController(weightView, weightModel, pet_id);

		weightView.setVisible(true);
	}

	// Allows user to view pet info
	private void viewPetInfo(int pet_id) {
		view.dispose();

		PetInfoView infoView = new PetInfoView();
		PetInfoModel infoModel = new PetInfoModel();
		new PetInfoController(infoView, infoModel, pet_id);

		infoView.setVisible(true);
	}

	// Allows user to record pet intake
	private void recordIntake(int pet_id) {
		view.dispose();

		foodEatenView intakeView = new foodEatenView();
		new foodEatenController(intakeView, pet_id);

		intakeView.setVisible(true);
	}

	// Allows user to view allergen info
	private void viewAllergies(int pet_id) {
		view.dispose();

		AllergenView allergyView = new AllergenView(pet_id);
		AllergenModel allergyModel = new AllergenModel();
		PetAllergenController pController = new PetAllergenController(new PetAllergenView(allergyView), new PetAllergenModel());
		new AllergenController(allergyView, allergyModel, pController);

		allergyView.setVisible(true);
	}

	// Allows user to go back to main menu1
	private void goBack() {
		view.dispose();

		MainMenuView1 menu1View = new MainMenuView1();
		MainMenuModel1 menu1Model = new MainMenuModel1();
		new MainMenuController1(menu1View, menu1Model);

		menu1View.setVisible(true);
	}
}