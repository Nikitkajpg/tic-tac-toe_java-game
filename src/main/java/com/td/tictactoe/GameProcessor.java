package com.td.tictactoe;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GameProcessor {
    private final LabelProcessor labelProcessor;
    private final Matrix matrix;
    private final Drawing drawing;
    private final Cell cell;
    private GameState gameState;
    private final Pane pane;
    private final Player player1, player2;
    private final AIPlayer aiPlayer;
    private int currentWins, currentDraws, currentLosses;
    private int turn;

    public GameProcessor(LabelProcessor labelProcessor, Matrix matrix, Drawing drawing, Pane pane, GameState gameState) {
        this.labelProcessor = labelProcessor;
        this.pane = pane;
        this.matrix = matrix;
        this.drawing = drawing;
        this.gameState = gameState;

        currentWins = 0;
        currentDraws = 0;
        currentLosses = 0;

        player1 = new Player();
        player2 = new Player();
        aiPlayer = new AIPlayer();

        turn = 1;
        cell = new Cell();
    }

    public void reset() {
        currentWins = 0;
        currentDraws = 0;
        currentLosses = 0;
        labelProcessor.fillRightLabels(currentWins, currentDraws, currentLosses);
    }

    public void start(GameState gameState, int firstMove) {
        this.gameState = gameState;
        setTurn();
        fillLabels();
        setMode(firstMove);
    }

    private void setMode(int firstMove) {
        if (gameState == GameState.PLAYING) {
            switch (Parameters.MODE.getString()) {
                case "PvE" -> playPvE(firstMove);
                case "PvP" -> playPvP();
            }
        }
    }

    private void setTurn() {
        if (turn == 1) {
            player1.setSign("X");
            player2.setSign("O");
            aiPlayer.setSign("O");
        } else {
            player1.setSign("O");
            player2.setSign("X");
            aiPlayer.setSign("X");
        }
        turn *= -1;
    }

    private void fillLabels() {
        labelProcessor.fillRightLabels(currentWins, currentDraws, currentLosses);
        labelProcessor.putVS(player1, player2, aiPlayer);
    }

    private void playPvE(int firstMove) {
        if (gameState != GameState.NON_PLAYING) {
            if (firstMove != 1 && firstMove != 2) {
                aiTurn(firstMove);
            }
            mainCycle(firstMove);
        }
    }

    private void mainCycle(int firstMove) {
        pane.setOnMouseClicked(event -> {
            if (gameState != GameState.NON_PLAYING) {
                boolean was = playerTurn(event, player1);
                if (!was) {
                    mainCycle(firstMove);
                }
                if (was && gameState != GameState.NON_PLAYING) {
                    aiTurn(firstMove);
                }
            }
        });
    }

    private void aiTurn(int firstMove) {
        if (matrix.freeCellExists()) {
            aiPlayer.move(cell, matrix, firstMove);

            matrix.setSignToSell(cell, aiPlayer.getSign());
            drawing.draw(aiPlayer, cell);

            checkWin(aiPlayer);
        }
    }

    private void checkWin(AIPlayer aiPlayer) {
        if (matrix.checkWin(aiPlayer.getSign(), drawing)) {
            labelProcessor.fillRightLabels(currentWins, currentDraws, ++currentLosses);
            gameState = GameState.NON_PLAYING;
        } else if (!matrix.freeCellExists()) {
            labelProcessor.fillRightLabels(currentWins, ++currentDraws, currentLosses);
        }
    }

    private void playPvP() {
        final boolean[] moveFirst = {true};
        pane.setOnMouseClicked(event -> {
            if (gameState != GameState.NON_PLAYING) {
                if (moveFirst[0]) {
                    if (playerTurn(event, player1)) {
                        moveFirst[0] = false;
                    }
                } else {
                    if (playerTurn(event, player2)) {
                        moveFirst[0] = true;
                    }
                }
            }
        });
    }

    private boolean playerTurn(MouseEvent event, Player player) {
        if (matrix.freeCellExists()) {
            cell.setX((int) event.getY() / 200);
            cell.setY((int) event.getX() / 200);

            if (matrix.cellIsEmpty(cell)) {
                matrix.setSignToSell(cell, player.getSign());
                drawing.draw(player, cell);
            } else {
                return false;
            }

            checkWin(player);
            return true;
        } else {
            return false;
        }
    }

    private void checkWin(Player player) {
        if (matrix.checkWin(player.getSign(), drawing)) {
            if (player == player1) {
                labelProcessor.fillRightLabels(++currentWins, currentDraws, currentLosses);
            } else {
                labelProcessor.fillRightLabels(currentWins, currentDraws, ++currentLosses);
            }
            gameState = GameState.NON_PLAYING;
        } else if (!matrix.freeCellExists()) {
            labelProcessor.fillRightLabels(currentWins, ++currentDraws, currentLosses);
        }
    }
}
