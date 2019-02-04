
package edu.memphis.nlp.data.serializable;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import java.io.Serializable;
import edu.memphis.nlp.utilities.VocabularyLookup;

/**
 *
 * @author nabin
 */
public class HMMModel implements Serializable{
    private double[] initialProbabilities = new double[PennTreeBankTagSet.values().length];
    private double[][] transitionProbabilities = new double[PennTreeBankTagSet.values().length][PennTreeBankTagSet.values().length];
    private double[][] emissionProbabilities = new double[PennTreeBankTagSet.values().length][VocabularyLookup.getInstance().getVocabIntMapping().size()];

    /**
     * @return the initialProbabilities
     */
    public double[] getInitialProbabilities() {
        return initialProbabilities;
    }

    /**
     * @param initialProbabilities the initialProbabilities to set
     */
    public void setInitialProbabilities(double[] initialProbabilities) {
        this.initialProbabilities = initialProbabilities;
    }

    /**
     * @return the transitionProbabilities
     */
    public double[][] getTransitionProbabilities() {
        return transitionProbabilities;
    }

    /**
     * @param transitionProbabilities the transitionProbabilities to set
     */
    public void setTransitionProbabilities(double[][] transitionProbabilities) {
        this.transitionProbabilities = transitionProbabilities;
    }

    /**
     * @return the emissionProbabilities
     */
    public double[][] getEmissionProbabilities() {
        return emissionProbabilities;
    }

    /**
     * @param emissionlProbabilities the emissionProbabilities to set
     */
    public void setEmissionProbabilities(double[][] emissionlProbabilities) {
        this.emissionProbabilities = emissionlProbabilities;
    }
}
