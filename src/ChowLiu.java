import java.lang.*;

public class ChowLiu {

	// Algoritmo Chow Liu
	
	public boolean [][] ChowLiu (DataSet d){
		WeightedGraph graph = new WeightedGraph (d.getDataListArraySize() -1);
		// pois não queremos a classificação
		
		
		for (int i =0; i< graph.getdim(); i++) {
			for (int j=0; j< graph.getdim(); j++) {
				
				double infomutua = 0;
				
				for (int xi=0; xi < d.getDomains()[i]+1; xi++) {
					for (int xj=0; xj < d.getDomains()[j]+1; xj++) {
						
						infomutua+= (d.Count(null, null)) ;
					}
				}
				
				graph.Add(i, j, infomutua);
			}
		}
		
		
		
		
		return MST(graph);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
