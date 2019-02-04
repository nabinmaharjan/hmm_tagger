
package edu.memphis.nlp.utilities;

import edu.memphis.nlp.data.serializable.UnknownWordEmissionProbabilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author nabin
 */
public class UnknownEmissionProbabilitiesLookup {
    private static UnknownEmissionProbabilitiesLookup _instance = null;
    private static final Object mutex = new Object();
    private double[] unknownEmissionProbabilities;
    
    public static UnknownEmissionProbabilitiesLookup getInstance(){
        synchronized(mutex){
            if(_instance == null){
            _instance = new UnknownEmissionProbabilitiesLookup();
        }
    }
      return _instance;
    }
    private UnknownEmissionProbabilitiesLookup() {
        try {
            String unknownEmissionProbFile = DataPath.getDataPath() + "processedInput\\unknownWordEmissionProbs.ser";
            ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(unknownEmissionProbFile));
            UnknownWordEmissionProbabilities unknownEmissbionProbsObj = (UnknownWordEmissionProbabilities)ois.readObject();
            unknownEmissionProbabilities = unknownEmissbionProbsObj.getUnknownEmissionProbabilities();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * @return the unknownEmissionProbabilities
     */
    public double[] getUnknownEmissionProbabilities() {
        return unknownEmissionProbabilities;
    }
}
