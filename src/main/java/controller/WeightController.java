package controller;

import model.WeightModel;
import view.WeightView;
import model.WeightSubMenuModel;
import view.WeightSubMenuView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.*;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

public class WeightController implements ActionListener
{
    private final WeightView view;
    private final WeightModel model;
    private WeightSubMenuModel subMenuModel;
    private final int petId;

    // jfree.data.time stores time through units that aren't dates .
    private TimeSeries weightPerDay;
    private TimeSeries weightPerMonth;
    private TimeSeries weightPerYear;

    private WeightModel.WeightUnit[] weightHistory;
    private graphChoice choice;

    public static void main(String[] args) {
        new WeightController(new WeightView(), new WeightModel(), 1);     // replace this bruh
    }

    /**
     * REPLACE THIS CONSTRUCTOR
     * @param view  -
     * @param model
     * @param petId
     */

    public WeightController(WeightView view, WeightModel model, int petId)
    {
        this.view = view;
        this.model = model;
        this.petId = petId;
        choice = graphChoice.YEAR;

        // initializing the stuff that's moving
        weightHistory = model.readAllWeight(petId);
        createDataSets(weightHistory);

        // initializing everything else
        this.view.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println(e.getActionCommand());

        switch (e.getActionCommand())
        {
            case "Year":
                choice = graphChoice.YEAR;
                view.updateGraphChoice(choice);
                break;

            case "Month":
                choice = graphChoice.MONTH;
                view.updateGraphChoice(choice);
                break;

            case "Day":
                choice = graphChoice.DAY;
                view.updateGraphChoice(choice);
                break;

            case "WeightInput":
                getWeightInput();
                break;

            case "Back":
                System.out.println("Back to main menu!");       // replace with backToMenu when merging
                break;
        }
    }

    public void backToMenu() {

    }

    public void getWeightInput()
    {
        subMenuModel = new WeightSubMenuModel();
        WeightSubMenuView subMenuView = new WeightSubMenuView();

        new WeightSubMenuController(this ,subMenuModel, subMenuView);
    }

    public void updateValues() {
        if (subMenuModel.isValid())
        {
            int day = subMenuModel.getDay();
            int month = subMenuModel.getMonth();
            int year = subMenuModel.getYear();
            double weight = subMenuModel.getWeight();

            model.recordWeight(petId, weight, LocalDate.of(year, month, day));          // updates weight_history
            model.updateRecentWeight(weight);                                           // updates pet
            updateWeightSeries(weight, LocalDate.of(year, month, day));                 // updates graphs
        }
    }

    // The following functions heavily rely on JFreeChart and JFreeCommon,
    // These functions will literally not work if Maven dependencies aren't set up properly.

    public enum graphChoice {
        YEAR,
        MONTH,
        DAY
    }

    // Helper Function for adding points to the dataset
    public void updateWeightSeries(double weight, LocalDate now)
    {
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();

        switch (choice)
        {
            case YEAR:
                weightPerYear.addOrUpdate(new Year(year), weight);
            case MONTH:
                weightPerMonth.addOrUpdate(new Month(month, year), weight);
            case DAY:
                weightPerDay.addOrUpdate(new Day(day, month, year), weight);
        }
    }

    public void updateDoneIsTheNameOfTheDog(double weight)
    {

    }

    public JFreeChart createYearGraph()
    {
        return ChartFactory.createTimeSeriesChart(
                "Weight Per Year",
                "Year",
                "Weight",
                new TimeSeriesCollection(weightPerYear),
                true,
                true,
                false
        );
    }

    public JFreeChart createMonthGraph()
    {
        return ChartFactory.createTimeSeriesChart(
                "Weight Per Month",
                "Month",
                "Weight",
                new TimeSeriesCollection(weightPerMonth),
                true,
                true,
                false
        );
    }

    public JFreeChart createDayGraph()
    {
        return ChartFactory.createTimeSeriesChart(
                "Weight Per Day",
                "Day",
                "Weight",
                new TimeSeriesCollection(weightPerDay),
                true,
                true,
                false
        );
    }

    /**
     * This function takes in the weightHistory containing the weights and their
     * corresponding ID's and dates. Think of this function as a parser that takes in
     * the values from SQL and outputs it in a form that JFreeChart can understand.
     *
     * @param weightHistory --
     */

    private void createDataSets(WeightModel.WeightUnit[] weightHistory)
    {
        weightPerDay = new TimeSeries("Weight Per Day");
        weightPerMonth = new TimeSeries("Weight Per Month");
        weightPerYear = new TimeSeries("Weight Per Year");

        for (WeightModel.WeightUnit weight: weightHistory)
        {
            updateWeightSeries(weight.getCurrWeight(), weight.getDate());
        }
    }

}