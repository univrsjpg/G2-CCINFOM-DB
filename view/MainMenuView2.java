package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView2 extends JFrame {
	private JButton viewPetInfo, viewWeight, recordIntake, viewAllergies, goBack;
	private String pet_name = "";

	// Sets up view
	public MainMenuView2(String pet_name) {
		super(pet_name);
		this.pet_name = pet_name;
		initComponents();
		layoutComponents();
	}

	// View w/o string petname
	public MainMenuView2() {
		super("Pet Tracker");
		initComponents();
		layoutComponents();
	}

	// Sets pet_name and changes layout in accordance
	public void setPetName(String pet_name) {
		this.pet_name = pet_name;
		layoutComponents();
	}

	// Initializes components
	private void initComponents() {
		viewPetInfo = new JButton("View Info");
		viewPetInfo.setBackground(new Color(200,40,33));
		viewPetInfo.setForeground(new Color(255, 255, 245));
		viewWeight = new JButton("View Weight");
		viewWeight.setBackground(new Color (207, 172, 72));
		viewWeight.setForeground(new Color(255, 255, 245));
		recordIntake = new JButton("Record Food Intake");
		recordIntake.setBackground(new Color(217,89,30));
		recordIntake.setForeground(new Color(255, 255, 245));
		viewAllergies = new JButton("View Allergies");
		viewAllergies.setBackground(new Color(32,117,111));
		viewAllergies.setForeground(new Color(255, 255, 245));

		goBack = new JButton("Return to last menu");
		goBack.setBackground(new Color(255, 255, 245));

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}

	// Layouts components
	private void layoutComponents() {

		JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		buttonPanel.add(viewPetInfo);
		buttonPanel.add(recordIntake);
		buttonPanel.add(viewWeight);
		buttonPanel.add(viewAllergies);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel backPanel = new JPanel();
		backPanel.add(goBack);
		backPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		backPanel.setBackground(new Color(163,198,181));

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));

		if (!pet_name.equals("")){
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
			for (int i = 0; i < pet_name.length(); i++) {
				switch (colorCount){
					case 1:
						headerString = headerString + "<span style='color:#C82821'>" +  pet_name.charAt(i) + "</span>";
						colorCount++; break;
					case 2:
						headerString = headerString + "<span style='color:#ad903b'>" +  pet_name.charAt(i) + "</span>";
						colorCount++; break;
					case 3:
						headerString = headerString + "<span style='color:#20756F'>" +  pet_name.charAt(i) + "</span>";
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
	
			mainPanel.add(headerWrapper, BorderLayout.NORTH);
			mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 70, 50, 70));
		}

		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(backPanel, BorderLayout.SOUTH);
		mainPanel.setBackground(new Color(163,198,181));
		add(mainPanel);
	}

	// Adds action listener
	public void addActionListener(ActionListener l) {
		viewPetInfo.addActionListener(l);
		recordIntake.addActionListener(l);
		viewWeight.addActionListener(l);
		viewAllergies.addActionListener(l);
		goBack.addActionListener(l);

		viewPetInfo.setActionCommand("ViewPetInfo");
		recordIntake.setActionCommand("RecordIntake");
		viewWeight.setActionCommand("ViewWeight");
		viewAllergies.setActionCommand("ViewAllergies");
		goBack.setActionCommand("Back");
	}
}