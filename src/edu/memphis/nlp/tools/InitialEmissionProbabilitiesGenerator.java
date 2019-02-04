
package edu.memphis.nlp.tools;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import edu.memphis.nlp.data.serializable.InitialEmissionProbabilities;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import edu.memphis.nlp.utilities.DataPath;
import edu.memphis.nlp.utilities.TagWordFreqMapLookup;
import edu.memphis.nlp.utilities.UnknownEmissionProbabilitiesLookup;
import edu.memphis.nlp.utilities.VocabularyLookup;

/**
 *
 * @author nabin
 */
public class InitialEmissionProbabilitiesGenerator {
    int[][] emissionCount;
    double[][] emissionProbs;
    public InitialEmissionProbabilitiesGenerator(){
        //
    }
    
    public void initialize(int vocabSize){
        emissionCount = new int[PennTreeBankTagSet.values().length][vocabSize];
        emissionProbs = new double[PennTreeBankTagSet.values().length][vocabSize];
        
        for(int i=0;i<PennTreeBankTagSet.values().length;i++){
            Arrays.fill(emissionCount[i], 0);
            Arrays.fill(emissionProbs[i], 0);
        }
    }
    public static void main(String args[]){
        InitialEmissionProbabilitiesGenerator initEmissionProbsGenerator = new InitialEmissionProbabilitiesGenerator();
        Map<String,Integer> vocabIntMapping = VocabularyLookup.getInstance().getVocabIntMapping();
        initEmissionProbsGenerator.initialize(vocabIntMapping.size());
        
        Map<String,Map<String,Integer>> map = TagWordFreqMapLookup.getInstance().getTagWordFreqMap();
        initEmissionProbsGenerator.getEmissionCounts(vocabIntMapping,map);
        
        double[] unknownEmissionProbabilities = UnknownEmissionProbabilitiesLookup.getInstance().getUnknownEmissionProbabilities();
        initEmissionProbsGenerator.computeInitialEmissionProbs(unknownEmissionProbabilities);
        
        try {
            initEmissionProbsGenerator.saveInitialEmissionProbabilities();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void getEmissionCounts(Map<String,Integer> vocabIntMapping,Map<String, Map<String, Integer>> map) {
        Set<String> tags = map.keySet();
        for(String tag:tags){
            Map<String, Integer> wordFreqMap = map.get(tag);
            PennTreeBankTagSet pennTag = (PennTreeBankTagSet)Enum.valueOf(PennTreeBankTagSet.class, PennTreeBankTagSet.getEnumName(tag));
            int tag_id = pennTag.getTagId();
            Set<String> words = wordFreqMap.keySet();
            for(String word:words){
                int word_id = vocabIntMapping.get(word);
                int wordFreq = wordFreqMap.get(word);
                emissionCount[tag_id][word_id] += wordFreq;
            }
        }
    }

    private void computeInitialEmissionProbs(double[] unknownEmissionProbabilities) {
        for(int i=0;i<emissionProbs.length;i++){
            int sum = getTotalSum(emissionCount[i]);
            if(sum > 0){
                //last observation is unknown word
                for(int j=0;j<emissionProbs[0].length-1;j++){
                    emissionProbs[i][j] = (double)emissionCount[i][j]/sum;
                    
                    //For state i, sum total of emissionProbs excluding unknown words = 1 - unknownEmissonProbabilities. so multiply each emission probability by 1 - unknown word emission prob for that state
                    emissionProbs[i][j] *= (1-unknownEmissionProbabilities[i]);
                   // emissionProbs[i][j] *= 1;
                }
                emissionProbs[i][emissionProbs[0].length-1] = unknownEmissionProbabilities[i];
            }
            
        }
    }

    private int getTotalSum(int[] arr) {
        int sum = 0;
        for(int i=0;i<arr.length;i++){
            sum+= arr[i];
        }
        return sum;
    }

    private void saveInitialEmissionProbabilities() throws IOException {
        InitialEmissionProbabilities initialEmissionProbs = new InitialEmissionProbabilities();
        initialEmissionProbs.setEmissionlProbabilities(emissionProbs);
        String fileName = DataPath.getDataPath() + "processedInput\\initialEmissionProbs.ser";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(initialEmissionProbs);
    }
}
