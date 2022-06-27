#Tennis Scoreboard

This program simulates and calculates Tennis Scoreboard.

it contains 4 Scoreboard Business Objects and 4 Scoreboard processor

##ScoreBoard Business Objects
1) GameScoreBoard : This contains score board of a game  along with its history.
2) SetScoreBoard : This contains score board of a set  along with its history.
3) SetTieScoreBoard : This contains score board of Tie score in a set along  with its history.
4) MatchScoreBoard : This contains score board of Match along with its history.

##ScoreBoard Processor
1) GameScoreProcessor : This process and maintains score of a game.
2) SetScoreProcessor : This Processor determines Set score with help of the result from the gameProcessor and TieScoreProcessor.
3) TieScoreProcessor : This Processor determines tie match score.
4) MatchScoreProcessor : This Processor determine match score and with help of the result from the SetScoreProcessor and TieScoreProcessor.
