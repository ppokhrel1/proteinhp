import java.util.*;

public class DiscreteStructures{
    private ArrayList<String> residues;
    private int length;
    private char[] resCombos;

    public DiscreteStructures(int length){
        this.residues = new ArrayList<String>();
        this.residues.add("");
        this.length = length;
        this.resCombos = new char[]{'U', 'D', 'R', 'L'};
    }

    public void generateAllCombinations(int length){
        //if (length != 0){
        for (int i = 0; i < length; i++){
            ArrayList<String> temp = new ArrayList<String>();

            //System.out.println("A");
            for (String res : this.residues){
                for (char c : this.resCombos){
                    temp.add(res + c);
                    System.out.println(res + c);
                }
            }
            this.residues = temp;
        }
           // generateAllCombinations(length - 1);

        //}

        //else{
            //do nothing, the loop is done by this point
        //}


    }
    public Iterator<String> iterator(){
        return this.residues.iterator();
    }
    
    public static void main(String[] args){
        DiscreteStructures A = new DiscreteStructures(7);
        A.generateAllCombinations(20);
        Iterator<String> res = A.iterator();
        for(Iterator<String> iter = A.iterator(); iter.hasNext(); ){
            System.out.println(iter.next());
            iter.remove();
        }

    }
}
