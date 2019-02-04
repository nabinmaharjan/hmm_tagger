
package edu.memphis.nlp.utilities;

import edu.memphis.nlp.data.PennTreeBankTagSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nabin
 */
public class LookupTest {
    public static void main(String args[]){
        Set<String> vocab = VocabularyLookup.getInstance().getVocabulary();
        System.out.println("Vocab Count: " + vocab.size());
        ArrayList<String> outLines = new ArrayList<>();
        for(String word:vocab){
            outLines.add(word);
        }
        FileWriterUtil.writeToFile(outLines, DataPath.getDataPath() + "vocab_plain.txt");
        
//        Map<String,Map<String,Integer>> map = TagWordFreqMapLookup.getInstance().getTagWordFreqMap();
//        Set<String> keys = map.keySet();
//        for(String key:keys){
//            Map<String,Integer> wordMap = map.get(key);
//            StringBuilder sb = new StringBuilder();
//            sb.append(key + ":\n");
//            Set<String>words = wordMap.keySet();
//            for(String word:words){
//                sb.append("\t"+word + ":" + wordMap.get(word) + "\n");
//            }
//            System.out.println(sb.toString());
//        }
        
//       double[] initialProbabilities = InitialAndTransitionProbabilitiesLookup.getInstance().getInitialProbabilities();
//       double[][] transitionProbabilities = InitialAndTransitionProbabilitiesLookup.getInstance().getTransitionProbabilities();
//       
//       StringBuilder sb = new StringBuilder();
//       for(int i=0;i<initialProbabilities.length;i++){
//           sb.append(PennTreeBankTagSet.getName(i)+":").append(initialProbabilities[i]).append("\t");
//           //sb.append(initialProbabilities[i]).append("\t");
//       }
//       System.out.println(sb.toString());
//       sb.setLength(0);
//       sb.append("\t");
//       for(int i=0;i<transitionProbabilities.length;i++){
//           sb.append(PennTreeBankTagSet.getName(i)+"\t");
//       }
//       System.out.println(sb.toString());
//       sb.setLength(0);
//        for(int i=0;i<transitionProbabilities.length;i++){
//            sb.append(PennTreeBankTagSet.getName(i)+"\t");
//            for(int j=0;j<transitionProbabilities[0].length;j++){
//                sb.append(transitionProbabilities[i][j]+"\t");
//            }
//            System.out.println(sb.toString());
//            sb.setLength(0);
//        } 
        
//        double[] unknownEmissionProbabilities = UnknownEmissionProbabilitiesLookup.getInstance().getUnknownEmissionProbabilities();
//
//       
//       StringBuilder sb = new StringBuilder();
//       for(int i=0;i<unknownEmissionProbabilities.length;i++){
//           sb.append(PennTreeBankTagSet.getName(i)+":").append(unknownEmissionProbabilities[i]).append("\t");
//           //sb.append(initialProbabilities[i]).append("\t");
//       }
//       System.out.println(sb.toString());
        
//        double[][] emissionProbabilities = InitialEmissionProbabilitiesLookup.getInstance().getEmissionProbabilities();
//        StringBuilder sb = new StringBuilder();
//               sb.append("\t");
//       for(int i=0;i<emissionProbabilities.length;i++){
//           sb.append(PennTreeBankTagSet.getName(i)+"\t");
//       }
//       System.out.println(sb.toString());
//       sb.setLength(0);
//        for(int i=0;i<emissionProbabilities.length;i++){
//            sb.append(PennTreeBankTagSet.getName(i)+"\t");
//            for(int j=0;j<emissionProbabilities[0].length;j++){
//                sb.append(emissionProbabilities[i][j]+"\t");
//            }
//            System.out.println(sb.toString());
//            sb.setLength(0);
//        } 
    }
}
