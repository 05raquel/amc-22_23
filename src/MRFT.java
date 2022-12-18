
public class MRFT {

	//Markov Random Field Tree
	
	private DataSet dataset;
	private Tree tree;
	//pensar que tipo de árvores usamos... grado, vetor,...?
	
	//CONSTRUTOR
	
	// coloca os phi ij (xi,xj) em cada aresta
	// que podem ser vistos como uma matriz
	// não esquecer: converter os valores inteiros do count em doubles
	
	public MRFT(DataSet dataset, Tree tree) {
		super();
		this.dataset = dataset;
		this.tree = tree;
	}
	
	//PROB
	
	public double Prob (int [] vetor) {
		return //probabilidade dos dados no dataset 
	}
	
	
	
}
