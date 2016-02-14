package proteinhp;
//import java.io.Serializable;



public class StringRep implements Comparable<StringRep>{
    String myString;
    String seq;
    public StringRep(String myString, String seq){
        this.myString = myString;
        this.seq = seq;
    }

    public int compareTo(StringRep newStringRep){
        //String seq = "";
        Chain myChain;
        Chain newChain;
        int returnVal = 0;
        if (newStringRep instanceof StringRep){

            String newString = ((StringRep)newStringRep).myString;

            if (myString.length() != newString.length())
                returnVal =  myString.compareTo(newString);
            //for (char chars : newString.toCharArray()){
            //    seq += "H";
            //}
            myChain = new Chain(seq, myString); //myString is the bond sequence
            newChain = new Chain(seq, newString);
            if (myChain.equals(newChain))
                return 0;
        }
        return returnVal;
    }

    public void add(char myChar){
        myString += myChar;
    }





}

