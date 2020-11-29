package com.example.tapthedot;

import java.util.Comparator;

public class gameScore{
    private String userName;
    private int score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public gameScore(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }
    public String toString()
    {
        return this.userName + ": " + this.score;
    }
}
// Class to compare gameScores by score
class Sortbyscore implements Comparator<gameScore>
{
    @Override
    public int compare(gameScore s1, gameScore s2) {
            if (s1.getScore() <= s2.getScore()) return 1;
            else return -1;

    }
}