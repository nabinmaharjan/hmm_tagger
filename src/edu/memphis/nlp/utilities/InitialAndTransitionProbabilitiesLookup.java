
package edu.memphis.nlp.utilities;

import edu.memphis.nlp.data.serializable.InitialAndTransitionProbabilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author nabin
 */
public class InitialAndTransitionProbabilitiesLookup {
    private static InitialAndTransitionProbabilitiesLookup _instance = null;
    private static final Object mutex = new Object();
    private double[] initialProbabilities;
    private double[][] transitionProbabilities;
    
    public static InitialAndTransitionProbabilitiesLookup getInstance(){
        synchronized(mutex){
            if(_instance == null){
            _instance = new InitialAndTransitionProbabilitiesLookup();
        }
    }
      return _instance;
    }
    private InitialAndTransitionProbabilitiesLookup() {
        try {
            String initStateTransitionFile = DataPath.getDataPath() + "processedInput\\initStatesAndTransitions.ser";
            ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(initStateTransitionFile));
            InitialAndTransitionProbabilities initStateTranObj = (InitialAndTransitionProbabilities)ois.readObject();
            initialProbabilities = initStateTranObj.getInitialProbabilities();
            transitionProbabilities = initStateTranObj.getTransitionProbabilities();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * @return the initialProbabilities
     */
    public double[] getInitialProbabilities() {
        return initialProbabilities;
    }

    /**
     * @return the transitionProbabilities
     */
    public double[][] getTransitionProbabilities() {
        return transitionProbabilities;
    }
}
