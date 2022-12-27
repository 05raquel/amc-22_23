import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Learning {
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		
		DataSet d = new DataSet("testegrande.csv");
		int [] doms = d.getDomains();
		
		int domClasses = doms[d.getDataListArraySize() -1];
		double [] freq = new double [domClasses + 1]; // domClasses +1 - número de valores diferentes
		System.out.println("a");
		
		// calcular frequências com base no dataset no algoritmo final do classificador!
		
		MRFT [] arrayfibers = new MRFT [domClasses + 1];
		// doms[coluna das fibras]
		System.out.println("b");
		
		for (int i=0; i <= domClasses; i++) {
		// do 0 até ao max(domClasses) inclusive
		// max classificação = d.getDomains()[-1]
			//fibers[i]= d.Fiber(i);
			//ChowLiu.Chow_liu(d.Fiber(i));
			System.out.println("Classe:"+i);
			freq[i]= (double) d.Fiber(i).data().size() / (double) d.data().size();
			// numero de elementos do fiber / numero de elementos do dataset
			System.out.println("d");
			
			//System.out.println(d.Count(new int[]{0, 1}, new int[] {1,0}));
			
			arrayfibers[i] = new MRFT(d.Fiber(i), ChowLiu.Chow_liu(d.Fiber(i)));
			System.out.println("e");
		}
		
		//arrayfibers - guardar no disco;
		
		Classifier classificador = new Classifier(arrayfibers, freq);
		System.out.println(classificador);
		
		try { //escrever objetos no ficheiro
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("classifier.ser"));
			os.writeObject(classificador);
			os.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		APLICAÇÃO 2
//		try {
//			ObjectInputStream oi = new ObjectInputStream (new FileInputStream("classifier.ser"));
//			Classifier nd = (Classifier) oi.readObject(); //ambos são Classifier
//			oi.close();
//			System.out.println(nd);
		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("freq = "+Arrays.toString(freq));
		
		Classifier classificar = new Classifier (arrayfibers, freq);
		//int [] valoresx = {0,0,0,0,0,0,0,0,0,0};
		//int [] valoresx = {1,0,2,3,2,1,1,0,1,1}; // classe 1
		//int [] valoresx = {1,0,2,3,2,1,1,2,1,1}; // classe 1
		//int [] valoresx = {1,0,2,3,2,0,1,2,1,1};
		//int [] valoresx = {1,0,0,2,2,1,1,2,1,1};
		int [] valoresx = {1,0,2,1,0,0,0,2,0,0};
		//1,0,2,1,0,0,0,2,0,0,1 - teste do1
		//0,0,1,0,0,1,1,0,0,0,0
		//1,0,0,2,2,1,1,2,1,1,0 - este
		//classificar.Classify (valoresx);
//		System.out.println("a");
		System.out.println(classificar.Classify (valoresx));
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
		
}


