package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private final List<ModelObserver> observers;
  private int currentPuzzleIndex;
  private int[][] lamp;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException("The puzzle library cannot be null or empty");
    }
    this.library = library;
    currentPuzzleIndex = 0;
    observers = new ArrayList<>();
    lamp =
        new int[library.getPuzzle(currentPuzzleIndex).getHeight()]
            [library.getPuzzle(currentPuzzleIndex).getWidth()];
  }

  @Override
  public void addLamp(int r, int c) {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);

    if (r < 0 || r >= currentPuzzle.getHeight() || c < 0 || c >= currentPuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bounds");
    }

    if (currentPuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    lamp[r][c] = 1;

    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);

    if (r < 0 || r >= currentPuzzle.getHeight() || c < 0 || c >= currentPuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bounds");
    }

    if (currentPuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    lamp[r][c] = 0;

    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);

    if (r < 0 || r >= currentPuzzle.getHeight() || c < 0 || c >= currentPuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bounds");
    }

    if (currentPuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    if (isLamp(r, c)) {
      return true;
    }

    // check if any lamps are lighting this cell
    for (int i = r - 1; i >= 0; i--) {
      if (currentPuzzle.getCellType(i, c) == CellType.WALL
          || currentPuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, c)) {
        return true;
      }
    }

    for (int i = r + 1; i < currentPuzzle.getHeight(); i++) {
      if (currentPuzzle.getCellType(i, c) == CellType.WALL
          || currentPuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, c)) {
        return true;
      }
    }

    for (int i = c - 1; i >= 0; i--) {
      if (currentPuzzle.getCellType(r, i) == CellType.WALL
          || currentPuzzle.getCellType(r, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(r, i)) {
        return true;
      }
    }

    for (int i = c + 1; i < currentPuzzle.getWidth(); i++) {
      if (currentPuzzle.getCellType(r, i) == CellType.WALL
          || currentPuzzle.getCellType(r, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(r, i)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);

    if (r < 0 || r >= currentPuzzle.getHeight() || c < 0 || c >= currentPuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bounds");
    }

    if (currentPuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    return lamp[r][c] == 1;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);

    if (r < 0 || r >= currentPuzzle.getHeight() || c < 0 || c >= currentPuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bounds");
    }

    if (currentPuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("The cell must be a corridor cell");
    }

    // check if any lamps are lighting this cell
    for (int i = r - 1; i >= 0; i--) {
      if (currentPuzzle.getCellType(i, c) == CellType.WALL
          || currentPuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, c)) {
        return true;
      }
    }

    for (int i = r + 1; i < currentPuzzle.getHeight(); i++) {
      if (currentPuzzle.getCellType(i, c) == CellType.WALL
          || currentPuzzle.getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (isLamp(i, c)) {
        return true;
      }
    }

    for (int i = c - 1; i >= 0; i--) {
      if (currentPuzzle.getCellType(r, i) == CellType.WALL
          || currentPuzzle.getCellType(r, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(r, i)) {
        return true;
      }
    }

    for (int i = c + 1; i < currentPuzzle.getWidth(); i++) {
      if (currentPuzzle.getCellType(r, i) == CellType.WALL
          || currentPuzzle.getCellType(r, i) == CellType.CLUE) {
        break;
      }
      if (isLamp(r, i)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(currentPuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return currentPuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException(
          "The index must be within the bounds of the puzzle library");
    }

    currentPuzzleIndex = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);
    lamp = new int[currentPuzzle.getHeight()][currentPuzzle.getWidth()];

    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public boolean isSolved() {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);

    for (int i = 0; i < currentPuzzle.getHeight(); i++) {
      for (int j = 0; j < currentPuzzle.getWidth(); j++) {
        if (currentPuzzle.getCellType(i, j) == CellType.CORRIDOR) {
          if (!isLit(i, j)) {
            return false;
          }

          if (isLamp(i, j) && isLampIllegal(i, j)) {
            return false;
          }
        }

        if (currentPuzzle.getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    Puzzle currentPuzzle = library.getPuzzle(currentPuzzleIndex);

    if (r < 0 || r >= currentPuzzle.getHeight() || c < 0 || c >= currentPuzzle.getWidth()) {
      throw new IndexOutOfBoundsException("The row and column must be within the puzzle bounds");
    }

    if (currentPuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("The cell must be a clue cell");
    }

    int clue = currentPuzzle.getClue(r, c);

    int litCount = 0;

    // check 4 directions
    if (r > 0 && currentPuzzle.getCellType(r - 1, c) == CellType.CORRIDOR && isLamp(r - 1, c)) {
      litCount++;
    }

    if (r < currentPuzzle.getHeight() - 1
        && currentPuzzle.getCellType(r + 1, c) == CellType.CORRIDOR
        && isLamp(r + 1, c)) {
      litCount++;
    }

    if (c > 0 && currentPuzzle.getCellType(r, c - 1) == CellType.CORRIDOR && isLamp(r, c - 1)) {
      litCount++;
    }

    if (c < currentPuzzle.getWidth() - 1
        && currentPuzzle.getCellType(r, c + 1) == CellType.CORRIDOR
        && isLamp(r, c + 1)) {
      litCount++;
    }

    return litCount == clue;
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
