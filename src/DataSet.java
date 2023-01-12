import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DataSet implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<int[]> dataList;
    private int[] domains;
    private int arraysize;
    double [][][][] matrixc;

    //atributo array list de inteiros - lista de vetores dinâmica

    public DataSet() {
        this.dataList = new ArrayList<int[]>();
        this.domains = null;
        this.arraysize=0;
    }

    /** Construtor de DataSet a partir de um ficheiro csv*/
    public DataSet(String csvFile) {
        // inicializar os atributos
        this.dataList = new ArrayList<int[]>();
        this.domains = null;
        this.arraysize = 0;

        String line;
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
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
        
        // não fazer matrixcount para o dataset todo mas só para cada fibra!
    }

    @Override
    public String toString() {
        String s = "[";
        if (dataList.size() > 0) s += Arrays.toString(dataList.get(0));
        for (int i = 1; i < dataList.size(); i++)
            s += "," + Arrays.toString(dataList.get(i));
        s += "]";
        return "Size=" + dataList.size() + " Dataset = " + s;
    }

    /** Converte uma linha String do csv num array de inteiros*/
    public static int[] convert(String line) {
        String cvsSplitBy = ",";
        String[] strings = line.split(cvsSplitBy);
        int[] stringToIntVec = new int[strings.length];
        for (int i = 0; i < strings.length; i++)
            stringToIntVec[i] = Integer.parseInt(strings[i]);
        return stringToIntVec;
    }

    /** Acede ao dataList do Dataset considerado*/
    public ArrayList<int[]> getDataList() { 
    	return dataList; 
    }

    /** Retorna o tamanho do Dataset (número de vetores)*/
    public int Samplelength () {
    	return dataList.size();  
    }
    
    /** Retorna o conjunto dos domínios das variáveis do Dataset */
    public int[] getDomains() {
        return domains;
    }
    
    /** Retorna o domínio da variável i */
    public int getDomain(int i) {
        return domains[i];
    }

    /**obtém o tamanho dos vetores no DataSet - número de características + classificação */
    public int getDataListArraySize() {
        return arraysize; 
    }
       //ainda inclui a última coluna com as classes. só retiramos essa coluna na altura de formar os grafos das fibras.
       //Aqui ainda dá jeito para identificar com -1 as diferentes classes

    /** Retorna o número de variáveis (características) do Dataset considerado*/
    public int NrVariables() {
    	return getDataListArraySize() -1;
    }

    public int getClassDomain() {
    	return domains[arraysize-1];
    }
    //COUNT

    /** Count ((i,j),(xi,xj)) -
     * Conta o número de vezes no dataset que as variáveis i e j tomam simultaneamente os valores (xi,xj)
     */
    public double Count(int var[], int val[]) {
        if (var.length != val.length) {
            throw new IllegalArgumentException(); //argumentos inválidos
            // var e val têm o mesmo tamanho!
        }
        //guardar os counts
		int compara = var.length;
			
		if (compara==1){
        	double valor1 = matrixc[var[0]][var[0]][0][val[0]];
        	if (valor1 >= 0) return valor1;
		}
        
		else if (compara==2) {
        	double valor2 = matrixc[var[0]][var[1]][val[0]][val[1]];
        	if (valor2 >= 0) return valor2;
		}
        
        double c = 0; // contador
        int arr[] = new int[var.length]; // cria novo array com o tamanho do número de variáveis a procurar
        for (int[] vetorObs : dataList) {
            //obter valores das variáveis vars necessários no vetorObs
            for (int j = 0; j < var.length; j++) {
                arr[j] = vetorObs[var[j]];  // preencher o array com os valores do vetorObs do datalist
            }
            if (Arrays.equals(arr, val)) c++; //contar quantos vetores têm os valores val
        }
        
		if (compara==1) matrixc[var[0]][var[0]][0][val[0]]=c;
		else if (compara==2) {
				matrixc[var[0]][var[1]][val[0]][val[1]]=c;
				matrixc[var[1]][var[0]][val[1]][val[0]]=c;
		}
		return c;
    }

    //ADD
    /** Adiciona um vetor ao DataSet, se este tiver o tamanho correto*/
    public void Add(int v[]) {
        if (!dataList.isEmpty() && v.length != dataList.get(0).length) { //Validar argumento
            throw new IllegalArgumentException();
        }
        //Adiciona um vetor ao dataset após verificar o seu tamanho (mesmo tamanho que os dados no dataset)
        dataList.add(v);

        //Calcular logo o domínio das variáveis

        // se for o primeiro vetor:
        if (domains == null) {
        	int size = v.length;
            domains = new int[size];
            arraysize = size;
        }
        
        //atualizar maximos dos elementos para cada característica
        for (int i = 0; i < v.length; i++) {
            if (v[i] >= domains[i]) { //alterei
                domains[i] = v[i]+1; //alterei // +1 para incluir o 0! - Domínio = nr de valores distintos tomados
            }
        }
    }

    // FIBER

    // criar int [] ou ArrayList<int []> para os valores das classes

    /** Retorna a fibra da característica value */
    public DataSet Fiber(int value) {
        //ArrayList <int []> fiber = new ArrayList<int []>();
        DataSet fiber = new DataSet();
        int Length = arraysize;

        for (int[] array : dataList) {
            if (array[Length - 1] == value) {
                fiber.Add(array); 
            }
        }
        fiber.matrixc = iniciacount(arraysize-1, arraysize-1);
        return fiber;
    }

    public DataSet [] Fibers() {
    	DataSet [] fib = new DataSet [domains[arraysize-1]];  //getClassDomain()
    	for (int i=0; i < fib.length; i++) {
    		fib[i]= new DataSet();
    		fib[i].matrixc = iniciacount(arraysize-1, arraysize-1); //NrVariables(
    	}
    	
    	for (int [] v: dataList) {
    		fib[v[arraysize-1]].Add(v);
    	}
    	return fib;
    }
       
    /**
     * inicia a MatrixCount com as dimensões corretas de acordo com os domínios das características
     */
    public double[][][][] iniciacount(int ni, int nj) {
        double[][][][] ma = new double[ni][nj][][];
        for (int itni = 0; itni < ni; itni++) { // itni = iterada de ni
            for (int itnj = itni; itnj < nj; itnj++) { //itnj = iterada de nj
            	//itnj=0
                if (itni == itnj) {
                	 // para cada matriz interior, define-se o seu tamanho - domínio de itni e itnj
                    ma[itni][itnj] = new double[1][domains[itnj]]; //alt
                    for (int it=0; it < domains[itnj]; it++) { //alt
                    	ma [itni][itnj][0][it]=-1;
                    }
                } else {
                    ma[itni][itnj] = new double[domains[itni]][domains[itnj]]; //alt
                    ma[itnj][itni] = new double[domains[itnj]][domains[itni]]; //alt
                    for (int i=0; i < domains[itni]; i++) { //alt
                    	for (int j=0; j < domains[itnj]; j++) { //alt
                    		ma [itni] [itnj] [i] [j]=-1;
                    		ma [itnj] [itni] [j] [i]=-1;
                    	}
                    }
                }
            }
        }
        return ma;
    }
    
    
}
