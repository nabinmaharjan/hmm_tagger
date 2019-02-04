package edu.memphis.nlp.utilities;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rajendra Created on May 24, 2013, 2:52:55 PM
 */
public class FileWriterUtil {
    
    //TODO: add more methods, and make sure that resources are handled properly (such as file handler closed), move them to 
    //      finally block.

    public static boolean writeToFile(List<String> lines, String fileName) {

        try {
            // Create file
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            for (String line : lines) {
                //out.write(line + System..lineSeparator());
                out.write(line + System.getProperty("line.separator"));
            }
            // Close the output stream
            out.close();
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public static boolean writeToFile(Map<String, Integer> map, String fileName) {

        try {
            // Create file
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            for (String word : map.keySet()) {
                //out.write(String.format("%20s", word) + " " + map.get(word) + System.lineSeparator());
                out.write(String.format("%20s", word) + " " + map.get(word) + System.getProperty("line.separator"));
            }
            // Close the output stream
            out.close();
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public static boolean writeToFile(String headerText, Map<String, Integer> map, String fileName) {

        try {
            // Create file
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);

            if (headerText != null) {
                //out.write("Summary: \n" + headerText + System.lineSeparator());
                out.write("Summary: \n" + headerText + System.getProperty("line.separator"));
                out.write("========================================\r\n");
            }

            for (String word : map.keySet()) {
                //out.write(String.format("%20s", word) + " " + map.get(word) + System.lineSeparator());
                out.write(String.format("%20s", word) + " " + map.get(word) + System.getProperty("line.separator"));
            }
            // Close the output stream
            out.close();
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public static boolean writeToFileFloat(String headerText, Map<String, Float> map, String fileName) {

        try {
            // Create file
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);

            if (headerText != null) {
                //out.write("Summary: \n" + headerText + System.lineSeparator());
                out.write("Summary: \n" + headerText + System.getProperty("line.separator"));
                out.write("========================================\r\n");
            }

            for (String word : map.keySet()) {
                //out.write(String.format("%30s", word) + " " + String.format("%.4f", map.get(word)) + System.lineSeparator());
                out.write(String.format("%30s", word) + " " + String.format("%.4f", map.get(word)) + System.getProperty("line.separator"));
            }
            // Close the output stream
            out.flush();
            out.close();
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public static boolean writeToFile(String headerText, Map<String, Integer> map, String fileName, int totalWords) {

        try {
            // Create file
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);

            double percent;
            int frequency;

            if (headerText != null) {
                //out.write("Summary: \n" + headerText + System.lineSeparator());
                out.write("Summary: \n" + headerText + System.getProperty("line.separator"));
                out.write("========================================\r\n");
            }

            for (String word : map.keySet()) {
                frequency = map.get(word);
                percent = 100.0 * frequency / totalWords;
                if (percent < 0.000001) {
                    percent = 0.0;
                }
                //out.write(String.format("%25s", word) + " " + String.format("%7d", frequency) + "  " + String.format("%.10f", percent) + "%" + System.lineSeparator());
                out.write(String.format("%25s", word) + " " + String.format("%7d", frequency) + "  " + String.format("%.10f", percent) + "%" + System.getProperty("line.separator"));
            }
            // Close the output stream
            out.close();
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public static boolean writeToFile(String text, String fileName) {

        try {
            // Create file
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(text);
            // Close the output stream
            out.close();
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    /*
     * Append if exists. Else, create and write.
     */
    public static boolean appendToFile(ArrayList<String> lines, String fileName) {
        try {
            File file = new File(fileName);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            for (int i = 0; i < lines.size(); i++) {
                bufferWritter.write(lines.get(i)); // assuming new line is given in data itself.
            }
            bufferWritter.flush();
            bufferWritter.close();


            System.out.println("Saved to file!!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
