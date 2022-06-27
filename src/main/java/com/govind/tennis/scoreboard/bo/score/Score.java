package com.govind.tennis.scoreboard.bo.score;

public class Score {

    private int playerOne;

    private int playerTwo;

    public Score(int playerOne, int playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public int getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(int playerOne) {
        this.playerOne = playerOne;
    }

    public int getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(int playerTwo) {
        this.playerTwo = playerTwo;
    }
}
