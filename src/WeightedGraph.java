import java.util.*;

public class WeightedGraph {  
	private int dim;                  //nr de nós = nr de caracteristicas
	private double [][] adjMatrix; 
	
	//CONSTRUTOR
	public WeightedGraph (int size) {  
		super();
		this.dim = size;
		this.adjMatrix = new double [size][size];
	}		
	
	// ADD
	/** Adiciona uma aresta entre o no1 e o no2 com o valor peso */
	public void Add (int no1, int no2, double peso) {   
		adjMatrix[no1][no2]=peso;
		adjMatrix[no2][no1]=peso;
	}
	
	public int getdim() {
		return dim;
	}
	
	
	/** MST - árvore de extensão de peso maximal  */
	public boolean [][] MST() {                      
		boolean [][] MSTree = new boolean [dim][dim];
		
		LinkedList<Integer> Nodes = new LinkedList<Integer>(); //Lista dos nós (j's)
		
		int init=0;

		Nodes.add(init); // nó inicial: init
		
		boolean encontreiArestaParaFora = true;
		while (encontreiArestaParaFora) {
		
			double max = -1;  // vetor max: (i, j, peso)
			int imax=-1;
			int jmax=-1;
			encontreiArestaParaFora = false;       
			
			for (int i : Nodes) { 
								
				for (int j = 0; j<dim; j++) {  //ver as arestas
				
					if (adjMatrix[i][j] > max && !Nodes.contains(j)) {   //atualizar o max para o valor 
						imax = i;								         // No encontrado não pode estar já na lista
						jmax = j;
						max = adjMatrix[i][j];
						encontreiArestaParaFora = true;
					}
				}
			}
			if (encontreiArestaParaFora) {
				Nodes.add(jmax);
				MSTree[imax][jmax] = true; 
				MSTree[jmax][imax] = true;
			}
		}
		return MSTree; //true or false consoante tem aresta ou não
	}	
}
