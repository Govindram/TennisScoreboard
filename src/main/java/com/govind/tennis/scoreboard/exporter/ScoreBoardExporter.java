package com.govind.tennis.scoreboard.exporter;

import com.govind.tennis.scoreboard.bo.scoreboard.MatchScoreBoard;

public interface ScoreBoardExporter {
    void export(MatchScoreBoard matchScoreBoard);
}
