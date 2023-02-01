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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Modify product scene controller
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productIndex = 0;
    Product product;

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

    /**
     * Adds associated part to product upon pressing button. User selects a part from top menu to bottom menu. <br>
     * RUNTIME ERROR: If selectedPart is null, the program will crash with a null pointer exception error. This is
     * solved by adding a conditional statement that checks if it is null and if it is, it creates a warning.
     * @param event
     */
    @FXML
    void onActionAddAsPart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
        } else if (!product.getAllAssociatedParts().contains(selectedPart)) {
            product.addAssociatedPart(selectedPart);
        }
    }

    /**
     * Redirects user to main menu upon pressing cancel button
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionCancelModifiedProduct(ActionEvent event) throws java.io.IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes selected associated part from the table upon pressing remove button <br>
     * LOGICAL ERROR: The program needs to figure out first whether a part can be deleted or not. A boolean value
     * called "ans" is declared in order to check for this. "ans" is used as a condition on line 110 to determine whether
     * the part will be deleted or not.
     * @param event
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        try {
            Part selectedPart = asPartsTableView.getSelectionModel().getSelectedItem();
            boolean ans = product.deleteAssociatedPart(selectedPart);
            Alert confirmDel = new Alert(Alert.AlertType.INFORMATION);
            confirmDel.setTitle("Confirmation");
            confirmDel.setHeaderText("Deleting associated part:" + selectedPart.getName() + "from the product.");
            confirmDel.showAndWait();
            if (ans) {
                product.deleteAssociatedPart(selectedPart);
            } else {
                Alert noDel = new Alert(Alert.AlertType.ERROR);
                noDel.setHeaderText("Could not delete " + selectedPart.getName());
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
    }

    /**
     * Saves modified product to inventory <br>
     * RUNTIME ERROR: The program will throw an ioexception error if "throws IOException" is not implemented because it
     * would not be able to load the Main Menu scene otherwise. This is solved by adding the "throws IOException"
     * statement to the declaration of the method.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveModifiedProduct(ActionEvent event) throws IOException {
        String error = "";
        int id = Integer.parseInt(modifyProductIdTxt.getText());
        try {
            error = "name";
            String productName = modifyProductNameTxt.getText();
            error = "inventory";
            int productInv = Integer.parseInt(modifyProductInvTxt.getText());
            error = "price";
            double productPrice = Double.parseDouble(modifyProductPriceTxt.getText());
            error = "maximum";
            int productMax = Integer.parseInt(modifyProductMaxTxt.getText());
            error = "minimum";
            int productMin = Integer.parseInt(modifyProductMinTxt.getText());
            if (productName.isBlank() || productName.matches("^[0-9]+$")) {
                generateAlert("name");
                return;
            }
            if (productMax <= productMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Try again");
                alert.setContentText("Minimum amount cannot be more than maximum amount");
                alert.showAndWait();
                return;
            }
            if (productInv > productMax || productInv < productMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Try again");
                alert.setContentText("Inventory amount has to be between the minimum and maximum amounts");
                alert.showAndWait();
                return;
            }
            product.setId(id);
            product.setName(productName);
            product.setPrice(productPrice);
            product.setStock(productInv);
            product.setMin(productMin);
            product.setMax(productMax);
            Inventory.updateProduct(productIndex, product);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please ensure " + error + " is correct");
            alert.showAndWait();
        }
    }

    /**
     * Creates a custom alert for the errors in the on action save method
     * @param field
     */
    public void generateAlert(String field) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Please ensure the " + field + " has correct values");
        alert.showAndWait();
    }

    /**
     * Searches for parts in the part table view
     * @param event
     */
    @FXML
    void onActionMPSearch(ActionEvent event) {
        String searchPart = modifyProductSearchTxt.getText();
        ObservableList<Part> parts = Inventory.lookUpPart(searchPart);
        boolean valueType = searchPart.matches("[0-9]+");
        if (valueType) {
            int partId = Integer.parseInt(searchPart);
            partsTableView.getSelectionModel().select(Inventory.lookUpPart(partId));
        } else {
            if (parts.isEmpty()) {
                Alert noParts = new Alert(Alert.AlertType.ERROR);
                noParts.setTitle("Error");
                noParts.setContentText("No part was found by that name.");
                noParts.showAndWait();
            } else if (parts.size() > 1) {
                partsTableView.setItems(parts);
            } else {
                partsTableView.getSelectionModel().select(Inventory.lookUpPart(parts.get(0).getId()));
            }
        }
    }

    /**
     * Initializes modify product scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = new Product(0, null, 0.0, 0, 0, 0);
        asPartsTableView.setItems(product.getAllAssociatedParts());
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

    /**
     * RUNTIME ERROR: The program would throw a null pointer exception error if the program didn't check whether
     * selectedItem is null or not. This was solved by adding a conditional statement.
     * Takes the selected product from the main menu and populates the fields with its existing properties.
     * @param index
     * @param selectedItem
     */
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
                product.addAssociatedPart(part);
            }
            asPartsTableView.setItems(product.getAllAssociatedParts());
        } else {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("No product was selected");
            noParts.showAndWait();
        }
    }
}