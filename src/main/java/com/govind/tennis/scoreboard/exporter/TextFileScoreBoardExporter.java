package com.govind.tennis.scoreboard.exporter;

import com.govind.tennis.scoreboard.bo.score.Score;
import com.govind.tennis.scoreboard.bo.scoreboard.GameScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.MatchScoreBoard;
import com.govind.tennis.scoreboard.bo.scoreboard.SetScoreBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TextFileScoreBoardExporter implements ScoreBoardExporter {

    private static Logger LOGGER = LoggerFactory.getLogger(TextFileScoreBoardExporter.class);

    public static final String EXPORTER_TYPE = "txt";
    @Override
    public void export(MatchScoreBoard matchScoreBoard) {
        ArrayList<Score> match = matchScoreBoard.getMatchScoreHistory();
        ArrayList<SetScoreBoard> setScoreBoards = matchScoreBoard.getGameSetScoreBoards();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("scoreboard.txt"), "utf-8"))) {

            for (int set = 0; set < match.size(); set++) {
                Score score = match.get(set);

                // get set scoreboard
                SetScoreBoard setScoreBoard = setScoreBoards.get(set);
                writer.write(String.format("Set %s : Player One Score :%s. Player Two Score: %s.",set+1 , setScoreBoard.getCurrentGameSetScore().getPlayerOne(),
                        setScoreBoard.getCurrentGameSetScore().getPlayerTwo()));
                // get set TieScoreBoard if present
                if (setScoreBoard.getSetTieScoreBoard() != null) {
                    writer.newLine();
                    writer.write(String.format("Tie score: Player One Score :%s. Player Two Score: %s.", setScoreBoard.getSetTieScoreBoard().getCurrentSetTieScore().getPlayerOne(),
                            setScoreBoard.getSetTieScoreBoard().getCurrentSetTieScore().getPlayerTwo()));
                }
                writer.newLine();
                // get set TieScoreBoard if present
                writer.write(String.format("Match score : Player One Score :%s. Player Two Score: %s.", score.getPlayerOne(),
                        score.getPlayerTwo()));
                writer.newLine();
                // write Match score
                ArrayList<Score> gameSetHistroy = setScoreBoard.getGameSetScoreHistory();
                List<GameScoreBoard> gameScoreBoardList = setScoreBoard.getGameScoreBoardsHistroy();
                writer.write("==============================================================");
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Unable to export scoreboard to text file", e);
        }
    }
}
