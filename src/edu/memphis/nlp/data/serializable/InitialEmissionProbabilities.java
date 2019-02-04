
package edu.memphis.nlp.data.serializable;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import java.io.Serializable;
import edu.memphis.nlp.utilities.VocabularyLookup;

/**
 *
 * @author nabin
 */
public class InitialEmissionProbabilities implements Serializable{
    
    private double[][] emissionlProbabilities = new double[PennTreeBankTagSet.values().length][VocabularyLookup.getInstance().getVocabIntMapping().size()];

    /**
     * @return the emissionlProbabilities
     */
    public double[][] getEmissionlProbabilities() {
        return emissionlProbabilities;
    }

    /**
     * @param emissionlProbabilities the emissionlProbabilities to set
     */
    public void setEmissionlProbabilities(double[][] emissionlProbabilities) {
        this.emissionlProbabilities = emissionlProbabilities;
    }
}
