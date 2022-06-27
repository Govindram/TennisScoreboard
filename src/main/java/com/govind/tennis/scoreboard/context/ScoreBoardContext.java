package com.govind.tennis.scoreboard.context;

import com.govind.tennis.scoreboard.bo.scoreboard.GameScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.MatchScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetTieScoreBoard;
import com.govind.tennis.scoreboard.utils.Player;

import java.util.Properties;

public class ScoreBoardContext {

    private Player currentGameWinner;
    private GameScoreBoard currentGameScoreBoard;
    private SetScoreBoard currentSetScoreBoard;
    private SetTieScoreBoard currentSetTieScoreBoard;
    private MatchScoreBoard currentMatchScoreBoard;
    private boolean hasMatchEnded;
    private Properties properties;

    public Player getCurrentGameWinner() {
        return currentGameWinner;
    }

    public void setCurrentGameWinner(Player currentGameWinner) {
        this.currentGameWinner = currentGameWinner;
    }

    public GameScoreBoard getCurrentGameScoreBoard() {
        return currentGameScoreBoard;
    }

    public void setCurrentGameScoreBoard(GameScoreBoard currentGameScoreBoard) {
        this.currentGameScoreBoard = currentGameScoreBoard;
    }

    public SetScoreBoard getCurrentSetScoreBoard() {
        return currentSetScoreBoard;
    }

    public void setCurrentSetScoreBoard(SetScoreBoard currentSetScoreBoard) {
        this.currentSetScoreBoard = currentSetScoreBoard;
    }

    public MatchScoreBoard getCurrentMatchScoreBoard() {
        return currentMatchScoreBoard;
    }

    public void setCurrentMatchScoreBoard(MatchScoreBoard currentMatchScoreBoard) {
        this.currentMatchScoreBoard = currentMatchScoreBoard;
    }

    public SetTieScoreBoard getCurrentSetTieScoreBoard() {
        return currentSetTieScoreBoard;
    }

    public void setCurrentSetTieScoreBoard(SetTieScoreBoard currentSetTieScoreBoard) {
        this.currentSetTieScoreBoard = currentSetTieScoreBoard;
    }

    public boolean hasMatchEnded() {
        return hasMatchEnded;
    }

    public void setHasMatchEnded(boolean hasMatchEnded) {
        this.hasMatchEnded = hasMatchEnded;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
