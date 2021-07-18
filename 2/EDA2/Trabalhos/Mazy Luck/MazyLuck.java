import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Double.POSITIVE_INFINITY;

public class MazyLuck {

	public static Graph inputToGraph() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		String[] graphDetails = input.split(" ");

		final int rooms = Integer.parseInt(graphDetails[0]);
		final int corridors = Integer.parseInt(graphDetails[1]);
		Graph g = new Graph(rooms, corridors);

		for (int i = 0; i < corridors; i++) {
			input= br.readLine();
			String[] edgeDetails = input.split(" ");
			String check = edgeDetails[2];

			int src = Integer.parseInt(edgeDetails[0]);
			int dst = Integer.parseInt(edgeDetails[1]);
			int weight = 0;

			if (check.equals("C"))
				weight = -Integer.parseInt(edgeDetails[3]);
			else {
				weight = Integer.parseInt(edgeDetails[3]);
			}
			g.edges[i] = new Edge(src, dst, weight);
		}

		br.close();
		return g;
	}

	public static void main(String[] args) throws IOException {

		Graph g = inputToGraph();
		int start = 0;
		int exit = g.numVertices - 1;
		g.initializeSingleSource(start);

		for (int i = 0; i < g.numVertices - 1; i++) {
			g.relaxed = false;
			for (Edge e : g.edges) {
				g.relax(e);
			}
			if (!g.relaxed)
				break;
		}

		for (Edge e : g.edges) {
			if (g.distance[e.source] + e.weight < g.distance[e.destination]) {
				System.out.println("yes");
				return;
			}
		}

		if (g.distance[exit] < 0)
			System.out.println("yes");
		else
			System.out.println("no");
	}
}

class Graph {

	Edge edges[];
	double distance[];
	int predecessor[];
	int numVertices;
	int numEdges;
	boolean relaxed;

	Graph(int v, int e) {

		numVertices = v;
		edges = new Edge[e];
		distance = new double[v];
		predecessor = new int[v];
		relaxed = false;
	}

	void initializeSingleSource(int source) {

		for (int i = 0; i < numVertices; i++) {
			distance[i] = POSITIVE_INFINITY;
			predecessor[i] = Integer.MIN_VALUE;
		}
		distance[source] = 0;
	}

	void relax(Edge a) {

		int src = a.source;
		int dst = a.destination;

		if (distance[src] + a.weight < distance[dst]) {
			distance[dst] = distance[src] + a.weight;
			predecessor[dst] = src;
			relaxed = true;
		}
	}
}

class Edge {

	int source;
	int destination;
	int weight;

	Edge(int s, int d, int w) {
		source = s;
		destination = d;
		weight = w;
	}
}