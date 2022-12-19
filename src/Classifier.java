
public class Classifier {

	private MRFT [] arrayMRFTs;
	private double [] frequencias;
	//confirmar tipo de dados de array MRFT
	//array de MRFTs, um para cada valor da classe
	// array com as frequencias das classes -> Pr (C=c)
	
	//CONSTRUTOR
	
	public Classifier(MRFT [] MRFTs, double[] valores) {
		super();
		this.arrayMRFTs = MRFTs;
		this.frequencias = valores;
		
		// calcular frequências com base no dataset!
	}
	
	//CLASSIFY
	
	public int Classify (int [] valoresx) { 
		// dados valores (x1,...,xn) das variáveis
		
		// array com classe e probabilidade correspondente
		return; //valor da classe mais provável
	
	}
}
