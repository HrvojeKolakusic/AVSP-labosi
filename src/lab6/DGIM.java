package lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class DGIM {

    static int N;
    static int vremenskaOznaka = 0;
    
    static LinkedList<Integer> pretinci;
    static LinkedList<Integer> listaOznaka;

    public static void main(String[] args) throws IOException {
    	
    	pretinci = new LinkedList<>();
    	listaOznaka = new LinkedList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 

        N = Integer.parseInt(br.readLine());
        
        String line;
        while ((line = br.readLine()) != null) {       	
        	String[] split = line.split(" ");
        	if (split[0].equals("q")) {
        		
                int total = 0;
                int zadnja = 0;
                
                for (int i = 0; i < pretinci.size(); ++i) {
                	if (listaOznaka.get(i) <= vremenskaOznaka - Integer.parseInt(split[1])) break;
                	total += pretinci.get(i);
                	zadnja = pretinci.get(i);
                }
                
                System.out.println(total - (int)Math.ceil((float)zadnja / 2));
                
        	} else for (int i = 0; i < line.length(); ++i) {
        		
                vremenskaOznaka++;
                if (Integer.parseInt(String.valueOf(line.charAt(i))) == 0) {
                    continue;
                }
                
                pretinci.addFirst(1);
                listaOznaka.addFirst(vremenskaOznaka);
                
                if (listaOznaka.getLast() < vremenskaOznaka - N) {
                	pretinci.removeLast();
                	listaOznaka.removeLast();
                }
                
                if (provjeri(0)) {
                	spoji(0);
                }
        	}        	
        }
    }
    
    static void spoji(int index) {
    	
    	int x = pretinci.get(index + 1);
    	pretinci.set(index + 1, pretinci.get(index + 1) + pretinci.get(index + 2));
    	
        pretinci.remove(index + 2);
        listaOznaka.remove(index + 2);
        
        if (provjeri(pretinci.indexOf(x)+1)) {
        	spoji(pretinci.indexOf(x)+1);
        }
    }
    
    static boolean provjeri(int index) {
    	
    	if (index + 1 < pretinci.size()) {
    		if (pretinci.get(index) != pretinci.get(index + 1)) return false;
    		else if (index + 2 < pretinci.size()) return pretinci.get(index + 1) == pretinci.get(index + 2);
    	}
    	
    	return false;
    }
}

