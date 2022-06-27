package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.bo.score.GameScore;
import com.govind.tennis.scoreboard.bo.scoreboard.GameScoreBoard;
import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import com.govind.tennis.scoreboard.utils.GameScoreEnum;
import com.govind.tennis.scoreboard.utils.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GameScoreProcessor extends com.govind.tennis.scoreboard.processor.impl.BaseScoreProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(GameScoreProcessor.class);

    @Override
    public void process(ScoreBoardContext context) {
        if (context.getCurrentSetTieScoreBoard() == null) {
            if (context.getCurrentGameScoreBoard() == null) {
                LOGGER.info("Starting new Game");
                context.setCurrentGameScoreBoard(new GameScoreBoard());
            }
            Player winner = context.getCurrentGameWinner();
            GameScore currentScore = context.getCurrentGameScoreBoard().getCurrentGameScore();
            //calculate new score
            GameScore newScore = calculateScore(currentScore, winner);
            //update context
            context.getCurrentGameScoreBoard().setCurrentGameScore(newScore);
            context.getCurrentGameScoreBoard().getGameScoreHistory().add(newScore);
            LOGGER.info("Game score : Player one : {}  Player Two : {}", newScore.getPlayerOne().getValue(), newScore.getPlayerTwo().getValue());
            // check if the game is won, if yes  call the set processor.
            if (newScore.getPlayerOne().equals(GameScoreEnum.S40W) ||
                    newScore.getPlayerTwo().equals(GameScoreEnum.S40W)) {
                LOGGER.info("Game Won");
                this.nextScoreProcessor.process(context);
            }
        } else { // if the tie game in progress go to next processor to handle tie scoreboard
            this.nextScoreProcessor.process(context);
        }
    }

    private GameScore calculateScore(GameScore currentGameScore, Player winner) {
        GameScoreEnum winnerScore = getGameScoreByPlayer(winner, currentGameScore);
        GameScoreEnum loserScore = getGameScoreByPlayer(winner.opposite(), currentGameScore);

        if (GameScoreEnum.S0.equals(winnerScore)) {
            winnerScore = GameScoreEnum.S15;
        } else if (GameScoreEnum.S15.equals(winnerScore)) {
            winnerScore = GameScoreEnum.S30;
        } else if (GameScoreEnum.S30.equals(winnerScore)) {
            winnerScore =  GameScoreEnum.S40;
        } else if (GameScoreEnum.S40.equals(winnerScore) && loserScore.getPoint() < GameScoreEnum.S40.getPoint()) {
            winnerScore =  GameScoreEnum.S40W;
        } else if (GameScoreEnum.S40.equals(winnerScore) && GameScoreEnum.S40.equals(loserScore)) {//advantage
            winnerScore =  GameScoreEnum.S40A;
        } else if (GameScoreEnum.S40.equals(winnerScore) && GameScoreEnum.S40A.equals(loserScore)) {//deuce
            loserScore = GameScoreEnum.S40; //reset to deuce on loser score
        } else if (GameScoreEnum.S40A.equals(winnerScore) && GameScoreEnum.S40.equals(loserScore)) {//advantage win
            winnerScore = GameScoreEnum.S40W;
        }

        if (winner.equals(Player.ONE)) {
            return  new GameScore(winnerScore, loserScore);
        } else {
            return  new GameScore(loserScore, winnerScore);
        }
    }

    private GameScoreEnum getGameScoreByPlayer(Player player, GameScore gameScore) {
        if (Player.ONE.equals(player)) {
            return gameScore.getPlayerOne();
        } else {
            return gameScore.getPlayerTwo();
        }
    }
}
