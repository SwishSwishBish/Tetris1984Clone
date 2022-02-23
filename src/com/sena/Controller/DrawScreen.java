package com.sena.Controller;

import com.sena.TetrisModel.TetrisModel;

public class DrawScreen{
    TetrisModel model;
    private final char[][] screen = new char[40][24];

    void clear() {
        for (int y = 0; y < 24; y++) {
            for (int x = 0; x < 40; x++) {
                screen[x][y] = ' ';
            }
        }
    }

    void print(int x, int y, String c) {
        for (int column = 0; column < c.length(); column++) {
            screen[x + column][y] = c.charAt(column);
        }
    }

    String convertScreenToText() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 24; y++) {
            for (int x = 0; x < 40; x++) {
                sb.append(screen[x][y]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void drawGameOver() {
        print(6, 9, "+=======================+");
        print(6, 10, "|       GAME OVER       |");
        print(6, 11, "|                       |");
        print(6, 12, "|  PRESS SPACE TO PLAY  |");
        print(6, 13, "+=======================+");
    }

    public void drawStart() {
        print(6, 9, "+=======================+");
        print(6, 10, "|                       |");
        print(6, 11, "|  PRESS SPACE TO PLAY  |");
        print(6, 12, "|                       |");
        print(6, 13, "+=======================+");
    }



    void drawGrid(TetrisModel model) {
        this.model=model;
        print(3, 1, "+----------+");
        print(3, 22, "+----------+");
        for (int row = 4; row < model.getGridRows(); row++) {
            print(3, row - 2, "|          |");
            for (int column = 0; column < model.getGridCols(); column++) {
                int c = model.getGridValue(column, row);
                if (c > 0) {
                    print(column + 4, row - 2, "\u2588");
                } else {
                    print(column + 4, row - 2, ".");
                }
            }
        }
    }

    void drawNextPiece(TetrisModel model) {
        this.model=model;
        print(22, 2, " NEXT: ");
        print(22, 3, "+-----+");
        print(22, 8, "+-----+");
        for (int row = 0; row < 4; row++) {
            print(22, row + 4, "|     |");
            for (int column = 0; column < 4; column++) {
                int c = model.getNextPieceValue(column, row);
                if (c > 0) {
                    print(column + 23, row + 4, "\u2588");
                } else {
                    print(column + 23, row + 4, " ");
                }
            }
        }
    }
    void drawScore(TetrisModel model) {
        this.model=model;
        print(18, 9, "+------------+");
        print(18, 10, "|            |");
        print(18, 11, "+------------+");
        print(20, 10, "SCORE: " + model.getScore());
    }
    void drawInfo(TetrisModel model) {
        print(21, 13, "CONTROLS:");
        print(17, 14, "+---------------+");
        print(17, 15, "| \u2191  Rotate     |");
        print(17, 16, "| \u2192  Right      |");
        print(17, 17, "| \u2190  Left       |");
        print(17, 18, "| \u2193  Fall Faster|");
        print(17, 19, "+---------------+");

    }

}
