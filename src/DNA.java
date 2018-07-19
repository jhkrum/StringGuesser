
public class DNA {
	
	char[] genes;
	double fitness;
	int normalFitness;
	char[] targetString;
	
	public DNA(char[] genes, String targetString) {
		this.genes = genes;
		this.targetString = targetString.toCharArray();
		
		getFitness();
		this.normalFitness = (int) (fitness * targetString.length());
	}
	
	String getGenes() {
		return new String(genes);
	}
	
	void setGenes(char[] genes) {
		this.genes = genes;
	}
	
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
	
	//Used for creation of mating pool and normalizing values to give a probability
	int getNormalFitness() {
		return normalFitness;
	}
}
