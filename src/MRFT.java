import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MRFT {

	//Markov Random Field Tree
	
	private DataSet dataset;
	private boolean [][] tree;	// usamos a MST tendo em conta o graph
	private int [] MRFTree; 
	private double [][][][] potentialMatrix;
	
	//CONSTRUTOR
	
	// coloca os phi ij (xi,xj) em cada aresta que podem ser vistos como uma matriz
	
	public MRFT(DataSet d, boolean [][] tree) {
		//tendo em conta o dataset e a MST
		super();
		this.dataset = d;
		this.tree = tree;
		
		int nnos = tree.length;
		
		boolean flag = false;
		int [] MRFTree = new int [nnos]; //MRFTree array com o tamanho do nr de nós 
		int [] arestae= new int [2];     //aresta especial - dá a direção das arestas
		arestae[0]=0;                    //nó 0 para começar
		int noe=0;
		for(; noe< nnos && !flag; noe++){
			if (tree[0][noe]) {            //escolher a primeira aresta que aparece como true na MST
				flag = true;
			}
		}
		
		arestae[1]=noe;                 //primeira aresta entre nó 0 e noe
		
		LinkedList<Integer> nospreenchidos = new LinkedList<Integer>(); //LinkedList para ser dinâmico
		
		MRFTree[0]= -1;	  // o primeiro nó não tem pai logo é -1
		nospreenchidos.add(0); //acrescenta o nó 0 à lista de nós preenchidos 
		
		for (int i =1; i< nnos;i++) {
			MRFTree[i]=-2;     //para as restantes entradas não iniciadas coloca-se -2
		}
		
		while (faltapreencher(MRFTree)) { //enquanto existirem nós sem pai - por preencher
			
			for (int cp=0; cp < nospreenchidos.size(); cp++) {
			//for (int cp: nospreenchidos) {  // cada cp (nó com pai) na lista nós preenchidos
				System.out.println(cp);
				for (int j=1; j< nnos; j++) {
					// nos preenchidos têm pai:  cp --> j
					if (tree[nospreenchidos.get(cp)][j] ) {          //ver onde existe aresta entre esse nó com pai e o nó j  
						//if (MRFTree[j]==-2) {
							MRFTree[j]=nospreenchidos.get(cp);            //colocamos o nó com pai no local j da MRFTree
							System.out.println(Arrays.toString(MRFTree));
							nospreenchidos.add(j); //adiciona esse j aos nós preenchidos
							System.out.println(nospreenchidos);
						//}
					} //se passar primeiro por um nó que não está ligado a um com pai, salta esse e depois volta quando preencher outros                                                                         
				}
			}
		}
		this.MRFTree = MRFTree;  	//atualizar a matriz de potenciais

		double [][][][] matrix = inicia(dataset, nnos, nnos); 
		
//nnos é o nr de nós
// i, j, xi valores que i toma, xj valores que j toma
// preencher as matrizes para as arestas da MRFTree 
// MRFTree[a] = pai = i
// a = j
		
		for (int a=0; a< MRFTree.length;a++) { 
			
			if (MRFTree[a]==noe && a==0) {  //se for a primeira aresta - aresta especial
				int [] var = {0,noe};
				for (int xi=0; xi < d.getDomains()[a]; xi++) {
					for (int xj=0; xj < d.getDomains()[noe]; xj++) {
						int [] val = {xi,xj};
						matrix[0][noe][xi][xj]= (d.Count(var, val) +0.2)/(d.getDataListArraySize()) +(0.2*(d.getDomains()[0]+1)*(d.getDomains()[noe]+1)) ;
					// domínio +1 porque o domínio é o maximo dos valores tomados - incluir o 0
					}
				}
			}
			else {
				int [] var = {MRFTree[a],a}; 
				int [] i = {MRFTree[a]};
				for (int xi=0; xi < d.getDomains()[a]; xi++) {
					for (int xj=0; xj < d.getDomains()[noe]; xj++) {
						int [] val = {xi,xj};
						int [] ix = {xi};
						matrix [MRFTree[a]][a][xi][xj] = (d.Count(var, val) +0.2) /(d.Count(i,ix) +(0.2*(d.getDomains()[MRFTree[a]]+1)*(d.getDomains()[a]+1)));
					}
				}
			}
		}
		this.potentialMatrix = matrix;
	}
	
	public boolean faltapreencher (int[]v) {
		boolean falta=false;
		for (int c=0; c<v.length && falta==false; c++) {
			if (v[c]==-2) falta=true;}
		
		return falta;
	}
	
	/*public LinkedList<Integer> nospreenchidos(int[]v) {
		LinkedList<Integer> lista = new LinkedList<Integer>();
		for (int i=0; i<v.length; i++) {
			if (v[i]!=-2) lista.add(i);
		}
		return lista;
	}*/
	
	public double [][][][] inicia (DataSet dataset, int ni, int nj){
		double [][][][] ma = new double [ni][nj][][]; 
		for (int itni = 0; itni<ni; itni++) { // itni = iterada de ni
			for (int itnj =0; itnj<nj; itnj++) { //itnj = iterada de nj
				ma [itni][itnj] = new double [dataset.getDomains()[itni]] [dataset.getDomains()[itnj]];
				// para cada matriz interior, define-se o seu tamanho - domínio de itni e itnj
			}
		}
		return ma;
	}
	
	public int getsizeMRFTree() {
			return MRFTree.length;
	}
	
	//PROB
	
	public double Prob (int [] vetor) {
		// Pr Mc ( vetor ) = produtório arestas potenciais phi i j
		double prob = 1;
		//produto (para cada aresta i, j - MRFTree) potencial (xi,xj)
		
		// MRFTree[a] = pai = i, 
		// f = j
		
		for (int f=1; f < MRFTree.length; f++) { // começa no 1 porque o 0 não tem pai
			prob = prob * potentialMatrix [MRFTree[f]] [f] [vetor[MRFTree[f]]][vetor[f]];
		}
		return prob;
	}
}
