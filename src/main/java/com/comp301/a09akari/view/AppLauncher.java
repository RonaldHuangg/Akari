package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.*;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Model model = new ModelImpl(PuzzleLibraryImpl.create());
    Controller controller = new Controller(model);

    AppView view = new AppView(controller);

    Scene scene = new Scene(view.render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);

    model.addObserver(
        (Model m) -> {
          scene.setRoot(view.render());
          stage.sizeToScene();
        });

    stage.setTitle("Akari");
    stage.show();
  }
}
