import java.util.Arrays;

public class ChowLiu { 

	/** Cria um grafo pesado e retorna a MST feita a partir dele */
	public static boolean [][] Chow_liu (DataSet d){
		
		WeightedGraph graph = new WeightedGraph (d.NrVariables());
		
		double m = d.Samplelength ();
		int dim = graph.getdim();
		
		for (int i = 0; i < dim; i++) {
			for (int j=i+1; j < dim; j++) {  //só vemos a matriz triangular superior - diagonal não tem significado=0
				
				double infomutua = 0;
				int [] var = {i,j};
				int [] vari = {i};
				int [] varj= {j};
				
				for (int xi=0; xi < d.getDomain(i); xi++) { 
					for (int xj=0; xj < d.getDomain(j); xj++) { 
						int [] val = {xi,xj};
						int [] vali = {xi};
						int [] valj= {xj};
						
						double count2 = d.Count(var, val);
						if (count2 == 0) {     
								infomutua += 0;  // no caso 0 * log(0) = 0
							}
						else {
							infomutua += (count2 /m)*Math.log((count2 *m)/(d.Count(vari, vali)*d.Count(varj, valj)));
						}
					}
				}
				graph.Add(i, j, infomutua);	  //pesar a aresta entre i e j 
			}
		}
		return graph.MST(); //MST feita a partir deste grafo
	}
	
}
