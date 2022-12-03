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

    private static int idTotal = 1;

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
    private TableColumn<Part, Integer> asPartIdCol;

    @FXML
    private TableColumn<Part, Integer> asPartInvLvlCol;

    @FXML
    private TableColumn<Part, String> asPartNameCol;

    @FXML
    private TableColumn<Part, Double> asPartPriceCol;

    @FXML
    private TableView<Part> asPartsTableView;

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


    //TODO: use getter and setter from product class instead of local observable list of associatedParts?
    @FXML
    void onActionAddAsPart(ActionEvent event) throws java.io.IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
        } else if (!associatedParts.contains(selectedPart)) {
            associatedParts.add(selectedPart);
            asPartsTableView.setItems(associatedParts);
        }

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
        Part selectedPart = asPartsTableView.getSelectionModel().getSelectedItem();

        if (asPartsTableView.getSelectionModel().isEmpty()) {
            Alert noSelection = new Alert(Alert.AlertType.WARNING);
            noSelection.setTitle("Warning");
            noSelection.setHeaderText("Please select a part to delete");
            noSelection.showAndWait();
        }
        boolean ans = associatedParts.remove(selectedPart);
        associatedParts.remove(selectedPart);
        if (!ans) {
            Alert noDel = new Alert(Alert.AlertType.ERROR);
            noDel.setHeaderText("Could not delete " + selectedPart.getName());
        }
    }


    @FXML
    void onActionSaveAddedProduct(ActionEvent event) throws java.io.IOException {

        try {
            int uniqueId = getNewId();
            String partName = addProductNameTxt.getText();
            int partInv = Integer.parseInt(addProductInvTxt.getText());
            double partPrice = Double.parseDouble(addProductPriceTxt.getText());
            int partMax = Integer.parseInt(addProductMaxTxt.getText());
            int partMin = Integer.parseInt(addProductMinTxt.getText());

            Product addProduct = new Product(uniqueId, partName, partPrice, partInv, partMin, partMax);
            for (Part part : asPartsTableView.getItems()) {
                addProduct.addAssociatedPart(part);
            }
            Inventory.addProduct(addProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please ensure all fields have correct values");
            alert.showAndWait();
        }


    }

    @FXML
    void onActionAPSearch(ActionEvent event) {
        String searchTxt = addProductSearchTxt.getText();
        ObservableList<Part> parts = Inventory.LookUpPart(searchTxt);

        boolean valueType = searchTxt.matches("[0-9]+");
        if (valueType) {
            int partId = Integer.parseInt(searchTxt);
            partsTableView.getSelectionModel().select(selectPart(partId));
        } else {
            if (parts.isEmpty()) {
                Alert noParts = new Alert(Alert.AlertType.WARNING);
                noParts.setTitle("Warning");
                noParts.setContentText("No part was found by that name.");
                noParts.showAndWait();
            } else if (parts.size() > 1) {
                partsTableView.setItems(parts);
            } else {
                partsTableView.getSelectionModel().select(selectPart(parts.get(0).getId()));
            }
        }
    }

    public Part selectPart(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id)
                return part;
        }
        return null;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        asPartsTableView.setItems(associatedParts);

        asPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public static synchronized int getNewId() {
        return idTotal++;
    }
}
