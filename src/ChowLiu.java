import java.util.Arrays;

public class ChowLiu {

	// Algoritmo Chow Liu

	public static boolean [][] Chow_liu (DataSet d){
		WeightedGraph graph = new WeightedGraph (d.getDataListArraySize() -1);
		//pois não queremos a classificação
		
		System.out.println("Doms: "+Arrays.toString(d.getDomains()));
		
		double m = d.getDataList().size();
		for (int i = 0; i < graph.getdim(); i++) {
			
			for (int j=i+1; j < graph.getdim(); j++) {
				// não ver j,i correspondente 
				// j e i são sempre diferentes - a diagonal não tem significado
				
				double infomutua = 0;
				int [] var = {i,j};
				int [] vari = {i};
				int [] varj= {j};
				for (int xi=0; xi < d.getDomain(i); xi++) { //alt
					for (int xj=0; xj < d.getDomain(j); xj++) { //alt
						int [] val = {xi,xj};
						int [] vali = {xi};
						int [] valj= {xj};
						
						double count2 = d.Count(var, val);
						if (count2 == 0) {
							// no caso 0 * log(0) = 0; infomutua continua igual
								infomutua += 0;
							}
						else {
							infomutua += (count2 /m)*java.lang.Math.log((count2 *m)/(d.Count(vari, vali)*d.Count(varj, valj)));
						}
					}
				}
				//pesar a aresta entre i e j 
				graph.Add(i, j, infomutua);
				
			}
		}
		System.out.println("Grafo:\n"+graph.printBonito2());
		return graph.MST();
	}
	
}
