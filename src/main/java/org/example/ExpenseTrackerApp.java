package org.example;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        System.out.println("\nWelcome to Expense Tracker! How can I help you?");
        Manager manager = new Manager();

        int choice = chooseMenu();
        while (choice != 5) {
            switch (choice) {
                case 1 : manager.entryExpense(); break;
                case 2 : manager.operateOCR(); break;
                case 3 : manager.saveExpenses(); break;
                case 4 : manager.readFile(); break;
            }
            choice = chooseMenu();
        }
        System.out.println("\nThank you for using this program. Have a superior day!");
    }

    private static int chooseMenu() {
        System.out.print("\n  1: Make an Entry Manually\n  2: Read a receipt picture\n" +
                "  3: Save data in file\n  4: Read a file\n  5: Quit\n\nPlease enter your choice in number -> ");

        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            try {
                choice = input.nextInt();
                if (choice >= 1 && choice <= 5) break;
                System.out.print("Invalid number.. Please enter 1 to 5 -> ");
            } catch (InputMismatchException e) {
                System.out.print("Invalid input.. Please enter an integer -> ");
                input.next();
            }
        }
        return choice;
    }
}