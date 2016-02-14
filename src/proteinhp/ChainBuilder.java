package proteinhp;
/*
    *** This class assigns co-ordinates to the given chain. ****


*/


import java.util.*;

public class ChainBuilder{

	
  
	private static void buildRandomCoords (Chain chain, Residue residue){  //sets random coordinates for the given residue based on sets of possibilities.
		try{
			ArrayList<int[]> possible = getCoordinatesPossibilitiesForNext(chain, residue);
			int randomChoice = (int)Math.round(Math.random()*(possible.size() - 1));// a random number <= size of posible coordinates set
			int[] choice = possible.get(randomChoice);
			residue.setCoordinates(choice);

            //We use the checkClash(chain, residue) from the flipMover class to checck for clash in the chain by the 
			if (checkClash(chain, residue))  
			    throw new Exception("adf");
		}
		catch(Exception e){  //Exception is caught if choice.length == 0;
			residue.setCoordinates(0, 0);
			residue.setType('Z');
			System.out.println("Chain can't be built");
		}

	}

    //Assigns random coordinates to the residues of the given chain
	public static void randomChainBuilder(Chain chain){
	    Chain.ChainIterator itr = chain.new ChainIterator();
	    itr.next();
	    while(itr.hasNext()){
	        buildRandomCoords(chain, itr.getResidue());
	        itr.next();
	    }

	    buildRandomCoords(chain, itr.getResidue()); //for the last residue.
	}

    public static char generateDir(Residue myRes1, Residue myRes2){
        int[] coords1 = myRes1.getCoordinates();
        int[] coords2 = myRes2.getCoordinates();
        if (coords1[0] < coords2[0])
            return 'R';
        else if (coords1[0] > coords2[0])
            return 'L';
        else if (coords1[1] < coords2[1])
            return 'U';
        else if (coords1[1] > coords2[1])
            return 'D';
        else
            return '\u0000';
    }


	public static ArrayList<Chain>  getAllPossibleConformers(Chain chain){
            //System.out.println("entered findlowest");

        String residueSequence = chain.getSequence();
        String bondSeqence = chain.getBondSequence();

        HashMap<String, String> chains = new HashMap<String, String>();
        //int i = 1;
        chains.put("", "" + residueSequence.charAt(0));
        for (int i = 1; i<residueSequence.length(); i++){
            HashMap<String, String> temp = new HashMap<String, String>();

            for (String key : chains.keySet()){
                String bondSeq = key;
                                
                String res = chains.get(key);
                //System.out.println(res);
                Chain xchain = new Chain(res, bondSeq);
                //System.out.println("Goo kha");
                char[] dirs = getPossibleChars(xchain, xchain.lastResidue);
                //System.out.println(dirs.length);
                if (dirs.length != 0){
                    for (char direction : dirs){
                        String newBondSeq = bondSeq + direction;
                        //System.out.println(newBondSeq);
                        String newRes = res + residueSequence.charAt(i);
                        //System.out.println(newRes);
                        temp.put(newBondSeq, newRes);
                    }
                }
                //System.out.println(temp);
                
                
                //System.out.println("Name");
            } 
            chains = new HashMap<String, String>(temp);
            temp.clear();
        }        
        System.out.println("Intial phase done");
        ArrayList<Chain> newChains = new ArrayList<Chain>();
        //System.out.println(chains);
        for (String s : chains.keySet()){
            //newChains.add(new Chain(chains.get(s), s));
            Chain x = new Chain(chains.get(s), s);
            if(!(newChains.contains(x)))
                newChains.add(x);

        }
        /*ArrayList<Chain> mne = new ArrayList<Chain>();
        for (Chain x : newChains){
            if (!(mne.contains(x)))
                mne.add(x);
        }
         return mne;       
        */
        //System.out.println(newChains.size());
        return newChains;
    }

    public static ArrayList<Chain> removeDuplicates(ArrayList<Chain> chains){
        ArrayList<Chain> temp = new ArrayList<Chain> (chains);
        for (int i = 0; i < chains.size(); i++){
            for (int j = 0; j < chains.size(); j++){
                if (!(i == j)){
                    if (chains.get(i).equals(chains.get(j))){
                        temp.remove(chains.get(j));
                    }
                }
            }
        }
        return temp;

    }

    public static ArrayList<Chain> getLowestEnergyConformers(Chain c) {

             ArrayList<Chain> chains = getAllPossibleConformers(c);
            //System.out.println(chains.size());

            //we now have the list of chains that we can use for checking energy and returning the one with lowest energy.
            double lowestEnergy = 0;
            ArrayList<Chain> lowestChains = new ArrayList<Chain>();
            for (Chain xChain : chains){
                double chainEnergy = EnergyCalculator.calculateEnergy(xChain);
                //System.out.println(chainEnergy);
                if (chainEnergy == lowestEnergy){
                    lowestChains.add(xChain);
                }
                //if chain energy is less than previous least energy, store latest run and delete the previous Chains saved
                else if (chainEnergy < lowestEnergy){
                    lowestChains.clear();
                    lowestEnergy = chainEnergy;
                    lowestChains.add(xChain);
                }
                //System.out.println(lowestEnergy);
                //System.out.println(xChain);
            }
            //System.out.println("Lowest energy is " + lowestEnergy);
            return lowestChains;
    }



