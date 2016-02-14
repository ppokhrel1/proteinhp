package test;

import proteinhp.*;


import junit.framework.TestCase;

import org.junit.Assert;



public class TestResidue extends TestCase{

    private Residue residue1;

    private Residue residue2;

    private Residue residue3;







    public void setUp(){

    	residue1 = new Residue("hydrophobic", 1,2 );

    	residue2 = new Residue("polar" , 0,0);

    	residue3 = new Residue();

    }



    

    //@AfterClass

    public void testgetType(){

        

        assertTrue(residue1.getType().equals("hydrophobic"));

        assertTrue(residue2.getType().equals("polar"));

        assertTrue(residue3.getType().equals("unset"));

    }



    

    public void testSetType(){

        residue1.setType("polar");

        assertTrue(residue1.getType().equals("polar"));

        residue2.setType('P');

        assertTrue(residue2.getType().equals('P'));

        residue3.setType('H');

        assertTrue(residue3.getType().equals('H'));

    }



    



    public void testSetPrevious(){

        residue1.setPrevious(residue2);

        residue2.setNext(residue1);

        assertTrue(residue1.getPreviousResidue().equals(residue2));

        assertTrue(residue2.getNextResidue().equals(residue1));

        

        residue2.setPrevious(residue3);

        residue3.setNext(residue2);

        assertTrue(residue2.getPreviousResidue().equals(residue3));

        assertTrue(residue3.getNextResidue().equals(residue2));



        residue3.setPrevious(residue1);

        residue1.setNext(residue3);

        assertTrue(residue3.getPreviousResidue().equals(residue1));

        assertTrue(residue1.getNextResidue().equals(residue3));

        

    }//End of testSetPrevious







    public void testSetNext(){

    	residue1.setNext(residue2);

    	residue2.setPrevious(residue1);

    	assertTrue(residue1.getNextResidue().equals(residue2));

    	assertTrue(residue2.getPreviousResidue().equals(residue1));



    	residue2.setNext(residue3);

    	residue3.setPrevious(residue2);

    	assertTrue(residue2.getNextResidue().equals(residue3));

    	assertTrue(residue3.getPreviousResidue().equals(residue2));



    	residue3.setNext(residue1);

    	residue1.setPrevious(residue3);

    	assertTrue(residue3.getNextResidue().equals(residue1));

    	assertTrue(residue1.getPreviousResidue().equals(residue3));

    }

    	

}// End of class TestResidue

