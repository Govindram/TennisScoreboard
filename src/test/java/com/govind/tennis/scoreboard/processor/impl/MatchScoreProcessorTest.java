package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.bo.score.Score;
import com.govind.tennis.scoreboard.bo.scoreboard.MatchScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetTieScoreBoard;
import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import com.govind.tennis.scoreboard.processor.ScoreProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class MatchScoreProcessorTest {

    private ScoreProcessor scoreProcessor;

    @Before
    public void setUp() {
        scoreProcessor = new MatchScoreProcessor();
    }

    @Test
    public void testMatchScoreUpdate() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        SetScoreBoard setScoreBoard = new SetScoreBoard();
        setScoreBoard.setCurrentGameSetScore(new Score(4, 6));
        scoreBoardContext.setCurrentSetScoreBoard(setScoreBoard);

        scoreProcessor.process(scoreBoardContext);


        assertEquals(1, scoreBoardContext.getCurrentMatchScoreBoard().getCurrentMatchScore().getPlayerTwo());
        assertEquals(1, scoreBoardContext.getCurrentMatchScoreBoard().getGameSetScoreBoards().size());
        assertNull(scoreBoardContext.getCurrentSetScoreBoard());
        assertFalse(scoreBoardContext.hasMatchEnded());

    }

    @Test
    public void testMatchScoreEnded() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        SetScoreBoard setScoreBoard = new SetScoreBoard();
        MatchScoreBoard matchScoreBoard = new MatchScoreBoard();
        matchScoreBoard.setCurrentMatchScore(new Score(1, 1));
        setScoreBoard.setCurrentGameSetScore(new Score(4, 6));
        scoreBoardContext.setCurrentSetScoreBoard(setScoreBoard);
        scoreBoardContext.setCurrentMatchScoreBoard(matchScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertEquals(2, scoreBoardContext.getCurrentMatchScoreBoard().getCurrentMatchScore().getPlayerTwo());
        assertEquals(1, scoreBoardContext.getCurrentMatchScoreBoard().getCurrentMatchScore().getPlayerOne());
        assertNull(scoreBoardContext.getCurrentSetScoreBoard());
        assertTrue(scoreBoardContext.hasMatchEnded());

    }

    @Test
    public void testMatchScoreEndedWithTieResult() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();

        SetScoreBoard mockSetScoreBoard = new SetScoreBoard();
        MatchScoreBoard mockMatchScoreBoard = new MatchScoreBoard();
        SetTieScoreBoard mockSetTieScoreBoard = new SetTieScoreBoard();

        mockMatchScoreBoard.setCurrentMatchScore(new Score(1, 1));
        mockSetScoreBoard.setCurrentGameSetScore(new Score(6, 6));
        mockSetTieScoreBoard.setCurrentSetTieScore(new Score(8, 6));

        mockSetScoreBoard.setSetTieScoreBoard(mockSetTieScoreBoard);
        scoreBoardContext.setCurrentSetScoreBoard(mockSetScoreBoard);
        scoreBoardContext.setCurrentMatchScoreBoard(mockMatchScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertEquals(1, scoreBoardContext.getCurrentMatchScoreBoard().getCurrentMatchScore().getPlayerTwo());
        assertEquals(2, scoreBoardContext.getCurrentMatchScoreBoard().getCurrentMatchScore().getPlayerOne());
        assertNull(scoreBoardContext.getCurrentSetScoreBoard());
        assertTrue(scoreBoardContext.hasMatchEnded());

    }

}