package proteinhp;
import java.io.Serializable;
import java.util.*;

public class Chain implements Serializable{

    private Residue firstResidue;
    protected Residue lastResidue;
    protected int length;
    private String residueSequence;

    public Chain(){
        firstResidue = null;
        lastResidue = null;
        length = 0;
    }
   
    public Chain(int numResidue){ 
    	Residue r;
        length = numResidue;
    	for(int i=0; i<numResidue; i++){
    		r = new Residue();
    		if (firstResidue == null){
	   		this.firstResidue = r;
    			this.lastResidue = r;
    		}
    		else{
    			this.lastResidue.setNext(r);
    			r.setPrevious(lastResidue);
    			this.lastResidue = r;
    		}
    	}
    }// End of constructor



    public Chain(Residue res){
        firstResidue = res;
      
    }

    public Chain(String Residues, String bondSequence){ //chain constructor when it is built using bondsequence and the sequence of residues;

        char[] myResidues = Residues.toCharArray();
        //this.residueSequence = Residues;
        int x = 0;
        int y = 0;
        Residue r;
        int[] coords = {x, y};
        char[] myBondSequence = bondSequence.toCharArray();
        firstResidue = new Residue(Residues.charAt(0), coords);
        for (int index = 0; index < myBondSequence.length; index++){
            char toGo = myBondSequence[index];
            if (toGo == 'U')
                y++;
            else if (toGo == 'D')
                y--;
            else if (toGo == 'L')
                x--;
            else if (toGo == 'R')
                x++;
            else
                break;

            r = new Residue(myResidues[index], new int[] {x,y});
            
            this.addResidue(r);

        }



    }

    public Chain(String givenString){
     	givenString = givenString.toUpperCase();
        residueSequence = givenString;
     	firstResidue = new Residue(givenString.charAt(0));
	    this.length = givenString.length();
     	Residue x = firstResidue;
     	for (int i = 1; i<givenString.length(); i++){
			Residue myResidue = new Residue(givenString.charAt(i));
     		x.setNext(myResidue);
		    myResidue.setPrevious(x);
     		x = myResidue;
     	}
     	lastResidue = x;
     }// End of constructor

    public Residue lastResidue(){
        return this.lastResidue;
    }
    
    public Residue firstResidue(){
        return this.firstResidue;
    }

    public Chain clone(){
        Chain.ChainIterator itr = this.new ChainIterator();
        Chain chain = new Chain();
        this.firstResidue = itr.getResidue();
        
        while(!(itr.isEnd())){
            Residue res = itr.getResidue().clone();
            //System.out.println(res);
            chain.addResidue(res);
            //lastResidue = res;
            chain.length++;
            itr.next();
        }
        //System.out.println("..........................");

        return chain;
    }

    public void buildFromLast(Residue lastResidue){
        //System.out.println(lastResidue);
        Residue res = lastResidue;
        if (res.getPreviousResidue() == null){
            this.firstResidue = lastResidue;
        }
        while(!(res.getPreviousResidue() == null)){
            this.lastResidue = lastResidue;
            res = res.getPreviousResidue();
            firstResidue = res;
            this.residueSequence += res.getType(); 
        }
        residueSequence = new StringBuilder(this.residueSequence).reverse().toString();
    }

    public void addResidue(Residue res){
        if (firstResidue == null){
            firstResidue = res;
            
        }
        else if (lastResidue == null){
            firstResidue.setNext(res);
            res.setPrevious(firstResidue);
            lastResidue = res;
        }

        else{
            lastResidue.setNext(res);
            res.setPrevious(lastResidue);
            lastResidue = res;
        }
        length++;
        this.residueSequence += res.getType();
    }


    public void addResidue(char res, char direction){
        this.residueSequence += res;
        Residue myPrevious;
        if (lastResidue == null)
            myPrevious = firstResidue;
        else
            myPrevious = this.lastResidue;

        int x = myPrevious.getX();
        int y = myPrevious.getY();
        if (direction == 'U')
            y++;
        else if (direction == 'D')
            y--;
        else if (direction == 'L')
            x--;
        else if (direction == 'R')
            x++;
        
        int[] coords = {x, y};
        Residue myRes = new Residue(res, coords);
        myPrevious.setNext(myRes);
        myRes.setPrevious(myPrevious);
        this.lastResidue = myRes;
    
    }
            
