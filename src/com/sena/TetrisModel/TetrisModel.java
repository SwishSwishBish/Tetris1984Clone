package com.sena.TetrisModel;

public class TetrisModel {
    boolean gameOver = false;
    boolean isStart= true;

    int score;
    final int[] scoreTable = { 0, 10, 30, 50, 100 };

    private final long[] brickData = {0xF00444400F02222L,
            0x660066006600660L, 0xC6004C800C60264L, 0x6C008C4006C0462L,
            0x8E0044C00E20644L, 0xE8044602E00C44L, 0x46404E004C400E4L};

    private final int[][][] brick = new int[7][4][8];
    int brickCurrent;
    int brickNext;
    int brickRotation;
    int brickRow = 0;
    int brickCol = 3;

    final int gridCols = 10;
    final int gridRows = 24;
    int[][] grid = new int[gridRows][gridCols];

    public TetrisModel() {
        getBricks();
        brickCurrent = (int) (7 * Math.random());
        brickNext = (int) (7 * Math.random());
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public void start() {
        grid = new int[gridRows][gridCols];
        brickCol = 3;
        brickRow = 0;
        score = 0;
        gameOver = false;
        isStart = false;
    }

    public boolean isStart() {
        return isStart;
    }

    public int getScore() {
        return score;
    }

    public int getGridCols() { return gridCols; }

    public int getGridRows() { return gridRows; }

    public void update() {
        int clearedLineCount = clearLines();
        score += scoreTable[clearedLineCount];
        if (collide(0, 1, brickRotation)) {
            brickRow++;
            return;
        }
        if (brickRow < 4 || gameOver) {
            gameOver = true;
            return;
        }
        solid();
        nextPiece();
    }

    void getBricks() {
        for (int p = 0; p < brickData.length; p++) {
            int colRow = 0;
            for (int b = 0; b < 64; b++) {
                colRow = b % 16 == 0 ? 0 : colRow;
                if (((brickData[p] >> b) & 1) == 1) {
                    brick[p][b / 16][colRow++] = b % 4;
                    brick[p][b / 16][colRow++] = (b % 16) / 4;
                }
            }
        }
    }
    public int getGridValue(int col, int row) {
        int[] block = brick[brickCurrent][brickRotation];
        for (int i = 0; i < block.length; i+=2) {
            if (col == block[i] + brickCol && row == block[i + 1] + brickRow) {
                return brickCurrent + 1;
            }
        }
        return grid[row][col];
    }

    public int getNextPieceValue(int col, int row) {
        int[] brick = this.brick[brickNext][0];
        for (int i = 0; i < brick.length; i+=2) {
            if (col == brick[i] && row == brick[i + 1]) {
                return brickNext + 1;
            }
        }
        return 0;
    }

    boolean collide(int dx, int dy, int rotation) {
        int[] brick = this.brick[brickCurrent][rotation];
        for (int i = 0; i < brick.length; i+=2) {
            int col = brick[i] + brickCol + dx;
            int row = brick[i + 1] + brickRow + dy;
            if (col < 0 || col > gridCols - 1
                    || row < 0 || row > gridRows - 1
                    || grid[row][col] > 0) {
                return false;
            }
        }
        return true;
    }



    public void move(int dx) {
        if (collide(dx, 0, brickRotation)) {
            brickCol += dx;
        }
    }

    public void rotate() {
        int nextBrickRotation = (brickRotation + 1) % 4;
        if (collide(0, 0, nextBrickRotation)) {
            brickRotation = nextBrickRotation;
        }
    }

    public void down() {
        while (collide(0, 1, brickRotation)) {
            brickRow++;
        }
    }



    int clearLines() {
        int clearedLineCount = 0;
        nextRow:
        for (int row=0; row<gridRows; row++) {
            for (int column=0; column<gridCols; column++) {
                if (grid[row][column] == 0) {
                    continue nextRow;
                }
            }
            for (int row2 = row; row2 > 0; row2--) {
                System.arraycopy(grid[row2 - 1], 0, grid[row2], 0, gridCols);
            }
            clearedLineCount++;
        }
        return clearedLineCount;
    }

    void solid() {
        int[] p = brick[brickCurrent][brickRotation];
        for (int i=0; i<p.length; i+=2) {
            grid[p[i + 1] + brickRow][p[i] + brickCol] = brickCurrent + 1;
        }
    }

    void nextPiece() {
        brickCol = 3;
        brickRow = 0;
        brickRotation = 0;
        brickCurrent = brickNext;
        brickNext = (int) (7 * Math.random());
    }

}