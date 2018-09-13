package ui;

import java.util.SimpleTimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import transit.Card;
import transit.Station;
import transit.UserAccount;
import transit.simplemodel.SimpleTrip;
import util.FormattedDate;

/**
 * Handle all events and user input within the main user screen.
 */
public class UserScreenController extends UiController {

  private UserAccount user;
  private Card card;
  private Station station;

  @FXML
  private Button createCardButton;

  @FXML
  private Label balanceAmountLabel;

  @FXML
  private ComboBox<Card> selectCardCombo;

  @FXML
  private ComboBox<Station> selectStationCombo;

  @FXML
  private TextField dateField;

  @FXML
  private TextField timeField;

  @FXML
  private Label errorMessage;

  @FXML
  private Label successMessage;

  @FXML
  private Label infoMessage;

  @FXML
  private TableView<SimpleTrip> tableViewTrips;

  @FXML
  protected void initialize() {

    // reset error message
    errorMessage.setText("");

    // getSimple current user
    user = (UserAccount) dataStore.get(UiDataStore.CURRENT_USER).data();

    // getSimple current station
    station = (Station) dataStore.get(UiDataStore.CURRENT_STATION).data();

    // getSimple current card
    card = (Card) dataStore.get(UiDataStore.CURRENT_CARD).data();
    if (card != null) {
      updateBalanceLabel();
    }

    // init table view trips
    tableViewTrips.getItems().setAll(api.user.getTrips(user));

    // set default date and time to now
    FormattedDate date = new FormattedDate();
    dateField.setText(date.toDateString());
    timeField.setText(date.toTimeString());

    // Display all cards user owns
    selectCardCombo.getItems().addAll(user.getCards());
    selectCardCombo.valueProperty().addListener((obs, oldVal, newVal) -> handleCardSelect());

    // Display all stations
    selectStationCombo.getItems().addAll(api.map.getStations());
    selectStationCombo.valueProperty().addListener((obs, oldVal, newVal) -> handleStationSelect());
  }

  /**
   * Helper function to update the balance label.
   */
  private void updateBalanceLabel() {
    clearMessages();
    if (card != null) {
      balanceAmountLabel.setText(api.card.getBalanceString(card));
    }
  }

  /**
   * Helper function to handle a switch in selected card.
   */
  private void handleCardSelect() {
    clearMessages();
    Card newCard = selectCardCombo.getSelectionModel().getSelectedItem();
    if (newCard != null) {
      card = newCard;
      dataStore.set(UiDataStore.CURRENT_CARD, new UiData<>(card));
      updateBalanceLabel();
    }
  }

  /**
   * Helper function to handle s switch of selected station.
   */
  private void handleStationSelect() {
    clearMessages();
    Station newStation = selectStationCombo.getSelectionModel().getSelectedItem();
    if (newStation != null) {
      station = newStation;
      dataStore.set(UiDataStore.CURRENT_STATION, new UiData<>(station));
    }
  }

  /**
   * Prompt the create card screen.
   *
   * @param event
   */
  @FXML
  protected void handleCreateCardButton(ActionEvent event) {
    loadTemplate(CREATE_CARD_SCREEN, createCardButton);
  }

  /**
   * Prompt the edit card screen.
   *
   * @param event
   */
  @FXML
  protected void handleEditCardButton(ActionEvent event) {
    loadTemplate(EDIT_CARD_SCREEN, createCardButton);
  }

  /**
   * Handle the user tapping in.
   *
   * @param event
   */
  @FXML
  protected void handleTapInCardButton(ActionEvent event) {
    try {
      api.card.tapIn(station, card, dateField.getText(), timeField.getText());
      updateBalanceLabel();
      setSuccessMessage();
    } catch (Exception e) {
      setErrorMessage();
    }
  }

  /**
   * Handle event when the user taps out.
   *
   * @param event
   */
  @FXML
  protected void handleTapOutCardButton(ActionEvent event) {
    try {
      api.card.tapOut(station, card, dateField.getText(), timeField.getText());
      updateBalanceLabel();
      setSuccessMessage();
    } catch (Exception e) {
      setErrorMessage();
    }
  }

  /**
   * Clear messages.
   */
  private void clearMessages() {
    errorMessage.setText("");
    successMessage.setText("");
    infoMessage.setText("");
  }

  /**
   * Pass a success message.
   */
  private void setSuccessMessage() {
    clearMessages();
    successMessage.setText("Success.");
  }

  /**
   * Pass an error message.
   */
  private void setErrorMessage() {
    clearMessages();
    errorMessage.setText("Tap failed.");
  }

  /**
   * Pass an info message.
   */
  private void setInfoMessage() {
    clearMessages();
    infoMessage.setText("Nothing to tap.");
  }
}
