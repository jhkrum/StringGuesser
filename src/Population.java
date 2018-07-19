import java.util.Random;
import java.util.ArrayList;
public class Population {

	char[] dictionary = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	
	DNA[] population;
	double populationFitness, mutationRate;
	String targetString;
	int populationSize;
	ArrayList<DNA> matingPool;
	
	
	public Population(String targetValue, int populationSize, double mutationRate) {
		this.targetString = targetValue;
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		
		//Establishes a population given the value we want to work towards and the number of elements in the population
		establish();
		
		//Calculates initial fitness of the system
		calculateFitness();
		
	}
	
	//Establishes a population given the length of the target string
	void establish() {
		
		Random rand = new Random();
		population = new DNA[populationSize];
		
		for(int i = 0; i < populationSize; i++) {
			
			char[] temp = new char[targetString.length()];
			
			for(int j = 0; j < targetString.length(); j++) {
				temp[j] = dictionary[rand.nextInt(27)];
			}
			population[i] = new DNA(temp, targetString);
		}
	}
	
	//Measures fitness as the number of correct terms towards the target
	double calculateFitness() {
		
		double totalFitness = 0;
		for(int i = 0; i < populationSize; i++) {
			totalFitness += population[i].getFitness();
		}
		
		populationFitness = totalFitness/populationSize;
		
		return populationFitness;
		
	}
	
	DNA getBestFitness() {
		
		DNA max = population[0];
		for(int i = 1; i < populationSize; i++) {
			if(max.getFitness() < population[i].getFitness()) max = population[i];
		}
		
		return max;
	}
	
	//Creates a proportional mating pool from how fit each DNA is
	void createMatingPool() {
		matingPool = new ArrayList<DNA>();
		
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
	
	//Genetic variation algorithm
	void crossover() {
		
		createMatingPool();
		
		DNA[] newPopulation = new DNA[populationSize];
		Random random = new Random();
		
		for(int i = 0; i < populationSize; i++) {
			
			String parentOne = matingPool.get(random.nextInt(matingPool.size())).getGenes();
			String parentTwo = matingPool.get(random.nextInt(matingPool.size())).getGenes();
			char[] child = (parentOne.substring(0, parentOne.length()/2) + parentTwo.substring(parentTwo.length()/2)).toCharArray();
			
			//Applies mutation
			for(int j = 0; j < child.length; j++) {
				if((random.nextInt(100) + 1) == mutationRate) {
					for(int k = 0; k < 27; k++) {
						if(child[j] == dictionary[k]) child[j] = dictionary[random.nextInt(27)];
					}
				}
			}
			
			
			DNA newElement = new DNA(child, targetString);
			
			newPopulation[i] = newElement;
		}
		
		population = newPopulation;
	}
	
	//Outputs the fitness and genes of all DNA in the population
	void getDNA() {
		for(int i = 0; i < populationSize; i++) {
			System.out.println("DNA #" + i + "\t\t" + population[i].getGenes() + "\t" + population[i].getFitness());
		}
	}
	
}
