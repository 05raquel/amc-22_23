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
	
	//CONSTRUTOR
	
	// coloca os phi ij (xi,xj) em cada aresta que podem ser vistos como uma matriz
	
	public MRFT(DataSet d, boolean [][] tree) {
		//tendo em conta o dataset e a MST
		super();
		
		nvar = d.getDataListArraySize() -1;
		
		int nnos = tree.length;
		int [] MRFTree = new int [nnos]; //MRFTree array com o tamanho do nr de nós 
		//(MRFTree: índice do pai de cada nó)
		
		
		//int [] arestae = new int [2];     //aresta especial - dá a direção das arestas
		//arestae[0]=0;                    //nó 0 para começar
		
		//tentar aceder para não fazer de novo
		boolean flaginit= false;
		int z=0;
		for (; z< nnos && !flaginit; z++) {
			if (d.getDomains()[z] !=0) {
				flaginit=true;
				z=z-1;
			}
		}
		int init=z;
		
		boolean flag = false;
		int b=0;
		for(; b < nnos && !flag; b++){
			if (tree[init][b]) {     //escolher a primeira aresta (que parte do 0) existente na MST (=true)
				flag = true;
				b=b-1;
			}
		}
		int noe=b; //nó especial
		
		// aresta especial é do init para o noe - dá a direção das outras arestas
		//System.out.println("noe:"+noe);
		
		LinkedList<Integer> nospreenchidos = new LinkedList<Integer>(); //LinkedList para ser dinâmico (nós que têm pai)
		nospreenchidos.add(0); //acrescenta o nó 0 à lista de nós preenchidos 
		  
		for (int i = 1; i < nnos; i++) {
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
		fila.add(0);
		
		while (!fila.isEmpty()) {
			int curr = fila.remove();
			if (!visitados.contains(curr)) { 
				List<Integer> vizinhos = offspring(tree, curr);
				for (int vizinho : vizinhos) {
					if (!nospreenchidos.contains(vizinho)) {
						nospreenchidos.add(vizinho);
						MRFTree[vizinho] = curr;
					}
				}
				visitados.add(curr);
				fila.addAll(vizinhos);
				
			}
		}
		System.out.println("MRF Tree: "+ Arrays.toString(MRFTree));
		
		
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

		double [][][][] matrix = inicia(d, nnos, nnos); 
		
		//nnos é o nr de nós
		// i, j, xi valores que i toma, xj valores que j toma
		// preencher as matrizes para as arestas da MRFTree 
		// MRFTree[a] = pai = i
		// a = j
		
		for (int a=0; a < MRFTree.length;a++) { 
			
			boolean isArestaEspecial = (a==noe && MRFTree[a]==0);
			boolean temPai = MRFTree[a]>=0;
			if (temPai) {
				int [] var = {MRFTree[a],a}; 
				int [] i = {MRFTree[a]};
				for (int xi=0; xi <= d.getDomains()[MRFTree[a]]; xi++) {
					for (int xj=0; xj <= d.getDomains()[a]; xj++) {
						int [] val = {xi,xj};
						int [] ix = {xi};
						if (isArestaEspecial) {
							matrix[0][noe][xi][xj]= 
									(d.Count(var, val) + 0.2)
									/(d.getDataList().size() //não faltam PARENTESES!
													+(0.2*(d.getDomains()[0]+1)
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
	}
	
	
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
	public double [][][][] inicia (DataSet dataset, int ni, int nj){
		double [][][][] ma = new double [ni][nj][][]; 
		for (int itni = 0; itni<ni; itni++) { // itni = iterada de ni
			for (int itnj =0; itnj<nj; itnj++) { //itnj = iterada de nj
				ma [itni][itnj] = new double [dataset.getDomains()[itni]+1] [dataset.getDomains()[itnj]+1];
				// para cada matriz interior, define-se o seu tamanho - domínio de itni e itnj
			}
		}
		return ma;
	}
	
	/** devolve a lista de descendentes do nó tendo em conta a tree*/
	public List<Integer> offspring(boolean[][] tree2, int no) {
		List<Integer> offs = new ArrayList<>();
		for (int i=0; i<tree2.length; i++) {
			if (tree2[no][i])offs.add(i);
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
		
		for (int j=1; j < MRFTree.length; j++) { // começa no 1 porque o 0 não tem pai
			boolean temPai = MRFTree[j]>=0;
			if (temPai) {
				prob = prob * potentialMatrix [MRFTree[j]] [j] [vetor[MRFTree[j]]][vetor[j]];
			}
		}	
		System.out.println("Probabilidade Mc"+prob);
		return prob;
	}

}
