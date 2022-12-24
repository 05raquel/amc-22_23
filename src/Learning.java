
public class Learning {
	
	public static void main(String[] args) {
		DataSet d = new DataSet("bcancer.csv");
		int [] doms = d.getDomains();
		
		int domClasses = doms[d.getDataListArraySize() -1];
		double [] freq = new double [domClasses + 1];
		System.out.println("a");
		
		// calcular frequências com base no dataset no algoritmo final do classificador!
		
		MRFT [] arrayfibers = new MRFT [domClasses + 1];
		// doms[coluna das fibras]
		System.out.println("b");
		
		//for (int i=0; i <= domClasses; i++) {
		// max classificação = d.getDomains()[-1]
			//fibers[i]= d.Fiber(i);
			//ChowLiu.Chow_liu(d.Fiber(i));
			System.out.println(1);
			freq[1]= (double) d.Fiber(1).data().size() / (double) d.data().size();
			System.out.println("d");
			
			arrayfibers[1] = new MRFT(d.Fiber(1), ChowLiu.Chow_liu(d.Fiber(1)));
			System.out.println("e");
		//}
		//arrayfibers - guardar no disco;
		
//		Classifier classificar = new Classifier (arrayfibers, freq);
//		int [] valoresx = {0,1,1,0,1,1,0,1,1,0};
//		classificar.Classify (valoresx);
//		System.out.println("a");
//		System.out.println(classificar.Classify (valoresx));
	}
		
}


