import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author jpbc
 */
public class Exercicio1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        int children = Integer.parseInt(br.readLine());
        int biggest_stick = Integer.MIN_VALUE;

        for (int i = 0; i < children; i++) {
            String line = br.readLine(); 
            String[] str = line.split(" ");      
            int n = Integer.parseInt(str[0]);
            for (int j = 1; j <= n; j++) {
                int current_stick = Integer.parseInt(str[j]);
                if (biggest_stick < current_stick)
                        biggest_stick = current_stick;
            }
        }
        System.out.println(biggest_stick);
        
    }

}

