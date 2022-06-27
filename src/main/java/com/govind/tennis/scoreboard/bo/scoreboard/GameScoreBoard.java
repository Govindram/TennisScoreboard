package com.govind.tennis.scoreboard.bo.scoreboard;

import com.govind.tennis.scoreboard.bo.score.GameScore;
import com.govind.tennis.scoreboard.utils.GameScoreEnum;

import java.util.ArrayList;

public class GameScoreBoard {

    private GameScore currentGameScore;
    private ArrayList<GameScore> gameScoreHistory;

    public GameScoreBoard() {
        this.currentGameScore = new GameScore(GameScoreEnum.S0,GameScoreEnum.S0);
        this.gameScoreHistory = new ArrayList<>();
    }

    public GameScore getCurrentGameScore() {
        return currentGameScore;
    }

    public void setCurrentGameScore(GameScore currentGameScore) {
        this.currentGameScore = currentGameScore;
    }

    public ArrayList<GameScore> getGameScoreHistory() {
        return gameScoreHistory;
    }

    public void setGameScoreHistory(ArrayList<GameScore> gameScoreHistory) {
        this.gameScoreHistory = gameScoreHistory;
    }
}
