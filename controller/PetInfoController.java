pakcage controller;

import view.PetInfoView;
import model.PetInfoModel;

import view.MainMenuView2;
import model.MainMenuModel2;

import java.awt.event.*;

public class PetInfoController implements ActionListener {
	private final PetInfoView view;
	private final PetInfoModel model;
	private final int petId; 

	public PetInfoController(PetInfoView view, PetInfoModel model, int petId) {
		this.view = view;
		this.model = model;
		this.view.addActionListener(this);
		refresh();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.GetActionCommand()) {
			case "Edit" -> editPet();
			case "Back" -> goBack();
		}
	}

	private void editPet() {
		System.out.println("edakdfamcurgjs");
	}

	private void goBack() {
		view.dispose();

		MainMenuView2 menu2View = new MainMenuView2();
		MainMenuModel2 menu2Model = new MainMenuModel2();
		new MainMenuController2(menu2View, menu2Model);

		menu2View.setVisible(true);
	}

	private void refresh(){
		PetInfoModel.Pet pet = model.getPetInfo(pet_id);
		view.refresh(pet);
	}
}