    public String getSequence(){
     	String sequence="";
     	Residue r = this.firstResidue;
     	Chain.ChainIterator itr = this.new ChainIterator();
     	while(!itr.isEnd()){
     		sequence += (itr.getResidue()).getType();
     		itr.next();
     	}
     	return sequence;
     }// End of getSequence

    public String getResidueSequence(){
        return this.residueSequence;
    }

     public int length(){
	    return this.length;
     }
      public String reverseSequence(){
     	String returnVal="";
		String sequence= this.getSequence().toUpperCase();
		for(int i = sequence.length(); i>=0; i--){
			returnVal += sequence.charAt(i);
		}
		return returnVal;
	}// End of reverseBondSequence



	public boolean compareSequence(Chain givenChain){
	    String myseq = givenChain.getSequence();
		if (this.getSequence().equals(myseq) || this.getSequence().equals(new StringBuffer(myseq).reverse().toString()))
		    return true;
		return false;
	}// End of compareSequence
     	

     	
     
    // This method assumes that the next residue and current residue donot have different x and y components
// i.e at least one component of the co-ordinates of current residue and next residue is same.
	public String getBondSequence(){	// This method gets bond sequence in the form of string.
		String returnValue = "";		// R stands that the next residue is at right of the current residue i.e. nextResidue.getX() == currentResidue.getX() +1
		ChainIterator itr = new ChainIterator();// L stands for left; U stands for up ; D stands for down
		while(!(itr.isEnd())){
			if(!(itr.getResidue().getNextResidue() == null)){
				int x = itr.getResidue().getX();
				int y = itr.getResidue().getY();
				int x2 = itr.getResidue().getNextResidue().getX();
				int y2 = itr.getResidue().getNextResidue().getY();
				if(x2 == x+1){
					returnValue += "R";
				}
				else if(x2 == x-1){
					returnValue += "L";
				}
				else if (y2 == y+1){
					returnValue+= "U";
				}
				else if(y2 == y-1){
					returnValue += "D";
				}
		    }
			itr.next();
		}
		//System.out.println(returnValue);
		return returnValue;
	}// End of getBondSequence



	public String reverseBondSequence(){ //backwards
		String returnVal="";
		String bondSequence= new StringBuffer(this.getBondSequence().toUpperCase()).reverse().toString();
		for(int i = bondSequence.length(); i>=0; i--){
			char myChar = bondSequence.charAt(i);

			if (myChar == 'U')
			    myChar = 'D'; 
			else if (myChar == 'D')
			    myChar = 'U';
			else if (myChar == 'L')
			    myChar = 'R';
			else if (myChar == 'R')
			    myChar = 'L';
			returnVal += myChar;
		}
		return returnVal;
	}// End of reverseBondSequence




	// @ require givenString should not have any character except 'R', 'D', 'U', 'L'
	public String rotate90CounterClockwise(){
		String returnVal="";
		String givenString = this.getBondSequence().toUpperCase();


		for(int i=0; i < givenString.length() ; i++){
			char char1 = givenString.charAt(i);
			if(char1 == 'R'){
				returnVal += 'U';
			}
			else if(char1 == 'U'){
				returnVal += 'L';
			}
			else if(char1 == 'L'){
				returnVal += 'D';
			}
			else if(char1 == 'D'){
				returnVal += 'R';
			}
		}
		return returnVal;
	}// End of rotate90CounterClockwise



	public String rotate90Clockwise(){
		String returnVal="";
		String givenString = this.getBondSequence().toUpperCase();
		for(int i=0; i < givenString.length() ; i++){
			char char1 = givenString.charAt(i);
			if(char1 == 'R'){
				returnVal += 'D';
			}
			else if(char1 == 'D'){
				returnVal += 'L';
			}
			else if(char1 == 'L'){
				returnVal += 'U';
			}
			else if(char1 == 'U'){
				returnVal += 'R';
			}
		}
		return returnVal;
	}// End of rotate90Clockwise





	public String rotate180(){
		String returnVal="";
		String givenString = this.getBondSequence().toUpperCase();
		for(int i=0; i < givenString.length() ; i++){
			char char1 = givenString.charAt(i);
			if(char1 == 'R'){
				returnVal += 'L';
			}
			else if(char1 == 'D'){
				returnVal += 'U';
			}
			else if(char1 == 'L'){
				returnVal += 'R';
			}
			else if(char1 == 'U'){
				returnVal += 'D';
			}
		}
		return returnVal;
	}// End of rotate180






