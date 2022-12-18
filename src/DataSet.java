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
		
		//atributo; array list de inteiros
		//uma espécie de uma matriz
		
		DataSet(String csvFile)  {
			this.dataList = new ArrayList<int []>();
			String line;
			BufferedReader br;
			
				try {
					br = new BufferedReader(new FileReader(csvFile));
					while((line = br.readLine()) != null) {
						dataList.add(convert(line));
					}
						br.close();
						
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					  
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
		
		public static int [] convert (String line) {
		String cvsSplitBy = ",";
		String[] strings     = line.split(cvsSplitBy);
		int[] stringToIntVec = new int[strings.length];
		for (int i = 0; i < strings.length; i++)
			stringToIntVec[i] = Integer.parseInt(strings[i]);
		return stringToIntVec;
		}
		
		public ArrayList<int []> data() {
			return dataList;
		}
		
		//COUNT
		
		public int Count (int var[], int val[]) { //acho que este tipo de dados está bom mas confirmar em conjunto
			// var e val têm o mesmo tamanho!!!
			int c = 0; // contador
			int arr[] = new int [var.length]; // cria novo array com o tamanho do número de variáveis a procurar
			
			for (int [] vetorObs: dataList) {
				//obter valores das vars necessários no vetorObs
				
				for(int j = 0; j < var.length; j++)	{
					arr[j]= vetorObs[var[j]];  // preencher o array com os valores do vetorObs do datalist
				}

				if (Arrays.equals(arr,val)) c++; //contar quantos vetores têm os valores val
				//confirmar que podemos usar equals
			}
			return c;
		}
		
		// var 0 1 2 3
		// e1: 1 2 3 3
		// e2: 0 0 0 0
		// e3: 4 3 2 1
		// e4: 0 1 0 1
		//Count([2,3], [0, 1]) 
		
		
		//pensar como vamos guardar todos os counts que precisamos!
		// vai ser usado como T.count
		
		//ADD
		
		int [] domínios = null;
		public void Add(int v[]) {
			//Calcular logo o domínio das variáveis
			// 
			if (domínios == null) {
				domínios = new int[v.length];
			}
			
			//Adiciona um vetor ao dataset após verificar o seu tamanho (mesmo tamanho que os dados no dataset)
			if (v.length == dataList.get(0).length) dataList.add(v);
		
		}
		//Fazer um add personalizado. Conter:
		//percorrer e calcular logo o máximo dos elementos para cada característica
		//como aceder aos domínios - variável global?
			
		/* public int domain(ArrayList<Integer> position) {
		 		//Recebe uma amostra e um vector de posições e retorna o número de elementos possíveis desse vector de posições.
		 		int domain = 1;
		        for (int p : position) {
		            domain *= domains.get(p);
		        }
		        return domain;
		    }
		*/
			
		// FIBER
		
		// confirmar o input
		
		// NO CLASSIFICADOR - PARTIÇÃO! vamos ter de fazer um método para calcular quantas fibras precisamos de fazer,
		//tendo em conta a contagem de classes
		// criar int [] ou ArrayList<int []> para os valores das classes
		
		public ArrayList<int []> Fiber(int value) { //fibra da característica value
			ArrayList <int []> fiber = new ArrayList<int []>();	
			int Length = dataList.get(0).length;
			
			for (int [] array: dataList) {	
				if (array[Length -1] == value) {	
					fiber.add(array);
				}
			}
			return fiber;	//sai uma coisa feiosa mas útil 
		}
		
		//auxiliar
		public void printBonito (ArrayList<int[]> fiber)	{
			for (int [] i: fiber)	{
				System.out.println(Arrays.toString(i));	
			}
		}
	
		public static void main(String[] args) {
				DataSet d = new DataSet("bcancer.csv");
				int v [] = {1,2,3,4,5,6};
				d.Add(v);
				System.out.println(d);
				d.printBonito(d.Fiber(1));
				int var [] = {10};
				int val [] = {11};
				System.out.println(d.Count(var, val));
		}
		
}
