package controller;

import view.RemovePetView;
import model.RemovePetModel;

import view.MainMenuView1;
import model.MainMenuModel1;

import javax.swing.JButton;
import java.awt.event.*;
import java.util.List;

// Remove pet
public class RemovePetController implements ActionListener {
	private final RemovePetView view;
	private final RemovePetModel model;

	public RemovePetController(RemovePetView view, RemovePetModel model) {
		this.view = view;
		this.model = model;
		refreshList();
		this.view.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "RemovePet":
				JButton source = (JButton) e.getSource();
				int petId = (int) source.getClientProperty("pet_id");
				String petName = (String) source.getClientProperty("pet_name");
				removePet(petId, petName);
				break;
			case "Back": goBack(); break;
		} 
	}

	private void removePet(int petId, String petName) {
		model.removePet(petId);
		view.showMessage(String.format("%s has been removed.", petName));
		goBack();
	}

	private void goBack() {
		view.dispose();

		MainMenuView1 menuView = new MainMenuView1();
		MainMenuModel1 menuModel = new MainMenuModel1();
		new MainMenuController1(menuView, menuModel);

		menuView.setVisible(true);
	}

	private void refreshList() {
		List<MainMenuModel1.Pet> pets = model.getPets();
		view.updatePetList(pets);
	}
}