
package edu.memphis.nlp.utilities;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import edu.memphis.nlp.data.HMMData;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nabin
 */
public class HMMDataReader {
    
    public static HMMData readSentencesFromFile(String fileName,int limit){
        HMMData hmmDataInstance = new HMMData();
        ArrayList<String> lines = FileReaderUtil.getLinesFromFile(fileName);
        
        Set<String> vocabulary = VocabularyLookup.getInstance().getVocabulary();
        Map<String,Integer> vocabIntMapping = VocabularyLookup.getInstance().getVocabIntMapping();
        
        for(int index=0;index<limit && index <lines.size();index++){
            
            ArrayList<String> origWordSequences = new ArrayList<>();
            ArrayList<String> procWordSequences = new ArrayList<>();
            ArrayList<String> actualTagSequences = new ArrayList<>();
            ArrayList<ObservationInteger> codedWordSequences = new ArrayList<>();
            
            String line = lines.get(index);
            String[] tagWordPairs = line.split("\t");
            for(int i=0;i<tagWordPairs.length;i++){
                String tagWordPair = tagWordPairs[i];
                String[] splits = tagWordPair.split(" ");
                String tag = splits[0];
                String word = splits[1];
                if(tag.equals("-NONE-")){
                    continue;
                }
                origWordSequences.add(word);
                if(!vocabulary.contains(word)){
                    word = VocabularyLookup.UNKNOWN_WORD;
                }
                procWordSequences.add(word);
                actualTagSequences.add(tag);
                codedWordSequences.add(new ObservationInteger(vocabIntMapping.get(word)));
            }
            hmmDataInstance.originalWordSequences.add(origWordSequences);
            hmmDataInstance.processedWordSequences.add(procWordSequences);
            hmmDataInstance.codedWordSequences.add(codedWordSequences);
            hmmDataInstance.actualTagSequences.add(actualTagSequences);
        }
        return hmmDataInstance;
    }
    
    public static HMMData readHMMDataInstance(ArrayList<String> tokenList){
        HMMData hmmDataInstance = new HMMData();
        Set<String> vocabulary = VocabularyLookup.getInstance().getVocabulary();
        Map<String,Integer> vocabIntMapping = VocabularyLookup.getInstance().getVocabIntMapping();
        

            
            ArrayList<String> origWordSequences = new ArrayList<>();
            ArrayList<String> procWordSequences = new ArrayList<>();
            ArrayList<ObservationInteger> codedWordSequences = new ArrayList<>();
            ArrayList<String> actualTagSequences = new ArrayList<>();
            
            for(int i=0;i<tokenList.size();i++){
                String word = tokenList.get(i);
                origWordSequences.add(word);
                if(!vocabulary.contains(word)){
                    word = VocabularyLookup.UNKNOWN_WORD;
                }
                procWordSequences.add(word);
                codedWordSequences.add(new ObservationInteger(vocabIntMapping.get(word)));
                actualTagSequences.add("");
            }
            
            hmmDataInstance.originalWordSequences.add(origWordSequences);
            hmmDataInstance.processedWordSequences.add(procWordSequences);
            hmmDataInstance.codedWordSequences.add(codedWordSequences);
            hmmDataInstance.actualTagSequences.add(actualTagSequences);

        return hmmDataInstance;
    }
    public static HMMData readTestSentencesFromFile(String fileName,int limit){
        HMMData hmmDataInstance = new HMMData();
        ArrayList<String> lines = FileReaderUtil.getLinesFromFile(fileName);
        Set<String> vocabulary = VocabularyLookup.getInstance().getVocabulary();
        Map<String,Integer> vocabIntMapping = VocabularyLookup.getInstance().getVocabIntMapping();
        
        for(int index=0;index<limit && index <lines.size();index++){
            
            ArrayList<String> origWordSequences = new ArrayList<>();
            ArrayList<String> procWordSequences = new ArrayList<>();
            ArrayList<ObservationInteger> codedWordSequences = new ArrayList<>();
            ArrayList<String> actualTagSequences = new ArrayList<>();
            
            String line = lines.get(index);
            String[] words = line.split(" ");
            for(int i=0;i<words.length;i++){
                String word = words[i].trim();
                origWordSequences.add(word);
                if(!vocabulary.contains(word)){
                    word = VocabularyLookup.UNKNOWN_WORD;
                }
                procWordSequences.add(word);
                codedWordSequences.add(new ObservationInteger(vocabIntMapping.get(word)));
                actualTagSequences.add("");
            }
            hmmDataInstance.originalWordSequences.add(origWordSequences);
            hmmDataInstance.processedWordSequences.add(procWordSequences);
            hmmDataInstance.codedWordSequences.add(codedWordSequences);
            hmmDataInstance.actualTagSequences.add(actualTagSequences);
        }
        return hmmDataInstance;
    }
}
