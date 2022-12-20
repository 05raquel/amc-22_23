
public class MRFT {

	//Markov Random Field Tree
	
	private DataSet dataset;
	private boolean [][] tree;	// usamos a MST tendo em conta o graph
	private int [] MRFTree; // confirmar o tipo depois de ter feito o construtor
	private double [][] potentialMatrix; // confirmar o tipho depois de ter feito o construtor
	
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
		MRFTree [0] = -1;
		
		int [] arestae= new int [2];
		arestae[0]=0;
		int noe=0;
		for(; noe< tree[0].length && !flag; noe++)	{
			if (tree[0][noe]!= false) {
				flag = true;
			}
		}
		arestae[1]=noe;
		
		
		for (int i = 0; i< tree.length; i++)	{
			if (tree[0][i])	{
				MRFTree[i]=0;
			}
			else MRFTree[i] = -2;
		}
		MRFTree [0]=-1;	
		
		//se o nó que ainda está a -2,então vamos ver as arestas com esse nó e escolhemos para pai aquele no que já tiver pai 
		//temos um problema se não encontrar um nó que já tenha um pai -> talvez fazer i++ e depois voltar atrás
		
		
		for (int j = 0; j < )
		dataset.Count(arestae, );
		
	
	}
	
	
		

		/*this.MRFTree = ;// arranjar as funções!! 
		this.potentialMatrix = ; // arranjar as funções
	}*/
	
	//PROB
	
	public double Prob (int [] vetor) {
		// Pr Mc ( vetor ) = produtório arestas potenciais phi i j
		return 0;
		//probabilidade dos dados no dataset 
	}
}
