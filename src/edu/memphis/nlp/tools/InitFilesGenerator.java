
package edu.memphis.nlp.tools;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import edu.memphis.nlp.data.serializable.InitialAndTransitionProbabilities;
import edu.memphis.nlp.data.serializable.TagWordFreqMap;
import edu.memphis.nlp.data.serializable.Vocabulary;
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
 * class to generate different init files that will be 
 * later used to initialize parameters of initial HMM
 */
public class InitFilesGenerator {
    
    private final Set<String> vocab;
    private final Map<String,Map<String,Integer>> tagWordFreqMap;
    
    private final double[] initialProbabilities;
    private final double[][] transitionProbabilities;
    int[] tags_T1_instant;
    int[][] transitionCount;
    public InitFilesGenerator(){
        vocab = new HashSet<>();
        tagWordFreqMap = new HashMap<>();
        
        tags_T1_instant = new int[PennTreeBankTagSet.values().length];
        transitionCount = new int[PennTreeBankTagSet.values().length][PennTreeBankTagSet.values().length];
        Arrays.fill(tags_T1_instant,0);
        for(int i=0;i<PennTreeBankTagSet.values().length;i++){
            Arrays.fill(transitionCount[i], 0);
        }
        initialProbabilities = new double[PennTreeBankTagSet.values().length];
        transitionProbabilities = new double[PennTreeBankTagSet.values().length][PennTreeBankTagSet.values().length];
        
        Arrays.fill(initialProbabilities,0);
        for(int i=0;i<PennTreeBankTagSet.values().length;i++){
            Arrays.fill(transitionProbabilities[i], 0);
        }
        
    }
    public void process(String fileName){ 
        ArrayList<String> lines = FileReaderUtil.getLinesFromFile(fileName);
        double trainPct = 0.6;
        int trainingLines = (int) Math.ceil(trainPct*lines.size());
        //trainingLines = 10;
        for(int index=0;index<trainingLines;index++){
            String line = lines.get(index);
            String[] tagWordPairs = line.split("\t");
            for(int i=0;i<tagWordPairs.length;i++){
                String tagWordPair = tagWordPairs[i];
                String[] splits = tagWordPair.split(" ");
                String tag = splits[0];
                String word = splits[1];
                //word = word.toLowerCase();
                if(i==0 && !tag.equals("-NONE-")){
                    PennTreeBankTagSet pennTag = (PennTreeBankTagSet)Enum.valueOf(PennTreeBankTagSet.class, PennTreeBankTagSet.getEnumName(tag));
                    tags_T1_instant[pennTag.getTagId()] += 1;
                }
                if(tag.equals("-NONE-")){
                    continue;
                }
                vocab.add(word);
                updateTagWordFreqMap(word,tag);
            }
            updateTransitionCounts(tagWordPairs);
        }
        
    }
    
    public static void main(String args[]){
        InitFilesGenerator generator = new InitFilesGenerator();
        String corpusFile = DataPath.getDataPath() + "input\\BROWN-pos.all.clean.training.txt";
        generator.process(corpusFile);
        generator.computeInitialProbabilites();
        generator.computeTransitionProbabilities();
        generator.saveInitFiles();
    }

    private void updateTagWordFreqMap(String word, String tag) {
        Map<String,Integer> wordFreqMap = tagWordFreqMap.get(tag);
        if(wordFreqMap == null){
            wordFreqMap = new HashMap<>();
            tagWordFreqMap.put(tag, wordFreqMap);
        }
        Integer freq = wordFreqMap.get(word);
        if(freq == null){
            freq = 0;
        }
        wordFreqMap.put(word, freq+1);
    }

    private void updateTransitionCounts(String[] tagWordPairs) {

        for (int count=0;count<tagWordPairs.length-1;count++){
            String tag1=getTagFromWordPair(tagWordPairs,count);
            if(tag1.equals("-NONE-")){
                continue;
            }
            int tag2Index = count+1;
            String tag2=getTagFromWordPair(tagWordPairs,tag2Index);
            while(tag2.equals("-NONE-") && tag2Index < tagWordPairs.length){
                tag2Index++;
                if(tag2Index < tagWordPairs.length)
                    tag2=getTagFromWordPair(tagWordPairs,tag2Index);
            }
            if(tag2.equals("-NONE-")){
                continue;
            }
            //System.out.println("tag1: " + tag1 + " tag2: " + tag2);
            PennTreeBankTagSet pennTag1 = (PennTreeBankTagSet)Enum.valueOf(PennTreeBankTagSet.class, PennTreeBankTagSet.getEnumName(tag1));
            PennTreeBankTagSet pennTag2 = (PennTreeBankTagSet)Enum.valueOf(PennTreeBankTagSet.class, PennTreeBankTagSet.getEnumName(tag2));
            transitionCount[pennTag1.getTagId()][pennTag2.getTagId()] += 1;
        }
    }

    private void computeInitialProbabilites() {
        int totalSum = getTotalSum(this.tags_T1_instant);
        for(int i=0;i<this.tags_T1_instant.length;i++){
            this.initialProbabilities[i] = (double)tags_T1_instant[i]/totalSum;
        }
    }

    private void computeTransitionProbabilities() {
        for(int i=0;i<this.transitionProbabilities.length;i++){
            int totalSum = getTotalSum(this.transitionCount[i]);
            for(int j=0;j<this.transitionProbabilities[0].length;j++){
                this.transitionProbabilities[i][j] = (double)this.transitionCount[i][j]/totalSum;
            }
        }
    }

    private void saveInitFiles() {
        try {
            this.saveVocabulary();
            this.saveTagWordFreqMap();
            this.saveInitalAndTransitionProbabilites();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private String getTagFromWordPair(String[] tagWordPairs, int i) {
        String tagWordPair = tagWordPairs[i]; 
        String[] splits = tagWordPair.split(" ");
        return splits[0];
    }

    private int getTotalSum(int[] arr) {
        int sum = 0;
        for(int i=0;i<arr.length;i++){
            sum+= arr[i];
        }
        return sum;
    }

    private void saveVocabulary() throws IOException {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setVocab(vocab);
        String fileName = DataPath.getDataPath() + "processedInput\\vocab.ser";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(vocabulary);
    }

    private void saveTagWordFreqMap() throws IOException {
        TagWordFreqMap tagWordFreqMap1 = new TagWordFreqMap();
        tagWordFreqMap1.setTagWordFreqMap(this.tagWordFreqMap);
        String fileName = DataPath.getDataPath() + "processedInput\\tagWordFreqMap.ser";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(tagWordFreqMap1);
    }

    private void saveInitalAndTransitionProbabilites() throws IOException {
        InitialAndTransitionProbabilities initTranProbs = new InitialAndTransitionProbabilities();
        initTranProbs.setInitialProbabilities(this.initialProbabilities);
        initTranProbs.setTransitionProbabilities(this.transitionProbabilities);
        String fileName = DataPath.getDataPath() + "processedInput\\initStatesAndTransitions.ser";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(initTranProbs);
    }
    
}
