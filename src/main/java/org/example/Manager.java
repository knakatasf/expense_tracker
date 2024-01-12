package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Manager {
    private List<Expense> expenses;
    private Scanner input;

    public Manager() {
        input = new Scanner(System.in);
        expenses = new ArrayList<>();
    }

    public void entryExpense() {
        Expense expense = new Expense();

        System.out.print("\nEnter date (mm/dd/yyyy): ");
        String date = input.next();
        String patStr = "^(1[0-2]|0?[1-9])/(3[01]|[12][0-9]|0?[1-9])/[0-9]{4}$";
        Pattern datePat = Pattern.compile(patStr);
        while (true) {
            Matcher dateMat = datePat.matcher(date);
            if (dateMat.matches()) break;
            else {
                System.out.print("Invalid date format.. Please use mm/dd/yyyy: ");
                date = input.next();
            }
        }

        String[] dateStr = date.split("/");
        if (dateStr[0].charAt(0) == '0') dateStr[0] = dateStr[0].substring(1);
        if (dateStr[1].charAt(0) == '0') dateStr[1] = dateStr[1].substring(1);
        int month = Integer.parseInt(dateStr[0]);
        int day = Integer.parseInt(dateStr[1]);
        int year = Integer.parseInt(dateStr[2]);

        expense.setDate(month, day, year);

        System.out.print("\nEnter item: ");
        String item = input.next();
        expense.setItem(item);

        System.out.print("\nEnter cost: ");
        double cost = input.nextDouble();
        expense.setCost(cost);

        enterExpense(expense);
    }

    private void enterExpense(Expense expense) {
        expenses.add(expense);
        Collections.sort(expenses);
    }

    public void operateOCR() {
        System.out.print("Please enter image path: ");
        String imagePath = input.next();
        if (imagePath.isEmpty()) return;

        OCROperation ocr = new OCROperation();
        Expense expense = ocr.readImage(imagePath);
        if (expense == null) {
            System.out.println("Couldn't read or extract data from the receipt..");
            return;
        }
        enterExpense(expense);
    }

    public void readFile() {
        System.out.print("Please enter name of file to read data: ");
        String filename = input.next();

        FileOperation fileOp = new FileOperation();
        List<Expense> readList = fileOp.readExpenses(filename);
        if (readList == null) return;
        displayExpenses(readList);
    }

    public void saveExpenses() {
        System.out.print("Please enter name of file to save data: ");
        String filename = input.next();

        FileOperation fileOp = new FileOperation();
        fileOp.saveExpenses(filename, expenses);
        System.out.println("Data saved in " + filename);
    }

    public void displayExpenses() {
        for (Expense expense : expenses) {
            expense.display();
        }
    }

    public void displayExpenses(List<Expense> expenses) {
        for (Expense expense : expenses) {
            System.out.println();
            expense.display();
        }
    }

}
