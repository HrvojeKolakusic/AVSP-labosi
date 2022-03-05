package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CF {

    public static void main(String[] args) throws IOException {
    	
        Locale.setDefault(new Locale("en", "US"));
        DecimalFormat df = new DecimalFormat("#.000", DecimalFormatSymbols.getInstance());
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
        int N;
        int M;
        String[] split = br.readLine().split(" ");
        N = Integer.parseInt(split[0]);
        M = Integer.parseInt(split[1]);

        float[][] tablica = new float[N][M];
        for (int i = 0; i < N; i++) {
        	split = br.readLine().split(" ");
        	int j = 0;
        	for (String value : split) {
        		if (value.equals("X")) tablica[i][j] = 0;
                else tablica[i][j] = Float.parseFloat(value);
        		++j;
        	}
        }
        
        float[] avgPoN = new float[N];
        float[] avgPoM = new float[M];
        for (int i = 0; i < N; ++i) {
        	float suma = 0;
        	int brojac = 0;
        	for (int j = 0; j < M; ++j) {
        		if (tablica[i][j] != 0) {
        			suma += tablica[i][j];
        			brojac++;
        		}
        	}
        	avgPoN[i] = suma/brojac;
        }
        
        for (int j = 0; j < M; ++j) {
        	float suma = 0;
        	int brojac = 0;
        	for (int i = 0; i < N; ++i) {
        		if (tablica[i][j] != 0) {
        			suma += tablica[i][j];
        			brojac++;
        		}
        	}
        	avgPoM[j] = suma/brojac;
        }
        
        int Q = Integer.parseInt(br.readLine());
        for (int u = 0; u < Q; u++) {
        	split = br.readLine().split(" ");
        	
            int I = Integer.parseInt(split[0]);
            int J = Integer.parseInt(split[1]);
            int T = Integer.parseInt(split[2]);
            int K = Integer.parseInt(split[3]);
            
            float[][] pomTablica;
            int max_x, max_y, x, y;
            float[] avgs;
            if (T == 0) {
            	pomTablica = new float[N][M];
            	for (int i = 0; i < N; ++i) {
            		for (int j = 0; j < M; ++j) {
            			pomTablica[i][j] = tablica[i][j];
            		}
            	}
            	max_x = N;
            	max_y = M;
            	avgs = avgPoN;
            	x = I-1;
            	y = J-1;
            	
            } else {
            	pomTablica = new float[M][N];
            	for (int i = 0; i < N; ++i) {
            		for (int j = 0; j < M; ++j) {
            			pomTablica[j][i] = tablica[i][j];
            		}
            	}
            	max_x = M;
            	max_y = N;
            	avgs = avgPoM;
            	x = J-1;
            	y = I-1;
            }

            List<Float> slicnosti = new ArrayList<Float>();
            List<Float> pom = new ArrayList<Float>();
            
            float[][] normTablica = new float[max_x][max_y];
            for (int i = 0; i < max_x; i++) {
            	for (int j = 0; j < max_y; j++) {
            		if (pomTablica[i][j] != 0) {
            			normTablica[i][j] = pomTablica[i][j] - avgs[i];
            		}
            	}
            }
            
            for (int i = 0; i < max_x; i++) {

                float sumaBroj = 0;
                float sumaNaz1 = 0;
                float sumaNaz2 = 0;
                float slicnost;

                if (i == x) slicnost = 1;
                else {
                    for (int j = 0; j < max_y; j++) {
                        sumaBroj += normTablica[x][j] * normTablica[i][j];
                        sumaNaz1 += Math.pow(normTablica[x][j], 2);
                        sumaNaz2 += Math.pow(normTablica[i][j], 2);
                    }
                    slicnost = (float) (sumaBroj / Math.sqrt(sumaNaz2 * sumaNaz1));
                }
                slicnosti.add(slicnost);
                pom.add(slicnost);
            }
            
            slicnosti.sort(Comparator.reverseOrder());

            int brojac = 0;
            float sumaSlicnosti = 0;
            float ocjena = 0;
            for (Float slicnost : slicnosti) {
                if (brojac == K) {
                    break;
                }

                if (slicnost > 0 && pomTablica[pom.indexOf(slicnost)][y] > 0 && pom.indexOf(slicnost) != x) {
                    brojac++;
                    sumaSlicnosti += slicnost;
                    ocjena += pomTablica[pom.indexOf(slicnost)][y] * slicnost;
                }
            }
            
            BigDecimal bd = new BigDecimal((float) ocjena / sumaSlicnosti);
            BigDecimal res = bd.setScale(3, RoundingMode.HALF_UP);
            System.out.println(df.format(res));
        }
    
        br.close();       
    }
}

