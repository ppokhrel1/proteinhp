package experiments;

import proteinhp.*;
import java.util.ArrayList;





public class DiscreteStructuresVsP{



    public static void main(String[] args){



        String seq = args[0];



        int noOfP = 0;

        for (int i = 0; i < seq.length(); i++){

            if (seq.toCharArray()[i] == 'P'){

                noOfP++;

            }

        }

        Chain chain = new Chain(seq);

        ArrayList<Chain> chains = ChainBuilder.getLowestEnergyConformers(chain);

        int discreteStructures = chains.size();



        System.out.println(seq + "\t" + "DiscreteStructures: " +discreteStructures);



        

    }





}
