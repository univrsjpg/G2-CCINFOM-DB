package view;

import controller.WeightSubMenuController;


import javax.swing.*;
import java.awt.*;

public class WeightSubMenuView extends JFrame
{
    private WeightSubMenuController controller;
    private SpringLayout layout;
    private Container contentPane;

    public JFormattedTextField weightField;
    public JFormattedTextField dayField;
    public JFormattedTextField monthField;
    public JFormattedTextField yearField;

    public WeightSubMenuView()
    {
        this.contentPane = getContentPane();
        this.layout = new SpringLayout();

        contentPane.setLayout(layout);
        setBackground(new Color(32, 117, 111));
        setLocationRelativeTo(null);
        setBounds(0, 0, 300, 300);
        setPreferredSize(new Dimension(300, 300));
    }

    public void addActionListener(WeightSubMenuController weightSubMenuController)
    {
        this.controller = weightSubMenuController;

        JLabel weightLabel = new JLabel("Weight: ");
        weightField = new JFormattedTextField();
        weightField.setPreferredSize(new Dimension(70, 20));
        weightField.addActionListener(controller);

        JLabel dayLabel = new JLabel("Day: ");
        dayField = new JFormattedTextField();
        dayField.setPreferredSize(new Dimension(70, 20));
        dayField.addActionListener(controller);

        JLabel monthLabel = new JLabel("Month: ");
        monthField = new JFormattedTextField();
        monthField.setPreferredSize(new Dimension(70, 20));
        monthField.addActionListener(controller);

        JLabel yearLabel = new JLabel("Year: ");
        yearField = new JFormattedTextField();
        yearField.setPreferredSize(new Dimension(70, 20));
        yearField.addActionListener(controller);

        JButton confirm = new JButton("Confirm");
        confirm.setActionCommand("confirm");
        confirm.addActionListener(controller);

        add(weightLabel);
        add(weightField);
        add(dayLabel);
        add(dayField);

        add(monthLabel);
        add(monthField);

        add(yearLabel);
        add(yearField);

        add(confirm);

        // spring layout constraints
        // pleasesesdfdfsfp

        layout.putConstraint(SpringLayout.NORTH, weightLabel,
                80,
                SpringLayout.NORTH, contentPane);

        // east of weight label is 5 pix away from west of weight text input
        layout.putConstraint(SpringLayout.WEST, weightLabel,
                80,
                SpringLayout.WEST, contentPane);                        // weightLabel is at (80, 80)

        layout.putConstraint(SpringLayout.WEST, weightField,
                10,
                SpringLayout.EAST, weightLabel);                        // weightField is at (0, 80)

        layout.putConstraint(SpringLayout.NORTH, weightField,
                80,
                SpringLayout.NORTH, contentPane);                       // weightField is at (80, 90) (this used tobe 67)


        layout.putConstraint(SpringLayout.NORTH, dayLabel,
                10,
                SpringLayout.SOUTH, weightLabel);                       // dayLabel is at (90, 0)

        layout.putConstraint(SpringLayout.WEST, dayLabel,
                80,
                SpringLayout.WEST, contentPane);                        // dayLabel (90, 80)

        layout.putConstraint(SpringLayout.WEST, dayField,
                34,
                SpringLayout.EAST, dayLabel);

        layout.putConstraint(SpringLayout.NORTH, dayField,
                80,
                SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.NORTH, dayField,
                10,
                SpringLayout.SOUTH, weightLabel);


        layout.putConstraint(SpringLayout.WEST, monthLabel,
                80,
                SpringLayout.WEST, contentPane);

        layout.putConstraint(SpringLayout.NORTH, monthLabel,
                100,
                SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.NORTH, monthLabel,
                10,
                SpringLayout.SOUTH, dayLabel);

        layout.putConstraint(SpringLayout.WEST, monthField,
                16,
                SpringLayout.EAST, monthLabel);

        layout.putConstraint(SpringLayout.NORTH, monthField,
                120,
                SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.NORTH, monthField,
                7,
                SpringLayout.SOUTH, dayField);

        layout.putConstraint(SpringLayout.WEST, yearLabel,
                80,
                SpringLayout.WEST, contentPane);

        layout.putConstraint(SpringLayout.NORTH, yearLabel,
                140,
                SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.NORTH, yearLabel,
                10,
                SpringLayout.SOUTH, monthLabel);

        layout.putConstraint(SpringLayout.WEST, yearField,
                29,
                SpringLayout.EAST, yearLabel);

        layout.putConstraint(SpringLayout.NORTH, yearField,
                140,
                SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.NORTH, yearField,
                8,
                SpringLayout.SOUTH, monthField);


        layout.putConstraint(SpringLayout.NORTH, confirm,                 // confirm
                230,
                SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, confirm,
                200,
                SpringLayout.WEST, contentPane);

        contentPane.setLayout(layout);
        setVisible(true);
    }

}
