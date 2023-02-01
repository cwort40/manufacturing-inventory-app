package c482.inventoryapp;

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

/**
 * Add product scene controller
 */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    private static int idTotal = 1;
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
    private TextField addProductInvTxt;
    @FXML
    private TextField addProductMaxTxt;
    @FXML
    private TextField addProductMinTxt;
    @FXML
    private TextField addProductNameTxt;
    @FXML
    private TextField addProductPriceTxt;
    @FXML
    private TextField addProductSearchTxt;

    /**
     * Adds associated part to product upon pressing button. User selects a part from top menu to bottom menu. <br>
     * LOGICAL ERROR: In order to prevent duplicate parts from being added to the list, the conditional statement on
     * line 73 was added to check for existing associated parts
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
    void onActionCancelAddedProduct(ActionEvent event) throws java.io.IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes selected associated part from the table upon pressing remove button <br>
     * RUNTIME ERROR: The method would execute when selectedPart was null (the user didn't select an item). This was
     * solved by adding a conditional statement to check if selectedPart is null. If it is null, an error message is
     * displayed, and the program returns.
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
     * Saves added product to inventory <br>
     * LOGICAL ERROR: In order to prevent incorrect input, conditional statements are used as filters. Inventory has
     * to be in between Min and Max. Max cannot be less than Min, & vice versa.
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionSaveAddedProduct(ActionEvent event) throws java.io.IOException {
        String error = "";
        try {
            error = "name";
            String productName = addProductNameTxt.getText();
            error = "inventory";
            int productInv = Integer.parseInt(addProductInvTxt.getText());
            error = "price";
            double productPrice = Double.parseDouble(addProductPriceTxt.getText());
            error = "maximum";
            int productMax = Integer.parseInt(addProductMaxTxt.getText());
            error = "minimum";
            int productMin = Integer.parseInt(addProductMinTxt.getText());
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
            product.setId(getNewId());
            product.setName(productName);
            product.setStock(productInv);
            product.setPrice(productPrice);
            product.setMax(productMax);
            product.setMin(productMin);
            Inventory.addProduct(product);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please ensure " + error + " has a correct value");
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
        alert.setContentText("Please ensure the " + field + " is correct");
        alert.showAndWait();
    }

    /**
     * Searches for parts in the part table view <br>
     * RUNTIME ERROR: The program would crash if invalid input was put into the search field. This was avoided by
     * adding a conditional statement on line 184 that checks if the matching part list is empty. If it is empty, then
     * an error message appears.
     * @param event
     */
    @FXML
    void onActionAPSearch(ActionEvent event) {
        String searchPart = addProductSearchTxt.getText();
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
     * Initializes the add product scene
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
     * uses the local idTotal integer to generate a contiguous uniqueId for each part
     * @return
     */
    public static synchronized int getNewId() {
        return idTotal++;
    }
}
