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
		editButton.setBackground(new Color(200,40,33));
		editButton.setForeground(new Color(255, 255, 245));
		backButton = new JButton("Return to main menu");
		backButton.setBackground(new Color(32,117,111));
		backButton.setForeground(new Color(255, 255, 245));

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}

	private void layoutComponents() {
		JPanel headerPanel = new JPanel(new BorderLayout()){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

				g2.dispose();
			}
		};

		headerPanel.setOpaque(false);
		headerPanel.setBackground(new Color(255, 255, 245));
		headerPanel.setLayout(new BorderLayout());

		String headerString = "<html><div style='font-size: 30px; font-weight: bold; text-align: center;'>";
		int colorCount = 1;
		for (int i = 0; i < name.length(); i++) {
			switch (colorCount){
				case 1:
					headerString = headerString + "<span style='color:#C82821'>" +  name.charAt(i) + "</span>";
					colorCount++; break;
				case 2:
					headerString = headerString + "<span style='color:#ad903b'>" +  name.charAt(i) + "</span>";
					colorCount++; break;
				case 3:
					headerString = headerString + "<span style='color:#20756F'>" +  name.charAt(i) + "</span>";
					colorCount = 1; break;
			}
		}
		headerString = headerString + "</div></html>";
		JLabel headerLabel = new JLabel(headerString);

		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setVerticalAlignment(SwingConstants.CENTER);

		headerPanel.add(headerLabel);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(2, 30, 2, 30));

		JPanel headerWrapper = new JPanel();
		headerWrapper.add(headerPanel);
		headerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		headerWrapper.setBackground(new Color(163,198,181));
	





		infoPanel = new JPanel(new GridLayout(5, 1, 2, 2));
		infoPanel.add(new JLabel("<html><h2><span style='color: #ad903b'>Species: </span>"+species+"</h2></html>"));
		infoPanel.add(new JLabel("<html><h2><span style='color: #20756F'>Gender: </span>"+gender+"</h2></html>"));
		infoPanel.add(new JLabel("<html><h2><span style='color: #C82821'>Age: </span>"+age+"</h2></html>"));

		if (weight == 0.0f) {
			infoPanel.add(new JLabel("<html><h2><span style='color: #ad903b'>"+"No recorded weight!"+"</h2></html>"));
			infoPanel.add(new JLabel("<html><h2><span style='color: #C82821'>"+name+"</span> is a <span style='color: #ad903b'>"+age+"</span> year old <span style='color: #20756F'>"+gender.toLowerCase()+"</span> <span style='color: #C82821'>"+species+"</span>!</h2></html>"));
		} else {
			infoPanel.add(new JLabel("<html><h2><span style='color: #ad903b'>Current weight: </span>"+String.format("%.2f", weight)+"</h2></html>"));
			infoPanel.add(new JLabel("<html><h2><span style='color: #C82821'>"+name+"</span> is a <span style='color: #ad903b'>"+age+"</span> year old <span style='color: #20756F'>"+String.format("%.2f", weight)+"kg</span>"
										+ " <span style='color: #C82821'>"+gender.toLowerCase()+"</span> <span syle='color: #ad903b'>"+species+"</span>!</h2></html>"));
		}
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		bottomPanel.add(backButton);
		bottomPanel.add(editButton);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
		bottomPanel.setBackground(new Color(163,198,181));

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 70, 50, 70));
		mainPanel.add(headerWrapper, BorderLayout.NORTH);
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.setBackground(new Color(163,198,181));
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

		revalidate();
		repaint();
	}
}