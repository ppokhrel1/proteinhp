package proteinhp;

/*

* Authored by Pujan Pokhrel

* This class rotates some part of the chain the same way you move a crank

* The chain shape is same to the shape before transformation

* Only difference is that some part of the chain is rotated by 180 degrees.



*/



import java.util.*;



public class CrankMover{



    private Chain chain;

    private Chain previousChain;



    public CrankMover(Chain chain){

        this.chain = chain;

        this.previousChain = chain;

    }





    private int[] findNormalVector(int x1, int y1, int x2, int y2){

        return new int[] {y1-y2, x1-x2};

    }





    private double[] findNormalUnitVector(int x1, int y1, int x2, int y2){

        double modulus = Math.sqrt(Math.pow(y1-y2, 2) + Math.pow(x1-x2, 2));



        int[] normalVector = new int[2];

        normalVector = findNormalVector(x1, y1, x2, y2);



        return new double[] {normalVector[0]/modulus, normalVector[1]/modulus};

    }



    private double[] findUnitVector(int x1, int y1, int x2, int y2){

        double modulus = Math.sqrt(Math.pow(y1-y2, 2) + Math.pow(x1-x2, 2));

        return new double[] {(x2-x1)/modulus, (y2-y1)/modulus};

    }



    

    // x and y are the coordinates of the old point

    private int[] newPoint(int x, int y, double distance, double[] unitVector){

        return new int[] {(int)Math.round(x + unitVector[0] * distance), (int)Math.round(y + unitVector[1] * distance)};

    }   





    private int[] transformPoint(int[] startPoint, int[] endPoint, int[] pointToBeTransformed){

        double[] transformationVector = new double[2];

        transformationVector = findNormalUnitVector(startPoint[0], startPoint[1], endPoint[0], endPoint[1]);



        double[] originalUnitVector = findUnitVector(startPoint[0], startPoint[1], pointToBeTransformed[0], pointToBeTransformed[1]);



         double[] transformedUnitVector = new double[] {transformationVector[0] - originalUnitVector[0], transformationVector[1] - originalUnitVector[1]  };



         double distance = Math.sqrt(Math.pow(pointToBeTransformed[0] - startPoint[0], 2) + Math.pow(pointToBeTransformed[1] - startPoint[1], 2));



         return new int[] {(int)Math.round(startPoint[0] + distance * transformedUnitVector[0]), (int)Math.round(startPoint[1] + distance * transformedUnitVector[1])};

    }



    public boolean flip(Residue start, Residue end){



        Chain.ChainIterator itr = chain.new ChainIterator();



        int[] startPos = new int[] {0, 0};   //startPoints

        int[] endPos = new int[] {0, 0}; //endPoints

        while(itr.getResidue() != start){

            itr.next();

            startPos[0] = itr.getResidue().getX();

            startPos[1] = itr.getResidue().getY();

        }



        Chain.ChainIterator itr2 = itr.clone();



        while(!(itr.getResidue().equals(end))){

            itr.next();

            endPos[0] = itr.getResidue().getX();

            endPos[1] = itr.getResidue().getY();

            

        }      



        while(!(itr2.getResidue().equals(end))){

            itr2.next();

            int[] pointToBeTransformed = new int[2];

            pointToBeTransformed = itr.getResidue().getCoordinates();



            int[] transformedPoint = new int[2];

            transformedPoint = transformPoint(startPos, endPos, pointToBeTransformed);

            itr.getResidue().setCoordinates(transformedPoint);

            if (checkClash(chain, itr.getResidue())){

                //chain = firstChain;

                return false;

            }



        }

        return true;  

    }



     public boolean checkClash(Chain chain, Residue res){

        ArrayList<int[]> coords = new ArrayList<int[]> ();

        coords = ChainBuilder.getCoordinates(chain, res);

        boolean returnValue = false;

        for (int[] coordinates :coords){

            if (Arrays.equals(coordinates, res.getCoordinates()))

                returnValue = true;

        }

        return returnValue;

    }

        

}
