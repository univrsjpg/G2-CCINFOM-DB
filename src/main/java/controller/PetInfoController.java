package controller;

import view.PetInfoView;
import model.PetInfoModel;

import view.MainMenuView2;
import model.MainMenuModel2;

import view.EditPetView;
import model.EditPetModel;

import java.awt.event.*;

public class PetInfoController implements ActionListener {
	private final PetInfoView view;
	private final PetInfoModel model;
	private final int petId; 

	public PetInfoController(PetInfoView view, PetInfoModel model, int petId) {
		this.view = view;
		this.model = model;
		this.petId = petId;
		this.view.addActionListener(this);
		PetInfoModel.Pet pet = model.getPetInfo(petId);
		view.refresh(pet);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Edit" -> editPet();
			case "Back" -> goBack();
		}
	}

	private void editPet() {
		EditPetView editView = new EditPetView();
		EditPetModel editModel = new EditPetModel();
		new EditPetController(editView, editModel, petId);

		editView.setVisible(true);
	}

	private void goBack() {
		view.dispose();

		MainMenuView2 menu2View = new MainMenuView2();
		MainMenuModel2 menu2Model = new MainMenuModel2();
		new MainMenuController2(menu2View, menu2Model, petId);

		menu2View.setVisible(true);
	}
}