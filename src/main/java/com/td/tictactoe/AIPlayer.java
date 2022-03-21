package com.td.tictactoe;

public class AIPlayer {
    private String sign;
    private final Cell cell1 = new Cell(0, 0);
    private final Cell cell2 = new Cell(0, 1);
    private final Cell cell3 = new Cell(0, 2);
    private final Cell cell4 = new Cell(1, 0);
    private final Cell cell5 = new Cell(1, 1);
    private final Cell cell6 = new Cell(1, 2);
    private final Cell cell7 = new Cell(2, 0);
    private final Cell cell8 = new Cell(2, 1);
    private final Cell cell9 = new Cell(2, 2);
    private final Cell tempCell = new Cell();

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public String getName() {
        return switch (Parameters.DIFFICULTY.getInt()) {
            case 0 -> Parameters.EASY_NAME.getString();
            case 1 -> Parameters.MEDIUM_NAME.getString();
            case 2 -> Parameters.HARD_NAME.getString();
            default -> "";
        };
    }

    public void move(Cell cell, Matrix matrix, int firstMove) {
        String playerSign = sign.equals("X") ? "O" : "X";
        switch (Parameters.DIFFICULTY.getInt()) {
            case 0 -> findEmptyRandomCell(cell, matrix);
            case 1 -> blockPlayer(cell, matrix, playerSign);
            case 2 -> algorithm(cell, matrix, playerSign, firstMove);
        }
    }

    // beginning of hard mode algorithm
    private void algorithm(Cell cell, Matrix matrix, String playerSign, int firstMove) {
        switch (matrix.countSigns(playerSign)) {
            case 0 -> {
                int random = (int) (Math.random() * 5);
                if (random == 0) {
                    cell.set(cell1.getX(), cell1.getY());
                } else if (random == 1) {
                    cell.set(cell3.getX(), cell3.getY());
                } else if (random == 2) {
                    cell.set(cell5.getX(), cell5.getY());
                } else if (random == 3) {
                    cell.set(cell7.getX(), cell7.getY());
                } else {
                    cell.set(cell9.getX(), cell9.getY());
                }
            }
            case 1 -> {
                if (firstMove == 1 || firstMove == 2) {
                    if (inCenter(playerSign, matrix)) {
                        findRandomCorner(cell, matrix);
                    } else {
                        Cell c = matrix.findPlayerCell(playerSign, cell);
                        tempCell.set(c.getX(), c.getY());
                        cell.set(cell5.getX(), cell5.getY());
                    }
                } else {
                    if (matrix.findSignInCell(cell5).equals(sign)) {
                        findRandomCorner(cell, matrix);
                    } else if (matrix.findSignInCell(cell5).equals(playerSign)) {
                        if (matrix.findSignInCell(cell1).equals(sign)) {
                            cell.set(cell9.getX(), cell9.getY());
                        } else if (matrix.findSignInCell(cell9).equals(sign)) {
                            cell.set(cell1.getX(), cell1.getY());
                        } else if (matrix.findSignInCell(cell3).equals(sign)) {
                            cell.set(cell7.getX(), cell7.getY());
                        } else if (matrix.findSignInCell(cell7).equals(sign)) {
                            cell.set(cell3.getX(), cell3.getY());
                        } else {
                            findRandomCorner(cell, matrix);
                        }
                    } else {
                        findRandomCorner(cell, matrix);
                    }
                }
            }
            case 2 -> {
                if (firstMove == 1 || firstMove == 2) {
                    if (inCenter(sign, matrix)) {
                        if (!matrix.findCellToBlock(cell, playerSign)) {
                            if (inCorner(tempCell)) {
                                if (matrix.oppositeCorners(playerSign)) {
                                    findRandomCenter(cell, matrix);
                                } else {
                                    if (tempCell.getX() == 0 && tempCell.getY() == 0) {
                                        cell.set(cell9.getX(), cell9.getY());
                                    } else if (tempCell.getX() == 2 && tempCell.getY() == 2) {
                                        cell.set(cell1.getX(), cell1.getY());
                                    } else if (tempCell.getX() == 0 && tempCell.getY() == 2) {
                                        cell.set(cell3.getX(), cell3.getY());
                                    } else if (tempCell.getX() == 2 && tempCell.getY() == 0) {
                                        cell.set(cell7.getX(), cell7.getY());
                                    }
                                }
                            } else {
                                if (!inCorner(playerSign, matrix)) {
                                    if (matrix.oppositeCenters(playerSign)) {
                                        findRandomCenter(cell, matrix);
                                    } else {
                                        findRandomCorner(cell, matrix);// you may win
                                    }
                                } else {
                                    if (!matrix.findCellToBlock(cell, playerSign)) {
                                        findRandomCorner(cell, matrix);// you may win
                                    }
                                }
                            }
                        }
                    } else if (inCorner(sign, matrix)) {
                        if (!matrix.findCellToBlock(cell, playerSign)) {
                            if (inCenter(playerSign, matrix)) {
                                findRandomCorner(cell, matrix);
                            } else {
                                lastMovesIfAIFirst(cell, matrix, playerSign);
                            }
                        }
                    }
                } else {
                    lastMovesIfAIFirst(cell, matrix, playerSign);
                }
            }
            default -> lastMovesIfAIFirst(cell, matrix, playerSign);
        }
    }

