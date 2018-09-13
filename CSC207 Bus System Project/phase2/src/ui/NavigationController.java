package ui;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import transit.UserAccount;

/**
 * Class that handles all inputs within the menu bar.
 */
public class NavigationController extends UiController implements Initializable {

  private UserAccount user;

  @FXML
  protected MenuBar menuBar;

  /**
   * Handle logging out.
   *
   * @param event
   */
  @FXML
  protected void handleLogoutButtonAction(ActionEvent event) {
    dataStore.set(UiDataStore.CURRENT_USER, null);
    loadTemplate(TITLE_SCREEN);
  }

  /**
   * Handle logging in.
   *
   * @param event
   */
  @FXML
  protected void handleLoginButtonAction(ActionEvent event) {
    loadTemplate(LOGIN_SCREEN);
  }

  /**
   * Handle navigating to the user's account page.
   *
   * @param event
   */
  @FXML
  protected void handleMyAccountButtonAction(ActionEvent event) {
    loadTemplate(HOMEPAGE_SCREEN);
  }

  /**
   * Handle navigating to the screen that displays all users.
   *
   * @param event
   */
  @FXML
  protected void handleUsersButtonAction(ActionEvent event) {
    loadTemplate(USERS_SCREEN);
  }

  /**
   * Handle navigating to the statistics page.
   *
   * @param event
   */
  @FXML
  protected void handleStatisticsButtonAction(ActionEvent event) {
    loadTemplate(STATS_SCREEN);
  }

  /**
   * Handle navigating to the maps page.
   *
   * @param event
   */
  @FXML
  protected void handleMapButtonAction(ActionEvent event) {
    loadTemplate(MAP_SCREEN);
  }

  /**
   * Initialize with the user's data. Show a different menu for admin users.
   *
   * @param arg0
   * @param arg1
   */
  @FXML
  public void initialize(java.net.URL arg0, ResourceBundle arg1) {

    user = (UserAccount) dataStore.get(UiDataStore.CURRENT_USER).data();

    if (user != null) {
      Label menuLogoutLabel = addMenuLabel("Logout");
      menuLogoutLabel.setOnMouseClicked(event -> handleLogoutButtonAction(null));
      Label menuAccountLabel = addMenuLabel("My Account");
      menuAccountLabel.setOnMouseClicked(event -> handleMyAccountButtonAction(null));
      if (user.isAdmin()) {
        createAdminMenu();
      }
    } else {
      Label menuLoginLabel = addMenuLabel("Login");
      menuLoginLabel.setOnMouseClicked(event -> handleLoginButtonAction(null));
    }
  }

  /**
   * Add the menu label.
   *
   * @param label
   * @return
   */
  private Label addMenuLabel(String label) {
    Menu menu = new Menu();
    Label menuLabel = new Label(label);
    menu.setGraphic(menuLabel);
    menuBar.getMenus().add(menu);
    return menuLabel;
  }

  /**
   * Create the Admin menu.
   */
  private void createAdminMenu() {
    Menu menuAdmin = new Menu("Admin");
    MenuItem menuItem1 = new MenuItem("Users");
    menuItem1.setOnAction(event -> handleUsersButtonAction(null));
    MenuItem menuItem2 = new MenuItem("Statistics");
    menuItem2.setOnAction(event -> handleStatisticsButtonAction(null));
    MenuItem menuItem3 = new MenuItem("Map");
    menuItem3.setOnAction(event -> handleMapButtonAction(null));
    menuAdmin.getItems().addAll(menuItem1, menuItem2, menuItem3);
    menuBar.getMenus().add(menuAdmin);
  }

  private void loadTemplate(String template) {
    super.loadTemplate(template, menuBar);
  }
}
