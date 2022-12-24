import java.lang.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class ChowLiu {

	// Algoritmo Chow Liu
	
	public static boolean [][] Chow_liu (DataSet d){
		WeightedGraph graph = new WeightedGraph (d.getDataListArraySize() -1);
		// pois não queremos a classificação
		//int errado=0;
		System.out.println("Doms: "+Arrays.toString(d.getDomains()));
		
		double m = d.data().size();
		for (int i =0; i< graph.getdim(); i++) {
			for (int j=0; j< graph.getdim(); j++) {
				
				double infomutua = 0;
				int [] var = {i,j};
				int [] vari = {i};
				int [] varj= {j};
				if (i!=j) { //ainda falta i, j - não ver j,i correspondente
				for (int xi=0; xi < d.getDomains()[i]+1; xi++) {
					for (int xj=0; xj < d.getDomains()[j]+1; xj++) {
						int [] val = {xi,xj};
						int [] vali = {xi};
						int [] valj= {xj};
//						if (Double.isNaN(infomutua)) {
//							errado++;
//						}
						if ((d.Count(var, val)/m) == 0 && ((d.Count(var, val)*m)/(d.Count(vari, vali)*d.Count(varj, valj))) ==0) {
							infomutua += 0;
						}
						else {
							infomutua += (d.Count(var, val)/m)*java.lang.Math.log((d.Count(var, val)*m)/(d.Count(vari, vali)*d.Count(varj, valj)));
						}
						//if (i==1||j==1) {
						//	System.out.println("What's up?");
						//}
					}
				}
				
				graph.Add(i, j, infomutua);
				}
			}
		}
		System.out.println("Grafo:\n"+graph.printBonito2());
		System.out.println(d.getDomains()[1]);
		return graph.MST();
	}
	
}
