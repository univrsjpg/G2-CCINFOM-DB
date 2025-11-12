package controller;

import view.MainMenuView1;
import model.MainMenuModel1;

import view.FoodStockView;
import model.FoodStockModel;

import view.MainMenuView2;

import javax.swing.JButton;
import java.awt.event.*;
import java.awt.Point;
import java.util.List;

// The first main menu of the program, allows user to create a pet, select a pet, or view the food stock.
public class MainMenuController1 implements ActionListener {
	private final MainMenuView1 view;
	private final MainMenuModel1 model;

	// Initializes MainMenu1 by setting view and model.
	public MainMenuController1(MainMenuView1 view, MainMenuModel1 model) {
		this.view = view;
		this.model = model;
		refreshList();
		this.view.addActionListener(this);
	}

	// Selects a method depending on the action performed by the user.
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("SelectPet")){
			JButton source = (JButton) e.getSource();
			int petId = (int) source.getClientProperty("pet_id");
			String petName = (String) source.getClientProperty("pet_name");
			selectPet(petId, petName);
		} else {
			switch (command) {
				case "AddPet" -> addPet();
				case "ViewStock" -> viewStock();
			}
		}
	}

	// Allows the user to select a pet.
	private void selectPet(int petId, String pet_name) {
		System.out.println("Selected pet: " + petId);

		view.dispose();

		MainMenuView2 menu2View = new MainMenuView2(pet_name);
		new MainMenuController2 (menu2View, petId);

		menu2View.setVisible(true);
	}

	// Allows the user to add a pet.
	private void addPet() {
		System.out.println("Adding a pet");
	}

	// Allows the user to check the food stock.
	private void viewStock() {
		System.out.println("Checking stock");

		view.dispose();

		FoodStockView stockView = new FoodStockView();
		FoodStockModel stockModel = new FoodStockModel();
		new FoodStockController(stockView, stockModel);

		/*
		stockView.setLocationRelativeTo(view);
		Point menuPos = view.getLocation();
		stockView.setLocation(menuPos.x + 50, menuPos.y + 30);
		*/

		stockView.setVisible(true);
	}

	// Updates the list of pets.
	private void refreshList() {
		List<MainMenuModel1.Pet> pets = model.getPetNames();
		view.updatePetList(pets);
	}
}