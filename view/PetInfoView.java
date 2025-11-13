package view;

import PetInfoModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PetInfoView extends JFrame {
	private JButton editButton, backButton;
	private JPanel infoPanel, bottomPanel;

	private String name, species, gender;
	private int age;
	private float weight;

	public PetInfoView() {
		super("Checking pet information.");
		initComponents();
	}

	private void initComponents() {
		editButton = new JButton("Edit pet info");
		backButton = new JButton("Return to main menu");

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}

	private void layoutComponents() {
		infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		infoPanel.add(new JLabel(String.format("Species: %s", species)));
		infoPanel.add(new JLabel(String.format("Gender: %s", gender)));
		infoPanel.add(new JLabel(String.format("Age: %d", age)));
		infoPanel.add(new JLabel(String.format("Current weight: %f")));
		infoPanel.add(new JLabel(String.format("%s is a %d year old %fkg %s %s!", name, age, weight, gender, species)));

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(new JLabel(String.format("%s's info!", name)));

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		bottomPanel.add(backButton);
		bottomPanel.add(editButton);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		add(mainPanel);
	}

	public void addActionListener(ActionListener l) {
		editButton.addActionListener(l);
		backButton.addActionListener(l);

		editButton.setActionCommand("Edit");
		backButton.setActionCommand("Back");
	}

	public void refresh(PetInfoView.Pet pet) {
		this.name = pet.getName();
		this.species = pet.getSpecies();
		this.gender = pet.getGender();
		this.age = pet.getAge();
		this.weight = pet.getWeight();

		layoutComponents();
	}
}