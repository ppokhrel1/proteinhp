package proteinhp;

public class RunSimulation{



    public static void main(String[] args){

        

        Chain chain = new Chain("HPPHHHPPPHPPHP");

		

		Chain.ChainIterator itr = chain.new ChainIterator();

		int x = 0;

		int y = 0;

		while(!(itr.isEnd())){

			Residue res = itr.getResidue();

			res.setCoordinates(x++, y);

			itr.next();

		}

		System.out.println(chain);



		MonteCarlo mntCarlo = new MonteCarlo(chain, 10);

		for (int i = 0; i< 5; i++){

		    mntCarlo.run((int) Math.round(Math.random()));

		    //System.out.println(chain);

		    

		}
        
		System.out.println(chain);

        System.out.println(EnergyCalculator.calculateEnergy(chain));

    }



}
