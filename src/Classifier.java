import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Classifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private MRFT [] arrayMRFTs;
	private double [] frequencias;
	//array de MRFTs, um para cada valor da classe
	// array com as frequencias das classes -> Pr (C=c)
	
	//CONSTRUTOR
	
	public Classifier(MRFT [] MRFTs, double[] valores) {
		super();
		this.arrayMRFTs = MRFTs;
		this.frequencias = valores; 
		
		//frequências calculadas no algoritmo final do classificador com base no dataset
	}
	 
	public MRFT[] getMRFTs () {
		return arrayMRFTs;
	}
	
	public double[] getfreqs() {
		return frequencias;
	}
	
	//CLASSIFY
	
	//TODO usar as duas funções
	public double [] Classify_parcial (int [] valoresx) {
		if (valoresx.length!= arrayMRFTs[0].getsizeMRFTree()) { //vetor valoresx com o número de características correto
			throw new IllegalArgumentException(); //argumento inválido
		}
		
		// dados valores (x1,...,xn) das variáveis
		// P = frequência x P MC (vetor)
		
		double [] probstotal = new double [frequencias.length];
		for (int i=0; i < frequencias.length; i++) {
			probstotal[i] = frequencias[i]*arrayMRFTs[i].Prob(valoresx); //i é a classe
			System.out.println("i:" + i);
		}
		System.out.println("Probs das classes"+Arrays.toString(probstotal));
		return probstotal;  //valor da classe mais provável
	}
	
	public int Classifybest (double [] probstotal) {
		// dados valores (x1,...,xn) das variáveis
		// P = frequência x P MC (vetor)
		double [] maxprob = {-1,-1}; //{classe,valor de prob)
		for (int i=0; i < probstotal.length; i++) {
			if (probstotal[i]> maxprob[1]) {
				maxprob[0]=i;
				maxprob[1]=probstotal[i];
			}
		}
		return (int)maxprob[0];  //valor da classe mais provável
	}
	
	
	/** A função retorna um objeto com a classe mais provável e um array com a probabilidade de cada classe */
	public Res Classify (int [] valoresx) {
		if (valoresx.length!= arrayMRFTs[0].getsizeMRFTree()) { //vetor valoresx com o número de características correto
			throw new IllegalArgumentException(); //argumento inválido
		}
		
		// dados valores (x1,...,xn) das variáveis
		// P = frequência x P MC (vetor)
		
		double [] probstotal = new double [frequencias.length];
		double [] maxprob = {-1,-1}; //{classe,valor de prob)
		for (int i=0; i < frequencias.length; i++) {
			probstotal[i] = frequencias[i]*arrayMRFTs[i].Prob(valoresx); //i é a classe
			System.out.println("i:" + i);
			if (probstotal[i]> maxprob[1]) {
				maxprob[0]=i;
				maxprob[1]=probstotal[i];
			}
		}
		System.out.println("Probs das classes"+Arrays.toString(probstotal));
		return new Res((int)maxprob[0], probstotal);  //valor da classe mais provável
	}
	
	public static final class Res {
		final double [] probstotal;
		final int bestClass;
		public Res(int bestClass, double[] probstotal) {
			this.probstotal = probstotal;
			this.bestClass = bestClass;
		}
		public double[] getProbstotal() {
			return probstotal;
		}
		public int getBestClass() {
			return bestClass;
		}
	}
	
	
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
			for (int i=0; i < prob.length; i++) {
				soma+= prob[i];
			}
		double [] probfinal = new double [prob.length];
			for (int i=0; i<prob.length; i++) {
				probfinal[i]= prob[i]/soma;
			}
		return probfinal;
	}
//   imprimir probfinal - label
//   Imprimir maxprob[0] - resposta final - label
}
