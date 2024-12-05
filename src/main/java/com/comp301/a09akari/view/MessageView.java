package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageView implements FXComponent {

  private final Controller controller;

  public MessageView(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    // Create a container for the message
    HBox layout = new HBox();
    layout.setAlignment(Pos.CENTER); // Center the message horizontally
    layout.getStyleClass().add("layout");

    // Display "Puzzle Solved!" message if the puzzle is solved
    Label solvedMessage = new Label("Puzzle Solved!");
    solvedMessage.getStyleClass().add("solved-message");
    solvedMessage.setVisible(controller.isSolved()); // Show only when solved

    layout.getChildren().add(solvedMessage);
    return layout;
  }
}
