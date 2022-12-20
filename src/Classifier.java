
public class Classifier {

	private MRFT [] arrayMRFTs;
	private double [] frequencias;
	//array de MRFTs, um para cada valor da classe
	// array com as frequencias das classes -> Pr (C=c)
	
	//CONSTRUTOR
	
	public Classifier(MRFT [] MRFTs, double[] valores) {
		super();
		this.arrayMRFTs = MRFTs;
		this.frequencias = valores;
		
		// calcular frequências com base no dataset no algoritmo final do classificador!
	}
	
	//CLASSIFY
	
	public int Classify (int [] valoresx) { 
		// dados valores (x1,...,xn) das variáveis
		// P = frequência x P MC (vetor)
		double [] probstotal = new double [frequencias.length];
		double [] maxprob = {-1,-1}; //{classe,valor de prob)
		for (int i=0; i < frequencias.length; i++) {
			probstotal[i] = frequencias[i]*arrayMRFTs[i].Prob(valoresx); //i é a classe
			if (probstotal[i]> maxprob[1]) {
				maxprob[0]=i;
				maxprob[1]=probstotal[i];
			}
		}
		return (int)maxprob[0];  //valor da classe mais provável
		
	
	}
}
