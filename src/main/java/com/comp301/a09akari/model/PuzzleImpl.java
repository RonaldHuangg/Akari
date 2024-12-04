package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final CellType[][] board;
  private final int[][] clues;

  public PuzzleImpl(int[][] board) {
    if (board == null) {
      throw new IllegalArgumentException();
    }

    int rows = board.length;
    int cols = board[0].length;

    this.board = new CellType[rows][cols];
    this.clues = new int[rows][cols];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int cellValue = board[row][col];
        if (cellValue >= 0 && cellValue <= 4) {
          this.board[row][col] = CellType.CLUE;
          this.clues[row][col] = cellValue;
        } else if (cellValue == 5) {
          this.board[row][col] = CellType.WALL;
        } else if (cellValue == 6) {
          this.board[row][col] = CellType.CORRIDOR;
        } else {
          throw new IllegalArgumentException("It is a invalid cell type");
        }
      }
    }
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int row, int col) {
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
      throw new IndexOutOfBoundsException("It is a invalid cell index");
    }
    return board[row][col];
  }

  @Override
  public int getClue(int row, int col) {
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
      throw new IndexOutOfBoundsException("It is a invalid cell index");
    }
    if (board[row][col] != CellType.CLUE) {
      throw new IllegalArgumentException("The cell is not a clue cell");
    }
    return clues[row][col];
  }
}
