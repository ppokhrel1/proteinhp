package proteinhp;
import java.util.ArrayList;

public class CrankShaftMover extends Mover{
    private Chain oldChain;
   
    public CrankShaftMover(Chain chain){
        this.chain = chain;
        
        this.oldChain = chain;

    }

    private ArrayList<Integer> getPossiblePoints(Chain chain){
        ArrayList<Integer> possible = new ArrayList<Integer>();
        for (int i = 0; i < chain.getBondSequence().length() - 1; i++){
            char[] myArray = chain.getBondSequence().toCharArray();
            if ((myArray[i] == 'U'|| myArray[i] == 'D') && (myArray[i+1] == 'L' || myArray[i+1] == 'R') || ((myArray[i] == 'L' || myArray[i] == 'R') && (myArray[i+1] == 'U' || myArray[i+1] == 'D')))  
            possible.add(i);

        }

        return possible;
    }

    public boolean flip(){
        int position = (int)(Math.round(Math.random() * getPossiblePoints(this.chain).size() - 1))+ 1 ;

        String seq = this.chain.getBondSequence();

        String temp = "";
        for (int i = 0; i < position; i++){
            temp += seq.charAt(i);
        }

        for(int i = position; i < seq.length(); i++){
            if (seq.charAt(i) == 'U')
                temp += 'D';
            else if (seq.charAt(i) == 'D')
                temp += 'U';
            else if (seq.charAt(i) == 'L')
                temp += 'R';
            else if (seq.charAt(i) == 'R')
                temp += 'L';
        

        }

        chain = new Chain(chain.getSequence(), chain.getBondSequence());
        Chain.ChainIterator itr = chain.new ChainIterator();
        while(!(itr.isEnd())){
            if (ChainBuilder.checkClash(chain, itr.getResidue()))
                return false;
            itr.next();
        }
        
        return true;


    }

    public void unwind(){
        chain = oldChain;
    }
}
