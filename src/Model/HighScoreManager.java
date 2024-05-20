package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreManager {
    private static final String FILENAME = "resources/highscores.txt";
    private static final int MAX_HIGH_SCORES = 3;

    public static void saveHighScores(List<HighScore> highScores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (HighScore score : highScores) {
                writer.println(score.getNickname() + "," + score.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<HighScore> loadHighScores() {
        List<HighScore> highScores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String nickname = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    highScores.add(new HighScore(nickname, score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return highScores;
    }

    public static void updateScores(List<HighScore> newScores) {
        List<HighScore> existingHighScores = loadHighScores();

        for (HighScore newScore : newScores) {
            boolean isUpdated = false;
            for (HighScore existingScore : existingHighScores) {
                if (existingScore.getNickname().equals(newScore.getNickname())) {
                    if (newScore.getScore() > existingScore.getScore()) {
                        existingScore.setScore(newScore.getScore());
                        isUpdated = true;
                    }
                    break;
                }
            }
            if (!isUpdated) {
                HighScore existing = existingHighScores.stream()
                        .filter(score -> score.getNickname().equals(newScore.getNickname()))
                        .findFirst()
                        .orElse(null);

                if (existing == null || newScore.getScore() > existing.getScore()) {
                    existingHighScores.remove(existing);
                    existingHighScores.add(newScore);
                }
            }
        }

        existingHighScores.sort(Comparator.comparingInt(HighScore::getScore).reversed());
        List<HighScore> topHighScores = existingHighScores.subList(0, Math.min(existingHighScores.size(), MAX_HIGH_SCORES));

        saveHighScores(topHighScores);
    }

    public static List<HighScore> getAllScores() {
        return loadHighScores();
    }

}
