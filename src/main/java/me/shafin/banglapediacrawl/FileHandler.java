/*
 */
package me.shafin.banglapediacrawl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SHAFIN
 */
public class FileHandler {

    public static List<String> readFile(String filePath) {

        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        String line = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return lines;
    }

    public static boolean writeFile(String filePath, String textData) {
        try {
            File file = new File(filePath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF8")) {
                textData = textData.replaceAll("\n", System.lineSeparator());
                outputStreamWriter.write(textData);
            }
            System.out.println("INFO: data has been written to "+filePath);
            return true;

        } catch (IOException e) {
            return false;

        }
    }
    
     public static boolean writeListToFile(String filePath, List<String> inputList) {
        try {
            File file = new File(filePath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            
            StringBuilder stringBuilder = new StringBuilder();
           
            for(String t:inputList){
                stringBuilder.append(t).append("\n");
            }
            String textData = stringBuilder.toString();

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF8")) {
                textData = textData.replaceAll("\n", System.lineSeparator());
                outputStreamWriter.write(textData);
            }
            System.out.println("INFO: list has been written to "+filePath);
            return true;

        } catch (IOException e) {
            return false;

        }
    }
}
