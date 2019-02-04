
package edu.memphis.nlp.data;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import java.util.ArrayList;

/**
 *
 * @author nabin
 */
public class HMMData {
    
    //input sequences
    public ArrayList<ArrayList<String>> originalWordSequences = new ArrayList<>();
    public ArrayList<ArrayList<String>> processedWordSequences = new ArrayList<>();
    public ArrayList<ArrayList<ObservationInteger>> codedWordSequences = new ArrayList<>();
    public ArrayList<ArrayList<String>> actualTagSequences = new ArrayList<>();
    //output sequences
    public ArrayList<ArrayList<String>> predictedTagSequences = new ArrayList<>();
}
