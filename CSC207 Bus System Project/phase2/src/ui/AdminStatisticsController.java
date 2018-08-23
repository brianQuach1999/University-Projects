package ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.FormattedDate;

public class AdminStatisticsController extends UiController {

  @FXML
  private TextField dateField;

  @FXML
  private StatsTablesController statsTablesController;

  @FXML
  public void initialize() {
    // set date to current filter date
    FormattedDate date = (FormattedDate) dataStore.get(UiDataStore.CURRENT_FILTER_DATE).data();
    if (date != null) {
      dateField.setText(date.toDateString());
    } else {
      dateField.setText("");
    }
  }

  @FXML
  public void handleApplyButtonAction() {
    FormattedDate date = new FormattedDate(dateField.getText());
    dataStore.set(UiDataStore.CURRENT_FILTER_DATE, new UiData<>(date));
    statsTablesController.initialize();
  }

  @FXML
  public void handleClearButtonAction() {
    dataStore.set(UiDataStore.CURRENT_FILTER_DATE, null);
    dateField.setText("");
    ;
    statsTablesController.initialize();
  }
}
