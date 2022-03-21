package com.td.tictactoe;

public enum Parameters {
    PLAYER_NAME("Player"),
    DIFFICULTY(0),
    MODE("PvE"),
    EASY_NAME("Bob"),
    MEDIUM_NAME("Mike"),
    HARD_NAME("Timur"),
    SECOND_NAME("Someone");

    private String string;
    private int i;

    Parameters(String string) {
        this.string = string;
    }

    Parameters(int i) {
        this.i = i;
    }

    public String getString() {
        return string;
    }

    public int getInt() {
        return i;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setInt(int i) {
        this.i = i;
    }
}
