import java.util.Arrays;

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
		
		for (int i=0; i <= domClasses; i++) {
		// max classificação = d.getDomains()[-1]
			//fibers[i]= d.Fiber(i);
			//ChowLiu.Chow_liu(d.Fiber(i));
			System.out.println("Classe:"+i);
			freq[i]= (double) d.Fiber(i).data().size() / (double) d.data().size();
			System.out.println("d");
			
			//System.out.println(d.Count(new int[]{0, 1}, new int[] {1,0}));
			
			
			arrayfibers[i] = new MRFT(d.Fiber(i), ChowLiu.Chow_liu(d.Fiber(i)));
			System.out.println("e");
		}
		//arrayfibers - guardar no disco;
		System.out.println("freq = "+Arrays.toString(freq));
		
		Classifier classificar = new Classifier (arrayfibers, freq);
		int [] valoresx = {1,0,2,3,2,0,1,2,1,1};
		//classificar.Classify (valoresx);
//		System.out.println("a");
		System.out.println(classificar.Classify (valoresx));
	
	}
		
}


