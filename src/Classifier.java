
public class Classifier {

	private MRFT [] arrayMRFTs;
	private double [] frequencias;
	//confirmar tipo de dados de array MRFT
	//array de MRFTs, um para cada valor da classe
	// array com as frequencias das classes
	
	//CONSTRUTOR
	
	public Classifier(MRFT [] MRFTs, double[] valores) {
		super();
		this.arrayMRFTs = MRFTs;
		this.frequencias = valores;
	}
	
	//CLASSIFY
	
	public int Classify (int [] valoresx) {
		// dados valores (x1,...,xn) das variáveis 
		return; //valor da classe mais provável
	}
	
	
	//calcular frequencia onde? DataSet
}
