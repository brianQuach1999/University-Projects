/* loic */

package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TitleScreen extends Application {

  public static void view() {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource(UiController.TITLE_SCREEN));
    root.getStylesheets().add(getClass().getResource("template/styles.css").toExternalForm());
    Scene scene = new Scene(root, 800, 600);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() {
    UiController.api.saveApplicationState();
  }
}
