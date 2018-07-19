/**
 * Tester used to run Population
 * @author Justin Krum
 *
 */
public class Simulate {
	
	
	public static void main(String[] args) {
		
		String targetString = "example string";
		int populationSize = 1000;
		int mutationRate = 1;
		
		Population testPopulation = new Population(targetString.toLowerCase(), populationSize, mutationRate);
		
		//Displays the individual in the population with the highest fitness
		//bestFitness(testPopulation);
		
		System.out.println("________________________________________________ Generation 0");
		bestFitness(testPopulation, targetString);
		
		for(int i = 1; i < Integer.MAX_VALUE; i++) {

			if(testPopulation.getBestFitness().getNormalFitness() == targetString.length()) break;
			
			System.out.println("________________________________________________ Generation " + i);
			testPopulation.crossover();
			bestFitness(testPopulation, targetString);
		}
		
		System.out.println("________________________________________________");
		System.out.println("Population: " + populationSize + "\t\tAvg Fitness: " + (testPopulation.calculateFitness() * targetString.length()));
		System.out.println("Mutation Rate: " + mutationRate + "%");
		
		
	}
	
	/**
	 * 
	 * 
	 * @param population Passed from the main method
	 * @param targetString Passed from the main method
	 */
	static void bestFitness(Population population, String targetString) {
		Individual bestFitness = population.getBestFitness();
		
//		System.out.println("Target String \tFitness: " + targetString.length());
		System.out.println(bestFitness.getGenes() + "\tFitness: " + bestFitness.getNormalFitness());
	}
	
	

}
