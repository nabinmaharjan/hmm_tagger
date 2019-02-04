
package edu.memphis.nlp.utilities;

import edu.memphis.nlp.data.serializable.Vocabulary;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nabin
 */
public class VocabularyLookup {
    private static VocabularyLookup _instance = null;
    private static final Object mutex = new Object();
    private Set<String> vocabulary = null;
    private Map<String,Integer> vocabIntMapping = new HashMap<>();
    
    public static final String UNKNOWN_WORD = "__unknown__";
    public static VocabularyLookup getInstance(){
        synchronized(mutex){
            if(_instance == null){
            _instance = new VocabularyLookup();
        }
    }
      return _instance;
    }
    private VocabularyLookup() {
        try {
            String vocabFile = DataPath.getDataPath() + "processedInput\\vocab.ser";
            ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(vocabFile));
            Vocabulary vocab = (Vocabulary)ois.readObject();
            vocabulary = vocab.getVocab();
            loadVocabToIntMapping();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * @return the vocabulary
     */
    public Set<String> getVocabulary() {
        return vocabulary;
    }

    private void loadVocabToIntMapping() {
        int code = 0;
        for(String word:vocabulary){
            vocabIntMapping.put(word, code++);
        }
        //add specail word __unknown__ to represent unseen words
        vocabIntMapping.put("__unknown__",code);
    }

    /**
     * @return the vocabIntMapping
     */
    public Map<String,Integer> getVocabIntMapping() {
        return vocabIntMapping;
    }

}
