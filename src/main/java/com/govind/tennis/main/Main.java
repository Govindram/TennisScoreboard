package com.govind.tennis.main;

import com.govind.tennis.scoreboard.context.ScoreBoardContext;
import com.govind.tennis.scoreboard.exporter.ScoreBoardExporter;
import com.govind.tennis.scoreboard.exporter.ScoreBoardExporterFactory;
import com.govind.tennis.scoreboard.processor.impl.GameScoreProcessor;
import com.govind.tennis.scoreboard.processor.impl.MatchScoreProcessor;
import com.govind.tennis.scoreboard.processor.ScoreProcessor;
import com.govind.tennis.scoreboard.processor.impl.SetScoreProcessor;
import com.govind.tennis.scoreboard.processor.impl.TieScoreProcessor;
import com.govind.tennis.scoreboard.provider.ResultProvider;
import com.govind.tennis.scoreboard.provider.ResultProviderFactory;
import com.govind.tennis.scoreboard.utils.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ScoreProcessor scoreProcessor = initScoreProcessor();

        ScoreBoardContext scoreBoardContext = new ScoreBoardContext();

        Properties properties = PropertiesHelper.loadProperties();

        ResultProvider provider = ResultProviderFactory.getResultProvider(properties.getProperty(PropertiesHelper.PROVIDER));

        do {
            scoreBoardContext.setCurrentGameWinner(provider.getWinner());
            LOGGER.info("Current Game Winner: {}", scoreBoardContext.getCurrentGameWinner());
            scoreProcessor.process(scoreBoardContext);
        } while(!scoreBoardContext.hasMatchEnded());

        ScoreBoardExporter exporter = ScoreBoardExporterFactory.getExporter(properties.getProperty(PropertiesHelper.EXPORTER));
        exporter.export(scoreBoardContext.getCurrentMatchScoreBoard());
    }

    private static ScoreProcessor initScoreProcessor() {
        ScoreProcessor scoreProcessor = new GameScoreProcessor();
        scoreProcessor.setNext(new TieScoreProcessor())
                 .setNext(new SetScoreProcessor())
                .setNext(new MatchScoreProcessor());
        return scoreProcessor;
    }
}
