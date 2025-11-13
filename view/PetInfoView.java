package view;

import model.PetInfoModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PetInfoView extends JFrame {
	private JButton editButton, backButton;
	private JPanel infoPanel, bottomPanel;

	private String name, species, gender;
	private int age;
	private double weight;

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
		infoPanel = new JPanel(new GridLayout(7, 1, 2, 2));
		infoPanel.add(new JPanel());
		infoPanel.add(new JLabel("<html><span style='font-size:15px'>"+String.format("Species: %s", species)+"</span></html>"));
		infoPanel.add(new JLabel("<html><span style='font-size:15px'>"+String.format("Gender: %s", gender)+"</span></html>"));
		infoPanel.add(new JLabel("<html><span style='font-size:15px'>"+String.format("Age: %d", age)+"</span></html>"));

		if (weight == 0.0f) {
			infoPanel.add(new JLabel("<html><span style='font-size:15px'>"+"No recorded weight!"+"</span></html>"));
			infoPanel.add(new JLabel("<html><span style='font-size:15px'>"+String.format("%s is a %d year old %s %s!", name, age, gender, species)+"</span></html>"));
		} else {
			infoPanel.add(new JLabel("<html><span style='font-size:15px'>"+String.format("Current weight: %.2f", weight)+"</span></html>"));
			infoPanel.add(new JLabel("<html><span style='font-size:15px'>"+String.format("%s is a %d year old %.2fkg %s %s!", name, age, weight, gender, species)+"</span></html>"));
		}

		infoPanel.add(new JPanel());

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(new JLabel("<html><span style='font-size:20px'>"+String.format("%s's info!", name)+"</span></html>"));

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

	public void refresh(PetInfoModel.Pet pet) {
		this.name = pet.getName();
		this.species = pet.getSpecies();
		this.gender = pet.getGender();
		this.age = pet.getAge();
		this.weight = pet.getWeight();

		layoutComponents();
	}

	public String[] promptEditField() {
		String[] fields = {"pet_name", "species", "gender", "age"};

		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		JComboBox<String> fieldDropdown = new JComboBox<>(fields);
		JTextField newValueField = new JTextField(10);

		panel.add(new JLabel("Select field to edit: "));
		panel.add(fieldDropdown);
		panel.add(new JLabel("Enter new value:"));
		panel.add(newValueField);

		int result = JOptionPane.showConfirmDialog(
            this, panel, "Edit Food Field", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION) {
			String field = (String) fieldDropdown.getSelectedItem();
			String newValue = newValueField.getText().trim();

			if (newValue.isEmpty()) return null;
			return new String[]{field, newValue};
		}
		return null;
		
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
}