package proteinhp;



public class MonteCarlo {

	

	double temperature;

	private Chain chain;

	//private Chain previousChain;

	private Mover[] moves;

	private int[] positions;

	private double[] angles;



	//private ChainBuilder chainBuilder;

	

	public MonteCarlo(Chain chain, double temperature){

		this.chain = chain;

		//this.previousChain = chain;

		moves = new Mover[2];

		

		this.temperature = temperature;

		

	}

	

	protected void generateFlipMover(){

		angles = new double[] {90, 180, 270, -90};

		double angle = angles[(int)Math.round(Math.random()*(angles.length - 1))];

		positions = new int[chain.getSequence().length()];

		for (int i = 0; i < positions.length; i++){

			positions[i] = i;

		}

	

		int position = positions[(int)Math.round(Math.random()*(positions.length - 2)) + 1];

		//System.out.println(position + " " + angle);

		moves[0] = new FlipMover(chain, position, angle);



	}

	

	protected void generateCrankShaftMover(){

        moves[1] = new CrankShaftMover(chain);

	}

	

	public Chain run(int index){

		this.generateFlipMover();

		this.generateCrankShaftMover();

		

		double energy1 = EnergyCalculator.calculateEnergy(chain);

		//Mover move1 = moves[(int)Math.round(Math.random()*1)];

		//Mover move1 = moves[(int)Math.round(Math.random() * 1)];
        Mover move1 = moves[index];
		boolean moveOrNot = (move1.flip());

		//System.out.println(moveOrNot);

		if (moveOrNot){

            //chain = move1.getChain();

			double energy2 = EnergyCalculator.calculateEnergy(chain);

			//If the move is not validated, our previous Chain remains the same, so there is no reason to do anything.

			if (validateMove(energy1 - energy2, temperature)){ // energy difference = this.energy - next.energy

				// do nothing

		    }

		    else{

		        move1.unwind();

		    }

		}



		else if (moveOrNot == false){

		    

		    move1.unwind();

		}

		chain = move1.getChain();

	

		return chain;

		 

	}

	

	private boolean validateMove(double energyDifference, double temperature){

		if (energyDifference < 0) 

			return true;

		else{

		    double moveProbability = Math.exp(-energyDifference / (temperature));

		    double randomValue = Math.random()*1;

		    if (randomValue < moveProbability)

				return true;

		    else

			    return false;

        }

	}

	

	

}

