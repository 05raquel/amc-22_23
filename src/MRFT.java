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
		
		
		//int [] arestae = new int [2];     //aresta especial - dá a direção das arestas
		//arestae[0]=0;                    //nó 0 para começar
		
		//tentar aceder para não fazer de novo - informação da MST
		boolean flaginit= false;
		int z=0;
		for (; z< nnos && !flaginit; z++) {
			if (d.getDomains()[z] !=0) {
				flaginit=true;
				z=z-1;
			}
		}
		int init=z;
		System.out.println("init: " + init);
		
		boolean flag = false;
		int b=0;
		for(; b < nnos && !flag; b++){
			if (tree[init][b]) {     //escolher a primeira aresta (que parte do init) existente na MST (=true)
				flag = true;
				b=b-1;
			}
		}
		int noe=b; //nó especial
		
		// aresta especial é do init para o noe - dá a direção das outras arestas
		//System.out.println("noe:"+noe);
		
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
		
		//inútil
//		while (faltapreencher(MRFTree)) { //enquanto existirem nós sem pai - por preencher
//			
//			for (int cp=0; cp < nospreenchidos.size(); cp++) {
//			//for (int cp: nospreenchidos) {  // cada cp (nó com pai) na lista nós preenchidos
//				System.out.println("cp="+cp);
//				for (int j=1; j< nnos; j++) {
//					// nos preenchidos têm pai:  cp --> j
//					if (tree[nospreenchidos.get(cp)][j] /*&& MRFTree[j]==-2*/ ) {          //ver onde existe aresta entre esse nó com pai e o nó j  
//							MRFTree[j]=nospreenchidos.get(cp);            //colocamos o nó com pai no local j da MRFTree
//							System.out.println("MRFT="+Arrays.toString(MRFTree));
//							if (!nospreenchidos.contains(j)) nospreenchidos.add(j); //adiciona esse j aos nós preenchidos
//							System.out.println("NósPreen="+nospreenchidos);
//					} //se passar primeiro por um nó que não está ligado a um com pai, salta esse e depois volta quando preencher outros                                                                         
//				}
//			}
//		}
//		System.out.println(Arrays.toString(MRFTree));
		
		this.MRFTree = MRFTree;  	//atualizar a matriz de potenciais

		double [][][][] matrix = inicia(domains, nnos, nnos); 
		
		//nnos é o nr de nós
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
				for (int xi=0; xi <= d.getDomains()[MRFTree[a]]; xi++) {
					for (int xj=0; xj <= d.getDomains()[a]; xj++) {
						int [] val = {xi,xj};
						int [] ix = {xi};
						if (isArestaEspecial) {
							matrix[init][noe][xi][xj]= 
									(d.Count(var, val) + 0.2)
									/(d.getDataList().size() //não faltam PARENTESES!
													+(0.2*(d.getDomains()[init]+1)
															*(d.getDomains()[noe]+1))) ;
							// domínio +1 porque o domínio é o maximo dos valores tomados - incluir o 0
						}
						else {
							//System.out.println("idxs: " + MRFTree[a] +", "+ a+", "+ xi +", "+ xj);
							matrix [MRFTree[a]][a][xi][xj] = 
									(d.Count(var, val) + 0.2) 
									/ (d.Count(i,ix) 
											+(0.2*(d.getDomains()[a]+1)));						
						}
					}
				}
			}
		}
		this.potentialMatrix = matrix;
		System.out.println("potential matrix: "+Arrays.deepToString(matrix));
	}
	
	//inútil
//	/** Se a MRFTree passada ainda tem nós sem pai */
//	public boolean faltapreencher (int[]v) {
//		boolean falta=false;
//		for (int c=0; c<v.length && falta==false; c++) {
//			if (v[c]==-2) falta=true;}
//		
//		return falta;
//	}
	
	
	public int getnvar() {
		return nvar;
	}
	
	
	/** inicia a potentialMatrix com as dimensões corretas de acordo com os domínios das características */
	public double [][][][] inicia (int [] domains, int ni, int nj){
		double [][][][] ma = new double [ni][nj][][]; 
		for (int itni = 0; itni<ni; itni++) { // itni = iterada de ni
			for (int itnj =0; itnj<nj; itnj++) { //itnj = iterada de nj
				ma [itni][itnj] = new double [domains[itni]+1] [domains[itnj]+1];
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
		//System.out.println("Lenght: "+MRFTree.length);
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
