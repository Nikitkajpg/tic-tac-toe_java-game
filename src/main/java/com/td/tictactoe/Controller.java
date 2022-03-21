package com.td.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Controller {
    @FXML
    private Pane pane;

    @FXML
    private Label modeLabel, difLabel;

    @FXML
    private Label vs, wins, draws, losses;

    @FXML
    private TextField firstPlayer, secondPlayer, easyAI, mediumAI, hardAI;

    @FXML
    private RadioMenuItem pve, pvp;

    @FXML
    private RadioMenuItem easy, medium, hard;

    @FXML
    private Button listOfPlayers;

    @FXML
    private HBox hBox1, hBox2, hBox3;

    private FileManager fileManager;

    private Matrix matrix;
    private GameState gameState;
    private GameProcessor gameProcessor;
    private LabelProcessor labelProcessor;

    private int firstMove = 4;

    @FXML
    private void initialize() throws IOException {
        Drawing drawing = new Drawing(pane);
        fileManager = new FileManager();
        labelProcessor = new LabelProcessor(pve, pvp, modeLabel, difLabel, easy, medium, hard,
                wins, draws, losses, vs, firstPlayer, secondPlayer, easyAI, mediumAI, hardAI, listOfPlayers,
                hBox1, hBox2, hBox3);
        matrix = new Matrix();
        gameProcessor = new GameProcessor(labelProcessor, matrix, drawing, pane, gameState);

        gameState = GameState.NON_PLAYING;
    }

    @FXML
    private void newGame() {
        gameState = GameState.PLAYING;

        drawField();
        firstMoveChange();
        gameProcessor.start(gameState, firstMove);
    }

    private void firstMoveChange() {
        if (firstMove + 1 != 5) {
            firstMove += 1;
        } else {
            firstMove = 1;
        }
    }

    private void drawField() {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            pane.getChildren().get(i).setVisible(false);
        }
        for (int i = 0; i < 4; i++) {
            pane.getChildren().get(i).setVisible(true);
        }
        matrix.clear();
    }

    @FXML
    private void exit() {
        try {
            labelProcessor.writeNames();
            fileManager.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @FXML
    private void pveMode() {
        labelProcessor.setPve();
    }

    @FXML
    private void pvpMode() {
        labelProcessor.setPvp();
    }

    @FXML
    private void easyDif() {
        labelProcessor.setEasy();
    }

    @FXML
    private void mediumDif() {
        labelProcessor.setMedium();
    }

    @FXML
    private void hardDif() {
        labelProcessor.setHard();
    }

    @FXML
    private void reset() {
        gameProcessor.reset();
    }

    private final javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        try {
            labelProcessor.writeNames();
            fileManager.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }
}