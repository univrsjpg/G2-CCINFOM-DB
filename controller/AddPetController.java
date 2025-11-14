package controller;

import view.AddPetView;
import model.AddPetModel;

import view.MainMenuView1;
import model.MainMenuModel1;

import java.awt.event.*;

public class AddPetController implements ActionListener {
	private final AddPetView view;
	private final AddPetModel model;

	private String gender;

	// Controller for adding a pet to db
	public AddPetController(AddPetView view, AddPetModel model) {
		this.view = view;
		this.model = model;
		this.view.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch (command){
			case "Add": addPet(); break;
			case "Back": goBack(); break;

			case "Female":
			case "Male":
				this.gender = command;
				view.updateGender(command);
				break;
		}
	}

	private void addPet() {
		try {
			String name = view.getNameInput();
			String species = view.getSpeciesInput();
			String gender = this.gender;
			int age = Integer.parseInt(view.getAgeInput());

			model.addPet(name, species, gender, age);
			view.showMessage(String.format("%s has joined Pet Tracker!", name));
			
			goBack();

		}

		catch (Exception ex) {
			view.showMessage("Invalid input. Please check your data.");
			ex.printStackTrace();
		}
	}

	private void goBack() {
		view.dispose();

		MainMenuView1 menu1View = new MainMenuView1();
		MainMenuModel1 menu1Model = new MainMenuModel1();
		new MainMenuController1(menu1View, menu1Model);

		menu1View.setVisible(true);
	}
}