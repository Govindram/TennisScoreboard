package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.bo.score.GameScore;
import com.govind.tennis.scoreboard.bo.score.Score;
import com.govind.tennis.scoreboard.bo.scoreboard.GameScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetTieScoreBoard;
import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import com.govind.tennis.scoreboard.processor.ScoreProcessor;
import com.govind.tennis.scoreboard.utils.GameScoreEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SetScoreProcessorTest {

    private ScoreProcessor scoreProcessor;
    private MatchScoreProcessor matchScoreProcessor;

    @Before
    public void setUp() {
        matchScoreProcessor = mock(MatchScoreProcessor.class);
        doNothing().when(matchScoreProcessor).process(any());
        scoreProcessor = new SetScoreProcessor();
        scoreProcessor.setNext(matchScoreProcessor);
    }

    @Test
    public void testSetScoreUpdate() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        GameScoreBoard mockGameScoreBoard = new GameScoreBoard();
        mockGameScoreBoard.setCurrentGameScore(new GameScore(GameScoreEnum.S30, GameScoreEnum.S40W));
        mockGameScoreBoard.getGameScoreHistory().add(new GameScore(GameScoreEnum.S0, GameScoreEnum.S15));
        mockGameScoreBoard.getGameScoreHistory().add(new GameScore(GameScoreEnum.S15, GameScoreEnum.S15));
        mockGameScoreBoard.getGameScoreHistory().add(new GameScore(GameScoreEnum.S15, GameScoreEnum.S30));
        mockGameScoreBoard.getGameScoreHistory().add(new GameScore(GameScoreEnum.S30, GameScoreEnum.S30));
        mockGameScoreBoard.getGameScoreHistory().add(new GameScore(GameScoreEnum.S30, GameScoreEnum.S40));
        mockGameScoreBoard.getGameScoreHistory().add(new GameScore(GameScoreEnum.S30, GameScoreEnum.S40W));
        scoreBoardContext.setCurrentGameScoreBoard(mockGameScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertEquals(1, scoreBoardContext.getCurrentSetScoreBoard().getCurrentGameSetScore().getPlayerTwo());
        assertEquals(1, scoreBoardContext.getCurrentSetScoreBoard().getGameScoreBoardsHistroy().size());
        assertEquals(6, scoreBoardContext.getCurrentSetScoreBoard().getGameScoreBoardsHistroy().get(0).getGameScoreHistory().size());
        assertNull(scoreBoardContext.getCurrentSetTieScoreBoard());
        assertNull(scoreBoardContext.getCurrentGameScoreBoard());
        Mockito.verify(matchScoreProcessor, times(0)).process(any());
    }


    @Test
    public void testSetScoreWin() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        GameScoreBoard mockGameScoreBoard = new GameScoreBoard();
        SetScoreBoard mockSetScoreBoard = new SetScoreBoard();
        mockGameScoreBoard.setCurrentGameScore(new GameScore(GameScoreEnum.S40W, GameScoreEnum.S30));
        scoreBoardContext.setCurrentGameScoreBoard(mockGameScoreBoard);
        mockSetScoreBoard.setCurrentGameSetScore(new Score(5 , 4));
        scoreBoardContext.setCurrentSetScoreBoard(mockSetScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertEquals(6, scoreBoardContext.getCurrentSetScoreBoard().getCurrentGameSetScore().getPlayerOne());
        assertNull(scoreBoardContext.getCurrentSetTieScoreBoard());
        Mockito.verify(matchScoreProcessor, times(1)).process(any());
    }

    @Test
    public void testSetScore() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        GameScoreBoard mockGameScoreBoard = new GameScoreBoard();
        SetScoreBoard mockSetScoreBoard = new SetScoreBoard();
        mockGameScoreBoard.setCurrentGameScore(new GameScore(GameScoreEnum.S30, GameScoreEnum.S40W));
        scoreBoardContext.setCurrentGameScoreBoard(mockGameScoreBoard);
        mockSetScoreBoard.setCurrentGameSetScore(new Score(5 , 5 ));
        scoreBoardContext.setCurrentSetScoreBoard(mockSetScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertEquals(6, scoreBoardContext.getCurrentSetScoreBoard().getCurrentGameSetScore().getPlayerTwo());
        assertNull(scoreBoardContext.getCurrentSetTieScoreBoard());
        Mockito.verify(matchScoreProcessor, times(0)).process(any());
    }

    @Test
    public void testSetScoreWinForBorderCondition() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        GameScoreBoard mockGameScoreBoard = new GameScoreBoard();
        SetScoreBoard mockSetScoreBoard = new SetScoreBoard();
        mockGameScoreBoard.setCurrentGameScore(new GameScore(GameScoreEnum.S30, GameScoreEnum.S40W));
        scoreBoardContext.setCurrentGameScoreBoard(mockGameScoreBoard);
        mockSetScoreBoard.setCurrentGameSetScore(new Score(5 , 6 ));
        scoreBoardContext.setCurrentSetScoreBoard(mockSetScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertEquals(7, scoreBoardContext.getCurrentSetScoreBoard().getCurrentGameSetScore().getPlayerTwo());
        assertNull(scoreBoardContext.getCurrentSetTieScoreBoard());
        Mockito.verify(matchScoreProcessor, times(1)).process(any());
    }

    @Test
    public void testSetScoreTie() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        GameScoreBoard mockGameScoreBoard = new GameScoreBoard();
        SetScoreBoard mockSetScoreBoard = new SetScoreBoard();
        mockGameScoreBoard.setCurrentGameScore(new GameScore(GameScoreEnum.S30, GameScoreEnum.S40W));
        scoreBoardContext.setCurrentGameScoreBoard(mockGameScoreBoard);
        mockSetScoreBoard.setCurrentGameSetScore(new Score(6 , 5 ));
        scoreBoardContext.setCurrentSetScoreBoard(mockSetScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertEquals(6, scoreBoardContext.getCurrentSetScoreBoard().getCurrentGameSetScore().getPlayerTwo());
        assertNotNull(scoreBoardContext.getCurrentSetTieScoreBoard());
        Mockito.verify(matchScoreProcessor, times(0)).process(any());
    }

    @Test
    public void testTieScoreResult() {
        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();
        SetTieScoreBoard mockSetTieScoreBoard = new SetTieScoreBoard();
        SetScoreBoard mockSetScoreBoard = new SetScoreBoard();
        scoreBoardContext.setCurrentSetScoreBoard(mockSetScoreBoard);
        mockSetTieScoreBoard.setCurrentSetTieScore(new Score(7 , 4 ));
        scoreBoardContext.setCurrentSetTieScoreBoard(mockSetTieScoreBoard);

        scoreProcessor.process(scoreBoardContext);

        assertNull(scoreBoardContext.getCurrentSetTieScoreBoard());
        assertNotNull(scoreBoardContext.getCurrentSetScoreBoard().getSetTieScoreBoard());
        Mockito.verify(matchScoreProcessor, times(1)).process(any());
    }
}