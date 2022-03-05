package lab1;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class SimHashBuckets {
	
	public static String simhash(String line) {
		
		String[] split = line.split(" ");
		int sh[] = new int[128];
		char chars[] = new char[128];
    	 
		for (String x : split) {
			byte[] hash = DigestUtils.md5(x);
    		 
			for (int i = 0; i < 16; ++i) {
				for (int j = 7; j >= 0; j--) {
					if (((hash[i] >> j) & 1 ) == 1) {
						sh[i*8 + 7-j] += 1; 
					} else sh[i*8 + 7-j] -= 1;
				}
			}
		}
		
		for (int i = 0; i < 128; i++) {
			if (sh[i] >= 0) chars[i]='1';
			else chars[i] = '0';           
		}
    	 
		return new String(chars);
	}
	
	public static Map<Integer, Set<Integer>> LSH(ArrayList<String> hashovi){

		Map<Integer, Set<Integer>> kands = new HashMap<Integer, Set<Integer>>();
        
		for (int pojas = 0; pojas < 8; pojas++) {
			Map<Integer, Set<Integer>> pretinci = new HashMap<Integer, Set<Integer>>();
			for (int hashId = 0; hashId < hashovi.size(); hashId++) {
				String hash = hashovi.get(hashId);
				
				String pojasBin = hash.substring(16*pojas, 16*pojas + 16);
				int broj = Integer.parseInt(pojasBin, 2);
				
				Set<Integer> textoviPret = new HashSet<>();
				
				if(pretinci.get(broj) != null) {
					textoviPret = pretinci.get(broj);
					for(Integer textId : textoviPret) {
						if(kands.get(hashId) == null) kands.put(hashId, new HashSet<>());
						kands.get(hashId).add(textId);
						
                             
						if(kands.get(textId) == null) kands.put(textId, new HashSet<>());
						kands.get(textId).add(hashId);
						
					}        
				} else textoviPret.clear();
				
				textoviPret.add(hashId);
				pretinci.put(broj, textoviPret);
			}
		}
		
		return kands;
	}
     
    public static void main(String[] args) throws IOException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    
		
		Map<Integer, Set<Integer>> kands = new HashMap<>();
        ArrayList<String> hashovi = new ArrayList<String>();
        int brUlaznihTekstova = Integer.parseInt(br.readLine());
        
        for (int i = 0; i < brUlaznihTekstova; i++) {
            hashovi.add(simhash(br.readLine()));
        }
        
        kands = LSH(hashovi);
        
        int brUpita = Integer.parseInt(br.readLine());
        
        for (int i = 0; i < brUpita; i++) {
            String[] split = br.readLine().split(" ");
            
            int brSlicnih = 0;
            int idUpita = Integer.parseInt(split[0]);
            char[] upitHash = hashovi.get(idUpita).toCharArray();
            int maxRazlika = Integer.parseInt(split[1]);
            
            Set<Integer> kandsZaUpit = kands.get(idUpita);
            
            for (Integer kand : kandsZaUpit) {
            	if (kand == idUpita) continue;
            	
            	char[] testHash = hashovi.get(kand).toCharArray();
            	int hamDist = 0;
            	
            	for (int k = 0; k < 128; ++k) {
            		if (upitHash[k] != testHash[k]) hamDist++;
            	}
            	
            	if (hamDist <= maxRazlika) brSlicnih++;
            }
            
            System.out.println(brSlicnih);
        }
        
        br.close();
    }  
}