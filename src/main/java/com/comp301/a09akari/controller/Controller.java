package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

public class Controller implements AlternateMvcController {
  private final Model model;

  public Controller(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int curr_idx = model.getActivePuzzleIndex();

    curr_idx = (curr_idx + 1) % model.getPuzzleLibrarySize();

    model.setActivePuzzleIndex(curr_idx);
  }

  @Override
  public void clickPrevPuzzle() {
    int curr_idx = model.getActivePuzzleIndex();

    curr_idx = (curr_idx - 1 + model.getPuzzleLibrarySize()) % model.getPuzzleLibrarySize();

    model.setActivePuzzleIndex(curr_idx);
  }

  @Override
  public void clickRandPuzzle() {
    int curr_idx = model.getActivePuzzleIndex();
    int next_idx = curr_idx;

    while (next_idx == curr_idx) {
      next_idx = (int) (Math.random() * model.getPuzzleLibrarySize());
    }

    model.setActivePuzzleIndex(next_idx);
  }

  @Override
  public void clickResetPuzzle() {

    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {

    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }
}
