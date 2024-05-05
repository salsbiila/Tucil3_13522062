import java.util.*;
import java.time.*;

import util.*;
import algorithms.*;

public class Main {
    public static void main(String[] args) {
        Map<Integer, List<String>> wordMap = Words.createWordMap("util/words.txt");
        boolean isGoing = true;
        Integer[] keys = wordMap.keySet().toArray(new Integer[0]);
        for (Integer key : keys) {
            System.out.println(key + " letter words: " + wordMap.get(key).size());
        }

        while (isGoing) {
            String startWord, targetWord;
            System.out.println("Enter the start word: ");
            startWord = System.console().readLine();
            System.out.println("Enter the target word: ");
            targetWord = System.console().readLine();

            try {
                Words.isExists(startWord, wordMap.get(startWord.length()), 0);
                Words.isExists(targetWord, wordMap.get(targetWord.length()), 1);
                Words.isSameLength(startWord, targetWord);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.println("Choose the algorithm:");
            System.out.println("1. Uniform Cost Search");
            System.out.println("2. Greedy Best First Search");
            System.out.println("3. A* Search");
            int choice = Integer.parseInt(System.console().readLine());
            if (choice == 1) {
                long startTime = System.nanoTime();
                Result res = UCS.searchUCS(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();
                List<List<String>> paths = res.getPaths();
                if (paths.isEmpty()) {
                    System.out.println("No path found.");
                } else {
                    System.out.println("Number of nodes visited: " + res.getVisited());
                    System.out.println("Number of paths found: " + paths.size());
                    for (List<String> path : paths) {
                        System.out.println(path);
                    }
                }
                System.out.println("Time taken: " + Duration.ofNanos(endTime - startTime).toMillis() + "ms");
            } else if (choice == 2) {
                long startTime = System.nanoTime();
                Result res = GreedySearch.searchGreedySearch(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();
                List<List<String>> paths = res.getPaths();
                if (paths.isEmpty()) {
                    System.out.println("No path found.");
                } else {
                    System.out.println("Number of nodes visited: " + res.getVisited());
                    System.out.println("Number of paths found: " + paths.size());
                    for (List<String> path : paths) {
                        System.out.println(path);
                    }
                }
                System.out.println("Time taken: " + Duration.ofNanos(endTime - startTime).toMillis() + "ms");
            } else if (choice == 3) {
                long startTime = System.nanoTime();
                Result res = AStar.searchAStar(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();
                List<List<String>> paths = res.getPaths();
                if (paths.isEmpty()) {
                    System.out.println("No path found.");
                } else {
                    System.out.println("Number of nodes visited: " + res.getVisited());
                    System.out.println("Number of paths found: " + paths.size());
                    for (List<String> path : paths) {
                        System.out.println(path);
                    }
                }
                System.out.println("Time taken: " + Duration.ofNanos(endTime - startTime).toMillis() + "ms");
            }

            System.out.println("Do you want to continue? (y/n)");
            String input = System.console().readLine();
            if (input.equals("n")) {
                isGoing = false;
            }
        }
    }
}