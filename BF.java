import java.util.*;

public class BF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter the number of vertices: ");
        n = sc.nextInt();
        System.out.println("Enter the weight matrix of the graph (use 0 for no edge):");
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter the source vertex (1-based index): ");
        int src = sc.nextInt();
        bellmanFord(n, graph, src - 1);
        sc.close();
    }
    public static void bellmanFord(int n, int[][] graph, int source) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                    }
                }
            }
        }
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                    dist[u] + graph[u][v] < dist[v]) {
                    System.out.println("Negative weight cycle detected");
                    return;
                }
            }
        }
        printSolution(dist);
    }
    public static void printSolution(int[] dist) {
        System.out.println("Vertex\tDistance from Source");
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println((i + 1) + "\t\tInfinity");
            } else {
                System.out.println((i + 1) + "\t\t" + dist[i]);
            }
        }
    }
}