package com.govind.tennis.scoreboard.exporter;

public class ScoreBoardExporterFactory {

    public static final ScoreBoardExporter getExporter(String providerType) {
        switch (providerType) {
            case TextFileScoreBoardExporter.EXPORTER_TYPE:
            default:
                return new TextFileScoreBoardExporter();
        }
    }
}
