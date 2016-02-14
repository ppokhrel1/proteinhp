package proteinhp;

/*

Pujan Pokhrel

This class calculates the energy of the chain ie basically the amount of H-H chains

each H-H contact outside the main chain has the energy of -1;

Although the residues could be iterated using normal iterators,

I chose to use an arraylist because  (a) There was no proper clone method available till then

                                     (b) it was really difficult to find nextResidue() and previousResidue() of each                                                                 residue because identical residues in  differrent iterators  were basically different objects, so could not be equal.





*/

import java.util.*;

public class EnergyCalculator{

public static double calculateEnergy(Chain chain){

    Chain.ChainIterator itr1 = chain.new ChainIterator();

    ArrayList<Residue> residues = new ArrayList<Residue>();

    double energy = 0;

    while(!(itr1.isEnd())){ // loop through iterator 1

        Residue res1 = itr1.getResidue();

        residues.add(res1);

        itr1.next();

    }  



    for (int i = 0; i<residues.size(); i++){

        Residue res1 = residues.get(i);

        for (int j = 0; j<residues.size(); j++){ // loop through iterator2

            //System.out.println("    "+residues.get(i)+ residues.get(j));

            Residue res2 = residues.get(j);

            //System.out.println("X");

            //possible coordinates a residue can look to calculate energy.

            if (!(res1.equals(res2) || res2.equals(res1.getPreviousResidue()) || res1.equals(res2.getPreviousResidue()))){

                int res1X = res1.getX();

                //int res2X = res2.getX();

                int res1Y = res1.getY();

                //int res2Y = res2.getY();



                int[] possible1 = new int[] { res1X, res1Y - 1};

                int[] possible2 = new int[] { res1X, res1Y + 1};

                int[] possible3 = new int[] { res1X-1, res1Y};

                int[] possible4 = new int[] { res1X + 1, res1Y};



                //If the residues are neighbours i.e position of i is j + 1 or j = i + 1

                //Also when residues are the same

                //Calculate energy to zero

                /*if (i == j || i == j+1 || j == i+1){

                    energy += 0;

               

                }*/

                int[] res2Coord = res2.getCoordinates();

                //If the coordinates lie within the range to look for energy calcualtion

                if (Arrays.equals(res2Coord, possible1) || Arrays.equals(res2Coord, possible2) || Arrays.equals(res2Coord, possible3) || Arrays.equals(res2Coord, possible4)){

                    char a = res1.getType();

                    char b = res2.getType();

                    if (a == 'P' && b == 'P')

                        energy += 0;

                    else if (a == 'H' && b == 'H') // add -1 to total energy if H-H interaction

                        energy -= 1;

                    else

                        energy += 0;

                

                }

            }

            

        }

                    

    }

    return energy/2; //energy is calculated twice, so divide it by two

}

}


