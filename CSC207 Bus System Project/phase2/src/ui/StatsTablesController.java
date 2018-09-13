package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import transit.simplemodel.SimpleRoute;
import transit.simplemodel.SimpleStation;
import transit.simplemodel.SimpleTap;
import transit.simplemodel.SimpleTransaction;
import transit.simplemodel.SimpleTrip;
import transit.simplemodel.SimpleUser;
import util.FormattedDate;

/**
 * Controller in charge of handling events and user inputs within the statistics screen.
 */
public class StatsTablesController extends UiController {

  @FXML
  private TableView<SimpleStation> tableViewStations;

  @FXML
  private TableView<SimpleTap> tableViewTaps;

  @FXML
  private TableView<SimpleTransaction> tableViewTransactions;

  @FXML
  private TableView<SimpleTrip> tableViewTrips;

  @FXML
  private TableView<SimpleUser> tableViewUsers;

  @FXML
  private TableView<SimpleRoute> tableViewRoutes;

  @FXML
  private Label revenueLabel;

  /**
   * Initialize the screen by fetching all necessary
   * statistical information from the backend.
   */
  @FXML
  public void initialize() {

    tableViewStations.getItems().clear();
    tableViewTrips.getItems().clear();
    tableViewTransactions.getItems().clear();
    tableViewTaps.getItems().clear();
    tableViewUsers.getItems().clear();
    tableViewRoutes.getItems().clear();


    FormattedDate date = (FormattedDate) dataStore.get(UiDataStore.CURRENT_FILTER_DATE).data();

    revenueLabel.setText("$" + String.format("%.2f", api.stats.getRevenueOnDate(date)));
    tableViewStations.getItems().setAll(api.stats.getStationsSimple(date));
    tableViewTrips.getItems().setAll(api.stats.getTripsSimple(date));
    tableViewTransactions.getItems().setAll(api.stats.getTransactionsSimple(date));
    tableViewTaps.getItems().setAll(api.stats.getTapsSimple(date));
    tableViewUsers.getItems().setAll(api.stats.getUsers(date));
    tableViewRoutes.getItems().setAll(api.stats.getRoutesSimple(date));


  }
}
