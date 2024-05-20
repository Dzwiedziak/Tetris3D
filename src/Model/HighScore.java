package Model;

import java.io.Serializable;

public class HighScore{
    private String nickname;
    private int score;

    public HighScore(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}