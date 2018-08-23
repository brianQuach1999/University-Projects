package ui;

import api.LoginFailedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import transit.UserAccount;

/**
 * Handles all events and user inputs within the user login screen.
 */
public class UserLoginScreenController extends UiController {

  @FXML
  private Button loginButton;

  @FXML
  private Button returnButton;

  @FXML
  private TextField userField;

  @FXML
  private PasswordField passField;

  @FXML
  private Label errorMessage;

  /**
   * Attempt to log in and check credentials.
   *
   * @param event
   */
  @FXML
  protected void handleLoginButtonAction(ActionEvent event) {
    String email = userField.getText();
    String password = passField.getText();

    errorMessage.setText("");

    if (email.isEmpty()) {
      email = "<blank>";
    }

    try {
      UserAccount user = api.user.login(email, password);
      dataStore.set(UiDataStore.CURRENT_USER, new UiData<UserAccount>(user));
      loadTemplate(UiController.HOMEPAGE_SCREEN, loginButton);
    } catch (LoginFailedException e) {
      errorMessage.setText("Log in failed.");
    }
  }

  /**
   * Return to the title screen.
   *
   * @param event
   */
  @FXML
  protected void handleReturnButtonAction(ActionEvent event) {
    loadTemplate(UiController.TITLE_SCREEN, returnButton);
  }
}
