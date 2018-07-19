/**
 * Data encapsulation object for fitness and "genes" on a basic level
 * @author Justin Krum
 *
 */
public class Individual {
	
	char[] genes;
	double fitness;
	int normalFitness;
	char[] targetString;
	
	/**
	 * Creates an individual of the population with a certain gene set and target to go towards
	 * 
	 * @param genes String of characters to be iterated towards the targetString through the population
	 * @param targetString Mainly used for fitness calculations
	 */
	public Individual(char[] genes, String targetString) {
		this.genes = genes;
		this.targetString = targetString.toCharArray();
		
		getFitness();
		this.normalFitness = (int) (fitness * targetString.length());
	}
	
	/**
	 * Gives the user the genes of the individual
	 * 
	 * @return Value of the genes
	 */
	String getGenes() {
		return new String(genes);
	}
	
	/**
	 * Gets the fitness of an individual in the population
	 * @return Percent towards targetString
	 */
	double getFitness() {
		double normalFitness = 0;
		for(int i = 0; i < genes.length; i++) {
			
			//If DNA and Target have same character in same position
			if(genes[i] == targetString[i]) normalFitness++;
		}
		fitness = normalFitness/genes.length;
		this.normalFitness = (int) normalFitness;
		return fitness;
	}
	
	/**
	 * Used for creation of mating pool and normalizing values to give a probability
	 * 
	 * @return Value of fitness out of the length of the target string
	 */
	int getNormalFitness() {
		return normalFitness;
	}
}
