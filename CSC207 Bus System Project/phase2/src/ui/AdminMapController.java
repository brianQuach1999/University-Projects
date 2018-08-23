package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import transit.Station;
import transit.simplemodel.SimpleRoute;
import transit.simplemodel.SimpleStation;

public class AdminMapController extends UiController {

  @FXML
  private Label stationsLabel;

  @FXML
  private TableView<SimpleStation> tableViewStations;

  @FXML
  private TableView<SimpleRoute> tableViewRoutes;

  @FXML
  private EditStationController editStationController;

  public void initialize() {

    tableViewRoutes.getSelectionModel().selectedIndexProperty()
        .addListener((num) -> handleRouteSelect());
    tableViewStations.getSelectionModel().selectedIndexProperty()
        .addListener((num) -> handleStationSelect());

    tableViewStations.getItems().setAll(api.map.getStationsSimple());
    tableViewRoutes.getItems().setAll(api.map.getRoutesSimple());
  }

  @FXML
  public void handleRouteSelect() {
    SimpleRoute route = tableViewRoutes.getSelectionModel().getSelectedItem();
    if (route != null) {
      stationsLabel.setText("Stations (" + route.getName() + " " + route.getType() + ")");
      tableViewStations.getItems().clear();
      tableViewStations.getItems().setAll(api.map.getStationsSimple(route));
    }
  }

  @FXML
  public void handleStationSelect() {
    SimpleStation simpleStation = tableViewStations.getSelectionModel().getSelectedItem();
    if (simpleStation != null) {
      Station station = api.map.getStation(simpleStation);
      dataStore.set(UiDataStore.CURRENT_STATION, new UiData<>(station));
      editStationController.initialize();
    }
  }

  @FXML
  public void handleClearButton() {
    stationsLabel.setText("Stations");
    tableViewRoutes.getSelectionModel().clearSelection();
    tableViewStations.getItems().setAll(api.map.getStationsSimple());
  }
}
