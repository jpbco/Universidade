import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author jpbc
 */
import java.util.HashMap;
public class Exercicio2 {

    static HashMap<Integer,ArrayList<Integer>> valores;

    public static void doStuff(int numMoedas, int[] valoresMoedas, int pergunta) {
        if(Arrays.binarySearch(valoresMoedas, pergunta)>=0){
            System.out.println(pergunta +": [1]");
        }else if(valores.containsKey(pergunta)){
            System.out.println(pergunta+": " + valores.get(pergunta).toString());
        }
        else{
            for(int i = 0; i < numMoedas; i++){
                for(int j = 0; + j < numMoedas; j++){
                    if(pergunta == numMoedas[i] + num)
                }
            }
            System.out.println("NO");
        }
    }

    public static int recursive(int numMoedas, int[] valoresMoedas, int pergunta, int inc){
        
        return 0;
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numMoedas = Integer.parseInt(br.readLine());

        String[] stringvaloresmoedas = br.readLine().split(" ");

        int[] valoresMoedas = new int[numMoedas];

        for (int a = 0; a < stringvaloresmoedas.length; a++){
            valoresMoedas[a] = Integer.parseInt(stringvaloresmoedas[a]);
        }

        int numPerguntas = Integer.parseInt(br.readLine());

        for (int i = 0; i < numPerguntas; i++) {
            doStuff(numMoedas, valoresMoedas, Integer.parseInt(br.readLine()));
        }
    }

}
