package com.td.tictactoe;

import java.util.Arrays;
import java.util.Objects;

public class Matrix {
    private final String[][] matrix;

    public Matrix() {
        matrix = new String[3][3];

        fillMatrix();
    }

    private void fillMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = "";
            }
        }
    }

    public void clear() {
        for (String[] strings : matrix) {
            Arrays.fill(strings, "");
        }
    }

    public boolean freeCellExists() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j].equals("")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cellIsEmpty(Cell cell) {
        return matrix[cell.getX()][cell.getY()].equals("");
    }

    public void setSignToSell(Cell cell, String sign) {
        matrix[cell.getX()][cell.getY()] = sign;
    }

    public boolean checkWin(String sign, Drawing drawing) {
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0].equals(sign) && matrix[i][1].equals(sign) && matrix[i][2].equals(sign)) {
                drawing.setWinLinesVisible(2, i);
                return true;
            }
            if (matrix[0][i].equals(sign) && matrix[1][i].equals(sign) && matrix[2][i].equals(sign)) {
                drawing.setWinLinesVisible(3, i);
                return true;
            }
        }
        if (matrix[0][0].equals(sign) && matrix[1][1].equals(sign) && matrix[2][2].equals(sign)) {
            drawing.setWinLinesVisible(1, 1);
            return true;
        }
        if (matrix[0][2].equals(sign) && matrix[1][1].equals(sign) && matrix[2][0].equals(sign)) {
            drawing.setWinLinesVisible(1, 2);
            return true;
        }
        return false;
    }

    public boolean findCellToBlock(Cell cell, String playerSign) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j].equals("")) {
                    matrix[i][j] = playerSign;
                    if (checkWin(playerSign)) {
                        cell.setX(i);
                        cell.setY(j);
                        matrix[i][j] = "";
                        return true;
                    }
                    matrix[i][j] = "";
                }
            }
        }
        return false;
    }

    public Cell findPlayerCell(String playerSign, Cell cell) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Objects.equals(matrix[i][j], playerSign)) {
                    cell.set(j, i);
                    return cell;
                }
            }
        }
        return cell;
    }

    public boolean oppositeCenters(String playerSign) {
        int count = 0;
        Cell cell1 = new Cell(), cell2 = new Cell();
        findTwoCells(playerSign, count, cell1, cell2);

        if (cell1.getX() == cell2.getX() && cell2.getY() - cell1.getY() == cell2.getY()) {
            return true;
        } else return cell2.getX() - cell1.getX() == cell2.getX() && cell1.getY() == cell2.getY();
    }

    public boolean oppositeCorners(String playerSign) {
        int count = 0;
        Cell cell1 = new Cell(), cell2 = new Cell();
        findTwoCells(playerSign, count, cell1, cell2);

        if (cell1.getX() == cell1.getY() && cell1.getX() == cell1.getY()) {
            return true;
        } else return cell1.getX() == cell2.getY() && cell1.getY() == cell2.getX();
    }

    private void findTwoCells(String playerSign, int count, Cell cell1, Cell cell2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (count == 0) {
                    if (matrix[i][j].equals(playerSign)) {
                        cell1.set(i, j);
                        count++;
                    }
                } else if (count == 1) {
                    if (matrix[i][j].equals(playerSign)) {
                        cell2.set(i, j);
                        count++;
                    }
                }
            }
        }
    }

    public boolean findCellToWin(Cell cell, String sign) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j].equals("")) {
                    matrix[i][j] = sign;
                    if (checkWin(sign)) {
                        cell.setX(i);
                        cell.setY(j);
                        matrix[i][j] = "";
                        return true;
                    }
                    matrix[i][j] = "";
                }
            }
        }
        return false;
    }

    public boolean checkWin(String sign) {
        for (int i = 0; i < 3; i++) {
            if (matrix[i][0].equals(sign) && matrix[i][1].equals(sign) && matrix[i][2].equals(sign)) {
                return true;
            }
            if (matrix[0][i].equals(sign) && matrix[1][i].equals(sign) && matrix[2][i].equals(sign)) {
                return true;
            }
        }
        if (matrix[0][0].equals(sign) && matrix[1][1].equals(sign) && matrix[2][2].equals(sign)) {
            return true;
        }
        return matrix[0][2].equals(sign) && matrix[1][1].equals(sign) && matrix[2][0].equals(sign);
    }

    public int countSigns(String sign) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j].equals(sign)) {
                    count++;
                }
            }
        }
        return count;
    }

    public String findSignInCell(Cell cell) {
        return matrix[cell.getX()][cell.getY()];
    }
}
