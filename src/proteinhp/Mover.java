package proteinhp;


import java.util.*;



public abstract class Mover {



	protected Chain chain;

	protected Chain previousChain;

	

	public abstract boolean flip();

	public abstract void unwind();

	

	public int[] rotate(int x1, int y1, int x2, int y2, double angle){

        int x = (int)(Math.round(Math.cos(Math.toRadians(angle)) * (x2-x1)) - Math.round(Math.sin(Math.toRadians(angle)) * (y2-y1)));

        int y = (int)(Math.round(Math.sin(Math.toRadians(angle)) * (x2-x1)) + Math.round(Math.cos(Math.toRadians(angle)) * (y2-y1)));

        int returnX = x + x1;

        //System.out.println(returnX);

        int returnY = y+y1;

        //System.out.println(returnY);

        return new int[] {returnX, returnY};  

    }

	

	// gets the list of coordinates before the given residue

    protected static ArrayList<int[]> getCoordinates(Chain chain, Residue residue){

	    ArrayList<int[]> coordinates = new ArrayList<int[]>();

	    Chain.ChainIterator itr = chain.new ChainIterator();

	    ArrayList<Residue> res = new ArrayList<Residue>();

	    //System.out.println("adfadf");

	    while(!(itr.getResidue().equals(residue))){

                res.add(itr.getResidue());

                itr.next();

            

        }

        for (Residue r : res){ 

            coordinates.add(r.getCoordinates());

        }

        //System.out.println(coordinates.size());

	    return coordinates;

    }



    public static boolean checkClash(Chain chain, Residue res){

        ArrayList<int[]> coords = new ArrayList<int[]> ();

        coords = getCoordinates(chain, res);

        //System.out.println("asdf");

        boolean returnValue = false;

        for (int[] coordinates : coords){

            if (coordinates[0] == res.getCoordinates()[0] && coordinates[1] == res.getCoordinates()[1]){

                returnValue = true;

                //System.out.println("M");

            }

        }

        return returnValue;

    }

    

    public Chain getChain(){

       // System.out.println("aaa");

    	return this.chain;

    }

}

