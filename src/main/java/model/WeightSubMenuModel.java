package model;

public class WeightSubMenuModel
{
    private double weight;
    private int day;
    private int month;
    private int year;

    private boolean valid;

    public WeightSubMenuModel() {}

    public double getWeight() { return weight; }
    public int getDay() { return day; }
    public int getMonth() { return month; }
    public int getYear() { return year; }

    public void setWeight(double weight) { this.weight = weight; }
    public void setDay(int day) { this.day = day; }
    public void setMonth(int month) { this.month = month; }
    public void setYear(int year) { this.year = year; }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
}
