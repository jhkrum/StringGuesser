import java.util.Random;
import java.util.ArrayList;

/**
 * Mimics the genetic variation behavior of a population
 * 
 * @author Justin Krum
 *
 */
public class Population {

	char[] dictionary = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	Individual[] population;
	double populationFitness, mutationRate;
	String targetString;
	int populationSize;
	ArrayList<Individual> matingPool;
	
	/**
	 * Creates a population given the parameters
	 * 
	 * @param targetValue Value that the population will evolve towards
	 * @param populationSize Number of individuals generated in the population
	 * @param mutationRate Rate at which mutations in the population will occur
	 */
	public Population(String targetValue, int populationSize, double mutationRate) {
		this.targetString = targetValue;
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		
		//Establishes a population given the value we want to work towards and the number of elements in the population
		establish();
		
	}
	
	
	/**
	 * Establishes a population given the length of the target string and population size
	 * 
	 */
	void establish() {
		
		Random rand = new Random();
		population = new Individual[populationSize];
		
		//Creates temporary individuals and adds them to population each time for every loop of the population
		for(int i = 0; i < populationSize; i++) {
			
			char[] temp = new char[targetString.length()];
			
			for(int j = 0; j < targetString.length(); j++) {
				temp[j] = dictionary[rand.nextInt(27)];
			}
			population[i] = new Individual(temp, targetString);
		}
	}
	
	/**
	 * Measures fitness as correct characters over total characters in the string
	 * 
	 * @return Average fitness for the entire population
	 */
	double calculateFitness() {
		
		double totalFitness = 0;
		for(int i = 0; i < populationSize; i++) {
			totalFitness += population[i].getFitness();
		}
		
		populationFitness = totalFitness/populationSize;
		
		return populationFitness;
		
	}
	
	/**
	 * Returns the individual of greatest fitness in the population
	 * 
	 */
	Individual getBestFitness() {
		
		Individual max = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(max.getFitness() < population[i].getFitness()) max = population[i];
		}
		
		return max;
	}
	
	/**
	 * Creates a proportional mating pool from how fit each DNA is
	 */
	void createMatingPool() {
		matingPool = new ArrayList<Individual>();
		
		for(int i = 0; i < populationSize; i++) {
			
			//If normal fitness is greater than 0, it will move to the mating pool
			if(population[i].getNormalFitness() > 0) {
				
				//The greater the value of normal fitness, the more times it will be in the mating pool
				for(int n = population[i].getNormalFitness(); n > 0; n--) {
					matingPool.add(population[i]);
				}
			}
		}
	}
	
	/**
	 * Applies principles of genetic variation to the population
	 */
	void crossover() {
		
		//Creates a mating pool for the population
		createMatingPool();
		
		Individual[] newPopulation = new Individual[populationSize];
		Random random = new Random();
		
		//Picks two parents at random from the population to combine into a child
		for(int i = 0; i < populationSize; i++) {
			
			String parentOne = matingPool.get(random.nextInt(matingPool.size())).getGenes();
			String parentTwo = matingPool.get(random.nextInt(matingPool.size())).getGenes();
			char[] child = (parentOne.substring(0, parentOne.length()/2) + parentTwo.substring(parentTwo.length()/2)).toCharArray();
			
			//Applies mutation to ensure that the genetic variation at the beginning does not become stagnant
			for(int j = 0; j < child.length; j++) {
				int mutation = (int) (100/mutationRate);
				if((random.nextInt(mutation) + 1) == 1) {
					for(int k = 0; k < 27; k++) {
						if(child[j] == dictionary[k]) child[j] = dictionary[random.nextInt(27)];
					}
				}
			}
			
			
			Individual newElement = new Individual(child, targetString);
			
			newPopulation[i] = newElement;
		}
		
		population = newPopulation;
	}
	
	/**
	 * Outputs the fitness and genes of all DNA in the population
	 */
	void getDNA() {
		for(int i = 0; i < populationSize; i++) {
			System.out.println("DNA #" + i + "\t\t" + population[i].getGenes() + "\t" + population[i].getFitness());
		}
	}
	
}
