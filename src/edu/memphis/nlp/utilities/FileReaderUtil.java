package edu.memphis.nlp.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Rajendra Created on May 24, 2013, 2:32:34 PM
 */
public class FileReaderUtil {

    //TODO: make sure that file handlers, readers are closed properly.. move them to finally block.
	/*
     * Gives the list of lines in a file.
     */

    public static ArrayList<String> getLinesFromFile(String fileName) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                lines.add(strLine.trim());
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
        }
        return lines;
    }

     public static void main(String args[]){
        
//         String inputFolder = "D:\\JavaWorkspace\\ModeDiscoveryUsingHMM\\data\\Tutor.com.CRF\\data";
//         File folder = new File(inputFolder);
//         String outputFile = "D:\\JavaWorkspace\\ModeDiscoveryUsingHMM\\data\\Tutor.com.CRF\\250KTutorCRF.txt";
//        // List<String> lines = FileReaderUtil.getDialogLinesFromFolder(folder, DIALOG_MODE_LINES, true);
//         FileWriterUtil.writeToFile(lines, outputFile);
    }
}
