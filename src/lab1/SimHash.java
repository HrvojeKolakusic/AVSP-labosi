package lab1;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SimHash {
	
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
     
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));       
        ArrayList<String> hashovi = new ArrayList<String>();
        int brUlaznihTekstova = Integer.parseInt(br.readLine());
        
        for (int i = 0; i < brUlaznihTekstova; i++) {
            hashovi.add(simhash(br.readLine()));
        }
        
        int brUpita = Integer.parseInt(br.readLine());
        
        for (int i = 0; i < brUpita; i++) {
            String[] split = br.readLine().split(" ");
            
            int brSlicnih = 0;
            int idUpita = Integer.parseInt(split[0]);
            char[] upitHash = hashovi.get(idUpita).toCharArray();
            int maxRazlika = Integer.parseInt(split[1]);
            
            for (int j = 0; j < hashovi.size(); ++j) {
            	if (j == idUpita) continue;
            	
            	char[] testHash = hashovi.get(j).toCharArray();
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