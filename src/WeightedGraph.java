import java.util.*;

public class WeightedGraph {   //não direcionado
	
	private int dim;
	private double [][] adjMatrix; 
	
	//CONSTRUTOR
	public WeightedGraph (int size) {  //este size tem de ser size do domain -1 (para não incluir a classe)
		super();
		this.dim = size;
		this.adjMatrix = new double [size][size];
	
	}		
	
	// ADD
	public void Add (int no1, int no2, double peso) {   //Adicionar uma aresta entre o no1 e o no2 com o valor peso
		adjMatrix[no1][no2]=peso;
		adjMatrix[no2][no1]=peso;
	}
	
	public double [][] getMatrix (){
		return adjMatrix;
	}
	
	public int getdim() {
		return dim;
	}
	
	// MST - árvore de extensão de peso maximal 
	
	//  grafo.MST()
	
	public boolean [][] MST() {
		boolean [][] MSTree = new boolean [dim][dim];
		//System.out.println(Arrays.deepToString(graph));
		double [][] Graph = new double [dim][dim];
		for (int i=0; i<dim; i++) {
			for (int j=0; j<dim; j++) {
				Graph[i][j]= adjMatrix [i][j];
			}
		}
		//cópia do grafo
		
		LinkedList<Integer> Nodes = new LinkedList<Integer>(); //Lista dos nós (j's)
		Nodes.add(0); // nó inicial -> 0
		
		while (Nodes.size() < dim) {
			double max = 0;  // vetor max: (i, j, peso)
			int imax=0;
			int jmax=0;
			
			for (int i : Nodes) { 
				
				LinkedList<Integer> notThis = new LinkedList<Integer>();				
				for (int j =0; j<dim; j++) {  //ver as arestas
				
					if (Graph[i][j] > max && !Nodes.contains(j) && !notThis.contains(j)) {   //atualizar o max para o valor 
						imax = i;
						jmax = j;
						max = Graph[i][j];
					}
					if (Nodes.contains(j) && !notThis.contains(j)) {
						notThis.add(j);
						j = 0;
					}
				}
				notThis.clear();
			}
			
			Nodes.add(jmax);
			Graph[imax][jmax] = 0;   //meter a 0 para não voltar a escolher esta 
			Graph[jmax][imax] = 0;   //o simétrico também
			MSTree[imax][jmax] = true;  //existe esta aresta na MST
			MSTree[jmax][imax] = true;
		}
		//System.out.println(Nodes);
		return MSTree; 
	}
	
	
	public String printBonito2 ()	{
		String out = "";
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				out += (adjMatrix[i][j]) + " ";
			}
			out += "\n";
		}
		return out;
	}
	
	/*public static void main(String[] args) {
		//DataSet d = new DataSet("bcancer.csv");
		WeightedGraph g = new WeightedGraph(4);
		
		g.Add(0, 1, 8);
		g.Add(0,0,0);
		g.Add(0,2,7);
		g.Add(0,3,5);
		g.Add(1,1,0);
		g.Add(1,2,3);
		g.Add(1,3,1);
		g.Add(2,2,0);
		g.Add(2,3,2);
		g.Add(3,3,0);
		//System.out.println(g);
		g.printBonito2();
		System.out.println(Arrays.deepToString(g.MST()));
		System.out.println(Arrays.deepToString(g.MST()));
		
	}*/
		
	
}
