package util;
import java.util.*;

public class Node{
    private String word;
    private int cost;
    private int heuristic;

    public Node(String word, int cost, int heuristic) {
        this.word = word;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    public String getWord() {
        return word;
    }

    public int getCost() {
        return cost;
    }

    public int getTotalCost() {
        return cost + heuristic;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public static List<String> createChildren(String word, List<String> wordList) {
        List<String> children = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            char[] chars = word.toCharArray();

            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String child = new String(chars);

                if (!child.equals(word) && wordList.contains(child)) {
                    children.add(child);
                }
            }
        }

        return children;
    }
}
