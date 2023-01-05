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
	/** Adiciona uma aresta entre o no1 e o no2 com o valor peso */
	public void Add (int no1, int no2, double peso) {   
		adjMatrix[no1][no2]=peso;
		adjMatrix[no2][no1]=peso;
	}
	
	public double [][] getMatrix (){
		return adjMatrix;
	} // Retorna uma copia da matriz que, se alterada, não afeta a "original"
	
	public int getdim() {
		return dim;
	}
	
	
	
	//grafo.MST()
	
	/** MST - árvore de extensão de peso maximal  */
	public boolean [][] /*gr_mst*/ MST() {
		boolean [][] MSTree = new boolean [dim][dim];
		//System.out.println(Arrays.deepToString(graph));
//		double [][] Graph = new double [dim][dim];
//		for (int i=0; i<dim; i++) {
//			for (int j=0; j<dim; j++) {
//				Graph[i][j]= adjMatrix [i][j];
//			}
//		}
//		//cópia do grafo
		
		LinkedList<Integer> Nodes = new LinkedList<Integer>(); //Lista dos nós (j's)
		
		int nnos = adjMatrix.length;
		int z=0;
		boolean flaginit= false;
		for (; z< nnos && !flaginit; z++) {
			for (int c=0; c<nnos && !flaginit; c++) {
				if (adjMatrix[z][c] !=0) {
					flaginit=true;
					z=z-1;
				}
			}
		}
		int init=z;
		
		Nodes.add(init); // nó inicial: init
		
		boolean encontreiArestaParaFora = true;
		while (encontreiArestaParaFora) {
		//while (Nodes.size() < dim) {
			double max = 0;  // vetor max: (i, j, peso)
			int imax=-1;
			int jmax=-1;
			encontreiArestaParaFora = false;
			
			for (int i : Nodes) { 
								
				for (int j = 0; j<dim; j++) {  //ver as arestas
				
					if (adjMatrix[i][j] > max && !Nodes.contains(j)) {   //atualizar o max para o valor 
						// Nodes não contem para não criar ciclos
						//Adicionar a aresta máxima naquele momento
						imax = i;
						jmax = j;
						max = adjMatrix[i][j];
						encontreiArestaParaFora = true;
					}
				}
			}
			if (encontreiArestaParaFora) {
				Nodes.add(jmax);
				MSTree[imax][jmax] = true;  //existe esta aresta na MST
				MSTree[jmax][imax] = true;
				//System.out.println("max(i,j,w)="+imax+","+jmax+","+max);
			}
		}
		//System.out.println(Nodes);
		System.out.println("MST: "+Arrays.deepToString(MSTree));
		return MSTree/*new gr_mst(init,MSTree)*/; 
	}
	
	//Ignorar?!
//	public static class gr_mst {
//		boolean [][] MSTree;
//		int init;
//		public gr_mst(int init, boolean [][] MSTree) {
//			this.MSTree = MSTree;
//			this.init = init;
//		}
//		public boolean [][] getMSTree() {
//			return MSTree;
//		}
//		public int getInit() {
//			return init;
//		}
//	}
	
	public String printBonito2 ()	{
		String out = "";
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				out += (adjMatrix[i][j]) + "  ";
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
