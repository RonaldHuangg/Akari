package com.comp301.a09akari.model;

import com.comp301.a09akari.SamplePuzzles;
import java.util.ArrayList;
import java.util.List;

public class PuzzleLibraryImpl implements PuzzleLibrary {
  private final List<Puzzle> puzzles;

  public PuzzleLibraryImpl() {
    this.puzzles = new ArrayList<>();
  }

  public static PuzzleLibrary create() {
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();
    puzzleLibrary.addPuzzle(puzzle1);
    puzzleLibrary.addPuzzle(puzzle2);
    puzzleLibrary.addPuzzle(puzzle3);
    puzzleLibrary.addPuzzle(puzzle4);
    puzzleLibrary.addPuzzle(puzzle5);

    return puzzleLibrary;
  }

  @Override
  public void addPuzzle(Puzzle puzzle) {
    if (puzzle == null) {
      throw new IllegalArgumentException("Cannot add null Puzzle to the library");
    }
    puzzles.add(puzzle);
  }

  @Override
  public Puzzle getPuzzle(int index) {
    return puzzles.get(index);
  }

  @Override
  public int size() {
    return puzzles.size();
  }
}
