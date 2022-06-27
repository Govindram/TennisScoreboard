package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.bo.score.GameScore;
import com.govind.tennis.scoreboard.bo.score.Score;
import com.govind.tennis.scoreboard.bo.scoreboard.SetScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetTieScoreBoard;
import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import com.govind.tennis.scoreboard.utils.GameScoreEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetScoreProcessor  extends BaseScoreProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(SetScoreProcessor.class);

    private static final int MIN_POINTS_TO_WIN_SET = 6;
    private static final int MIN_POINTS_DIFFERENCES_TO_WIN_SET = 2;

    @Override
    public void process(ScoreBoardContext context) {

        // process the standard (without tie) game match result
        if (context.getCurrentSetTieScoreBoard() == null) {
            if (context.getCurrentSetScoreBoard() == null) {
                LOGGER.info("Creating Set Score");
                context.setCurrentSetScoreBoard(new SetScoreBoard());
            }
            SetScoreBoard setScoreBoard = context.getCurrentSetScoreBoard();
            //calculate new set score
            Score newScore = calculateSetScore(setScoreBoard.getCurrentGameSetScore(), context.getCurrentGameScoreBoard().getCurrentGameScore());
            setScoreBoard.getGameSetScoreHistory().add(newScore);
            setScoreBoard.setCurrentGameSetScore(newScore);
            // Add the current GameScoreboard to history and reset the current game which is finished
            setScoreBoard.getGameScoreBoardsHistroy().add(context.getCurrentGameScoreBoard());
            context.setCurrentGameScoreBoard(null);
            LOGGER.info("Set score : Player one : {}  Player Two : {}", newScore.getPlayerOne(), newScore.getPlayerTwo());
            // if the score reaches a tie, then create tie scoreboard
            if (isSetTie(newScore)) {
                LOGGER.info("Set score tie");
                context.setCurrentSetTieScoreBoard(new SetTieScoreBoard());
            } else if (isSetWon(newScore)) { // if the set is won then go to the next processor
                LOGGER.info("Set score won");
                nextScoreProcessor.process(context);
            }
        } else { // process tie match result
            LOGGER.info("Set score won with a tie match");
            context.getCurrentSetScoreBoard().setSetTieScoreBoard(context.getCurrentSetTieScoreBoard());
            context.setCurrentSetTieScoreBoard(null);//Tie game finished so reset
            nextScoreProcessor.process(context);
        }
    }

    private boolean isSetTie(Score setScore) {
        return setScore.getPlayerOne() == MIN_POINTS_TO_WIN_SET && setScore.getPlayerTwo() == MIN_POINTS_TO_WIN_SET;
    }

    private boolean isSetWon(Score setScore) {
        return ((setScore.getPlayerOne() >= MIN_POINTS_TO_WIN_SET && (setScore.getPlayerOne() - setScore.getPlayerTwo()) >= MIN_POINTS_DIFFERENCES_TO_WIN_SET) ||
                (setScore.getPlayerTwo() >= MIN_POINTS_TO_WIN_SET && (setScore.getPlayerTwo() - setScore.getPlayerOne()) >= MIN_POINTS_DIFFERENCES_TO_WIN_SET));
    }

    private Score calculateSetScore(Score currentGameSetScore, GameScore gameScore) {
        Score newScore = new Score(currentGameSetScore.getPlayerOne(), currentGameSetScore.getPlayerTwo());
        if (gameScore.getPlayerOne().equals(GameScoreEnum.S40W)) {
            newScore.setPlayerOne(currentGameSetScore.getPlayerOne() + 1);
        } else {
            newScore.setPlayerTwo(currentGameSetScore.getPlayerTwo() + 1);
        }
        return newScore;
    }
}
