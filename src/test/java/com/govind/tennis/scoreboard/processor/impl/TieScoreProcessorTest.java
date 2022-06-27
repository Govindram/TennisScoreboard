package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import com.govind.tennis.scoreboard.bo.scoreboard.SetTieScoreBoard;
import com.govind.tennis.scoreboard.processor.ScoreProcessor;
import com.govind.tennis.scoreboard.utils.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TieScoreProcessorTest {

    private ScoreProcessor scoreProcessor;
    private SetScoreProcessor setScoreProcessor;

    @Before
    public void setUp() {
        setScoreProcessor = mock(SetScoreProcessor.class);
        doNothing().when(setScoreProcessor).process(any());
        scoreProcessor = new TieScoreProcessor();
        scoreProcessor.setNext(setScoreProcessor);
    }

    @Test
    public void testTieScoreBoardUpdate() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();

        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.ONE, Player.ONE};

        scoreBoardContext.setCurrentSetTieScoreBoard(new SetTieScoreBoard());
        process(scoreBoardContext, mockResults);

        assertEquals(4, scoreBoardContext.getCurrentSetTieScoreBoard().getCurrentSetTieScore().getPlayerOne());
        assertEquals(2, scoreBoardContext.getCurrentSetTieScoreBoard().getCurrentSetTieScore().getPlayerTwo());
        assertEquals(6, scoreBoardContext.getCurrentSetTieScoreBoard().getSetTieScoreHistory().size());
        Mockito.verify(setScoreProcessor, times(0)).process(any());
    }

    @Test
    public void testTieScoreBoardWin() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();

        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.ONE, Player.ONE, Player.TWO, Player.ONE, Player.ONE, Player.ONE};

        scoreBoardContext.setCurrentSetTieScoreBoard(new SetTieScoreBoard());
        process(scoreBoardContext, mockResults);

        assertEquals(7, scoreBoardContext.getCurrentSetTieScoreBoard().getCurrentSetTieScore().getPlayerOne());
        assertEquals(3, scoreBoardContext.getCurrentSetTieScoreBoard().getCurrentSetTieScore().getPlayerTwo());
        assertEquals(10, scoreBoardContext.getCurrentSetTieScoreBoard().getSetTieScoreHistory().size());
        Mockito.verify(setScoreProcessor, times(1)).process(any());
    }

    @Test
    public void testTieScoreBoardExtendedWin() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();

        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.ONE, Player.TWO, Player.TWO,
                Player.ONE, Player.TWO, Player.TWO, Player.ONE, Player.ONE, Player.TWO, Player.TWO };

        scoreBoardContext.setCurrentSetTieScoreBoard(new SetTieScoreBoard());
        process(scoreBoardContext, mockResults);

        assertEquals(6, scoreBoardContext.getCurrentSetTieScoreBoard().getCurrentSetTieScore().getPlayerOne());
        assertEquals(8, scoreBoardContext.getCurrentSetTieScoreBoard().getCurrentSetTieScore().getPlayerTwo());
        assertEquals(14, scoreBoardContext.getCurrentSetTieScoreBoard().getSetTieScoreHistory().size());
        Mockito.verify(setScoreProcessor, times(1)).process(any());
    }

    private void process(ScoreBoardContext scoreBoardContext, Player[] mockResults) {
        for (Player winner : mockResults) {
            scoreBoardContext.setCurrentGameWinner(winner);
            scoreProcessor.process(scoreBoardContext);
        }
    }
}