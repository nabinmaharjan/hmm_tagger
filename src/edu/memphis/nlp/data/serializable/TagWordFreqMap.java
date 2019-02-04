
package edu.memphis.nlp.data.serializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nabin
 */
public class TagWordFreqMap implements Serializable {
    private Map<String,Map<String,Integer>> tagWordFreqMap = new HashMap<>();

    /**
     * @return the tagWordFreqMap
     */
    public Map<String,Map<String,Integer>> getTagWordFreqMap() {
        return tagWordFreqMap;
    }

    /**
     * @param tagWordFreqMap the tagWordFreqMap to set
     */
    public void setTagWordFreqMap(Map<String,Map<String,Integer>> tagWordFreqMap) {
        this.tagWordFreqMap = tagWordFreqMap;
    }
}
