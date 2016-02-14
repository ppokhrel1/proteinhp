package proteinhp;



import java.util.*;

public class FlipMover extends Mover{



    private int position;

    private double angle;

    

    

    public FlipMover(Chain chain, int position, double angle){

        this.chain = chain;

        this.previousChain = chain;

        this.position = position;

        this.angle = angle;

    }



    //flips the chain after some position "position" by counterclockwise angle "angle"

    // if the chain clashes, the chain is returned to the old.chain with a messaage in the screen.

    //if the chain clashes, returns false else true.

    public boolean flip(){ 

       // System.out.println("Mya sala");

        Chain.ChainIterator itr = chain.new ChainIterator();

        int rotX = 0;

        int rotY = 0;

        for (int i = 0; i < position; i++){

        	itr.next();

        	rotX = itr.getResidue().getX();

            rotY = itr.getResidue().getY();

            

        }

        

        //Residue lastResidue = new Residue();

        //Now the actual turn..................

        boolean returnValue = true;

        //itr.next();

        while(!(itr.isEnd())){

        	Residue res = itr.getResidue();

            int[] coords = rotate(rotX, rotY, res.getX(), res.getY(), angle);

            int x = coords[0];

            int y = coords[1];

       

            //need to add code to check for clash

         /*   int[] x1 = new int[] {x-1, y};

            int[] x2 = new int[] {x+1, y};

            int[] y1 = new int[] {x, y-1};

            int[] y2 = new int[] {x, y+1};

            int[][] possibleCoordinates = new int[][] {x1, x2, y1, y2};



            boolean isSensible = false;

            for (int[] co : possibleCoordinates){

                if (Arrays.equals(res.getPreviousResidue().getCoordinates(), co)){

                    isSensible = true;

                }

            }



            if (!(isSensible)){

                returnValue = false;

            }

            */

            res.setCoordinates(x, y);

            //System.out.println(res);

            //lastResidue = res;

            itr.next();

            /*if (checkClash(chain, res)){

                returnValue = false;

            } */

        }

        

        itr.reset();

        while(!(itr.isEnd())){

            if (checkClash(chain, itr.getResidue()))

                returnValue = false;

            itr.next();

        }

        

        return returnValue;

    }



    public void unwind(){

    	this.angle = 360 - angle ;

    	this.flip();

    	

    }

   

    

}



