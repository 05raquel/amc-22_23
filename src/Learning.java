
public class Learning {
	
	public static void main(String[] args) {
		DataSet d = new DataSet("bcancer.csv");
		int [] doms = d.getDomains();
		
		double [] freq = new double [doms[d.getDataListArraySize() -1] + 1];
		System.out.println("a");
		
		// calcular frequências com base no dataset no algoritmo final do classificador!
		
		MRFT [] arrayfibers = new MRFT [doms[d.getDataListArraySize() -1] + 1];
		// doms[coluna das fibras]
		System.out.println("b");
		
		for (int i=0; i <= doms[d.getDataListArraySize() -1]; i++) {
		// max classificação = d.getDomains()[-1]
			//fibers[i]= d.Fiber(i);
			//ChowLiu.Chow_liu(d.Fiber(i));
			System.out.println(i);
			freq[i]= d.Fiber(i).data().size() / d.data().size();
			System.out.println("d");
			
			arrayfibers[i] = new MRFT(d.Fiber(i), ChowLiu.Chow_liu(d.Fiber(i)));
			System.out.println("e");
		}
		//arrayfibers - guardar no disco;
		
		Classifier classificar = new Classifier (arrayfibers, freq);
		int [] valoresx = {0,1,1,0,1,1,0,1,1,0};
		classificar.Classify (valoresx);
		System.out.println("a");
		System.out.println(classificar.Classify (valoresx));
	}
		
}


