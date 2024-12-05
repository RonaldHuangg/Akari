package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AppView implements FXComponent {
  private final Controller controller;

  public AppView(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Pane layout = new VBox();

    FXComponent top = new MessageView(this.controller);
    FXComponent puzzle = new PuzzleView(this.controller);

    layout.getChildren().add(puzzle.render());
    layout.getChildren().add(top.render());

    return layout;
  }
}
