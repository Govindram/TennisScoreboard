package com.govind.tennis.scoreboard.processor.impl;

import com.govind.tennis.scoreboard.processor.ScoreProcessor;

public abstract class BaseScoreProcessor implements ScoreProcessor {

    protected ScoreProcessor nextScoreProcessor;

    public ScoreProcessor setNext(ScoreProcessor nextScoreProcessor) {
        this.nextScoreProcessor = nextScoreProcessor;
        return this.nextScoreProcessor;
    }
}
