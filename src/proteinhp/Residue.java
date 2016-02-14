package proteinhp;



import java.util.*;



public class Residue{

	private char type;

	private int[] coords;

	private Residue itsNextRes;

	private Residue itsPrevRes;

    private static int UNIQUE_ID = 0;

    private int uid; 

    

	public Residue(){
	    this.coords = new int[2];
	    coords[0] = 0;
	    coords[1] = 0;
	    this.itsNextRes = null;
	    this.itsPrevRes = null;
	    uid = ++UNIQUE_ID;
	}

	public Residue(char type, int[] coords) {
	    this();  // call the default constructor explicitly
	    this.type = type;
	    this.coords = coords;
	}

	public Residue(char givenType){
		this();
		this.type = givenType;
	}

	public Residue(char type, int x, int y){
        this();
	    this.type = type;
	    this.coords[0] = x;
	    this.coords[1] = y;
	}

	public Residue(Residue prevResidue, Residue nextResidue){
		this();
		this.itsPrevRes = prevResidue;
		this.itsNextRes = nextResidue;
	}

    public Residue (Residue otherResidue){
        this.coords = otherResidue.coords;
        this.itsNextRes = otherResidue.getNextResidue();
        this.itsPrevRes = otherResidue.getPreviousResidue();
        this.type = otherResidue.getType();

    }
	public Residue clone(){
	    return new Residue(this.getType(), this.getCoordinates());
	}



	public void setType(char type){

	    this.type = type;

	}



	public Character getType(){

	    return this.type;

	}



	public void setCoordinates(int x, int y){

	    this.coords[0] = x;

	    this.coords[1] = y;

	}



	public void setCoordinates(int[] coord){

	    this.coords = coord;

	}



	public int[] getCoordinates(){

	    return this.coords;

	}



	public int getX(){

	    return this.coords[0];

	}



	public int getY(){

	    return this.coords[1];

	}



	public void setPrevious(Residue residue){

	    this.itsPrevRes = residue;     

	        

	}



	public void setNext(Residue residue){

	    this.itsNextRes = residue;

	              

	}



	public Residue getPreviousResidue(){

	    return this.itsPrevRes;

	}



	public Residue getNextResidue(){

	    return this.itsNextRes;

	}

	

	//@override

	public boolean equals(Object o){

        if (!(o == null)){

    		if ((o instanceof Residue)){

    		    Residue res = (Residue) o;

                if ((this.hashCode() == ((Residue)o).hashCode()))

                    if (res.getType().equals(this.getType()) && Arrays.equals(res.getCoordinates(), this.getCoordinates()))

                        return true;

                

	        }  

	    }

	    return false;		

	}

	public int hashCode(){



		return this.uid;

	}

	public String toString(){

	    return "Type: " + this.type + " has co-ordinates " + this.coords[0] +", "+ this.coords[1];

	}

}
