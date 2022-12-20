import java.util.ArrayList;
import java.util.LinkedList;

public class MRFT {

	//Markov Random Field Tree
	
	private DataSet dataset;
	private boolean [][] tree;	// usamos a MST tendo em conta o graph
	private int [] MRFTree; // confirmar o tipo depois de ter feito o construtor
	private double [][] potentialMatrix; // confirmar o tipo depois de ter feito o construtor
	
	//CONSTRUTOR
	
	// coloca os phi ij (xi,xj) em cada aresta
	// que podem ser vistos como uma matriz
	// não esquecer: converter os valores inteiros do count em double's
	
	
	public MRFT(DataSet dataset, boolean [][] tree) {
		//tendo em conta o dataset e uma árvore (a MST)
		super();
		this.dataset = dataset;
		this.tree = tree;
		
		boolean flag = false;
		int [] MRFTree = new int [tree.length];
		
		int [] arestae= new int [2]; //aresta especial - dá a direção das arestas
		arestae[0]=0;
		int noe=0;
		for(; noe< tree[0].length && !flag; noe++)	{
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
		MRFTree[0]= -1;	
		for (int i =1; i< tree.length;i++) {
			MRFTree[i]=-2;
		}
		while (faltapreencher(MRFTree)) {
			//enquanto existirem nós sem pai - por preencher
			for (int cp: nospreenchidos(MRFTree)) { 
				for (int j=0; j< tree.length; j++) {
					if (tree[cp][j]) MRFTree[j]=cp;
				}
			}
		}
		//então vamos ver as arestas com esse nó e escolhemos para pai aquele no que já tiver pai 
		//temos um problema se não encontrar um nó que já tenha um pai -> talvez fazer i++ e depois voltar atrás
		
		this.MRFTree = MRFTree;
		
		//atualizar a matriz de potenciais
		
		
		for (int a=0; a< MRFTree.length;a++) {}
		//ArrayList< LinkedList<Integer> >[][] aa = new ArrayList<LinkedList<Integer>> [2][3];
				//new [tree.length][tree.length] ArrayList<int []>();
		
		
		//(dataset.Count(arestae, (x0,xnoe) +0,2)/(dataset.getDataListArraySize +(0,2*(dataset.getDomains()[0]+1)*(dataset.getDomains()[noe]+1)) ;		
	
		//(dataset.Count((i,j), (xi,xj) +0,2)/(dataset.Count(i,xi) +(0,2*(dataset.getDomains()[0]+1)*(dataset.getDomains()[noe]+1));
		
		//this.potentialMatrix =  ;
	}
	
	public boolean faltapreencher (int[]v) {
		boolean falta=false;
		for (int c=0; c<v.length && !falta; c++) {
			if (v[c]==-2) falta=true;}
		return falta;
	}
	
	public LinkedList<Integer> nospreenchidos(int[]v) {
		LinkedList<Integer> lista = new LinkedList<Integer>();
		for (int i=0; i<v.length; i++) {
			if (v[i]!=-2) lista.add(i);
		}
		return lista;
	}
	
	//PROB
	
	/*public double Prob (int [] vetor) {
		// Pr Mc ( vetor ) = produtório arestas potenciais phi i j
		return 0;
		//probabilidade dos dados no dataset 
	}
	*/
}
