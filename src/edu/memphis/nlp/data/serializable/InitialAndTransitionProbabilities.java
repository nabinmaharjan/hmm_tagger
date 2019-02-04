
package edu.memphis.nlp.data.serializable;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import java.io.Serializable;

/**
 *
 * @author nabin
 */
public class InitialAndTransitionProbabilities implements Serializable{
    private double[] initialProbabilities = new double[PennTreeBankTagSet.values().length];
    private double[][] transitionProbabilities = new double[PennTreeBankTagSet.values().length][PennTreeBankTagSet.values().length];

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
}
