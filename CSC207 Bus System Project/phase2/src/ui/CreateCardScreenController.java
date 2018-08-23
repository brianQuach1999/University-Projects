/* the create card controller */

package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import transit.UserAccount;

public class CreateCardScreenController extends UiController {

  @FXML
  private Button returnButton;

  @FXML
  private TextField nameField;

  @FXML
  protected void handleReturnButtonAction(ActionEvent event) {
    loadTemplate(HOMEPAGE_SCREEN, returnButton);
  }

  @FXML
  protected void handleCreateCardButton(ActionEvent event) {

    // getSimple current user
    UserAccount user = (UserAccount) dataStore.get(UiDataStore.CURRENT_USER).data();
    if (user != null) {
      api.card.create(user, nameField.getText());
      loadTemplate(SUCCESS_SCREEN, returnButton);
    } else {
      logger.log.warning("Can't create card on null user.");
    }
  }
}
