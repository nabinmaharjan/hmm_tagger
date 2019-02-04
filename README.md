# HMM Tagger (a netbeans project based on JAVA)
Given a sentence, i.e., a sequence of words as an input, the HMM tagger generates a sequence of POS tags as an output.

---------------------
# How to run
-------------------------
1. First, run InitFilesGenerator.java and then, InitialEmissionProbabilitiesGenerator.java to create serialized files of vocabulary and initial estimates of initial probabilities, transition probabilities and emission probabilites.
2. run HMMTagger.java in learn mode for HMM training (set mode variable in the main method to "learn" value)
3. run HMMTagger.java in predict mode for HMM testing/prediction.
