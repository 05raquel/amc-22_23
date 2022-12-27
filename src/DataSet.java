import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DataSet implements Serializable {
	    private static final long serialVersionUID = 1L;
	    
		private ArrayList<int []> dataList;
		private int [] domains;
		double [][][][] matrixc;
		
		//atributo; array list de inteiros - uma espécie de matriz
		
		DataSet(){
			this.dataList = new ArrayList<int []>();
			this.domains = null;
		}
		
		
		//construtor a partir de um ficheiro csv
		DataSet(String csvFile)  {
			// inicializar os atributos
			this.dataList = new ArrayList<int []>();
			this.domains = null; 
			
			String line;
			BufferedReader br;
			
				try {
					br = new BufferedReader(new FileReader(csvFile));
					while((line = br.readLine()) != null) {
						this.Add(convert(line));
					}
						br.close();
						
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			matrixc = iniciacount(domains.length-1, domains.length-1);
		}
		
		

		@Override
		public String toString() {
			String s="[";
			if (dataList.size()>0) s+=Arrays.toString(dataList.get(0));
			for (int i=1; i<dataList.size();i++)
				s+=","+Arrays.toString(dataList.get(i));
			s+="]";
			return "Size=" + dataList.size() + " Dataset = " + s;
		}
		
		// converter uma linha string num [] de inteiros
		public static int [] convert (String line) {
		String cvsSplitBy = ",";
		String[] strings     = line.split(cvsSplitBy);
		int[] stringToIntVec = new int[strings.length];
		for (int i = 0; i < strings.length; i++)
			stringToIntVec[i] = Integer.parseInt(strings[i]);
		return stringToIntVec;
		}
		
		//aceder ao dataList do dataset considerado
		public ArrayList<int []> data() {
			return dataList;
		}
		
		//aceder aos domínios
		public int[] getDomains() {
			return domains;
		}
		
		//obter o tamanho dos vetores no dataSet - número de característica + classificação
		public int getDataListArraySize () {
			return domains.length;
			//dataList.get(0).length;  //ainda inclui a última coluna com as classes. só retiramos essa coluna na altura de formar os grafos das fibras.
		}                              //Aqui ainda dá jeito para identificar com -1 as diferentes classes  
		
		
		//COUNT
		// conta o número de vezes no dataset que as variáveis i e j tomam simultaneamente os valores (xi,xj) 
		//ex: count ((i,j),(xi,xj))
		
		//double [][][][] matrixc = iniciacount(domains.length-1, domains.length-1);
		public double Count (int var[], int val[]) { 
			if (var.length!=val.length) {
				throw new IllegalArgumentException(); //argumentos inválidos
				// var e val têm o mesmo tamanho!
			}
			
			//guardar os counts
			int compara = var.length;
								
			if (compara==1 && matrixc[var[0]][var[0]][val[0]][val[0]]!=0) {
				return matrixc[var[0]][var[0]][val[0]][val[0]];
			}
			else if (compara==2 && matrixc[var[0]][var[1]][val[0]][val[1]] != 0) {
				return matrixc[var[0]][var[1]][val[0]][val[1]];
			}
		
			int c = 0; // contador
			int arr[] = new int [var.length]; // cria novo array com o tamanho do número de variáveis a procurar
			
			for (int [] vetorObs: dataList) {
				//obter valores das variáveis vars necessários no vetorObs
				
				for(int j = 0; j < var.length; j++)	{
					arr[j]= vetorObs[var[j]];  // preencher o array com os valores do vetorObs do datalist
				}

				if (Arrays.equals(arr,val)) 
					c++; //contar quantos vetores têm os valores val
			}
	
			if (compara==1) matrixc[var[0]][var[0]][val[0]][val[0]]=c;
			else if (compara==2) {
				matrixc[var[0]][var[1]][val[0]][val[1]]=c;
				matrixc[var[1]][var[0]][val[1]][val[0]]=c;
			}
			return (double)c;
		}
		
		/* Função equals - verifica se os arrays são iguais
		public boolean equalQ (int a [], int b[]) {
		boolean br=a.length==b.length;
		for (int i = 0; i< b.length && br; i++) {
			if (a[i]!=b[i]) br = false;
		}
		return br;
	}
		*/
		
		
		// var 0 1 2 3
		// e1: 1 2 3 3
		// e2: 0 0 0 0
		// e3: 4 3 2 1
		// e4: 0 1 0 1
		//Count([2,3], [0, 1]) 
		
		//pensar como vamos guardar todos os counts que precisamos!
		// vai ser usado como T.count para futuras operações
		
		
		//ADD
		
		public void Add(int v[]) {
			if (!dataList.isEmpty() && v.length != dataList.get(0).length) { //Validar argumento
				throw new IllegalArgumentException();
			}
			//Adiciona um vetor ao dataset após verificar o seu tamanho (mesmo tamanho que os dados no dataset)
			dataList.add(v);
			
			//Calcular logo o domínio das variáveis
			
			// se for o primeiro vetor:
			if (domains == null) {
				domains = new int[v.length];
			}
			//atualizar maximos dos elementos para cada característica 
			for (int i=0; i<v.length; i++) {
				if (v[i]>domains[i]) {
					domains[i] = v[i];
				}
			}
			matrixc = iniciacount(domains.length-1, domains.length-1);
		}
			
		// FIBER
		
		// NO CLASSIFICADOR - PARTIÇÃO! vamos ter de fazer um método para calcular quantas fibras precisamos de fazer,
		//tendo em conta a contagem de classes
		// criar int [] ou ArrayList<int []> para os valores das classes
		
		//retorna a fibra da característica value
		public DataSet  Fiber(int value) { 
			//ArrayList <int []> fiber = new ArrayList<int []>();
			DataSet fiber = new DataSet();
			int Length = dataList.get(0).length;
			
			for (int [] array: dataList) {	
				if (array[Length -1] == value) {	
					fiber.Add(array);
				}
			}
			return fiber; 
		}

		/** inicia a potentialMatrix com as dimensões corretas de acordo com os domínios das características */
		public double [][][][] iniciacount (int ni, int nj){
			double [][][][] ma = new double [ni][nj][][]; 
			for (int itni = 0; itni<ni; itni++) { // itni = iterada de ni
				for (int itnj =0; itnj<nj; itnj++) { //itnj = iterada de nj
//					if (itni==itnj) {
//						ma [itni][itnj]= new double [1] [domains[itnj]+1];
//					}
//					else {
						ma [itni][itnj] = new double [domains[itni]+1] [domains[itnj]+1];
						// para cada matriz interior, define-se o seu tamanho - domínio de itni e itnj
//					}
				}
			}
			return ma;
		}
		
		//auxiliar
		/*public void printBonito (ArrayList<int[]> fiber)	{
			for (int [] i: fiber)	{
				System.out.println(Arrays.toString(i));	
			}
		}*/
	
		/*public static void main(String[] args) {
				DataSet d = new DataSet("bcancer.csv");
				//int v [] = {0,2,3,4,5,6,7,8,9,10,11};
				//d.Add(v);
				System.out.println(Arrays.toString(d.getDomains()));
				//System.out.println(d);
				//System.out.println(d.Fiber(0));
				//System.out.println(d.Fiber(1));
				//System.out.println(d.Fiber(11));
				//d.printBonito(d.Fiber(0));
				
				//int var [] = {10};
				//int val [] = {11};
				//System.out.println(d.Count(var, val));
		}
		*/
}
