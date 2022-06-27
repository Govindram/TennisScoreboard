package com.govind.tennis.scoreboard.utils;

public enum Player {
    ONE,
    TWO;

    public Player opposite(){
        if(this.equals(ONE)) {
            return TWO;
        } else {
            return ONE;
        }
    }
}
