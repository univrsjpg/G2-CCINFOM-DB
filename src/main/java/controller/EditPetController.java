package controller;

import view.EditPetView;
import model.EditPetModel;
import java.util.ArrayList;

import java.awt.event.*;

public class EditPetController implements ActionListener {
	private final EditPetView view;
	private final EditPetModel model;

	private int pet_id;
	private String gender;

	public EditPetController(EditPetView view, EditPetModel model, int pet_id) {
		this.view = view;
		this.model = model;
		this.pet_id = pet_id;
		this.view.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch(command) {
			case "Save": saveEdits(); break;
			case "Exit": view.dispose(); break;

			case "Female":
			case "Male":
				this.gender = command;
				view.updateGender(command);
				break;
		}
	}

	private void saveEdits() {
		ArrayList<ArrayList<String>> changes = view.sendInfo();
		int numChanges = 0;

		if (gender != null){
			model.changeGender(pet_id, gender);
			numChanges++;}

		for (int i = 0; i < changes.size(); i++) {
			if (changes.get(i).get(0).equals("pet_name")){
				model.changeName(pet_id, changes.get(i).get(1));
			numChanges++;
			}

			else if (changes.get(i).get(0).equals("species")) {
				model.changeSpecies(pet_id, changes.get(i).get(1));
				numChanges++;
			}

			else if (changes.get(i).get(0).equals("age")) {
				try {
					int age = Integer.parseInt(changes.get(i).get(1));
					model.changeAge(pet_id, age);
					numChanges++;
				}
				catch (Exception e) {
					continue;
				}
			}
		}

		if (numChanges == 0) {
			view.showMessage("No changes have been made.");}
		else {
			view.showMessage("Pet has been edited! Please exit pet info page to see changes :)");
		}

		view.dispose();
		
	}
}