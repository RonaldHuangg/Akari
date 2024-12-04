package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private final List<ModelObserver> observers;
  private int activeIndex;
  private int[][] lamps;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException("The puzzle library cannot be empty or null");
    }
    this.library = library;
    this.activeIndex = 0;
    this.observers = new ArrayList<>();
    initializeLamps();
  }

  private void initializeLamps() {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);
    this.lamps = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
  }

  @Override
  public void addLamp(int row, int col) {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);

    if (row < 0 || row >= activePuzzle.getHeight() || col < 0 || col >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bound");
    }

    if (activePuzzle.getCellType(row, col) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    lamps[row][col] = 1;

    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public void removeLamp(int row, int col) {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);

    if (row < 0 || row >= activePuzzle.getHeight() || col < 0 || col >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bound");
    }

    if (activePuzzle.getCellType(row, col) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    lamps[row][col] = 0;

    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public boolean isLit(int row, int col) {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);

    if (row < 0 || row >= activePuzzle.getHeight() || col < 0 || col >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bound");
    }

    if (activePuzzle.getCellType(row, col) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    if (isLamp(row, col)) {
      return true;
    }

    for (int i = row - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(i, col) == CellType.WALL
          || activePuzzle.getCellType(i, col) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, col)) {
        return true;
      }
    }

    for (int i = row + 1; i < activePuzzle.getHeight(); i++) {
      if (activePuzzle.getCellType(i, col) == CellType.WALL
          || activePuzzle.getCellType(i, col) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, col)) {
        return true;
      }
    }

    for (int i = col - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(row, i) == CellType.WALL
          || activePuzzle.getCellType(row, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(row, i)) {
        return true;
      }
    }

    for (int i = col + 1; i < activePuzzle.getWidth(); i++) {
      if (activePuzzle.getCellType(row, i) == CellType.WALL
          || activePuzzle.getCellType(row, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(row, i)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isLamp(int row, int col) {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);

    if (row < 0 || row >= activePuzzle.getHeight() || col < 0 || col >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bound");
    }

    if (activePuzzle.getCellType(row, col) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    return lamps[row][col] == 1;
  }

  @Override
  public boolean isLampIllegal(int row, int col) {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);

    if (row < 0 || row >= activePuzzle.getHeight() || col < 0 || col >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bound");
    }

    if (activePuzzle.getCellType(row, col) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    for (int i = row - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(i, col) == CellType.WALL
          || activePuzzle.getCellType(i, col) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, col)) {
        return true;
      }
    }

    for (int i = row + 1; i < activePuzzle.getHeight(); i++) {
      if (activePuzzle.getCellType(i, col) == CellType.WALL
          || activePuzzle.getCellType(i, col) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, col)) {
        return true;
      }
    }

    for (int i = col - 1; i >= 0; i--) {
      if (activePuzzle.getCellType(row, i) == CellType.WALL
          || activePuzzle.getCellType(row, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(row, i)) {
        return true;
      }
    }

    for (int i = col + 1; i < activePuzzle.getWidth(); i++) {
      if (activePuzzle.getCellType(row, i) == CellType.WALL
          || activePuzzle.getCellType(row, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(row, i)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(activeIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return activeIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException(
          "The index must be within the bound of the puzzle library");
    }
    activeIndex = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    initializeLamps();
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public boolean isSolved() {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);

    for (int row = 0; row < activePuzzle.getHeight(); row++) {
      for (int col = 0; col < activePuzzle.getWidth(); col++) {
        if (activePuzzle.getCellType(row, col) == CellType.CORRIDOR) {
          if (!isLit(row, col) || (isLamp(row, col) && isLampIllegal(row, col))) {
            return false;
          }
        }
        if (activePuzzle.getCellType(row, col) == CellType.CLUE) {
          if (!isClueSatisfied(row, col)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int row, int col) {
    Puzzle activePuzzle = library.getPuzzle(activeIndex);

    if (row < 0 || row >= activePuzzle.getHeight() || col < 0 || col >= activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bound");
    }

    if (activePuzzle.getCellType(row, col) != CellType.CLUE) {
      throw new IllegalArgumentException("The cell must be a clue cell");
    }

    int clue = activePuzzle.getClue(row, col);
    int lampCount = 0;

    if (row > 0 && isLamp(row - 1, col)) {
      lampCount++;
    }
    if (row < activePuzzle.getHeight() - 1 && isLamp(row + 1, col)) {
      lampCount++;
    }
    if (col > 0 && isLamp(row, col - 1)) {
      lampCount++;
    }
    if (col < activePuzzle.getWidth() - 1 && isLamp(row, col + 1)) {
      lampCount++;
    }

    return lampCount == clue;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }
}
