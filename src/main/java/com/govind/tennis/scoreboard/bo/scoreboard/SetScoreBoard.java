package com.govind.tennis.scoreboard.bo.scoreboard;

import com.govind.tennis.scoreboard.bo.score.Score;

import java.util.ArrayList;

public class SetScoreBoard {

    private Score currentGameSetScore;
    private ArrayList<Score> gameSetScoreHistory;
    private ArrayList<GameScoreBoard> gameScoreBoardsHistroy ;
    private SetTieScoreBoard setTieScoreBoard;

    public SetScoreBoard() {
        this.currentGameSetScore = new Score(0,0);
        this.gameSetScoreHistory = new ArrayList<>();
        this.gameScoreBoardsHistroy = new ArrayList<>();
    }

    public Score getCurrentGameSetScore() {
        return currentGameSetScore;
    }

    public void setCurrentGameSetScore(Score currentGameSetScore) {
        this.currentGameSetScore = currentGameSetScore;
    }

    public ArrayList<Score> getGameSetScoreHistory() {
        return gameSetScoreHistory;
    }

    public void setGameSetScoreHistory(ArrayList<Score> gameSetScoreHistory) {
        this.gameSetScoreHistory = gameSetScoreHistory;
    }

    public ArrayList<GameScoreBoard> getGameScoreBoardsHistroy() {
        return gameScoreBoardsHistroy;
    }

    public void setGameScoreBoardsHistroy(ArrayList<GameScoreBoard> gameScoreBoardsHistroy) {
        this.gameScoreBoardsHistroy = gameScoreBoardsHistroy;
    }

    public SetTieScoreBoard getSetTieScoreBoard() {
        return setTieScoreBoard;
    }

    public void setSetTieScoreBoard(SetTieScoreBoard setTieScoreBoard) {
        this.setTieScoreBoard = setTieScoreBoard;
    }
}
