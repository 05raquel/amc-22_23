import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class MRFT implements Serializable {
	private static final long serialVersionUID = 1L;

	private int [] MRFTree;                   //array com pais do nó com o nr da posição (índice do pai de cada nó)
	private double [][][][] potentialMatrix;  //matriz de matrizes com phis
	
	
	/**CONSTRUTOR de MRFT:coloca os phi ij (xi,xj) em cada aresta que podem ser vistos como uma matriz */
	public MRFT(DataSet d, boolean [][] tree, int [] domains) {  
		super();	
		
		int nnos = d.NrVariables();
		int [] MRFTree = new int [nnos]; //MRFTree array com o tamanho do nr de nós 
		
		boolean flaginit= false;
		int init=0; 
		int noe=init+1;                        //noe = nó especial                   
		for (; noe<nnos && !flaginit; noe++) {
			if (tree[init][noe]) {             //se existir uma aresta
				flaginit=true;
				noe=noe-1;
			}
		}

		// aresta especial é do init (raiz tree) para o noe
		
		LinkedList<Integer> nospreenchidos = new LinkedList<Integer>(); //LinkedList para ser dinâmico (nós que têm pai)
		nospreenchidos.add(init); //acrescenta o nó init à lista de nós preenchidos 
		MRFTree[init]=-1; 
		  
		//PERCORRER EM LARGURA
		//Começar num nó
		//visitar os filhos (tirando os já visitados anteriormente)
		//Repetir para cada filho
		
		Set<Integer> visitados = new TreeSet<>(); 
		Queue<Integer> fila = new LinkedList<>();
		
		fila.add(init);
		
		while (!fila.isEmpty()) {
			int atual = fila.remove();         // fazer coisas com o atual
			if (!visitados.contains(atual)) {  // se ainda não tivermos executado esse atual
				List<Integer> filhos = offspring(tree, atual); // lista de descendentes do nó atual
				for (int filho : filhos) { 
					if (!nospreenchidos.contains(filho)) {  //se ainda não tivermos esse no preenchido
						MRFTree[filho] = atual;             //definimos o seu pai e adicionamos aos nos preenchidos
						nospreenchidos.add(filho);
					}
				}
				visitados.add(atual);          //adicionar o atual aos pais visitados
				fila.addAll(filhos);           //adicionar à fila todos os filhos do no atual
			}
		}
		
		this.MRFTree = MRFTree;  	           //atualizar a MRFT
		

		double [][][][] matrix = new double [nnos][nnos][][];  //matriz de potenciais phis
		for (int a = 1; a < nnos; a++) { 
			matrix [MRFTree[a]][a] = new double [domains[MRFTree[a]]] [domains[a]]; //matriz na entrada (pai,filho) = matriz de tamanho dos dominios
		
		// i, j, xi (valores que i toma), xj (valores que j toma)
		// a = filho ; MRFTree[a] = pai de a
			
		// preencher as matrizes para as arestas da MRFTree 
			boolean isArestaEspecial = (a==noe && MRFTree[a]==init);
			int [] var = {MRFTree[a],a}; 
			int [] i = {MRFTree[a]};
			for (int xi=0; xi < d.getDomain(MRFTree[a]); xi++) { //for's para preencher todas as combinações de vals possiveis
				for (int xj=0; xj < d.getDomain(a); xj++) {      
					int [] val = {xi,xj};
					int [] ix = {xi};
					
					//cálculo de phis diferente para aresta especial
					if (isArestaEspecial) {
							matrix[init][noe][xi][xj]= 
									(d.Count(var, val) + 0.2)
									/(d.Samplelength()+(0.2*(d.getDomain(init))*(d.getDomain(noe)))) ;
					}
					else {
							matrix [MRFTree[a]][a][xi][xj] = 
									(d.Count(var, val) + 0.2) 
									/ (d.Count(i,ix)+(0.2*(d.getDomain(a))));			
					}
				}
			}
		}
		this.potentialMatrix = matrix; //atualiza o atributo matriz de potenciais
	}
		
	/** devolve a lista de descendentes do nó tendo em conta a tree*/
	public List<Integer> offspring(boolean[][] tree, int no) {
		List<Integer> offs = new ArrayList<>();
		for (int i=0; i<tree.length; i++) {
			if (tree[no][i])
				offs.add(i); //aresta do nó para o i
		}
		return offs;
	}
	
	//PROB
	
	public double Prob (int [] vetor) {
		// Pr Mc (vetor) = produtório potenciais phi nas arestas i(pai-mrft[j]) -> j(filho) [val de i , val j]
		double prob = 1;
		
		for (int j=1; j < MRFTree.length; j++) { // começa no 1 porque o 0 não tem pai
				prob = prob * potentialMatrix [MRFTree[j]] [j] [vetor[MRFTree[j]]][vetor[j]];

		}	
		//System.out.println("Probabilidade Mc "+prob);
		return prob;
	}
	
	
}
