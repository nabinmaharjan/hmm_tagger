
package edu.memphis.nlp.hmmtagger;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.Opdf;
import be.ac.ulg.montefiore.run.jahmm.OpdfInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import edu.memphis.nlp.data.HMMData;
import edu.memphis.nlp.data.PennTreeBankTagSet;
import edu.memphis.nlp.data.serializable.HMMModel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.memphis.nlp.utilities.DataPath;
import edu.memphis.nlp.utilities.HMMDataReader;
import edu.memphis.nlp.utilities.InitialAndTransitionProbabilitiesLookup;
import edu.memphis.nlp.utilities.InitialEmissionProbabilitiesLookup;
import edu.memphis.nlp.utilities.VocabularyLookup;
import java.io.File;

/**
 *
 * @author nabin
 */
public class HMMTagger {
    
    Hmm<ObservationInteger> taggerHMM = null;
    public HMMTagger(){
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            HMMTagger tagger = new HMMTagger();
            
//            if (args.length != 1){
//                System.out.println("No or invalid number of arguments provided!");
//                return;
//            }
//            String fileName = args[0].trim();
//            
//            if(!new File(fileName).exists()){
//                System.out.println("Given input file does not exist!");
//                return;
//            }
            String fileName = DataPath.getDataPath() + "input\\BROWN-pos.all.clean.training.txt";
           
            int limit = 99999;
            HMMData hmmDataInstance = HMMDataReader.readSentencesFromFile(fileName,limit);
            //HMMData hmmDataInstance = HMMDataReader.readTestSentencesFromFile(fileName,limit);
            
            ArrayList<ArrayList<ObservationInteger>> trainingSequences = hmmDataInstance.codedWordSequences;
            int size = hmmDataInstance.codedWordSequences.size();
            System.out.println("Seq Size:" + size);
            
            String mode = "predict";//or predict
            switch(mode){
                case "learn":
                    int numOfIterances = 1;
                    tagger.trainHMM(trainingSequences,numOfIterances);
                    tagger.saveHMM();
                    break;
                case "predict":
                    tagger.loadHMM();
                    tagger.predict(hmmDataInstance);
                    tagger.displayOutput(hmmDataInstance);
                    tagger.computeAccuracy(hmmDataInstance);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(HMMTagger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HMMTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private void trainHMM(ArrayList<ArrayList<ObservationInteger>> trainingSequences, int numOfIterations) {
        this.buildInitialTaggerHMM(null);
//        System.out.println("Initial HMM created!");
//        BaumWelchLearner bwl = new BaumWelchLearner();
//        for (int i = 0; i < numOfIterations; i++) {
//                taggerHMM = bwl.iterate(taggerHMM, trainingSequences);
//         }
        System.out.println("Resulting HMM:\r\n" + taggerHMM);
    }
    
    public ArrayList<String> getTagList(ArrayList<String> tokenList) throws IOException, ClassNotFoundException{
        HMMData hmmDataInstance = HMMDataReader.readHMMDataInstance(tokenList);
        if(taggerHMM == null){
            this.loadHMM();
        }    
        this.predict(hmmDataInstance);
        return hmmDataInstance.predictedTagSequences.get(0);
    }
    
    private void predict(HMMData hmmDataInstance) {
        
        ArrayList<ArrayList<ObservationInteger>> testSequences = hmmDataInstance.codedWordSequences;
        ArrayList<ArrayList<String>> predictedTagSequenceList = new ArrayList<>();
        for(ArrayList<ObservationInteger> testSeq: testSequences){
            int[] states = taggerHMM.mostLikelyStateSequence(testSeq);
            ArrayList<String> predictedTagSequence = new ArrayList<>();
            for(int i=0;i<states.length;i++){
                predictedTagSequence.add(PennTreeBankTagSet.getName(states[i]));
            }
            predictedTagSequenceList.add(predictedTagSequence);
        }
        hmmDataInstance.predictedTagSequences = predictedTagSequenceList;
    }

    public void saveHMM() throws IOException {
        HMMModel hmmModel = new HMMModel();
        for(int i=0;i<taggerHMM.nbStates();i++){
            hmmModel.getInitialProbabilities()[i] = taggerHMM.getPi(i);
            
            Opdf<ObservationInteger> eProbs = taggerHMM.getOpdf(i);
            for(int j=0;j<hmmModel.getEmissionProbabilities()[0].length;j++){
                hmmModel.getEmissionProbabilities()[i][j] = eProbs.probability(new ObservationInteger(j));
            }
            
            for (int j = 0; j < taggerHMM.nbStates(); j++) {
                hmmModel.getTransitionProbabilities()[i][j] = taggerHMM.getAij(i, j);
            }
        }
        String fileName = DataPath.getDataPath() + "taggerHMM.ser";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(hmmModel);
    }

    public void loadHMM() throws IOException, ClassNotFoundException {
        String fileName = DataPath.getDataPath() + "taggerHMM.ser";
        ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(fileName));
        HMMModel hmmModel = (HMMModel)ois.readObject();
        this.buildInitialTaggerHMM(hmmModel);
    }

    private void buildInitialTaggerHMM(HMMModel hmmModel) {
        int numOfStates = PennTreeBankTagSet.values().length;
        //add 1 to vocab size to account for unknown word 
        int observationSize = VocabularyLookup.getInstance().getVocabulary().size() + 1;
        OpdfIntegerFactory factory = new OpdfIntegerFactory(observationSize);
        Hmm<ObservationInteger> hmm = new Hmm<>(numOfStates, factory);
        
        double[] initialProbabilities = hmmModel!=null?hmmModel.getInitialProbabilities():InitialAndTransitionProbabilitiesLookup.getInstance().getInitialProbabilities();
        double[][] transitionProbabilities = hmmModel!=null?hmmModel.getTransitionProbabilities():InitialAndTransitionProbabilitiesLookup.getInstance().getTransitionProbabilities();
        double[][] emissionProbabilities = hmmModel!=null?hmmModel.getEmissionProbabilities():InitialEmissionProbabilitiesLookup.getInstance().getEmissionProbabilities();
        
        for (int i = 0; i < numOfStates; i++) {

            hmm.setPi(i, initialProbabilities[i]);
            hmm.setOpdf(i, new OpdfInteger(emissionProbabilities[i]));
        }

        for (int i = 0; i < numOfStates; i++) {
            double[] transProbs = transitionProbabilities[i];
            for (int j = 0; j < numOfStates; j++) {
                hmm.setAij(i, j, transProbs[j]);
            }
        }
        
        taggerHMM = hmm;
    }
    
    public void computeAccuracy(HMMData hmmDataInstance){
        int size = hmmDataInstance.codedWordSequences.size();
        int total = 0;
        int correct = 0;
        
        int totalUnknownWords = 0;
        int correctPredictedTagsForUnknownWords = 0;
        for(int i=0;i<size;i++){
            ArrayList<String> actualTagSeqs =  hmmDataInstance.actualTagSequences.get(i);
            ArrayList<String> predictedTagSeqs =  hmmDataInstance.predictedTagSequences.get(i);
            ArrayList<String> processedWords =  hmmDataInstance.processedWordSequences.get(i);
            for(int j=0;j<actualTagSeqs.size();j++){
                if(processedWords.get(j) == "__unknown__"){
                    totalUnknownWords++;
                }
                if(actualTagSeqs.get(j).equals(predictedTagSeqs.get(j))){
                   if(processedWords.get(j) == "__unknown__"){
                       correctPredictedTagsForUnknownWords++;
                   }
                    correct++;
                }
                total++;
            }
        }
        System.out.println("Accuracy: " + (double)correct*100/total);
        System.out.println("Unknown Word Counts: " + totalUnknownWords);
        System.out.println("Unknown Word Accuracy: " + (double)correctPredictedTagsForUnknownWords*100/totalUnknownWords);
    }
    private void displayOutput(HMMData hmmDataInstance) {
        int size = hmmDataInstance.codedWordSequences.size();
        for(int i=0;i<size;i++){
                StringBuilder sb = new StringBuilder();
                ArrayList<String> wordSeqs =  hmmDataInstance.originalWordSequences.get(i);
                ArrayList<String> processedWordSeqs =  hmmDataInstance.processedWordSequences.get(i);
                ArrayList<String> actualTagSeqs =  hmmDataInstance.actualTagSequences.get(i);
                ArrayList<String> predictedTagSeqs =  hmmDataInstance.predictedTagSequences.get(i);
                
                for(int j=0;j<wordSeqs.size();j++){
                    if(j!=0){
                        sb.append("\t");
                    }
                    sb.append(wordSeqs.get(j)).append("/").append(processedWordSeqs.get(j)).append("/").append(actualTagSeqs.get(j)).append("/").append(predictedTagSeqs.get(j));
                }
                System.out.println(sb.toString());
            }
    }
    
}
