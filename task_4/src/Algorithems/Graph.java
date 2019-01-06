package Algorithems;

import java.util.List;
/**
 * This class is thanks to http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
