package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class PuzzleView implements FXComponent {

  private final Controller controller;
  private static final int CELL_SIZE = 50; // Fixed size for all cells
  private static final int BOARD_WIDTH = 500; // Fixed width for the board
  private static final int BOARD_HEIGHT = 500; // Fixed height for the board

  public PuzzleView(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox(20); // Vertical spacing
    layout.setAlignment(Pos.CENTER); // Center everything
    layout.getStyleClass().add("layout");

    // Header: Game title and puzzle index
    HBox header = new HBox(10); // Horizontal spacing
    header.setAlignment(Pos.CENTER);
    Label title = new Label("Akari");
    title.getStyleClass().add("logo");
    Label puzzleIndex =
        new Label(
            "Puzzle "
                + (controller.getActivePuzzleIndex() + 1)
                + " of "
                + 5); // Total puzzles hardcoded
    puzzleIndex.getStyleClass().add("score");

    header.getChildren().addAll(title, puzzleIndex);

    // Game board container
    StackPane boardWrapper = new StackPane();
    boardWrapper.setPrefSize(BOARD_WIDTH, BOARD_HEIGHT); // Fixed size for the board container
    boardWrapper.setMaxSize(BOARD_WIDTH, BOARD_HEIGHT);
    boardWrapper.setStyle(
        "-fx-background-color: #bbada0; -fx-border-radius: 10;"); // Match board background

    // Game board grid
    GridPane board = new GridPane();
    board.setHgap(5);
    board.setVgap(5);
    board.setAlignment(Pos.CENTER); // Center the grid inside the container

    // Add cells to the board
    Puzzle active = controller.getActivePuzzle();
    for (int r = 0; r < active.getHeight(); r++) {
      for (int c = 0; c < active.getWidth(); c++) {
        CellType type = active.getCellType(r, c);
        switch (type) {
          case CLUE:
            board.add(makeClue(r, c), c, r);
            break;
          case WALL:
            board.add(makeWall(), c, r);
            break;
          case CORRIDOR:
            board.add(makeCorridor(r, c), c, r);
            break;
          default:
            break;
        }
      }
    }

    boardWrapper.getChildren().add(board); // Add grid to the board wrapper

    // Controls
    HBox controls = makeControls();

    // Add all components to the layout
    layout.getChildren().addAll(header, boardWrapper, controls);

    return layout;
  }

  private Label makeClue(int r, int c) {
    Label label = new Label(String.valueOf(controller.getActivePuzzle().getClue(r, c)));
    label.getStyleClass().add(controller.isClueSatisfied(r, c) ? "clue-satisfied" : "clue");
    label.setPrefSize(CELL_SIZE, CELL_SIZE); // Fixed size for all cells
    label.setAlignment(Pos.CENTER);
    return label;
  }

  private Label makeWall() {
    Label label = new Label();
    label.getStyleClass().add("wall");
    label.setPrefSize(CELL_SIZE, CELL_SIZE); // Fixed size for all cells
    return label;
  }

  private Button makeCorridor(int r, int c) {
    Button button = new Button();
    button.setPrefSize(CELL_SIZE, CELL_SIZE); // Fixed size for all cells

    if (controller.isLamp(r, c)) {
      button.setText("💡");
      button.getStyleClass().add(controller.isLampIllegal(r, c) ? "lamp-illegal" : "lamp");
    } else if (controller.isLit(r, c)) {
      button.getStyleClass().add("lit");
    } else {
      button.getStyleClass().add("corridor");
    }

    button.setOnAction(
        e -> {
          controller.clickCell(r, c);
          button.getScene().setRoot(render()); // Rerender the scene
        });

    return button;
  }

  private HBox makeControls() {
    HBox controls = new HBox(15); // Spacing between buttons
    controls.setAlignment(Pos.CENTER); // Center the buttons

    Button prev = new Button("Previous");
    prev.setOnAction(
        e -> {
          controller.clickPrevPuzzle();
          prev.getScene().setRoot(render());
        });

    Button next = new Button("Next");
    next.setOnAction(
        e -> {
          controller.clickNextPuzzle();
          next.getScene().setRoot(render());
        });

    Button reset = new Button("Reset");
    reset.setOnAction(
        e -> {
          controller.clickResetPuzzle();
          reset.getScene().setRoot(render());
        });

    Button random = new Button("Random");
    random.setOnAction(
        e -> {
          controller.clickRandPuzzle();
          random.getScene().setRoot(render());
        });

    controls.getChildren().addAll(prev, random, reset, next);
    return controls;
  }
}
