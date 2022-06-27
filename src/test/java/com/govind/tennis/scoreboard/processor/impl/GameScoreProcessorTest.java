package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.context.ScoreBoardContext;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.govind.tennis.scoreboard.processor.ScoreProcessor;
import com.govind.tennis.scoreboard.utils.GameScoreEnum;
import com.govind.tennis.scoreboard.utils.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class GameScoreProcessorTest {

    private ScoreProcessor scoreProcessor;
    private SetScoreProcessor setScoreProcessor;

    @Before
    public void setUp() {
        setScoreProcessor = mock(SetScoreProcessor.class);
        doNothing().when(setScoreProcessor).process(any());
        scoreProcessor = new GameScoreProcessor();
        scoreProcessor.setNext(setScoreProcessor);
    }

    @Test
    public void testStandardWinWithoutDeuce() {

        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.ONE, Player.ONE};

        process(scoreBoardContext, mockResults);

        assertEquals(GameScoreEnum.S40W, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerOne());
        assertEquals(GameScoreEnum.S30, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerTwo());
        Mockito.verify(setScoreProcessor, times(1)).process(any());
    }

    @Test
    public void testDeuce() {

        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE};

        process(scoreBoardContext, mockResults);

        assertEquals(GameScoreEnum.S40, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerOne());
        assertEquals(GameScoreEnum.S40, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerTwo());
        Mockito.verify(setScoreProcessor, times(0)).process(any());
    }

    @Test
    public void testDeuceAdvantage() {

        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO};

        process(scoreBoardContext, mockResults);

        assertEquals(GameScoreEnum.S40, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerOne());
        assertEquals(GameScoreEnum.S40A, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerTwo());
        Mockito.verify(setScoreProcessor, times(0)).process(any());
    }

    @Test
    public void testDeuceAdvantageDeuce() {

        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE};

        process(scoreBoardContext, mockResults);

        assertEquals(GameScoreEnum.S40, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerOne());
        assertEquals(GameScoreEnum.S40, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerTwo());
        assertEquals(8, scoreBoardContext.getCurrentGameScoreBoard().getGameScoreHistory().size());
        Mockito.verify(setScoreProcessor, times(0)).process(any());
    }

    @Test
    public void testDeuceAdvantageDeuceAdvantage() {

        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.ONE};

        process(scoreBoardContext, mockResults);

        assertEquals(GameScoreEnum.S40A, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerOne());
        assertEquals(GameScoreEnum.S40, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerTwo());
        assertEquals(9, scoreBoardContext.getCurrentGameScoreBoard().getGameScoreHistory().size());
        Mockito.verify(setScoreProcessor, times(0)).process(any());
    }

    @Test
    public void testDeuceAdvantageDeuceAdvantageWin() {

        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        Player[] mockResults = {Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.TWO, Player.ONE, Player.ONE, Player.ONE};

        process(scoreBoardContext, mockResults);

        assertEquals(GameScoreEnum.S40W, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerOne());
        assertEquals(GameScoreEnum.S40, scoreBoardContext.getCurrentGameScoreBoard().getCurrentGameScore().getPlayerTwo());
        assertEquals(10, scoreBoardContext.getCurrentGameScoreBoard().getGameScoreHistory().size());
        Mockito.verify(setScoreProcessor, times(1)).process(any());
    }

    private void process(ScoreBoardContext scoreBoardContext, Player[] mockResults) {
        for (Player winner : mockResults) {
            scoreBoardContext.setCurrentGameWinner(winner);
            scoreProcessor.process(scoreBoardContext);
        }
    }

}