package org.example;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Comparable<Expense>, Serializable {
    private String item;
    private double cost;
    private LocalDate date;

    public Expense() {}
    public Expense(int month, int day, int year, String item, double cost) {
        setDate(month, day, year);
        setItem(item);
        setCost(cost);
    }

    public void setItem(String item) {
        this.item = item;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public void setDate(int month, int day, int year) {
        try {
            LocalDate date = LocalDate.of(year, month, day);
            this.date = date;
        } catch (Exception var5) {
            System.err.println("Invalid date..");
        }

    }
    public LocalDate getDate() {
        return this.date;
    }
    public String getItem() {
        return this.item;
    }
    public double getCost() {
        return this.cost;
    }

    @Override
    public int compareTo(Expense other) {
        return getDate().compareTo(other.getDate());
    }

    public String toString() {
        String expenseStr = "Date: " + getDate() + "\nItem: " + getItem()
                + "\nCost: $" + String.format("%.2f", getCost());
        return expenseStr;
    }
    public void display() {
        String expenseStr = toString();
        System.out.println(expenseStr);
    }
}
