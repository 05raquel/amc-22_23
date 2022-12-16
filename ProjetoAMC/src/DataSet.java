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
		
		/*public static int Count (ArrayList<int []> dataset, int v []) {		//NOVA  variaveis 3.5 tomam os valores 0.1
			int c=0;
			for (int [] var : dataset) {  			
				if (Arrays.equals (var, v)) c++;
			}
			return c;
		}*/
		
		public static int Count(int var[], int val[]) {
			int c = 0;
			if()
			}
		}
		
		
		
		public ArrayList<int []> data() {			//NOVA
			return dataList;
		}
		
		
		public void Add(int v[]) {			//NOVA
			dataList.add(v);
		}
		
		
		
	
		public static void main(String[] args) {
				DataSet d = new DataSet("bcancer.csv");
				int v [] = {1,2,3,4,5,6};
				d.Add(v);
				System.out.println(d);
				
		}
		
}
