package util;

import java.util.List;

public class Result {
    private List<List<String>> paths;
    private int visited;

    public Result(List<List<String>> paths, int visited) {
        this.paths = paths;
        this.visited = visited;
    }

    public List<List<String>> getPaths() {
        return paths;
    }

    public int getVisited() {
        return visited;
    }
}
