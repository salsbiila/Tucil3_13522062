import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.time.Duration;

import util.*;
import algorithms.*;


public class mainGUI extends JFrame {
    private JPanel panel;
    private JButton unitedCostSearchButton;
    private JButton greedyBestFirstSearchButton;
    private JTextField enterStartingWordTextField;
    private JTextField enterTargetWordTextField;    
    private JButton aSearchButton;
    private JTextArea resultArea;
    private final Map<Integer, List<String>> wordMap;

    public mainGUI(Map<Integer, List<String>> map) {

        this.wordMap = map;
        aSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startWord = enterStartingWordTextField.getText().trim();
                String targetWord = enterTargetWordTextField.getText().trim();

                try {
                    Words.isExists(startWord, wordMap.get(startWord.length()), 0);
                    Words.isExists(targetWord, wordMap.get(targetWord.length()), 1);
                    Words.isSameLength(startWord, targetWord);
                } catch (IllegalArgumentException ex) {
                    resultArea.setText(ex.getMessage());
                    return;
                }

                long startTime = System.nanoTime();
                Result res = AStar.searchAStar(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();

                List<List<String>> paths = res.getPaths();
                StringBuilder result = new StringBuilder();
                result.append("Number of nodes visited: ").append(res.getVisited()).append("\n");
                if (paths.isEmpty()) {
                    result.append("No path found.");
                } else {
                    result.append("Number of paths found: ").append(paths.size()).append("\n");
                    for (List<String> path : paths) {
                        result.append(path).append("\n");
                    }
                }
                result.append("Time taken: ").append(Duration.ofNanos(endTime - startTime).toMillis()).append("ms");
                resultArea.setText(result.toString());
            }
        });
        greedyBestFirstSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startWord = enterStartingWordTextField.getText().trim();
                String targetWord = enterTargetWordTextField.getText().trim();

                try {
                    Words.isExists(startWord, wordMap.get(startWord.length()), 0);
                    Words.isExists(targetWord, wordMap.get(targetWord.length()), 1);
                    Words.isSameLength(startWord, targetWord);
                } catch (IllegalArgumentException ex) {
                    resultArea.setText(ex.getMessage());
                    return;
                }

                long startTime = System.nanoTime();
                Result res = UCS.searchUCS(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();

                List<List<String>> paths = res.getPaths();
                StringBuilder result = new StringBuilder();
                result.append("Number of nodes visited: ").append(res.getVisited()).append("\n");
                if (paths.isEmpty()) {
                    result.append("No path found.");
                } else {
                    result.append("Number of paths found: ").append(paths.size()).append("\n");
                    for (List<String> path : paths) {
                        result.append(path).append("\n");
                    }
                }
                result.append("Time taken: ").append(Duration.ofNanos(endTime - startTime).toMillis()).append("ms");
                resultArea.setText(result.toString());
            }
        });
        unitedCostSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startWord = enterStartingWordTextField.getText().trim();
                String targetWord = enterTargetWordTextField.getText().trim();

                try {
                    Words.isExists(startWord, wordMap.get(startWord.length()), 0);
                    Words.isExists(targetWord, wordMap.get(targetWord.length()), 1);
                    Words.isSameLength(startWord, targetWord);
                } catch (IllegalArgumentException ex) {
                    resultArea.setText(ex.getMessage());
                    return;
                }

                long startTime = System.nanoTime();
                Result res = GreedySearch.searchGreedySearch(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();

                List<List<String>> paths = res.getPaths();
                StringBuilder result = new StringBuilder();
                result.append("Number of nodes visited: ").append(res.getVisited()).append("\n");
                if (paths.isEmpty()) {
                    result.append("No path found.");
                } else {
                    result.append("Number of paths found: ").append(paths.size()).append("\n");
                    for (List<String> path : paths) {
                        result.append(path).append("\n");
                    }
                }
                result.append("Time taken: ").append(Duration.ofNanos(endTime - startTime).toMillis()).append("ms");
                resultArea.setText(result.toString());
            }
        });
    }

    public static void main(String[] args) {
        Map<Integer, List<String>> map = Words.createWordMap("src/util/words.txt");
        JFrame frame = new JFrame("Word Ladder Solver");
        frame.setContentPane(new mainGUI(map).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
