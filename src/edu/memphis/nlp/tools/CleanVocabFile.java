
package edu.memphis.nlp.tools;

import java.util.ArrayList;
import edu.memphis.nlp.utilities.DataPath;
import edu.memphis.nlp.utilities.FileReaderUtil;
import edu.memphis.nlp.utilities.FileWriterUtil;

/**
 *
 * @author nabin
 * to clean some words such as punctuation, reserved keywords and numbers in vocabulary by transforming them so than they can 
 * be specified as instance of WordType enum
 */
public class CleanVocabFile {
    public static void main(String args[]){
        String fileName = DataPath.getDataPath() + "vocab_plain.txt";
        ArrayList<String> lines = FileReaderUtil.getLinesFromFile(fileName);
        ArrayList<String> outLines = new ArrayList<>();
        for(String line:lines){
            
            line = line.replaceAll("\\?", "_questionMark_");
            line = line.replaceAll("=","_equal_");
            line = line.replaceAll("!","_exclamationMark_");
            line = line.replaceAll(",", "_comma_");
            line = line.replaceAll(":", "_colon_");
            line = line.replaceAll(";", "_semicolon_");
            line = line.replaceAll("`", "_singlegrave_");
            line = line.replaceAll("``", "_doublegrave_");
            line = line.replaceAll("%", "_pct_");
            line = line.replaceAll("&", "_ampersand_");
            line = line.replaceAll("'", "_apos_");
            line = line.replaceAll("\\+", "_plus_");
            line = line.replaceAll("\\*", "_asterisk_");
            line = line.replaceAll("\\\\", "_backslash_");
            line = line.replaceAll("\\.", "_dot_");
            line = line.replaceAll("\\[", "_lsb_");
            line = line.replaceAll("]", "_rsb_");
            line = line.replaceAll("\\(", "_lrb_");
            line = line.replaceAll("\\)", "_rrb_");
            line = line.replaceAll("\\{", "_lcb_");
            line = line.replaceAll("}", "_rcb_");
            line = line.replaceAll("-", "_");
            
            line = "_" + line;
            outLines.add(line);
        }
        FileWriterUtil.writeToFile(outLines, DataPath.getDataPath() + "vocab_clean.txt");
    }
}
