
package edu.memphis.nlp.utilities;

import edu.memphis.nlp.data.serializable.TagWordFreqMap;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 *
 * @author nabin
 */
public class TagWordFreqMapLookup {
    private static TagWordFreqMapLookup _instance = null;
    private static final Object mutex = new Object();
    private Map<String,Map<String,Integer>> tagWordFreqMap = null;
    
    public static TagWordFreqMapLookup getInstance(){
        synchronized(mutex){
            if(_instance == null){
            _instance = new TagWordFreqMapLookup();
        }
    }
      return _instance;
    }
    private TagWordFreqMapLookup() {
        try {
            String tagWordFreqFile = DataPath.getDataPath() + "processedInput\\tagWordFreqMap.ser";
            ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(tagWordFreqFile));
            TagWordFreqMap map = (TagWordFreqMap)ois.readObject();
            tagWordFreqMap = map.getTagWordFreqMap();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * @return the tagWordFreqMap
     */
    public Map<String,Map<String,Integer>> getTagWordFreqMap() {
        return tagWordFreqMap;
    }
}
