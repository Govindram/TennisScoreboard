package com.govind.tennis.scoreboard.bo.scoreboard;

import com.govind.tennis.scoreboard.bo.score.Score;

import java.util.ArrayList;

public class SetTieScoreBoard {
    private Score currentSetTieScore;
    private ArrayList<Score> setTieScoreHistory;

    public SetTieScoreBoard() {
        currentSetTieScore = new Score(0,0);
        setTieScoreHistory = new ArrayList<>();
    }

    public Score getCurrentSetTieScore() {
        return currentSetTieScore;
    }

    public void setCurrentSetTieScore(Score currentSetTieScore) {
        this.currentSetTieScore = currentSetTieScore;
    }

    public ArrayList<Score> getSetTieScoreHistory() {
        return setTieScoreHistory;
    }

    public void setSetTieScoreHistory(ArrayList<Score> setTieScoreHistory) {
        this.setTieScoreHistory = setTieScoreHistory;
    }
}
