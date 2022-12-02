package c482.inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<?, ?> asPartIdCol;

    @FXML
    private TableColumn<?, ?> asPartInvLvlCol;

    @FXML
    private TableColumn<?, ?> asPartNameCol;

    @FXML
    private TableColumn<?, ?> asPartPriceCol;

    @FXML
    private TableView<?> asPartsTableView;

    @FXML
    private TextField addProductIdTxt;

    @FXML
    private RadioButton addProductInHouseCb;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private RadioButton addProductOutsourcedCb;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private ToggleGroup addProductTG;

    @FXML
    void onActionAddAsPart(ActionEvent event) {

    }

    @FXML
    void onActionCancelAddedProduct(ActionEvent event) throws java.io.IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionRemovePart(ActionEvent event) {

    }

    @FXML
    void onActionSaveAddedProduct(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

//        asPartsTableView.setItems(associatedParts);
//
//        asPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        asPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        asPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        asPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
