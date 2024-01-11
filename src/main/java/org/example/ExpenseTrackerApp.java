package org.example;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class ExpenseTrackerApp {
    public static void main(String[] args) {

        Manager manager = new Manager();
        manager.operateOCR();
        manager.displayExpenses();


//            String result = tesseract.doOCR(new File("/Users/claudio/Downloads/114688.jpeg"));
//            System.out.println(result);

    }
}