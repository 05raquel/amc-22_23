import java.lang.*;

public class ChowLiu {

	// Algoritmo Chow Liu
	
	public static boolean [][] Chow_liu (DataSet d){
		WeightedGraph graph = new WeightedGraph (d.getDataListArraySize() -1);
		// pois não queremos a classificação
		
		double m = d.data().size();
		for (int i =0; i< graph.getdim(); i++) {
			for (int j=0; j< graph.getdim(); j++) {
				
				double infomutua = 0;
				int [] var = {i,j};
				int [] vari = {i};
				int [] varj= {j};
				for (int xi=0; xi < d.getDomains()[i]+1; xi++) {
					for (int xj=0; xj < d.getDomains()[j]+1; xj++) {
						int [] val = {xi,xj};
						int [] vali = {xi};
						int [] valj= {xj};
						infomutua += (d.Count(var, val)/m)*java.lang.Math.log((d.Count(var, val)*m)/(d.Count(vari, vali)*d.Count(varj, valj)));
					}
				}
				
				graph.Add(i, j, infomutua);
			}
		}
		return graph.MST();
	}
	
}
