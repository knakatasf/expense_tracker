package org.example;

import java.io.*;
import java.util.List;

public class FileOperation {
    public static void saveExpenses(String filename, List<Expense> expenses) {
        try {
            // append mode
            FileOutputStream file = new FileOutputStream(filename, true);
            ObjectOutputStream oos = new ObjectOutputStream(file);

            oos.writeObject(expenses);
            oos.close();
            file.close();
        } catch (IOException e) {
            System.out.println("Couldn't add data..");
        }
    }
    public static List<Expense> readExpenses(String filename) {
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(file);

            List<Expense> expenses =  (List<Expense>)ois.readObject();
            ois.close();
            file.close();
            return expenses;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't read the file..");
            return null;
        }
    }
}
