package com.td.tictactoe;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class LabelProcessor {
    private final String modeLabelText = "Game mode: ";
    private final String difLabelText = "Difficulty: ";
    private final RadioMenuItem pve, pvp, easy, medium, hard;
    private final Label difLabel, modeLabel, wins, draws, losses, vs;
    private final TextField[] playerList;
    private final Button listOfPlayers;
    private final HBox hBox1, hBox2, hBox3;

    private Tooltip easyT, mediumT, hardT, someoneT, playerT;
    private final String easyTText = Parameters.EASY_NAME.getString() + " is the easiest opponent, it's easy to win.";
    private final String mediumTText = Parameters.MEDIUM_NAME.getString() + " is a more capable opponent, it's more difficult to win.";
    private final String hardTText = Parameters.HARD_NAME.getString() + " leaves almost no chance of winning";
    private final String someoneTText = Parameters.SECOND_NAME.getString() + "? How do I know who it is?";
    private final String playerTText = "It is you, " + Parameters.PLAYER_NAME.getString();

    public LabelProcessor(RadioMenuItem pve, RadioMenuItem pvp,
                          Label modeLabel, Label difLabel,
                          RadioMenuItem easy, RadioMenuItem medium, RadioMenuItem hard,
                          Label wins, Label draws, Label losses, Label vs,
                          TextField firstPlayer, TextField secondPlayer,
                          TextField easyAI, TextField mediumAI, TextField hardAI, Button listOfPlayers,
                          HBox hBox1, HBox hBox2, HBox hBox3) {
        this.pve = pve;
        this.pvp = pvp;
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
        this.difLabel = difLabel;
        this.modeLabel = modeLabel;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.vs = vs;
        this.listOfPlayers = listOfPlayers;
        this.hBox1 = hBox1;
        this.hBox2 = hBox2;
        this.hBox3 = hBox3;

        playerList = new TextField[]{firstPlayer, secondPlayer, easyAI, mediumAI, hardAI};

        initTooltips();

        switch (Parameters.MODE.getString()) {
            case "PvE" -> pve.setSelected(true);
            case "PvP" -> pvp.setSelected(true);
        }

        initBottomLine();

        wins.setText("Wins: 0");
        draws.setText("Draws: 0");
        losses.setText("Losses: 0");
        initTF();

        listOfPlayers.setOnAction(event -> {
            if (!firstPlayer.isVisible()) {
                playerListVisibility(true, "Close");
            } else {
                playerListVisibility(false, "Show");
            }
        });
    }

    private void initTF() {
        playerList[0].setText(Parameters.PLAYER_NAME.getString());
        playerList[1].setText(Parameters.SECOND_NAME.getString());
        playerList[2].setText(Parameters.EASY_NAME.getString());
        playerList[3].setText(Parameters.MEDIUM_NAME.getString());
        playerList[4].setText(Parameters.HARD_NAME.getString());
    }

    private void playerListVisibility(boolean b, String s) {
        for (int i = 0; i < 5; i++) {
            playerList[i].setVisible(b);
        }
        hBox1.getChildren().get(1).setVisible(b);
        hBox2.getChildren().get(1).setVisible(b);
        hBox3.getChildren().get(1).setVisible(b);
        if (!b) {
            writeNames();
            updateTooltips();
        }
        listOfPlayers.setText(s + " player list");
    }

    public void writeNames() {
        Parameters.PLAYER_NAME.setString(playerList[0].getText());
        Parameters.SECOND_NAME.setString(playerList[1].getText());
        Parameters.EASY_NAME.setString(playerList[2].getText());
        Parameters.MEDIUM_NAME.setString(playerList[3].getText());
        Parameters.HARD_NAME.setString(playerList[4].getText());
    }

    private void initBottomLine() {
        modeLabel.setText(modeLabelText + Parameters.MODE.getString());
        if (Parameters.DIFFICULTY.getInt() == 0) {
            difLabel.setText(difLabelText + "Easy");
            easy.setSelected(true);
        } else if (Parameters.DIFFICULTY.getInt() == 1) {
            difLabel.setText(difLabelText + "Medium");
            medium.setSelected(true);
        } else if (Parameters.DIFFICULTY.getInt() == 2) {
            difLabel.setText(difLabelText + "Hard");
            hard.setSelected(true);
        }
    }

    private void initTooltips() {
        easyT = new Tooltip(easyTText);
        mediumT = new Tooltip(mediumTText);
        hardT = new Tooltip(hardTText);
        someoneT = new Tooltip(someoneTText);
        playerT = new Tooltip(playerTText);

        playerList[0].setTooltip(playerT);
        playerList[1].setTooltip(someoneT);
        playerList[2].setTooltip(easyT);
        playerList[3].setTooltip(mediumT);
        playerList[4].setTooltip(hardT);
    }

    private void updateTooltips() {
        easyT.setText(easyTText);
        mediumT.setText(mediumTText);
        hardT.setText(hardTText);
        someoneT.setText(someoneTText);
        playerT.setText(playerTText);
    }

    public void setPve() {
        pvp.setSelected(false);
        Parameters.MODE.setString("PvE");
        modeLabel.setText(modeLabelText + "PvE");
    }

    public void setPvp() {
        pve.setSelected(false);
        Parameters.MODE.setString("PvP");
        modeLabel.setText(modeLabelText + "PvP");
    }

    public void setEasy() {
        medium.setSelected(false);
        hard.setSelected(false);
        Parameters.DIFFICULTY.setInt(0);
        difLabel.setText(difLabelText + "Easy");
    }

    public void setMedium() {
        easy.setSelected(false);
        hard.setSelected(false);
        Parameters.DIFFICULTY.setInt(1);
        difLabel.setText(difLabelText + "Medium");
    }

    public void setHard() {
        easy.setSelected(false);
        medium.setSelected(false);
        Parameters.DIFFICULTY.setInt(2);
        difLabel.setText(difLabelText + "Hard");
    }

    public void fillRightLabels(int currentWins, int currentDraws, int currentLosses) {
        wins.setText("Wins: " + currentWins);
        draws.setText("Draws: " + currentDraws);
        losses.setText("Losses: " + currentLosses);
    }

    public void putVS(Player player1, Player player2, AIPlayer aiPlayer) {
        if (pve.isSelected()) {
            vs.setText(player1.getSign() + "  " + Parameters.PLAYER_NAME.getString() + " VS " + aiPlayer.getName() + "  " + aiPlayer.getSign());
        } else if (pvp.isSelected()) {
            vs.setText(player1.getSign() + "  " + Parameters.PLAYER_NAME.getString() + " VS " + Parameters.SECOND_NAME.getString() + "  " + player2.getSign());
        }
    }
}
