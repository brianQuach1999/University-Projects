package ui;

import api.Api;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.EasyLogger;

/**
 * Parent class for UI classes that links the API and the front-end together.
 */
public class UiController {

  public static Api api;
  public static EasyLogger logger = new EasyLogger("ui");

  static final String TITLE_SCREEN = "template/title_screen.fxml";
  static final String MAP_SCREEN = "template/admin_map_screen.fxml";
  static final String STATS_SCREEN = "template/admin_statistics_screen.fxml";
  static final String USERS_SCREEN = "template/admin_users_screen.fxml";
  static final String EDIT_CARD_SCREEN = "template/edit_card_screen.fxml";
  static final String CREATE_CARD_SCREEN = "template/create_card_screen.fxml";
  static final String SUCCESS_SCREEN = "template/success_create_screen.fxml";
  static final String LOGIN_SCREEN = "template/user_login_screen.fxml";
  static final String HOMEPAGE_SCREEN = "template/user_screen.fxml";

  //UI runtime data store
  public static UiDataStore dataStore = new UiDataStore();

  protected void loadTemplate(String template, Control control) {
    Window owner = control.getScene().getWindow();

    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(template));
      Scene scene = new Scene(loader.load(), 800, 600);
      Stage stage = new Stage();
      stage.setScene(scene);
      owner.hide();
      stage.show();
    } catch (IOException e) {
      logger.log.severe("Failed to load " + template);
    }
  }
}
