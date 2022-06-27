package com.govind.tennis.scoreboard.processor;

import com.govind.tennis.scoreboard.context.ScoreBoardContext;

public interface ScoreProcessor {

    ScoreProcessor setNext(ScoreProcessor scoreProcessor);

    void process(ScoreBoardContext context);
}