    public static char[] getPossibleChars(Chain chain, Residue residue){
        ArrayList<int[]> myCoords = getCoordinatesPossibilitiesForNext(chain, residue);
        char[] dirs = new char[4];
        int notNull = 0;
        try{
            int[] prevCoords = residue.getPreviousResidue().getCoordinates();
            
            for (int i = 0; i < myCoords.size(); i++){
                int[] coord = myCoords.get(i);
                if (prevCoords[0] < coord[0])
                    dirs[i] = 'R';
                else if (prevCoords[0] > coord[0])
                    dirs[i] = 'L';
                else if (prevCoords[1] < coord[1])
                    dirs[i] = 'U';
                else if (prevCoords[1] > coord[1])
                    dirs[i] = 'D';
            }   
    
            notNull = dirs.length;
            for (int i = 0; i<dirs.length; i++){
                if (dirs[i] == ' ')
                    notNull = i;
                break;
    
            }
        }catch (Exception e){
            dirs[0] = 'U';
            dirs[1] = 'D';
            dirs[2] = 'R';
            dirs[3] = 'L';
            return dirs;
        }
        return Arrays.copyOfRange(dirs, 0, notNull);



    }

    
	// this method gives the list of possible coordinates the given residue in the chain can occupy.
	public static ArrayList<int[]>  getCoordinatesPossibilitiesForNext(Chain chain, Residue residue){
		ArrayList<int[]> possibleFits = new ArrayList<int[]>();
		ArrayList<int[]> occupied = getCoordinates(chain, residue);
		try{
		
		    int x = residue.getPreviousResidue().getX();
		    int y = residue.getPreviousResidue().getY();
    
            //points to look for possible coordinates the residue can occupy.
            //it is based on the position of the previous resiudue and the chain		
		    int[] choice1 = new int[]{x-1, y};
		    int[] choice2 = new int[]{x+1, y};
		    int[] choice3 = new int[] {x, y-1};
		    int[] choice4 = new int[] {x, y+1};
		    
		    ArrayList<int[]> possibleChoices = new ArrayList<int[]>();
		    possibleChoices.add(choice1);
		    possibleChoices.add(choice2);
		    possibleChoices.add(choice3);
		    possibleChoices.add(choice4);
            
		    possibleFits = possibleChoices; // we need an extra array because we cant change the native array while looping........
		    for (int[] coords1 : possibleChoices){  //loop through all coordinates in possibleChoices
			    for(int[] coords2 : occupied){  //loop throough all coordinates in occupied
    
			    	if(coords1[0] == coords2[0] && coords1[1] == coords2[1]){ // if Arraylist occupied contains a coordinate in possibleChoices
			    		ArrayList<int[]> xarray = new ArrayList<int[]>(possibleFits);
			    		try{
			    			xarray.remove(coords1); // remove the coordinate from xarray.
			    			possibleFits = xarray;  // you cant change the elements of an array while looping
			    			                        // possibleFits = xarray, thus coordinate removed.
			    			//possibleFits.remove(coords1);
			    		}
			    		catch(Exception e){ // just to be safe, (the undesirable coordinate gets removed anyway)
                            continue;
			    		}
    
			    	}
			    }
		    }
            //possibleFits = xArray;
        }catch(Exception e){ //catch null pointer exception
            //Residue prev = residue.getPreviousResidue();
        //if(prev == null){
            possibleFits.add(new int[]{0, 1});
            possibleFits.add(new int[]{0, -1});
            possibleFits.add(new int[]{1, 0});
            possibleFits.add(new int[]{-1, 0});
            return possibleFits;
        }

		return possibleFits;
	}

	protected static ArrayList<int[]> getCoordinates(Chain chain){// searches for co-ordinates occcupied by all the residues in the chain
        ArrayList<int[]> myCoords = new ArrayList<int[]>();
        Chain.ChainIterator itr = chain.new ChainIterator();
        while(!(itr.isEnd())){
            myCoords.add(itr.getResidue().getCoordinates());
            itr.next();
        }
        return myCoords;
     }


     
// gets the list of coordinates before the given residue
    protected static ArrayList<int[]> getCoordinates(Chain chain, Residue residue){
	    ArrayList<int[]> coordinates = new ArrayList<int[]>();
	    Chain.ChainIterator itr = chain.new ChainIterator();
	    while(!(itr.isEnd())){
		    if (itr.getResidue().equals(residue)){
			    break;
	    	}
	    	coordinates.add(itr.getResidue().getCoordinates());
	    	itr.next();
	    }
	    return coordinates;
    }

    public static boolean checkClash(Chain chain, Residue res){
        ArrayList<int[]> coords = new ArrayList<int[]> ();
        coords = ChainBuilder.getCoordinates(chain, res);
        for (int[] a : coords){
            System.out.print(a[0] + "   " + a[1]);
        }
        boolean returnValue = false;
        for (int[] coordinates :coords){
            if (coordinates[0] == res.getCoordinates()[0] && coordinates[1] == res.getCoordinates()[1])
                returnValue = true;
        }
        return returnValue;
    }

}
