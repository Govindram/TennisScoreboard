package com.govind.tennis.scoreboard.utils;

public enum GameScoreEnum {

    S0("0", 1),
    S15("15", 2),
    S30("30", 3),
    S40("40", 4),
    S40A("40-A", 5), //advantage
    S40W("40-W", 6); //win

    private String value;

    private int point;

    GameScoreEnum(String value, int point) {
        this.value = value;
        this.point = point;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
