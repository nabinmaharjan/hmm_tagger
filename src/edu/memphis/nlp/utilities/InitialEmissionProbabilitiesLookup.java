
package edu.memphis.nlp.utilities;

import edu.memphis.nlp.data.serializable.InitialEmissionProbabilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author nabin
 */
public class InitialEmissionProbabilitiesLookup {
    private static InitialEmissionProbabilitiesLookup _instance = null;
    private static final Object mutex = new Object();
    private double[][] emissionProbabilities;
    
    public static InitialEmissionProbabilitiesLookup getInstance(){
        synchronized(mutex){
            if(_instance == null){
            _instance = new InitialEmissionProbabilitiesLookup();
        }
    }
      return _instance;
    }
    private InitialEmissionProbabilitiesLookup() {
        try {
            String emissionProbFile = DataPath.getDataPath() + "processedInput\\initialEmissionProbs.ser";
            ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(emissionProbFile));
            InitialEmissionProbabilities initialEmitProbs = (InitialEmissionProbabilities)ois.readObject();
            emissionProbabilities = initialEmitProbs.getEmissionlProbabilities();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * @return the emissionProbabilities
     */
    public double[][] getEmissionProbabilities() {
        return emissionProbabilities;
    }
}
