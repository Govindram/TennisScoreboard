package com.govind.tennis.scoreboard.bo.score;

import com.govind.tennis.scoreboard.utils.GameScoreEnum;

public class GameScore {

    private GameScoreEnum playerOne;

    private GameScoreEnum playerTwo;



    public GameScore(GameScoreEnum playerOne, GameScoreEnum playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public GameScoreEnum getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(GameScoreEnum playerOne) {
        this.playerOne = playerOne;
    }

    public GameScoreEnum getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(GameScoreEnum playerTwo) {
        this.playerTwo = playerTwo;
    }
}
