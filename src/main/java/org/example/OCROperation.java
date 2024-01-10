package org.example;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCROperation {
    public String readImage(String imagePath) {
        System.setProperty("jna.library.path", "/opt/homebrew/Cellar/tesseract/5.3.3/lib/");
        System.setProperty("TESSDATA_PREFIX", "/opt/homebrew/Cellar/tesseract/5.3.3/share/tessdata/");

        System.out.printf("Hello and welcome!");

        ITesseract tesseract = new Tesseract();

        try {
            String result = tesseract.doOCR(new File(imagePath));
            return result;
        } catch (TesseractException e) {
            e.printStackTrace();
            return "";
        }
    }
}
