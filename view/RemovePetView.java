package view;

import model.MainMenuModel1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RemovePetView extends JFrame {
	private JPanel petButtonPanel;
	private JButton backButton;

	public RemovePetView() {
		super("Removing a pet");
		initComponents();
		layoutComponents();
	}

	private void initComponents() {
		petButtonPanel = new JPanel(new GridLayout(3,3,5,5));
		petButtonPanel.setBorder(BorderFactory.createTitledBorder("Remove a Pet:"));

		backButton = new JButton("Return to main menu");

		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
	}

	private void layoutComponents() {
		JPanel petPanel = new JPanel(new BorderLayout());
		petPanel.add(petButtonPanel, BorderLayout.CENTER);
		petPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(backButton);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50,70,50,70));
		mainPanel.add(petPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);
	}

	public void addActionListener(ActionListener l) {
		for (Component c : petButtonPanel.getComponents()) {
			if (c instanceof JButton petButton) {
				petButton.addActionListener(l);
			}
		}

		backButton.addActionListener(l);
		backButton.setActionCommand("Back");
	}

	public void updatePetList(List<MainMenuModel1.Pet> pets) {
		petButtonPanel.removeAll();

		if (pets.isEmpty()) {
			petButtonPanel.add(new JLabel("You have no pets!"));
		}
		else {
			for (MainMenuModel1.Pet pet : pets) {
				JButton petButton = new JButton(pet.getName());
				petButton.putClientProperty("pet_id", pet.getId());
				petButton.putClientProperty("pet_name", pet.getName());
				petButton.setActionCommand("RemovePet");
				petButtonPanel.add(petButton);
			}
		}

		petButtonPanel.revalidate();
		petButtonPanel.repaint();
	}

	public void showMessage (String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
}