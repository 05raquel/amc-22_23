import java.util.ArrayList;
import java.util.LinkedList;

public class MRFT {

	//Markov Random Field Tree
	
	private DataSet dataset;
	private boolean [][] tree;	// usamos a MST tendo em conta o graph
	private int [] MRFTree; // confirmar o tipo depois de ter feito o construtor
	private double [][][][] potentialMatrix; // confirmar o tipo depois de ter feito o construtor
	
	//CONSTRUTOR
	
	// coloca os phi ij (xi,xj) em cada aresta
	// que podem ser vistos como uma matriz
	// não esquecer: converter os valores inteiros do count em double's
	
	
	public MRFT(DataSet d, boolean [][] tree) {
		//tendo em conta o dataset e uma árvore (a MST)
		super();
		this.dataset = d;
		this.tree = tree;
		
		int nnos = tree.length;
		
		boolean flag = false;
		int [] MRFTree = new int [nnos];
		
		int [] arestae= new int [2]; //aresta especial - dá a direção das arestas
		arestae[0]=0;
		int noe=0;
		for(; noe< nnos && !flag; noe++)	{
			if (tree[0][noe]) {
				flag = true;
			}
		}
		arestae[1]=noe;
		
		// Valores iniciais da MRFTree - aresta especial e nós ligados ao 0
		/*for (int i = 0; i< tree.length; i++)	{
			if (tree[0][i])	{
				MRFTree[i]=0;
			}
			else MRFTree[i] = -2;
		}*/
		
		LinkedList<Integer> nospreenchidos = new LinkedList<Integer>();
		
		MRFTree[0]= -1;	
		nospreenchidos.add(0);
		
		
		for (int i =1; i< nnos;i++) {
			MRFTree[i]=-2;
		}
		
		while (faltapreencher(MRFTree)) {
			//enquanto existirem nós sem pai - por preencher
			for (int cp: nospreenchidos) { // cada cp (nó com pai) na lista nós preenchidos
				for (int j=0; j< nnos; j++) {
					if (tree[cp][j]) {
						MRFTree[j]=cp;
						nospreenchidos.add(j);
					}
				}
			}
		}
		
		//então vamos ver as arestas com esse nó e escolhemos para pai aquele no que já tiver pai 
		//temos um problema se não encontrar um nó que já tenha um pai -> talvez fazer i++ e depois voltar atrás

		this.MRFTree = MRFTree;
		
		//atualizar a matriz de potenciais
		
		double [][][][] matrix = inicia(dataset, nnos, nnos); //número de nós 
		// i j, xi valores que i toma, xj valores que j toma
		
		// preencher as matrizes para as arestas da MRFTree 
		
		// MRFTree[a] = pai = i, 
		// a = j
		
		
		for (int a=0; a< MRFTree.length;a++) {
			
			if (MRFTree[a]==noe && a==0) {
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
		for (int c=0; c<v.length && !falta; c++) {
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
