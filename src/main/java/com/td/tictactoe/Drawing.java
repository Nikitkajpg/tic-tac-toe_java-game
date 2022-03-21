package com.td.tictactoe;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Drawing {
    private final Line[] winLines;
    private Line lineH1, lineH2, lineV1, lineV2;
    private final Line[][] linesUD;
    private final Line[][] linesDU;
    private final Circle[][] circles;

    public Drawing(Pane pane) {
        winLines = new Line[8];
        linesUD = new Line[3][3];
        linesDU = new Line[3][3];
        circles = new Circle[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initCrosses(i, j);
                initCircles(i, j);
            }
        }
        initFieldLines();
        initWinLines();

        addElementsToPane(pane);
    }

    private void addElementsToPane(Pane pane) {
        pane.getChildren().addAll(lineH1, lineH2, lineV1, lineV2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pane.getChildren().addAll(linesUD[i][j], linesDU[i][j], circles[i][j]);
            }
        }
        for (int i = 0; i < 8; i++) {
            pane.getChildren().add(winLines[i]);
        }
    }

    private void initCrosses(int i, int j) {
        Line lineUD = new Line(j * 200 + 20, i * 200 + 20, j * 200 + 180, i * 200 + 180);
        Line lineDU = new Line(j * 200 + 20, i * 200 + 180, j * 200 + 180, i * 200 + 20);
        lineUD.setStroke(Color.web("#25291C"));
        lineUD.setStrokeWidth(3);
        lineDU.setStroke(Color.web("#25291C"));
        lineDU.setStrokeWidth(3);
        lineDU.setVisible(false);
        lineUD.setVisible(false);
        linesUD[i][j] = lineUD;
        linesDU[i][j] = lineDU;
    }

    private void initCircles(int i, int j) {
        Circle circle = new Circle(j * 200 + 100, i * 200 + 100, 80);
        circle.setFill(null);
        circle.setStroke(Color.web("#25291C"));
        circle.setStrokeWidth(3);
        circle.setVisible(false);
        circles[i][j] = circle;
    }

    private void initFieldLines() {
        lineH1 = new Line(0, 200, 600, 200);
        lineH2 = new Line(0, 400, 600, 400);
        lineV1 = new Line(200, 0, 200, 600);
        lineV2 = new Line(400, 0, 400, 600);
        lineH1.setStroke(Color.web("#25291C"));
        lineH1.setStrokeWidth(3);
        lineH2.setStroke(Color.web("#25291C"));
        lineH2.setStrokeWidth(3);
        lineV1.setStroke(Color.web("#25291C"));
        lineV1.setStrokeWidth(3);
        lineV2.setStroke(Color.web("#25291C"));
        lineV2.setStrokeWidth(3);
    }

    private void initWinLines() {
        winLines[0] = new Line(50, 50, 550, 550);   // UD
        winLines[1] = new Line(550, 50, 50, 550);   // DU
        winLines[2] = new Line(50, 100, 550, 100);  // H1
        winLines[3] = new Line(50, 300, 550, 300);  // H2
        winLines[4] = new Line(50, 500, 550, 500);  // H3
        winLines[5] = new Line(100, 50, 100, 550);  // V1
        winLines[6] = new Line(300, 50, 300, 550);  // V2
        winLines[7] = new Line(500, 50, 500, 550);  // V3

        for (int i = 0; i < 8; i++) {
            winLines[i].setVisible(false);
            winLines[i].setStroke(Color.web("#E71D36"));
            winLines[i].setStrokeWidth(5);
        }
    }

    public void setCrossesVisible(Cell cell) {
        linesUD[cell.getX()][cell.getY()].setVisible(true);
        linesDU[cell.getX()][cell.getY()].setVisible(true);
    }

    public void setCircleVisible(Cell cell) {
        circles[cell.getX()][cell.getY()].setVisible(true);
    }

    public void setWinLinesVisible(int num, int i) {
        if (num == 1 && i == 1) {
            winLines[0].setVisible(true);
        } else if (num == 1 && i == 2) {
            winLines[1].setVisible(true);
        } else if (num == 2 && i == 0) {
            winLines[2].setVisible(true);
        } else if (num == 2 && i == 1) {
            winLines[3].setVisible(true);
        } else if (num == 2 && i == 2) {
            winLines[4].setVisible(true);
        } else if (num == 3 && i == 0) {
            winLines[5].setVisible(true);
        } else if (num == 3 && i == 1) {
            winLines[6].setVisible(true);
        } else if (num == 3 && i == 2) {
            winLines[7].setVisible(true);
        }
    }

    public void draw(Player player, Cell cell) {
        if (player.getSign().equals("X")) {
            setCrossesVisible(cell);
        } else {
            setCircleVisible(cell);
        }
    }

    public void draw(AIPlayer aiPlayer, Cell cell) {
        if (aiPlayer.getSign().equals("X")) {
            setCrossesVisible(cell);
        } else {
            setCircleVisible(cell);
        }
    }
}
