
package edu.memphis.nlp.data.serializable;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import java.io.Serializable;

/**
 *
 * @author nabin
 */
public class UnknownWordEmissionProbabilities implements Serializable{
    private double[] unknownEmissionProbabilities = new double[PennTreeBankTagSet.values().length];

    /**
     * @return the unknownEmissionProbabilities
     */
    public double[] getUnknownEmissionProbabilities() {
        return unknownEmissionProbabilities;
    }

    /**
     * @param unknownEmissionProbabilities the unknownEmissionProbabilities to set
     */
    public void setUnknownEmissionProbabilities(double[] unknownEmissionProbabilities) {
        this.unknownEmissionProbabilities = unknownEmissionProbabilities;
    }
}
