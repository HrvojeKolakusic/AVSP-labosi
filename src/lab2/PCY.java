package lab2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PCY {

    public static void main(String[] args) throws IOException {
    	
    	 Path put = Paths.get("D:/Faks/AVSP/test2/R.in");
     	BufferedReader br = new BufferedReader(
 				new InputStreamReader(
 						new BufferedInputStream(
 								new FileInputStream(put.toString()))));

        String line;
        int brKos = Integer.parseInt(br.readLine());
        int prag = (int)Math.floor(Float.parseFloat(br.readLine()) * brKos);
        int brPret = Integer.parseInt(br.readLine());
              
        List<ArrayList<Integer>> kosare = new ArrayList<ArrayList<Integer>>();
        while ((line = br.readLine()) != null) {
        	String[] split = line.split(" ");
        	ArrayList<Integer> pom = new ArrayList<Integer>();
        	for (String broj : split) {
        		pom.add(Integer.parseInt(broj));
        	}
        	kosare.add(pom);
        }
        

        Map<Integer, Integer> brPred = new HashMap<>();
        for (ArrayList<Integer> lista : kosare) {
        	for (Integer broj : lista) {
        		if (!brPred.containsKey(broj)) {
        			brPred.put(broj, 1);
        		} else {
        			brPred.put(broj, brPred.get(broj) + 1);
        		}
        	}
        }
                

        Map<Integer, Integer> pretinci = new HashMap<>();
        for (ArrayList<Integer> lista : kosare) {
        	for (int i = 0; i < lista.size(); i++) {
        		for (int j = i + 1; j < lista.size(); j++) {
        			if ((brPred.get(lista.get(i)) >= prag) && (brPred.get(lista.get(j)) >= prag)) {
        				int k = (lista.get(i) * brPred.size() + lista.get(j)) % brPret;
        				if (!pretinci.containsKey(k)) {
        					pretinci.put(k, 1);
        				} else {
        					pretinci.put(k, pretinci.get(k) + 1);
        				}
        			}
        		}
        	}
        }
        
                

        Map<String, Integer> parovi = new HashMap<>();
        for (ArrayList<Integer> lista : kosare){
        	for (int i = 0; i < lista.size(); i++) {
        		for (int j = i + 1; j < lista.size(); j++) {
        			if ((brPred.get(lista.get(i)) >= prag) && (brPred.get(lista.get(j)) >= prag)) {
        				int k = (lista.get(i) * brPred.size() + lista.get(j)) % brPret;
        				if (pretinci.get(k) >= prag) {
        					String par = lista.get(i) + " " + lista.get(j);
        					if (!parovi.containsKey(par)) {
        						parovi.put(par, 1);
        					} else {
        						parovi.put(par, parovi.get(par) + 1);
        					}
        				}
        			}
        		}
        	}
        }
        
        int m = 0;
        for (Integer broj : brPred.values()) {
        	if (broj >= prag) m++;
        }
        
        System.out.println((m * (m - 1)) / 2);
        System.out.println(parovi.size());
        
        
        List<Integer> paroviVal = new ArrayList<Integer>(parovi.values());
        Collections.sort(paroviVal, Collections.reverseOrder());
        for (Integer broj : paroviVal) {
        	System.out.println(broj);
        }
        
        br.close();
    }
}
