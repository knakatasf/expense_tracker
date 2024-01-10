package org.example;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class ExpenseTrackerApp {
    public static void main(String[] args) {

        OCROperation ocr = new OCROperation();
        String result = ocr.readImage("/Users/claudio/Downloads/114688.jpeg");
        System.out.println(result);

//        System.setProperty("jna.library.path", "/opt/homebrew/Cellar/tesseract/5.3.3/lib/");
//        System.setProperty("TESSDATA_PREFIX", "/opt/homebrew/Cellar/tesseract/5.3.3/share/tessdata/");
//
//        System.out.printf("Hello and welcome!");
//
//        ITesseract tesseract = new Tesseract();
//
//        try {
//            String result = tesseract.doOCR(new File("/Users/claudio/Downloads/114688.jpeg"));
//            System.out.println(result);
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
    }
}