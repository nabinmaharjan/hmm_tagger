
package edu.memphis.nlp.data.serializable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author nabin
 */
public class Vocabulary implements Serializable {
    private Set<String> vocab = new HashSet<>();

    /**
     * @return the vocab
     */
    public Set<String> getVocab() {
        return vocab;
    }

    /**
     * @param vocab the vocab to set
     */
    public void setVocab(Set<String> vocab) {
        this.vocab = vocab;
    }
}
