
package edu.memphis.nlp.data;

/**
 *
 * @author nabin
 */
public enum PennTreeBankTagSet {

$(0,"$"),
COMMA(1,","),
DOT(2,"."),
COLON(3,":"),
LQUOTE(4,"``"),
CC(5,"CC"),
CD(6,"CD"),
DT(7,"DT"),
EX(8,"EX"),
FW(9,"FW"),
IN(10,"IN"),
JJ(11,"JJ"),
JJR(12,"JJR"),
JJS(13,"JJS"),
LRB(14,"-LRB-"),
LS(15,"LS"),
MD(16,"MD"),
NN(17,"NN"),
NNP(18,"NNP"),
NNPS(19,"NNPS"),
NNS(20,"NNS"),
PDT(21,"PDT"),
POS(22,"POS"),
PRP(23,"PRP"),
PRP$(24,"PRP$"),
PRP$R(25,"PRP$R"),
RB(26,"RB"),
RBR(27,"RBR"),
RBS(28,"RBS"),
RP(29,"RP"),
RRB(30,"-RRB-"),
SYM(31,"SYM"),
TO(32,"TO"),
UH(33,"UH"),
VB(34,"VB"),
VBD(35,"VBD"),
VBG(36,"VBG"),
VBN(37,"VBN"),
VBP(38,"VBP"),
VBZ(39,"VBZ"),
WDT(40,"WDT"),
WP(41,"WP"),
WP$(42,"WP$"),
WRB(43,"WRB"),
RQUOTE(44,"''");


private int id;
private String tag;
private PennTreeBankTagSet(int value,String tag) {
        this.id = value;
        this.tag = tag;
    }

public int getTagId() {
    return id;
}

public String getTag(){
    return tag;
}
 
public static String getName(int value){
        String text = "";
        for(PennTreeBankTagSet t:PennTreeBankTagSet.values()){
            if(t.getTagId() == value){
                text = t.getTag();
                break;
            }      
        }
        return text;
    }

public static String getEnumName(String tag){
    String enumName = tag;
    switch(tag){
        case ",":
            enumName = "COMMA";
            break;
        case ".":
            enumName = "DOT";
            break;
        case ":":
            enumName = "COLON";
            break;
        case "``":
            enumName = "LQUOTE";
            break;
        case "''":
            enumName = "RQUOTE";
            break;
        case "-RRB-":
            enumName = "RRB";
            break;
        case "-LRB-":
            enumName = "LRB";
            break;
    }
    return enumName;
}
}
