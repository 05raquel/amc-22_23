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

	//Markov Random Field Tree

	private int [] MRFTree; 
	private double [][][][] potentialMatrix;
	private int nvar;
	
	//CONSTRUTOR de MRFT
	
	// coloca os phi ij (xi,xj) em cada aresta que podem ser vistos como uma matriz
	
	public MRFT(DataSet d, boolean [][] tree, int [] domains) {
		//tendo em conta o dataset e a MST
		super();	
		nvar = d.getDataListArraySize() -1;
		
		int nnos = tree.length;
		int [] MRFTree = new int [nnos]; //MRFTree array com o tamanho do nr de nós 
		//(MRFTree: índice do pai de cada nó)
		
		//Utilizando a informação da MST, define-se o nó init - não pode ser independente
		boolean flaginit= false;
		int init=0; int noe=0;
		for (; init< nnos && !flaginit; init++) {
			noe=init+1;
			for (; noe<nnos && !flaginit; noe++) {
				if (tree[init][noe]) { //se existir pelo menos uma aresta
					flaginit=true;
					init=init-1;
					noe=noe-1;
				}
			}
		}
		//init // então, encontrou-se o nó init - dependente!
		//noe - nó especial
		System.out.println("init: " + init); 
		//System.out.println("noe: "+noe);
			
		// aresta especial é do init para o noe - dá a direção das outras arestas
		
		LinkedList<Integer> nospreenchidos = new LinkedList<Integer>(); //LinkedList para ser dinâmico (nós que têm pai)
		nospreenchidos.add(init); //acrescenta o nó init à lista de nós preenchidos 
		  
		for (int i = 0; i < nnos; i++) {
			if (i==init) {
				MRFTree[i]=-1;     // o primeiro nó (init) não tem pai logo é -1
			}
			else {
				MRFTree[i]=-2;     //para as restantes entradas não iniciadas coloca-se -2
			}
		}
		
		//bfs
		// começar num nó
		// visitar os filhos (tirando os já visitados anteriormente)
		// repetir para cada filho
		Set<Integer> visitados = new TreeSet<>(); 
		Queue<Integer> fila = new LinkedList<>();
		fila.add(init);
		
		while (!fila.isEmpty()) {
			int curr = fila.remove(); // fazer coisas com o atual
			if (!visitados.contains(curr)) { //se ainda não tivermos executado esse atual
				List<Integer> vizinhos = offspring(tree, curr); // lista de descendentes do nó atual
				for (int vizinho : vizinhos) { 
					if (!nospreenchidos.contains(vizinho)) {
						MRFTree[vizinho] = curr;
						nospreenchidos.add(vizinho);
					}
				}
				visitados.add(curr);
				fila.addAll(vizinhos);
			}
		}
		System.out.println("MRF Tree: "+ Arrays.toString(MRFTree));
		
		this.MRFTree = MRFTree;  	//atualizar a matriz de potenciais

		double [][][][] matrix = inicia(domains, nnos, nnos); 
		
		// i, j, xi valores que i toma, xj valores que j toma
		// preencher as matrizes para as arestas da MRFTree 
		// MRFTree[a] = pai = i
		// a = j
		
		for (int a=0; a < MRFTree.length;a++) { 
			boolean isArestaEspecial = (a==noe && MRFTree[a]==init);
			boolean temPai = MRFTree[a]>=0;
			if (temPai) {
				int [] var = {MRFTree[a],a}; 
				int [] i = {MRFTree[a]};
				for (int xi=0; xi < d.getDomain(MRFTree[a]); xi++) { //alt
					for (int xj=0; xj < d.getDomain(a); xj++) { //alt
						int [] val = {xi,xj};
						int [] ix = {xi};
						if (isArestaEspecial) {
							matrix[init][noe][xi][xj]= 
									(d.Count(var, val) + 0.2)
									/(d.getDataList().size() //não faltam PARENTESES!
													+(0.2*(d.getDomain(init)) //alt
															*(d.getDomain(noe)))) ; //alt
							
						}
						else {
							//System.out.println("idxs: " + MRFTree[a] +", "+ a+", "+ xi +", "+ xj);
							matrix [MRFTree[a]][a][xi][xj] = 
									(d.Count(var, val) + 0.2) 
									/ (d.Count(i,ix) 
											+(0.2*(d.getDomain(a))));	//alt				
						}
					}
				}
			}
		}
		this.potentialMatrix = matrix;
		System.out.println("potential matrix: "+Arrays.deepToString(matrix));
	}
	
	/** Devolve o número de variáveis do Dataset - características*/
	public int getnvar() {
		return nvar;
	}

	/** inicia a potentialMatrix com as dimensões corretas de acordo com os domínios das características */
	public double [][][][] inicia (int [] domains, int ni, int nj){
		double [][][][] ma = new double [ni][nj][][]; 
		for (int itni = 0; itni<ni; itni++) { // itni = iterada de ni
			for (int itnj =itni+1; itnj<nj; itnj++) { //itnj = iterada de nj
				// a diagonal não tem significado - só há potenciais de arestas
				ma [itni][itnj] = new double [domains[itni]] [domains[itnj]]; //alt
				ma [itnj][itni] = new double [domains[itnj]] [domains[itni]]; //alt
				// para cada matriz interior, define-se o seu tamanho - domínio de itni e itnj
			}
		}
		return ma;
	}
	
	/** devolve a lista de descendentes do nó tendo em conta a tree*/
	public List<Integer> offspring(boolean[][] tree2, int no) {
		List<Integer> offs = new ArrayList<>();
		for (int i=0; i<tree2.length; i++) {
			if (tree2[no][i]) offs.add(i); //aresta do nó para o i
		}
		return offs;
	}
	
	public int getsizeMRFTree() {
			return MRFTree.length;
	}
	
	//PROB
	
	public double Prob (int [] vetor) {
		// Pr Mc ( vetor ) = produtório arestas potenciais phi i j
		double prob = 1;
		//produto (para cada aresta i, j - MRFTree) potencial (xi,xj)
		
		// MRFTree[j] = pai de j = i
		// aresta i --> j 
		//System.out.println("Length: "+MRFTree.length);
		for (int j=0; j < MRFTree.length; j++) { // começa no 1 porque o 0 não tem pai
			//System.out.println("j: "+j);
			boolean temPai = MRFTree[j]>=0;
			//System.out.println("j: "+j);
			if (temPai) {
				//System.out.println("potential matrix: " + potentialMatrix [MRFTree[j]] [j] [vetor[MRFTree[j]]][vetor[j]]);
				prob = prob * potentialMatrix [MRFTree[j]] [j] [vetor[MRFTree[j]]][vetor[j]];
			}
		}	
		System.out.println("Probabilidade Mc "+prob);
		return prob;
	}
	
	
}
