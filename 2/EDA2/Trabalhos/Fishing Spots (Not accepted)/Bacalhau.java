import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Bacalhau {

    static LinkedList<Solucao> solucao = new LinkedList<Solucao>();

    public static void main(String[] args) throws IOException {
        HashMap<Entry, LinkedList<Entry>> boats_per_spot = new HashMap<Entry, LinkedList<Entry>>();
        HashMap<Entry, LinkedList<Solucao>> solutions_per_spot = new HashMap<Entry, LinkedList<Solucao>>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstline = br.readLine();
        String[] str1 = firstline.split(" ");

        int numBoats = Integer.parseInt(str1[0]);
        int numSpots = Integer.parseInt(str1[1]);

        LinkedList<Entry> boats = new LinkedList<Entry>();
        LinkedList<Entry> spots = new LinkedList<Entry>();

        for (int i = 0; i < numBoats; i++) {
            String line = br.readLine();
            String[] str = line.split(" ");
            boats.add(new Entry(new Point(Integer.parseInt(str[0]), Integer.parseInt(str[1])), Integer.parseInt(str[2]),
                    false));
        }

        Collections.sort(boats);

        for (int i = 0; i < numSpots; i++) {
            String line = br.readLine();
            String[] str = line.split(" ");
            spots.add(new Entry(new Point(Integer.parseInt(str[0]), Integer.parseInt(str[1])), Integer.parseInt(str[2]),
                    true));
        }
        Collections.sort(spots);

        if (numBoats <= numSpots) {
            for (int i = 0; i < numBoats; i++) {
                Entry spotsr = spots.removeLast();
                Entry boatsr = boats.removeLast();
                solucao.add(new Solucao(boatsr.getValue(), spotsr.getValue(),
                        Point.distManhattan(spotsr.getP(), boatsr.getP())));

            }
        } else {
            // + boats q spot
            int spotSize;
            int bloq_dir;
            int bloq_esq;
            for (int i = 0; i < spots.size(); i++) {
                spotSize = spots.size() - i; // 1
                bloq_dir = spotSize - 1; // 0
                bloq_esq = i; // 1
                LinkedList<Entry> sublista_boats = new LinkedList<Entry>(
                        boats.subList(bloq_esq, boats.size() - bloq_dir));
                boats_per_spot.put(spots.get(i), sublista_boats);
            } // Spot -> Lista de Barcos possiveis


            
            

            // int i = 1;
            // recursiva(spots, boats, i);

        }

        // result(solucao);

    }

    public static void recursiva(LinkedList<Entry> spots_lidos, LinkedList<Entry> boats_lidos, int depth) {

        int spotSize = spots_lidos.size();
        int bloq_dir = spotSize - 1;
        int bloq_esq = depth - 1;

        if (spots_lidos.isEmpty())
            return;
        else {
            LinkedList<Entry> sublista_boats = new LinkedList<Entry>(
                    boats_lidos.subList(bloq_esq, boats_lidos.size() - bloq_dir));
            LinkedList<Entry> aux = new LinkedList<Entry>();
            Entry s = spots_lidos.removeFirst();
            Entry b = sublista_boats.removeFirst();

            for (Entry boat : sublista_boats) {
                if (Point.distManhattan(s.getP(), boat.getP()) < Point.distManhattan(s.getP(), b.getP())) {

                    b = boat; // b = boat com menor dist
                }
            }

            for (Entry boat : sublista_boats) {
                if (Point.distManhattan(s.getP(), boat.getP()) == Point.distManhattan(s.getP(), b.getP())) {
                    aux.add(boat);
                }
            }

            for (Entry boat : aux) {
                if (b.compareTo(boat) > 0) {
                    b = boat;
                }
            }

            solucao.add(new Solucao(b.getValue(), s.getValue(), Point.distManhattan(s.getP(), b.getP())));

        }
        depth += 1;
        recursiva(spots_lidos, boats_lidos, depth);
    }

    public static void result(LinkedList<Solucao> bd) {
        int result[] = new int[3];
        for (Solucao sol : bd) {
            result[0] += sol.getN_fish();
            result[1] += sol.getDistance();
            result[2] += sol.getRating();
        }

        StringBuilder sbl = new StringBuilder();
        sbl.append(result[0]);
        sbl.append(" ");
        sbl.append(result[1]);
        sbl.append(" ");
        sbl.append(result[2]);
        System.out.println(sbl);
    }
}

class Solucao {
    private int rating;
    private int n_fish;
    private int distance;

    public Solucao(int rating, int n_fish, int distance) {
        this.rating = rating;
        this.n_fish = n_fish;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public int getN_fish() {
        return n_fish;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        StringBuilder sbl = new StringBuilder();
        sbl.append("S:[");
        sbl.append(n_fish);
        sbl.append(",");
        sbl.append(distance);
        sbl.append(",");
        sbl.append(rating);
        sbl.append("] ");
        System.out.println(sbl);
        return sbl.toString();
    }
}

class Entry implements Comparable<Entry> {
    private Point p;
    private int value;
    private boolean boatOrSpot; // 0 spot 1 boat

    Entry(Point p, int value, boolean boatOrSpot) {
        this.p = p;
        this.value = value;
        this.boatOrSpot = boatOrSpot;
    }

    public Point getP() {
        return p;
    }

    public int getValue() {
        return value;
    }

    public boolean getBoS() {
        return boatOrSpot;
    }

    @Override
    public String toString() {
        if (boatOrSpot) {
            return "Spot " + this.p.toString() + ", " + " value:" + value + " | ";
        }
        return "Ship " + this.p.toString() + ", " + " value:" + value + " | ";
    }

    @Override
    public int compareTo(Entry o) {

        if (this.value < o.value)
            return -1;
        if (this.value > o.value)
            return 1;
        else
            return 0;
    }

}

class Point {
    private int x;
    private int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {

        return "(" + this.getX() + "," + this.getY() + ")";
    }

    public static int distManhattan(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}