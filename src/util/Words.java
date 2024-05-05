package util;
import java.io.*;
import java.util.*;

public class Words {
    public static Map<Integer, List<String>> createWordMap(String filePath) {
        Map<Integer, List<String>> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                int wordLength = line.length();

                map.putIfAbsent(wordLength, new ArrayList<>());
                map.get(wordLength).add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return map;
    }

    public static int countCharDifference(String word1, String word2) {
        int count = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isExists(String word, List<String> wordList, int type) {
        String var = type == 0 ? "Starting" : "Target";
        if (!wordList.contains(word)) {
            throw new IllegalArgumentException(var +" word does not exist in the word list!");
        }
        return true;
    }

    public static boolean isSameLength(String startWord, String targetWord) {
        if (startWord.length() != targetWord.length()) {
            throw new IllegalArgumentException("Starting and target words must be of the same length!");
        }
        return true;
    }
}