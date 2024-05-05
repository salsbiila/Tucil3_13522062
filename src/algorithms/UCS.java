package algorithms;
import java.util.*;
import util.*;

public class UCS {
    public static Result searchUCS(String startWord, String targetWord, List<String> wordList) {
        if (startWord.equals(targetWord)) {
            return new Result(Collections.singletonList(Collections.singletonList(startWord)), 1);
        } else {
            Map<String, List<String>> parentMap = new HashMap<>();
            Map<String, Integer> distances = new HashMap<>();
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));

            distances.put(startWord, 0);
            pq.offer(new Node(startWord, 0, Words.countCharDifference(startWord, targetWord)));

            int visited = 0;
            while (!pq.isEmpty()) {
                Node currentNode = pq.poll();
                String currentWord = currentNode.getWord();
                int currentCost = currentNode.getCost();
                visited++;

                if (currentWord.equals(targetWord)) {
                    break;
                }

                List<String> children = Node.createChildren(currentWord, wordList);

                for (String child : children) {
                    int newCost = currentCost + 1;
                    if (!distances.containsKey(child) || newCost < distances.get(child)) {
                        distances.put(child, newCost);
                        parentMap.put(child, new ArrayList<>());
                        pq.offer(new Node(child, newCost, Words.countCharDifference(child, targetWord)));
                    }

                    if (newCost == distances.get(child)) {
                        parentMap.get(child).add(currentWord);
                    }
                }
            }

            List<List<String>> paths = new ArrayList<>();
            List<String> path = new ArrayList<>();
            path.add(targetWord);
            AStar.backtrack(targetWord, startWord, parentMap, path, paths);
            return new Result(paths, visited);
        }
    }
}
