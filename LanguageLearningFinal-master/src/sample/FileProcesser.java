package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileProcesser {
    private String fullTranslation;

    public void addToCSV(String _fullTranslation) throws IOException{
        fullTranslation = _fullTranslation;

        try {
            FileWriter fileWriter = new FileWriter("words.csv",true);
            fileWriter.write(fullTranslation+"\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Not working");
        }
    }
}
