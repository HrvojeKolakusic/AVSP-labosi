package lab4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClosestBlackNodeTest {

    public static void main(String[] args) throws IOException {
    	
    	int N;
        int e;
        
        Path put = Paths.get("D:/Faks/AVSP/lab4test/lab4b/btest2/R.in");
        BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new BufferedInputStream(
								new FileInputStream(put.toString())))); 

        String[] split = br.readLine().split(" ");
        N = Integer.parseInt(split[0]);
        e = Integer.parseInt(split[1]);

        List<Integer> crni = new ArrayList<>();
        Map<Integer, Integer> d = new HashMap<>();
        Map<Integer, Integer> najbliziCrni = new HashMap<>();
        Map<Integer, Boolean> jeCrn = new HashMap<>();

        for (int i = 0; i < N; ++i) {
        	if (br.readLine().equals("1")) {
        		d.put(i, 0);
        		najbliziCrni.put(i, i);
        		crni.add(i);
        		jeCrn.put(i, true);
        	} else { 
        		d.put(i, 11);
        		jeCrn.put(i, false);
        	}
        }

        Map<Integer, ArrayList<Integer>> povezani = new HashMap<>();
        
        for (int i = 0; i < e; i++) {
        	split = br.readLine().split(" ");
        	
        	if (!povezani.containsKey(Integer.parseInt(split[0]))) povezani.put(Integer.parseInt(split[0]), new ArrayList<>());
        	if (!povezani.containsKey(Integer.parseInt(split[1]))) povezani.put(Integer.parseInt(split[1]), new ArrayList<>());
        	
        	povezani.get(Integer.parseInt(split[0])).add(Integer.parseInt(split[1]));
        	povezani.get(Integer.parseInt(split[1])).add(Integer.parseInt(split[0]));
        }

        int node;

        while (crni.size() != 0) {
        	node = crni.get(0);
        	crni.remove(0);
        	
        	for (Integer cvor : povezani.get(node)) {
        		if (!jeCrn.get(cvor)) {
        			crni.add(cvor);
        			jeCrn.put(cvor, true);
        			if (d.get(cvor) > d.get(node) + 1) {
        				najbliziCrni.put(cvor, najbliziCrni.get(node));
        				d.put(cvor, d.get(node) + 1);
        			}
        		}
        	}

        }
            
        for (int i = 0; i < N; ++i) {
        	if (d.get(i) == 11) System.out.println("-1 -1");
        	else System.out.println(najbliziCrni.get(i) + " " + d.get(i));
        }
        
        br.close();
    }
}