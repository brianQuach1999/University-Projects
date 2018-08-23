package ui;

import api.CreateUserException;
import api.UpdateUserException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import transit.UserAccount;
import transit.simplemodel.SimpleUser;

public class AdminUsersController extends UiController {

  private SimpleUser selectedUser;

  @FXML
  private TableView<SimpleUser> tableViewUsers;

  @FXML
  private TextField nameField;

  @FXML
  private TextField emailField;

  @FXML
  private TextField passwordField;

  @FXML
  private CheckBox isAdminCheckBox;

  @FXML
  private Label errorMessage;

  @FXML
  public void handleUserSelect() {
    SimpleUser user = tableViewUsers.getSelectionModel().getSelectedItem();
    if (user != null) {
      selectedUser = user;
      nameField.setText(user.getName());
      emailField.setText(user.getEmail());
      passwordField.setText(user.getPassword());
      isAdminCheckBox.setSelected(user.getIsAdmin());
    }
  }

  @FXML
  public void handleCreateButton() {
    try {
      UserAccount user = api.user
          .create(nameField.getText(), emailField.getText(), isAdminCheckBox.isSelected());
      logger.log.fine("Created user " + user.toString());
      updateView();
    } catch (CreateUserException e) {
      errorMessage
          .setText("Failed to create user. All fields are required. Emails must be unique.");
    }
  }

  @FXML
  public void handleDeleteButton() {
    api.user.delete(selectedUser.getEmail());
    updateView();
  }

  @FXML
  public void handleSaveButton() {
    try {
      api.user.update(selectedUser.getEmail(), nameField.getText(), emailField.getText(),
          passwordField.getText(), isAdminCheckBox.isSelected());
      updateView();
      // update navigation bar if updated user is the current user
      UserAccount currentUser = (UserAccount) dataStore.get(UiDataStore.CURRENT_USER).data();
    } catch (UpdateUserException e) {
      errorMessage
          .setText("Failed to update user. All fields are required. Emails must be unique.");
    }
  }

  @FXML
  public void initialize() {

    tableViewUsers.getSelectionModel().selectedIndexProperty()
        .addListener((num) -> handleUserSelect());
    updateView();
  }

  private void updateView() {
    errorMessage.setText("");
    tableViewUsers.getItems().setAll(api.user.getSimple());
  }


}
