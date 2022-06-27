package com.govind.tennis.scoreboard.bo.scoreboard;

import com.govind.tennis.scoreboard.bo.score.Score;

import java.util.ArrayList;

public class MatchScoreBoard {

    private Score currentMatchScore;
    private ArrayList<Score> matchScoreHistory;
    private ArrayList<SetScoreBoard> gameSetScoreBoards ;

    public MatchScoreBoard() {
        currentMatchScore = new Score(0,0);
        matchScoreHistory = new ArrayList<>();
        gameSetScoreBoards = new ArrayList<>();
    }

    public Score getCurrentMatchScore() {
        return currentMatchScore;
    }

    public void setCurrentMatchScore(Score currentMatchScore) {
        this.currentMatchScore = currentMatchScore;
    }

    public ArrayList<Score> getMatchScoreHistory() {
        return matchScoreHistory;
    }

    public void setMatchScoreHistory(ArrayList<Score> matchScoreHistory) {
        this.matchScoreHistory = matchScoreHistory;
    }

    public ArrayList<SetScoreBoard> getGameSetScoreBoards() {
        return gameSetScoreBoards;
    }

    public void setGameSetScoreBoards(ArrayList<SetScoreBoard> gameSetScoreBoards) {
        this.gameSetScoreBoards = gameSetScoreBoards;
    }
}
