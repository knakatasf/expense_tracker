package org.example;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import static java.lang.Math.min;

public class OCROperation {
    public Expense readImage(String imagePath) {
        System.setProperty("jna.library.path", "/opt/homebrew/Cellar/tesseract/5.3.3/lib/");
        System.setProperty("TESSDATA_PREFIX", "/opt/homebrew/Cellar/tesseract/5.3.3/share/tessdata/");

        ITesseract tesseract = new Tesseract();
        String ocrResult = "";
        while (true) {
            try {
                ocrResult = tesseract.doOCR(new File(imagePath));
                break;
            } catch (TesseractException e) {
                e.printStackTrace();
                System.out.print("Couldn't read the image.. Please try new image or press enter to quit." +
                        "\nImage path: ");
                Scanner input = new Scanner(System.in);
                imagePath = input.next();
                if (imagePath.isEmpty()) break;
            }
        }
        if (ocrResult.isEmpty()) return null;

        Expense expense = makeExpenseObj(ocrResult);
        System.out.println("Extracted Data:\n");
        expense.display();

        Scanner input = new Scanner(System.in);
        System.out.print("\nDo you want to edit data?\n" +
                "If no, press enter. If yes, press 1: Date, 2: Item Name, 3: Cost -> ");
        while (true) {
            String choice = input.next();
            if (choice.isEmpty()) {
                input.next();
                break;
            }
            try {
                int toEdit = Integer.parseInt(choice);
                if (toEdit >= 1 && toEdit <= 3) {
                    editExtractedData(expense, toEdit);
                    break;
                }
                System.out.print("Please input 1 to 3. Or press enter not to edit -> ");
            } catch (Exception e) {
                System.out.print("Please input 1 to 3. Or press enter not to edit -> ");
            }
        }
        return expense;
    }

    private Expense makeExpenseObj(String ocrResult) {
        String[] words = ocrResult.split("\\s+");

        Expense expense = new Expense();

        int[] dateInt = extractDate(words);
        expense.setDate(dateInt[0], dateInt[1], dateInt[2]);

        String itemName = extractItem(words);
        expense.setItem(itemName);

        double cost = extractCost(words);
        expense.setCost(cost);

        return expense;
    }

    private int[] extractDate(String[] words) {
        Pattern datePat1 = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
        Pattern datePat2 = Pattern.compile("\\d{1,2}-\\d{1,2}-\\d{4}");
        for (String word : words) {
            Matcher dateMat1 = datePat1.matcher(word);
            if (dateMat1.matches()) {
                return makeIntArray(word, "/");
            }
            Matcher dateMat2 = datePat2.matcher(word);
            if (dateMat2.matches()) {
                return makeIntArray(word, "-");
            }
        }
        int[] fail = {1, 1, 2000};
        return fail;
    }

    private static int[] makeIntArray(String word, String delim) {
        String[] dateStr = word.split(delim);
        if (dateStr[0].charAt(0) == '0') dateStr[0] = dateStr[0].substring(1);
        if (dateStr[1].charAt(0) == '0') dateStr[1] = dateStr[1].substring(1);
        int[] dateInt = new int[3];
        for (int i = 0; i < 3; i++) {
            dateInt[i] = Integer.parseInt(dateStr[i]);
        }
        return dateInt;
    }

    private String extractItem(String[] words) {
        // Assume name of business comes first in receipt. Extract the first 5 words.
        String itemName = "";
        for (int i = 0; i < min(3, words.length); i++) {
            itemName += words[i];
        }
        return itemName;
    }

    private double extractCost(String[] words) {
        // Assume the total price is the first digit value which comes after the word "Total", "Amount".
        Pattern flagPat1 = Pattern.compile("(?i)TOTAL");
        Pattern flagPat2 = Pattern.compile("(?i)AMOUNT");
        Pattern flagPat3 = Pattern.compile("(?i)AMT");
        Pattern costPat = Pattern.compile("[$]?\\d+(?:,\\d{3})*\\.\\d{2}");

        boolean flag = false;
        for (String word : words) {
            Matcher flagMat1 = flagPat1.matcher(word);
            Matcher flagMat2 = flagPat2.matcher(word);
            Matcher flagMat3 = flagPat3.matcher(word);
            if (flagMat1.matches() || flagMat2.matches() || flagMat3.matches()) {
                flag = true;
                continue;
            }
            if (flag) { // If found the keyword "Total", "Amount", the next digit value should be total price
                Matcher costMat = costPat.matcher(word);
                if (costMat.matches()) {
                    word = word.replace("$", "");
                    word = word.replace(",", "");
                    return Double.parseDouble(word);
                }
            }
        }
        return 0;
    }

    private void editExtractedData(Expense expense, int toEdit) {
        Scanner input = new Scanner(System.in);

        switch (toEdit) {
            case 1 :
                System.out.print("Enter date (mm/dd/yyyy): ");
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
                break;

            case 2 :
                System.out.print("\nEnter item: ");
                String item = input.next();
                expense.setItem(item);
                break;

            case 3 :
                System.out.print("\nEnter cost: ");
                double cost = input.nextDouble();
                expense.setCost(cost);
        }
    }
}
