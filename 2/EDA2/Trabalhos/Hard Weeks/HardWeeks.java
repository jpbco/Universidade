import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HardWeeks {

    public static Graph inputToGraph() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String firstline = br.readLine();
        String[] str1 = firstline.split(" ");

        final int tasks = Integer.parseInt(str1[0]); // number of tasks
        final int precedences = Integer.parseInt(str1[1]); // number of (distinct) direct precedences among tasks
        final int limit = Integer.parseInt(str1[2]); // limit used in the definition of hard week

        Graph g = new Graph(tasks, limit);

        for (int i = 0; i < precedences; i++) {
            String line = br.readLine();
            String[] str = line.split(" ");
            g.addEdge(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
        }

        br.close();
        return g;
    }

    public static void main(String[] args) throws IOException {
        Graph g = inputToGraph(); // o(E) n = numero de arcos

        for (int i = 0; i < g.getVertices(); i++) { // o(V)
            List<Integer> verticesTemp = g.getAdjacencyListByIndex(i); // o(1)
            for (int vertice : verticesTemp) // o (E)
                g.getIndegree()[vertice]++; // o(1)
        } // o(V + E)

        Queue<Integer> queue = new LinkedList<Integer>(); // o(1)

        for (int i = 0; i < g.getVertices(); i++) { // o(V)
            if (g.getIndegree()[i] == 0) // o(1)
                queue.add(i); // o(1)
        } // o(V)

        int maxTarefas = 0; // o (1)
        int hardWeeks = 0; // o (1)
        int count = queue.size(); // o (1)

        if (count > g.getLimit()) // o(1)
            hardWeeks++; // o(1)

        if (count > maxTarefas) // o(1)
            maxTarefas = count; // o(1)

        while (!queue.isEmpty()) { 
            count--;
            int n = queue.poll();

            for (int node : g.getAdjacencyListByIndex(n)) {
                if ((--g.getIndegree()[node] == 0)) {
                    queue.add(node);
                }
            }
            // if it's a "work week" then we can compare the values and increment them if needed
            if (count == 0) {  
                count = queue.size();
                if (maxTarefas < count)
                    maxTarefas = count;
                if (queue.size() > g.getLimit())
                    hardWeeks++;
            }
        } // o(V + E)
        System.out.println(maxTarefas + " " + hardWeeks);
    }
}   

class Graph {
    private int vertices;
    private List<Integer> adjacencyList[];
    private int indegree[];
    private int limit;

    @SuppressWarnings("unchecked")
    public Graph(int v, int l) {
        this.vertices = v;
        this.adjacencyList = (ArrayList<Integer>[]) new ArrayList[vertices];
        this.indegree = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<Integer>();
        }

        this.limit = l;
    }

    public List<Integer> getAdjacencyListByIndex(int index) {
        return adjacencyList[index];
    }

    public int getVertices() {
        return vertices;
    }

    public int getLimit() {
        return limit;
    }

    public int[] getIndegree() {
        return indegree;
    }

    public void addEdge(int a, int b) {
        adjacencyList[a].add(b);
    }

}