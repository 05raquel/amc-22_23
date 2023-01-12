
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Leave_one_out {

   public static void main (String[] args) {
	   String Samples = "src/datasets/diabetes.csv";
	   
	   long startTime = System.nanoTime();

	   DataSet sample = new DataSet(Samples);
	   ArrayList<int[]> dataset = sample.getDataList();
	   DataSet trainingset = new DataSet(Samples);
	   
//       // Initialize a list to store the predictions
//       List<Integer> predictions = new ArrayList<>();
       
       ArrayList<Integer> classificações = new ArrayList<>();
       int certos = 0;
       int aaaa = dataset.size();
       ArrayList<Integer> tamanho = new ArrayList<>();
       
       // Iterate over the samples
       for (int i = 0; i < dataset.size(); i++) {
    	   System.out.println("\n\n############## "+i+" ############## \n\n");
    	   tamanho.add(dataset.size());
           // Create a training set by removing i-t sample
           int [] test = sample.getDataList().get(i);
           trainingset.getDataList().remove(i);
           
 
	       int [] doms = trainingset.getDomains(); 
	       int domClasses = doms[trainingset.getDataListArraySize() -1]; 
	       double [] freq = new double [domClasses]; //alt
	       MRFT [] arrayfibers = new MRFT [domClasses]; //alt
	       
	       DataSet [] fib = trainingset.Fibers();
	       for (int j=0; j < domClasses; j++) { //alt
	           DataSet fiber = fib [j];
	           freq[j]= (double) fiber.getDataList().size() / (double) trainingset.getDataList().size();
	           arrayfibers[j] = new MRFT(fiber, ChowLiu.Chow_liu(fiber), doms);
	       }
	
	       Classifier classificador = new Classifier(arrayfibers, freq,trainingset.NrVariables());
       
	       int [] testfinal = new int[test.length-1];
	       
	       for (int a=0; a<test.length-1; a++) {
	    	  testfinal[a] = test[a];
	       }
	       int classe = classificador.Classify(testfinal).getBestClass();
	       classificações.add(classe);
	       if (classe == test[test.length-1]) certos ++;
	       trainingset.getDataList().add(i, test);
       }
       System.out.println("certos: "+certos);
       System.out.println("certos%: "+((double)certos/(double)aaaa)*100);
       long endTime   = System.nanoTime();
	   long totalTime = endTime - startTime;
	   System.out.println(totalTime);

//       System.out.println("classificações: "+classificações);
//       System.out.println(classificações.size());
       System.out.println(sample.getDataList().size());
//       System.out.println(toString(dataset));
//       System.out.println(toString2(tamanho));
   	}

 public static String toString(ArrayList<int[]> dataList) {
        String s = "[";
        if (dataList.size() > 0) s += Arrays.toString(dataList.get(0));
        for (int i = 1; i < dataList.size(); i++)
            s += "," + Arrays.toString(dataList.get(i));
        s += "]";
        return "Size=" + dataList.size() + " Dataset = " + s;
    }
 
 public static String toString2(ArrayList<Integer> dataList) {
     String s = "[";
     if (dataList.size() > 0) s += (dataList.get(0));
     for (int i = 1; i < dataList.size(); i++)
         s += "," + (dataList.get(i));
     s += "]";
     return "Size=" + dataList.size() + " Dataset = " + s;
 }
}