    public class ChainIterator{
    	private Residue current;

	public ChainIterator(){
    		current = Chain.this.firstResidue;
    	}

    	public boolean hasNext(){
    		return (current.getNextResidue() != null);
    	}

    	public void next(){
    		current = current.getNextResidue() ;
    	}

    	public void reset(){
    		this.current =Chain.this.firstResidue;
    	}

    	public boolean isEnd(){
    		return (this.current == null);
		} 

    	public Residue getResidue(){
    		return this.current;
    	}

    	public ChainIterator clone(){
    	    return this;
        }
    	
    	
    }// End of class ChainIterator



	 public boolean equals(Object obj){
        //System.out.println("Pujan");
    	if(obj instanceof Chain){
    		Chain theChain = (Chain)obj;
    		String sequence1 = this.getSequence();
    		String sequence2 = theChain.getSequence();

    		if(this.compareSequence(theChain)){	// checking if the sequence are same from forward and backward
    				String bondSequence1 = theChain.getBondSequence();
    				if(bondSequence1.equals(this.getBondSequence())){
    					return true;
    				}
    				else if(bondSequence1.equals(this.rotate90CounterClockwise())){
    					return true;
    				}
    				else if(bondSequence1.equals(this.rotate90Clockwise())){
    					return true;
    				}
    				else if(bondSequence1.equals(this.rotate180())){
    					return true;
    				}
    		    }
    		}
    	return false;
    					
    }	// end of equals
    	
    	
    			
	/*public String toString(){
        String returnVal = "";
        ChainIterator itr = new ChainIterator();
        while (!itr.isEnd()){
			returnVal += (itr.getResidue()).toString()+ "\n";
        	itr.next();	
        }
		return returnVal;
	}// End of toString 

    */
    
	public String toString(){

        int lineLength = 4*length+1;
        char[][] rep = new char[lineLength][lineLength];
        
	    for (int i=0; i<lineLength; i++) {
	        for (int j=0; j<lineLength; j++) {
	            rep[i][j] = ' ';  // we're going to make these spaces later on
	        }
	    }
        
	    char upDown = '|';
	    char leftRight = '-';
    
        int xOffset = 2*length;
        int yOffset = 2*length;
        int[] up = {0,-1};
        int[] down = {0,1};
        int[] right = {1,0};
        int[] left = {-1,0};
        
	    ChainIterator itr = new ChainIterator();
        while(!(itr.isEnd())){
            int[] currentCoords = itr.getResidue().getCoordinates();
            int[] whereToDraw = {(currentCoords[0]+xOffset/2)*2,(currentCoords[1]+yOffset/2)*2};
            char type = itr.getResidue().getType();
            rep[whereToDraw[0]][whereToDraw[1]] = type;
            if (itr.getResidue().getNextResidue() != null) {
                int[] nextCoords = itr.getResidue().getNextResidue().getCoordinates();
                int[] whereToGo = null;
                if (nextCoords[0] < currentCoords[0]) 
                    whereToGo = left;
                else if (nextCoords[0] > currentCoords[0])
                    whereToGo = right;
                else if (nextCoords[1] < currentCoords[1])
                    whereToGo = up;
                else if (nextCoords[1] > currentCoords[1])
                    whereToGo = down;

                if (whereToGo == left || whereToGo == right)// x and y are flipped when printing on the screen
                   rep[whereToDraw[0] + whereToGo[0]][whereToDraw[1]+whereToGo[1]] = upDown;
                if (whereToGo == up || whereToGo == down)//x and y are flipped when printing on the screen.
                   rep[whereToDraw[0] + whereToGo[0]][whereToDraw[1]+whereToGo[1]] = leftRight;
                
            }    
            itr.next();
        }
    
        
    
        String returnValue = "";
        for (int i=0; i<lineLength; i++) {
	        for (int j=0; j<lineLength; j++) {
	           returnValue += rep[i][j];  // we're going to make these spaces later on
	        }
	        returnValue += "\n";
	    }
	    returnValue += "\n";
        return returnValue ;
	}



}// End of class chain



    
