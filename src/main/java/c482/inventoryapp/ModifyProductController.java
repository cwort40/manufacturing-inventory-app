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
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productIndex = 0;


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
    private TableView<Part> asPartsTableView;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductSearchTxt;


    @FXML
    void onActionAddAsPart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
        }
        else if (!associatedParts.contains(selectedPart))
        {
            associatedParts.add(selectedPart);
            asPartsTableView.setItems(associatedParts);
        }
    }

    @FXML
    void onActionCancelModifiedProduct(ActionEvent event) throws java.io.IOException {

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
    void onActionSaveModifiedProduct(ActionEvent event) throws java.io.IOException {

        try {
            int id = Integer.parseInt(modifyProductIdTxt.getText());
            String productName = modifyProductNameTxt.getText();
            int productInv = Integer.parseInt(modifyProductInvTxt.getText());
            double productPrice = Double.parseDouble(modifyProductPriceTxt.getText());
            int productMax = Integer.parseInt(modifyProductMaxTxt.getText());
            int productMin = Integer.parseInt(modifyProductMinTxt.getText());

            Product modifyProduct = new Product(id, productName, productPrice, productInv, productMin, productMax);
            for (Part part : associatedParts) {
                modifyProduct.addAssociatedPart(part);
            }
            Inventory.updateProduct(productIndex, modifyProduct);

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
    void onActionMPSearch(ActionEvent event) {
        String searchTxt = modifyProductSearchTxt.getText();
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
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        asPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void sendProduct(int index, Product selectedItem) {

        productIndex = index;

        if (selectedItem != null) {
            modifyProductIdTxt.setText(String.valueOf(selectedItem.getId()));
            modifyProductNameTxt.setText(selectedItem.getName());
            modifyProductInvTxt.setText(String.valueOf(selectedItem.getStock()));
            modifyProductPriceTxt.setText(String.valueOf(selectedItem.getPrice()));
            modifyProductMaxTxt.setText(String.valueOf(selectedItem.getMax()));
            modifyProductMinTxt.setText(String.valueOf(selectedItem.getMin()));
            for (Part part : selectedItem.getAllAssociatedParts()) {
                associatedParts.add(part);
            }
            asPartsTableView.setItems(associatedParts);
        } else {
            Alert noPart = new Alert(Alert.AlertType.WARNING);
            noPart.setHeaderText("Warning");
            noPart.setContentText("No product was selected.");
            noPart.showAndWait();
        }
    }
}