
package edu.memphis.nlp.tools;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import edu.memphis.nlp.data.serializable.UnknownWordEmissionProbabilities;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import edu.memphis.nlp.utilities.DataPath;
import edu.memphis.nlp.utilities.FileReaderUtil;

/**
 *
 * @author nabin
 * class that returns HashMap containing unknown word counts for different tags
 */
public class UnknownWordEstimator {
    
    double traininPct = 0.6;
    Set<String> vocab;
    double[] unknownProbabilities;
    Map<String,Map<String,Integer>> tagUnknownWordCountMap;
    public UnknownWordEstimator(){
        vocab = new HashSet<>();
        unknownProbabilities = new double[PennTreeBankTagSet.values().length];
        Arrays.fill(unknownProbabilities,0);
        tagUnknownWordCountMap = new HashMap<>();
    }
    public static void main(String args[]){
         String corpusFile = DataPath.getDataPath() + "input\\BROWN-pos.all.clean.training.txt";
         //pct of corpusFile that will be used for training vocabulary 
         double trainPct = 0.6;
         ArrayList<String> lines = FileReaderUtil.getLinesFromFile(corpusFile);
         int trainingLines = (int) Math.ceil(trainPct*lines.size());
         
         UnknownWordEstimator unKnownWordEstimator = new UnknownWordEstimator();
         unKnownWordEstimator.buildVocabulary(lines,trainingLines);
         unKnownWordEstimator.estimateUnknownProbabilities(lines,trainingLines);
        try {
            unKnownWordEstimator.saveUnknownProbabilities();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void buildVocabulary(ArrayList<String> lines, int trainingLines) {
        for(int i=0;i<trainingLines;i++){
            String line = lines.get(i);
            String[] tagWordPairs = line.split("\t");
            for(int j=0;j<tagWordPairs.length;j++){
                String tagWordPair = tagWordPairs[j];
                String[] splits = tagWordPair.split(" ");
                String tag = splits[0];
                String word = splits[1];
               // word = word.toLowerCase();
                if(tag.equals("-NONE-")){
                    continue;
                }
                vocab.add(word);
            }
        }
    }

    private void estimateUnknownProbabilities(ArrayList<String> lines, int trainingLines) {
        for(int i=trainingLines;i<lines.size();i++){
            String line = lines.get(i);
            String[] tagWordPairs = line.split("\t");
            for(int j=0;j<tagWordPairs.length;j++){
                String tagWordPair = tagWordPairs[j];
                String[] splits = tagWordPair.split(" ");
                String tag = splits[0];
                String word = splits[1];
                if(tag.equals("-NONE-")){
                    continue;
                }
                updateTagWordFreqMap(word,tag);
            }
        }
        this.computeUnknownProbabilities();
    }

    private void updateTagWordFreqMap(String word, String tag) {
        Map<String,Integer> wordFreqMap = tagUnknownWordCountMap.get(tag);
        if(wordFreqMap == null){
            wordFreqMap = new HashMap<>();
            tagUnknownWordCountMap.put(tag, wordFreqMap);
        }
        
        String wordtype = vocab.contains(word)?"known":"__unknown__";
        Integer freq = wordFreqMap.get(wordtype);
        if(freq == null){
            freq = 0;
        }
        wordFreqMap.put(wordtype, freq+1);
    }

    private void computeUnknownProbabilities() {
        for(int i=0;i<unknownProbabilities.length;i++){
            String tag = PennTreeBankTagSet.getName(i);
            Map<String,Integer> wordFreqMap = tagUnknownWordCountMap.get(tag);
            //we care about only those tags that have possible unknown words
            if(wordFreqMap != null){
                int sum = 0;
                Set<String>words = wordFreqMap.keySet();
                for(String word:words){
                    sum += wordFreqMap.get(word);
                }
                Integer unknownWordCount = wordFreqMap.get("__unknown__");
                if(unknownWordCount == null){
                    unknownWordCount = 0;
                }
                double probability = (double)unknownWordCount/sum;
                unknownProbabilities[i] = probability;
            }
        }
    }

    private void saveUnknownProbabilities() throws IOException {
        UnknownWordEmissionProbabilities unknownEmissionProbs = new UnknownWordEmissionProbabilities();
        unknownEmissionProbs.setUnknownEmissionProbabilities(unknownProbabilities);
        String fileName = DataPath.getDataPath() + "processedInput\\unknownWordEmissionProbs.ser";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(unknownEmissionProbs);
    }
}
