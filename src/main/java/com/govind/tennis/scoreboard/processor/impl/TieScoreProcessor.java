package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.bo.score.Score;
import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import com.govind.tennis.scoreboard.utils.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TieScoreProcessor extends BaseScoreProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(TieScoreProcessor.class);

    private static final int MIN_POINTS_TO_WIN_TIE = 7;
    private static final int MIN_POINTS_DIFFERENCES_TO_WIN_TIE = 2;

    @Override
    public void process(ScoreBoardContext context) {
        if (context.getCurrentSetTieScoreBoard() == null) {
            nextScoreProcessor.process(context);
        } else {
            Player winner = context.getCurrentGameWinner();
            Score newScore = calculateNewScore(context.getCurrentSetTieScoreBoard().getCurrentSetTieScore(), winner);
            context.getCurrentSetTieScoreBoard().getSetTieScoreHistory().add(newScore);
            context.getCurrentSetTieScoreBoard().setCurrentSetTieScore(newScore);
            LOGGER.info("Set tie match score : Player one : {}  Player Two : {}", newScore.getPlayerOne(), newScore.getPlayerTwo());
            if (isTieGameWon(newScore)) {
                LOGGER.info("Set tie match won");
                nextScoreProcessor.process(context);
            }
        }
    }

    private boolean isTieGameWon(Score setScore) {
        return ((setScore.getPlayerOne() >= MIN_POINTS_TO_WIN_TIE && (setScore.getPlayerOne() - setScore.getPlayerTwo()) >= MIN_POINTS_DIFFERENCES_TO_WIN_TIE) ||
                (setScore.getPlayerTwo() >= MIN_POINTS_TO_WIN_TIE && (setScore.getPlayerTwo() - setScore.getPlayerOne()) >= MIN_POINTS_DIFFERENCES_TO_WIN_TIE));
    }

    private Score calculateNewScore(Score currentSetTieScore, Player winner) {
        Score newScore = new Score(currentSetTieScore.getPlayerOne(), currentSetTieScore.getPlayerTwo());
        if (Player.ONE.equals(winner)) {
            newScore.setPlayerOne(currentSetTieScore.getPlayerOne() + 1);
        } else {
            newScore.setPlayerTwo(currentSetTieScore.getPlayerTwo() + 1);
        }
        return newScore;
    }
}
