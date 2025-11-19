package controller;

import model.WeightSubMenuModel;
import view.WeightSubMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeightSubMenuController implements ActionListener
{
    private WeightSubMenuModel model;
    private WeightSubMenuView view;
    private WeightController parentController;

    private boolean isLeap;
    private boolean isFeb;

    private boolean invalidWeight;
    private boolean invalidDay;
    private boolean invalidMonth;
    private boolean invalidYear;

    public WeightSubMenuController(WeightController weightController, WeightSubMenuModel model, WeightSubMenuView view)
    {
        this.model = model;
        this.view = view;
        this.parentController = weightController;

        this.view.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        boolean invalid_input = false;

        switch (e.getActionCommand()) {
            case "confirm" -> invalid_input = confirm();
        }

        // pop up window and start over
        if (!invalid_input) {
            model.setValid(true);
            parentController.updateValues();
        }

    }
    private boolean confirm()
    {
        String weightInput = view.weightField.getText();
        if (!validWeight(weightInput)) { return true; }

        String yearInput = view.yearField.getText();
        if (!validYear(yearInput)) { return true; }

        String monthInput = view.monthField.getText();
        if (!validMonth(monthInput)) { return true; }

        String dayInput = view.dayField.getText();
        if (!validDay(dayInput)) { return true; }

        return false;
    }

    private boolean validWeight(String weightInput)
    {
        if (weightInput == null || weightInput.isEmpty()) {
            return false;
        }

        double weight = Double.parseDouble(weightInput);

        if (weight < 0.0) {
            return false;
        }

        model.setWeight(weight);
        return true;
    }
    private boolean validYear(String yearInput)
    {
        if (yearInput == null || yearInput.isEmpty()) {
            return false;
        }

        int year = Integer.parseInt(yearInput);

        if (year < 1) {
            return false;
        }

        if (year % 100 == 0)
        {
            if (year % 400 == 0) { isLeap = true; }
        }
        else if (year % 4 == 0) { isLeap = true; }

        model.setYear(year);
        return true;
    }

    private boolean validMonth(String monthInput)
    {
        if (monthInput == null || monthInput.isEmpty()) {
            return false;
        }

        int month = Integer.parseInt(monthInput);

        if (month < 1 || month > 12) {
            return false;
        }

        model.setMonth(month);
        return true;
    }

        private boolean validDay(String dayInput)
        {
            if (dayInput == null || dayInput.isEmpty()) {
                return false;
            }

            int day = Integer.parseInt(dayInput);

            if (day < 1 || day > 31) {
                return false;
            }
            else if (isLeap && model.getMonth() == 2 && day == 29)
            {
                return false;
            }

            model.setDay(day);
            return true;
        }
}
