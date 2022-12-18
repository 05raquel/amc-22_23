import java.util.*;

public class WeightedGraph {
	//não direcionado
	
	private int dim;
	private double [][] adjMatrix; 
	
	public WeightedGraph (int size) {
		super();
		this.dim = size;
		this.adjMatrix = new double [size-1][size -1]; //retiramos a classe
	}		
	
	// ADD
	
	public void Add (int no1, int no2, double peso) {
		adjMatrix[no1][no2]=peso;
		adjMatrix[no2][no1]=peso;
	}
	
	/* MST
	árvore de extensão de peso maximal */
	
	public boolean [][] MST() {
		boolean [][] tree = new boolean [dim][dim];
		// nó inicial -> 0
				
				
		return; 
		
		//tree
		
	}
	
	/*
1 A cada nó v associa-se

a) C[v] o custo máximo para v (inicializado a -∞)
b) E[v] a aresta que com esse custo (inic. a null) 

2) F inicializa-se como uma floresta com nó u e C[u]=0

3) Q os vértices não incluídos em F (inic com V\{u})

4) while(!Q.vaziaQ()){

a) encontrar a aresta leve e={x,v} para o corte {F,Q} com x em F
b) Q.remove(v); //F.add(v); 
c) E[v]=e; C[v]=g.weight(e) //não é nec para proj
d) F.addedge(e) 

5) return F
	 */
	
	public void printBonito2 ()	{
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				System.out.print(adjMatrix[i][j]);
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args) {
		DataSet d = new DataSet("bcancer.csv");
		WeightedGraph g = new WeightedGraph(d.getDataListArraySize());
		g.Add(0, 1, 3);
		g.printBonito2();
		}
	
}
