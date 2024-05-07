import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

import util.*;
import algorithms.*;

public class MainGUI {
    private static Map<Integer, List<String>> wordMap;
    private static JTextField startWordField, targetWordField;
    private static JFrame frame;
    private static final Dimension BUTTON_SIZE = new Dimension(200, 25);
    private static BufferedImage backgroundImage;

    public static void main(String[] args) {
        wordMap = Words.createWordMap("../src/util/words.txt");

        try {
            backgroundImage = ImageIO.read(MainGUI.class.getResourceAsStream("Frame_2.png")); // Load the image
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Word Ladder Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Draw the background image, centered in the panel
                    int x = (getWidth() - backgroundImage.getWidth()) / 2;
                    int y = (getHeight() - backgroundImage.getHeight()) / 2;
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        };
        frame.add(mainPanel);
    
        // Add margin insets to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(200, 100, 200, 100));
    
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Make center panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Adjust insets here to decrease distance
        
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false); // Make input panel transparent
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
    
        startWordField = new JTextField(10);
        startWordField.setFont(new Font("Gadugi", Font.PLAIN, 14));
        inputPanel.add(startWordField);
    
        inputPanel.add(Box.createHorizontalStrut(20));
        targetWordField = new JTextField(10);
        targetWordField.setFont(new Font("Gadugi", Font.PLAIN, 14));
        inputPanel.add(targetWordField);
        
        centerPanel.add(inputPanel, gbc);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
        JButton ucsButton = new JButton("Uniform Cost Search");
        configureButton(ucsButton);
        ucsButton.setBackground(new Color(29, 99, 255));
        ucsButton.setForeground(Color.WHITE);
        ucsButton.setFont(new Font("Gadugi", Font.PLAIN, 14));
        ucsButton.addActionListener(new UCSListener());
        buttonPanel.add(ucsButton);
    
        JButton aStarButton = new JButton("A* Search");
        configureButton(aStarButton);
        aStarButton.setBackground(new Color(254, 143, 172));
        aStarButton.setForeground(Color.WHITE);
        aStarButton.setFont(new Font("Gadugi", Font.PLAIN, 14));
        aStarButton.addActionListener(new AStarListener());
        buttonPanel.add(aStarButton);
    
        JButton gbfsButton = new JButton("Greedy Best First Search");
        configureButton(gbfsButton);
        gbfsButton.setBackground(new Color(58, 58, 58));
        gbfsButton.setForeground(Color.WHITE);
        gbfsButton.setFont(new Font("Gadugi", Font.PLAIN, 14));
        gbfsButton.addActionListener(new GreedyListener());
        buttonPanel.add(gbfsButton);
        
        gbc.gridy = 1;
        centerPanel.add(buttonPanel, gbc);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void configureButton(JButton button) {
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setMinimumSize(BUTTON_SIZE);
    }

    private static class UCSListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String startWord = startWordField.getText().toLowerCase();
            String targetWord = targetWordField.getText().toLowerCase();

            try {
                Words.isInputValid(startWord);
                Words.isInputValid(targetWord);
                Words.isExists(startWord, wordMap.get(startWord.length()), 0);
                Words.isExists(targetWord, wordMap.get(targetWord.length()), 1);
                Words.isSameLength(startWord, targetWord);
                long startTime = System.nanoTime();
                Result res = UCS.searchUCS(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();
                displayResult(res, startTime, endTime);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class GreedyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String startWord = startWordField.getText().toLowerCase();
            String targetWord = targetWordField.getText().toLowerCase();

            try {
                Words.isInputValid(startWord);
                Words.isInputValid(targetWord);
                Words.isExists(startWord, wordMap.get(startWord.length()), 0);
                Words.isExists(targetWord, wordMap.get(targetWord.length()), 1);
                Words.isSameLength(startWord, targetWord);
                long startTime = System.nanoTime();
                Result res = GreedySearch.searchGreedySearch(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();
                displayResult(res, startTime, endTime);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class AStarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String startWord = startWordField.getText().toLowerCase();
            String targetWord = targetWordField.getText().toLowerCase();

            try {
                Words.isInputValid(startWord);
                Words.isInputValid(targetWord);
                Words.isExists(startWord, wordMap.get(startWord.length()), 0);
                Words.isExists(targetWord, wordMap.get(targetWord.length()), 1);
                Words.isSameLength(startWord, targetWord);
                long startTime = System.nanoTime();
                Result res = AStar.searchAStar(startWord, targetWord, wordMap.get(startWord.length()));
                long endTime = System.nanoTime();
                displayResult(res, startTime, endTime);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void displayResult(Result res, long startTime, long endTime) {
        List<List<String>> paths = res.getPaths();
        if (paths.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No path found.", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder result = new StringBuilder();
            result.append("Number of nodes visited: ").append(res.getVisited()).append("\n");
            result.append("Number of paths found: ").append(paths.size()).append("\n");
            for (List<String> path : paths) {
                result.append(path).append("\n");
            }
            result.append("Time taken: ").append(Duration.ofNanos(endTime - startTime).toMillis()).append("ms");
            
            // Create a JTextArea to hold the result
            JTextArea textArea = new JTextArea(result.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            
            // Wrap the JTextArea in a JScrollPane to enable scrolling
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300)); // Set the maximum size here
            
            // Show the JScrollPane in the option pane
            JOptionPane.showMessageDialog(frame, scrollPane, "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
