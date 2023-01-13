import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DataSet implements Serializable { //permite guardar no disco
    private static final long serialVersionUID = 1L;

    private ArrayList<int[]> dataList;   //lista vetores dinâmica
    private int[] domains;               //array com o nr de valores que existem para cada caracteristica
    private int arraysize;               //nr caracteristicas + classe
    private double [][][][] matrixc;     //matriz dos counts


    /** Construtor de DataSet vazio*/
    public DataSet() {
        this.dataList = new ArrayList<int[]>();  
        this.domains = null;
        this.arraysize=0;
                         //não inicializamos a matrixc para todo o dataset mas sim para cada fibra
    }

    /** Construtor de DataSet a partir de um ficheiro csv*/
    public DataSet(String csvFile) { 
        this.dataList = new ArrayList<int[]>();
        this.domains = null;
        this.arraysize = 0;

        String line;
        BufferedReader br; 

        try {       
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                this.Add(convert(line));  //vai mexer em todos os atributos - add feito por nós
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

    /** Acede ao dataList do Dataset */
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

    /** Obtém o tamanho dos vetores no DataSet - nr de características + classe */
    public int getDataListArraySize() {
        return arraysize; 
    }

    /** Retorna o nr de variáveis (características) do Dataset */
    public int NrVariables() {
    	return arraysize -1;
    }

    /** Retorna o nr de classes*/
    public int getClassDomain() {
    	return domains[arraysize-1];
    }
    
    //COUNT

    /** Conta o número de vezes no dataset que as variáveis i e j tomam simultaneamente os valores (xi,xj)*/
    public double Count(int var[], int val[]) {
        if (var.length != val.length) {
            throw new IllegalArgumentException(); //argumentos inválidos se var e val não têm o mesmo tamanho!
        }

//Quando a matrix já está preenchida nessa entrada, só aceder ao valor
		int compara = var.length;
			
		if (compara==1){                                        //count individual
        	double valor1 = matrixc[var[0]][var[0]][0][val[0]];
        	if (valor1 >= 0) return valor1;                     //só se essa entrada estiver já preenchida (não for -1)
		}
        
		else if (compara==2) {                                     
        	double valor2 = matrixc[var[0]][var[1]][val[0]][val[1]];
        	if (valor2 >= 0) return valor2;
		}
       
//Quando a matrix ainda não está preenchida, preenchemos
        double c = 0;                                          //contador
        int arr[] = new int[var.length];                       //cria novo array com o tamanho do número de variáveis a procurar
        for (int[] vetorObs : dataList) {
            //obter valores das variáveis vars necessários no vetorObs
            for (int j = 0; j < var.length; j++) {
                arr[j] = vetorObs[var[j]];  // preencher o array com os valores do vetorObs do datalist
            }
            if (Arrays.equals(arr, val)) c++; //contar quantos vetores têm os valores val
        }
        
//        if (compara==1) {
//        	for (int [] vetor: dataList) {
//        		if (vetor[var[0]]==val[0]) {
//        			c++;
//        		}
//        	}
//        	matrixc[var[0]][var[0]][0][val[0]]=c;
//        }
//		else if (compara==2) {
//			for (int [] vetor: dataList) {
//				if (vetor[var[0]]==val[0] & vetor[var[1]]==val[1]) {
//					c++;
//				}
//			}
//			matrixc[var[0]][var[1]][val[0]][val[1]]=c;
//			matrixc[var[1]][var[0]][val[1]][val[0]]=c;
//		}
//		return c;
//		
		
		if (compara==1) matrixc[var[0]][var[0]][0][val[0]]=c;
		else if (compara==2) {
				matrixc[var[0]][var[1]][val[0]][val[1]]=c;
				matrixc[var[1]][var[0]][val[1]][val[0]]=c;
		}
		return c;
    }

    //ADD
    /** Adiciona um vetor ao DataSet*/
    public void Add(int v[]) {
        if (!dataList.isEmpty() && v.length != arraysize) { //se não tiver o tamanho certo
            throw new IllegalArgumentException();
        }
        //Adiciona um vetor ao dataset 
        dataList.add(v);
        int size = v.length;
        
        // Atualizar os domains
        if (domains == null) {
            domains = new int[size];
            arraysize = size;
        }
       
        for (int i = 0; i < size; i++) {
            if (v[i] >= domains[i]) {
                domains[i] = v[i]+1;      // +1 para incluir o 0
            }
        }
    }

    // FIBER
    /** Retorna um array de datasets/fibras - tamanho do nr de classses*/
    public DataSet [] Fibers() {
    	DataSet [] fib = new DataSet [domains[arraysize-1]];  
    	for (int i=0; i < fib.length; i++) {
    		fib[i]= new DataSet();
    		fib[i].matrixc = iniciacount(arraysize-1, arraysize-1); //inicializar a matriz de counts com o tamanho do nr de caracteristicas
    	}
    	
    	for (int [] v: dataList) {
    		fib[v[arraysize-1]].Add(v);
    	}
    	return fib;
    }
       
    /** Inicia a MatrixCount com as dimensões corretas de acordo com os domínios das características*/
    public double[][][][] iniciacount(int ni, int nj) {
        double[][][][] ma = new double[ni][nj][][];
        for (int i = 0; i < ni; i++) { 
            for (int j = i; j < nj; j++) {  
     
                // para cada matriz interior, define-se o seu tamanho - domínio de i e j
                if (i == j) {
                    ma[i][j] = new double[1][domains[j]]; 
                    for (int it=0; it < domains[j]; it++) { 
                    	ma [i][j][0][it]=-1;
                    }
                } else {
                    ma[i][j] = new double[domains[i]][domains[j]]; 
                    ma[j][i] = new double[domains[j]][domains[i]]; 
                    for (int a=0; a < domains[i]; a++) { 
                    	for (int b=0; b < domains[j]; b++) { 
                    		ma [i] [j] [a] [b]=-1;
                    		ma [j] [i] [b] [a]=-1;
                    	}
                    }
                }
            }
        }
        return ma;
    }
    
}   
    
    
    
//    
//    /** Retorna a fibra da característica value */
//    public DataSet Fiber(int value) {
//        //ArrayList <int []> fiber = new ArrayList<int []>();
//        DataSet fiber = new DataSet();
//        int Length = arraysize;
//
//        for (int[] array : dataList) {
//            if (array[Length - 1] == value) {
//                fiber.Add(array); 
//            }
//        }
//        fiber.matrixc = iniciacount(arraysize-1, arraysize-1);
//        return fiber;
//    }

