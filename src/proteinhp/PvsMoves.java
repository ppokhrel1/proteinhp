package proteinhp;
import java.util.ArrayList;

public class PvsMoves{


    public static void main(String[] args){
        int noOfRes = args[0].length();
        String chainSeq = "";
        for (int i = 0; i<noOfRes; i++){
            chainSeq += "U"; //Random value for now
                            //chain synchronizes itself later on
        }

        Chain chain = new Chain(args[0]);
        int noOfP = 0;
        for (int i = 0; i < args[0].length(); i++){
            if (args[0].toCharArray()[i] == 'P')
                noOfP++;
        }
        ArrayList<Chain> lowestChains = ChainBuilder.getLowestEnergyConformers(chain);
        MonteCarlo mntCarlo = new MonteCarlo(chain, Double.parseDouble(args[1])); //args[1] is the temp value. we keep it constant for now

        int noOfMoves = 0;
        boolean counter = true;
        while(counter){
            for (Chain xChain : lowestChains){
                if (chain.equals(xChain)){
                    System.out.println("Found lowest energy config in " + noOfMoves + " moves");
                    counter = false;
                }else{
                    chain = mntCarlo.run((int)Math.round(Math.random() ));
                    noOfMoves++;
                
                }

            }
            if (noOfMoves > 10000){
                System.out.println("Run unsucessful, can't compute with monte carlo");
                counter = false;
            }
        }

    }

}
