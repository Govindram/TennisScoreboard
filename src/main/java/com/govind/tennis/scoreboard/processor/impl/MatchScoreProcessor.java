package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.bo.score.Score;
import com.govind.tennis.scoreboard.bo.scoreboard.MatchScoreBoard;
import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatchScoreProcessor extends BaseScoreProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(MatchScoreProcessor.class);

    private static final int MIN_POINTS_TO_WIN_MATCH= 2;

    @Override
    public void process(ScoreBoardContext context) {
        if (context.getCurrentMatchScoreBoard() == null) {
            context.setCurrentMatchScoreBoard(new MatchScoreBoard());
        }
        Score setTieScore = null;
        // get tie score if present
        if (context.getCurrentSetScoreBoard().getSetTieScoreBoard() != null) {
            setTieScore =  context.getCurrentSetScoreBoard().getSetTieScoreBoard().getCurrentSetTieScore();
        }

        MatchScoreBoard matchScoreBoard = context.getCurrentMatchScoreBoard();
        Score newMatchScore = calculateNewScore(matchScoreBoard.getCurrentMatchScore(),
                context.getCurrentSetScoreBoard().getCurrentGameSetScore(), setTieScore);
        matchScoreBoard.getMatchScoreHistory().add(newMatchScore);
        matchScoreBoard.setCurrentMatchScore(newMatchScore);

        LOGGER.info("Match score : Player one : {}  Player Two : {}", newMatchScore.getPlayerOne(), newMatchScore.getPlayerTwo());

        matchScoreBoard.getGameSetScoreBoards().add(context.getCurrentSetScoreBoard());
        context.setCurrentSetScoreBoard(null);

        if (newMatchScore.getPlayerOne() >= MIN_POINTS_TO_WIN_MATCH  || newMatchScore.getPlayerTwo() >= MIN_POINTS_TO_WIN_MATCH) {
            LOGGER.info("Match won final score : Player one : {}  Player Two : {}", newMatchScore.getPlayerOne(), newMatchScore.getPlayerTwo());
            context.setHasMatchEnded(true);
        }
    }

    private Score calculateNewScore(Score currentMatchScore, Score currentGameSetScore, Score tieScore) {
        Score score  = new Score(currentMatchScore.getPlayerOne(), currentMatchScore.getPlayerTwo());
        if (currentGameSetScore.getPlayerOne() > currentGameSetScore.getPlayerTwo()) {
            score.setPlayerOne(currentMatchScore.getPlayerOne() + 1);
        } else if (currentGameSetScore.getPlayerOne() < currentGameSetScore.getPlayerTwo()) {
            score.setPlayerTwo(currentMatchScore.getPlayerTwo() + 1);
        } else if (tieScore.getPlayerOne() > tieScore.getPlayerTwo()){
            score.setPlayerOne(currentMatchScore.getPlayerOne() + 1);
        } else if (tieScore.getPlayerOne() < tieScore.getPlayerTwo()) {
            score.setPlayerTwo(currentMatchScore.getPlayerTwo() + 1);
        }
        return score;
    }
}
