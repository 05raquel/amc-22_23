import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Classifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private MRFT [] arrayMRFTs;      // array de MRFTs, um para cada valor da classe
	private double [] frequencias;   // array com as frequencias das classes -> Pr (C=c)
	private int nvar;
	
	//CONSTRUTOR
	public Classifier(MRFT [] MRFTs, double[] valores, int nvar) {
		super();
		this.arrayMRFTs = MRFTs;
		this.frequencias = valores; 
		this.nvar = nvar;
	}
	
	
	/** Retorna o conjunto das frequências*/
	public double[] getfreqs() {
		return frequencias;
	}
	
	/** Retorna o número de classes*/
	public int getNrClasses() {
		return frequencias.length;
	}
	
	/** Retorna o número de características*/
	public int getNrVariables() {
		return  nvar;
	}
	
	//CLASSIFY
		
	/** Retorna a classe mais provável e um array com a probabilidade de cada classe */
	public Res Classify (int [] valoresx) {   
		if (valoresx.length!= nvar) {             //vetor com dim invalidas
			throw new IllegalArgumentException(); 
		}
		
		// dados valores (x1,...,xn) das variáveis
		
		
		double [] probstotal = new double [frequencias.length];
		int bestclass = -1;
		double maxprob = -1;
		
		for (int i=0; i < frequencias.length; i++) {
			probstotal[i] = frequencias[i]*arrayMRFTs[i].Prob(valoresx); // P[i] = frequência[i] x P Mi (vetor)
			System.out.println("classe:" + i);                           // Classe = i
			if (probstotal[i]> maxprob) {
				bestclass=i;
				maxprob=probstotal[i];
			}	
		}
		//System.out.println("Probs das classes"+Arrays.toString(probstotal));
		return new Res(bestclass, probstotal);  //valor da classe mais provável
	}
	
	/**Classe Resultados -> para retornar 2 tipos de dados no classify*/
	
	public static final class Res {  // final - não pode ser alterado assim que é definido
		final double [] probstotal;
		final int bestClass;
		
		public Res(int bestClass, double[] probstotal) {
			this.probstotal = probstotal;
			this.bestClass = bestClass;
		}
		/**Retorna o conjunto das probabilidades das classes*/
		public double[] getProbstotal() {
			return probstotal;
		}
				
		/** Retorna a classe mais provável*/
		public int getBestClass() {
			return bestClass;
		}
	}
	
	/** Abrir o ficheiro clf e devolver o objeto classifier correspondente*/
	public static Classifier openclf(String filename) throws IOException, ClassNotFoundException {
		FileInputStream f = new FileInputStream(filename);
		ObjectInputStream o = new ObjectInputStream(f);
		Classifier classifier =  (Classifier) o.readObject();
		o.close();
		f.close();
		return classifier;
	}
	
	/** Recebe o vetor de probabilidades das classes e retorna-o normalizado*/
	public static double[] NormProb(double [] prob) {
		double soma=0;
		for (int i=0; i < prob.length; i++) { //prob.length = nr de classes
			soma+= prob[i];
		}
		double [] probfinal = new double [prob.length];
		for (int i=0; i < prob.length; i++) {
			probfinal[i] = prob[i]/soma;
		}
		return probfinal;
	}
}
