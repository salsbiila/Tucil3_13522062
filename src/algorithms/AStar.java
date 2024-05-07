package algorithms;
import java.util.*;
import util.*;

public class AStar {
    public static Result searchAStar(String startWord, String targetWord, List<String> wordList) {
        if (startWord.equals(targetWord)) {
            return new Result(Collections.singletonList(Collections.singletonList(startWord)), 1);
        } else {
            Map<String, List<String>> parentMap = new HashMap<>();
            Map<String, Integer> distances = new HashMap<>();
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalCost).thenComparing(Node::getWord));

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
            backtrack(targetWord, startWord, parentMap, path, paths);
            return new Result(paths, visited);
        }
    }

    public static void backtrack(String targetWord, String startWord, Map<String, List<String>> parentMap, List<String> path, List<List<String>> paths) {
        if (targetWord.equals(startWord)) {
            paths.add(new ArrayList<>(path));
            Collections.reverse(paths.get(paths.size() - 1));
            return;
        }

        if (!parentMap.containsKey(targetWord)) {
            return;
        }

        for (String parent : parentMap.get(targetWord)) {
            path.add(parent);
            backtrack(parent, startWord, parentMap, path, paths);
            path.remove(path.size() - 1);
        }
    }
}
