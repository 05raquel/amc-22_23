import java.lang.*;
import java.util.Arrays;

public class ChowLiu {

	// Algoritmo Chow Liu

	public static boolean [][] Chow_liu (DataSet d){
		WeightedGraph graph = new WeightedGraph (d.getDataListArraySize() -1);
		// pois não queremos a classificação
		//int errado=0;
		System.out.println("Doms: "+Arrays.toString(d.getDomains()));
		
		double m = d.getDataList().size();
		for (int i = 0; i < graph.getdim(); i++) {
			for (int j=i+1; j< graph.getdim(); j++) {
				// não ver j,i correspondente 
				// j e i são sempre diferentes
				
				double infomutua = 0;
				int [] var = {i,j};
				int [] vari = {i};
				int [] varj= {j};
				for (int xi=0; xi <= d.getDomains()[i]; xi++) {
					for (int xj=0; xj <= d.getDomains()[j]; xj++) {
						int [] val = {xi,xj};
						int [] vali = {xi};
						int [] valj= {xj};

						if ((d.Count(var, val)/m) != 0 || ((d.Count(var, val)*m)/(d.Count(vari, vali)*d.Count(varj, valj))) !=0) {
						// no caso 0 * log(0) = 0; infomutua continua igual
							infomutua += (d.Count(var, val)/m)
									*java.lang.Math.log((d.Count(var, val)*m)
														/(d.Count(vari, vali)*d.Count(varj, valj)));
						}
					}
				}
				
				graph.Add(i, j, infomutua);
				
			}
		}
		
		System.out.println("Grafo:\n"+graph.printBonito2());
		System.out.println(d.getDomains()[1]);
		return graph.MST();
	}
	
}
