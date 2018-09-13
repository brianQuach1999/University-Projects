/* loic */

package ui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import transit.Card;
import transit.PriceModifier;
import transit.PriceModifierCharityPass;
import transit.PriceModifierFree;
import transit.PriceModifierMonthlyPass;
import util.FormattedDate;

public class EditCardScreenController extends UiController {

  private Card card;

  @FXML
  public Button addFifty;

  @FXML
  private Button returnButton;

  @FXML
  private Label balanceField;

  @FXML
  private Label nameLabel;

  @FXML
  private TextField nameField;

  @FXML
  private CheckBox isActiveCheckBox;

  @FXML
  private CheckBox hasTaxCheckBox;

  @FXML
  private ComboBox<PriceModifier> selectPriceModCombo;

  public void initialize() {

    card = (Card) dataStore.get(UiDataStore.CURRENT_CARD).data();
    balanceField.setText(card.getBalanceString());
    nameLabel.setText(card.getNickname());
    nameField.setText(card.getNickname());
    isActiveCheckBox.setSelected(card.isActive());
    hasTaxCheckBox.setSelected(card.isTaxOn());

    // create price modifiers drop down
    FormattedDate date = new FormattedDate();
    ArrayList<PriceModifier> priceModifiers = new ArrayList<>();
    priceModifiers.add(new PriceModifierFree());
    priceModifiers.add(new PriceModifierCharityPass(date));
    priceModifiers.add(new PriceModifierMonthlyPass(date));

    selectPriceModCombo.getItems().setAll(priceModifiers);
    PriceModifier currentPriceMod = card.getPriceModifier();
    if (currentPriceMod instanceof PriceModifierFree) {
      selectPriceModCombo.getSelectionModel().select(0);
    } else if (currentPriceMod instanceof PriceModifierCharityPass) {
      selectPriceModCombo.getSelectionModel().select(1);
    } else if (currentPriceMod instanceof PriceModifierMonthlyPass) {
      selectPriceModCombo.getSelectionModel().select(2);
    }
  }

  @FXML
  protected void handleAddTenButtonAction(ActionEvent event) {
    load(10);
  }

  @FXML
  protected void handleAddTwentyButtonAction(ActionEvent event) {
    load(20);
  }

  @FXML
  protected void handleAddFiftyButtonAction(ActionEvent event) {
    load(50);
  }

  @FXML
  protected void handleReturnButtonAction(ActionEvent event) {
    loadTemplate(HOMEPAGE_SCREEN, returnButton);
  }

  @FXML
  protected void handleSaveButtonAction() {
    api.card.update(card, nameField.getText(), isActiveCheckBox.isSelected());
    card.setPriceModifier(selectPriceModCombo.getSelectionModel().getSelectedItem());
    card.setTaxOn(hasTaxCheckBox.isSelected());
    initialize();
  }

  private void load(double amount) {
    api.card.load(card, amount);
    balanceField.setText(card.getBalanceString());
  }
}