    private void lastMovesIfAIFirst(Cell cell, Matrix matrix, String playerSign) {
        if (!matrix.findCellToWin(cell, sign)) {
            if (!matrix.findCellToBlock(cell, playerSign)) {
                findEmptyRandomCell(cell, matrix);
            }
        }
    }

    private void findRandomCenter(Cell cell, Matrix matrix) {
        do {
            int random = (int) (Math.random() * 4);
            if (random == 0) {
                if (matrix.cellIsEmpty(cell2)) {
                    cell.set(cell2.getX(), cell2.getY());
                }
            } else if (random == 1) {
                if (matrix.cellIsEmpty(cell4)) {
                    cell.set(cell4.getX(), cell4.getY());
                }
            } else if (random == 2) {
                if (matrix.cellIsEmpty(cell6)) {
                    cell.set(cell6.getX(), cell6.getY());
                }
            } else {
                if (matrix.cellIsEmpty(cell8)) {
                    cell.set(cell8.getX(), cell8.getY());
                }
            }
        } while (!matrix.cellIsEmpty(cell));
    }

    private void findRandomCorner(Cell cell, Matrix matrix) {
        do {
            int random = (int) (Math.random() * 4);
            if (random == 0) {
                if (matrix.cellIsEmpty(cell1)) {
                    cell.set(cell1.getX(), cell1.getY());
                }
            } else if (random == 1) {
                if (matrix.cellIsEmpty(cell3)) {
                    cell.set(cell3.getX(), cell3.getY());
                }
            } else if (random == 2) {
                if (matrix.cellIsEmpty(cell7)) {
                    cell.set(cell7.getX(), cell7.getY());
                }
            } else {
                if (matrix.cellIsEmpty(cell9)) {
                    cell.set(cell9.getX(), cell9.getY());
                }
            }
        } while (!matrix.cellIsEmpty(cell));
    }

    private boolean inCenter(String sign, Matrix matrix) {
        return matrix.findSignInCell(cell5).equals(sign);
    }

    private boolean inCorner(String sign, Matrix matrix) {
        return matrix.findSignInCell(cell1).equals(sign) ||
                matrix.findSignInCell(cell3).equals(sign) ||
                matrix.findSignInCell(cell7).equals(sign) ||
                matrix.findSignInCell(cell9).equals(sign);
    }

    private boolean inCorner(Cell tempCell) {
        return tempCell.getX() == tempCell.getY() ||
                (tempCell.getX() == 0 && tempCell.getY() == 2) ||
                (tempCell.getX() == 2 && tempCell.getY() == 0);
    }
    // end of hard mode algorithm

    private void blockPlayer(Cell cell, Matrix matrix, String playerSign) {
        if (!matrix.findCellToBlock(cell, playerSign)) {
            findEmptyRandomCell(cell, matrix);
        }
    }

    private void findEmptyRandomCell(Cell cell, Matrix matrix) {
        do {
            cell.setX((int) (Math.random() * 3));
            cell.setY((int) (Math.random() * 3));
        } while (!matrix.cellIsEmpty(cell));
    }
}
