package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodeRank {

    public static void main(String[] args) throws IOException {	
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 

        String[] line = br.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        double beta = Double.parseDouble(line[1]);

        int[] brIzlaza = new int[N];
        Map<Integer, ArrayList<Integer>> ulazi = new HashMap<>();
        
        for (int i = 0; i < N; i++) {
        	line = br.readLine().split(" ");
        	brIzlaza[i] = line.length;
        	for (String izlaz : line) {
        		int x = Integer.parseInt(izlaz);
        		if (!ulazi.containsKey(x)) ulazi.put(x, new ArrayList<>());
        		ulazi.get(x).add(i);        		
        	}	        	
        }
        
        Map<Integer, Double[]> iteracije = new HashMap<>();
        Double[] nulta = new Double[N];
        for (int i = 0; i < N; ++i) {
        	nulta[i] = (double) 1/N;
        }
        
        iteracije.put(0, nulta);

        for (int i = 0; i < 100; ++i) {
            double acc = 0;
            Double[] nextIt = new Double[N];
            
            for (int j = 0; j < N; ++j) {
            	nextIt[j] = (double) 0;
            	if (ulazi.containsKey(j)) {
            		
                    for (Integer ulaz : ulazi.get(j)) nextIt[j] += beta * iteracije.get(i)[ulaz] / brIzlaza[ulaz];
            	}
                acc += nextIt[j];
            }
            
            for (int j = 0; j < N; j++) {
                nextIt[j] += (1 - acc) / N;
            }
            iteracije.put(i+1, nextIt);
        }

        DecimalFormat df = new DecimalFormat("0.0000000000");
        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            line = br.readLine().split(" ");
            double rankZaIspis = iteracije.get(Integer.parseInt(line[1]))[Integer.parseInt(line[0])];
            System.out.println(df.format(rankZaIspis).replace(",", "."));
        }
        
        br.close();
    }
}
