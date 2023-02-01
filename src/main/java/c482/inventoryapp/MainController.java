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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main menu scene controller
 */
public class MainController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, Integer> productInvLvlCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TextField partSearchTxt;
    @FXML
    private TextField productSearchTxt;

    /**
     * Brings user to add part scene
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws java.io.IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        stage.setTitle("Add Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Brings user to add product scene
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws java.io.IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Delete part from part view table upon pressing button <br>
     * RUNTIME ERROR: Program would crash if no product was selected because selectedPart would be null. This was fixed
     * by adding a conditional statement that confirms that an item has been selected prior to performing the delete
     * operation
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionDeletePart(ActionEvent event) throws java.io.IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (!(partsTableView.getSelectionModel().isEmpty())) {
            Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDel.setTitle("Confirmation");
            confirmDel.setHeaderText("Delete " + selectedPart.getName() + "?");
            Optional<ButtonType> result = confirmDel.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean ans = Inventory.deletePart(selectedPart);
                if (!ans) {
                    Alert noDel = new Alert(Alert.AlertType.ERROR);
                    noDel.setHeaderText("Could not delete " + selectedPart.getName());
                } else {
                    Inventory.deletePart(selectedPart);
                }
            }
        } else {
            Alert noSelection = new Alert(Alert.AlertType.WARNING);
            noSelection.setTitle("Warning");
            noSelection.setHeaderText("Please select a part to delete");
            noSelection.showAndWait();
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Delete product from product view table upon pressing button <br>
     * LOGICAL ERROR: The program requires the product to not be deleted if the product has associated parts. This is
     * completed by adding a conditional statement on line 132 that checks if the product's associated part list is
     * empty using the getAllAssociatedParts() method
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws java.io.IOException {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (productsTableView.getSelectionModel().isEmpty()) {
            Alert noSelection = new Alert(Alert.AlertType.WARNING);
            noSelection.setTitle("Warning");
            noSelection.setHeaderText("Please select a product to delete");
            noSelection.showAndWait();
        } else if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDel.setTitle("Confirmation");
            confirmDel.setHeaderText("Delete " + selectedProduct.getName() + "?");
            Optional<ButtonType> result = confirmDel.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean ans = Inventory.deleteProduct(selectedProduct);
                if (ans) {
                    Inventory.deleteProduct(selectedProduct);
                } else {
                    Alert noDel = new Alert(Alert.AlertType.ERROR);
                    noDel.setHeaderText("Could not delete " + selectedProduct.getName());
                }
            }
        } else {
            Alert confirmDel = new Alert(Alert.AlertType.WARNING);
            confirmDel.setTitle("WARNING");
            confirmDel.setHeaderText("Cannot delete " + selectedProduct.getName());
            confirmDel.setContentText("Please remove any associated parts from this product before deleting");
            confirmDel.showAndWait();
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Requires user to confirm and then exits program
     * @param event
     */
    @FXML
    void onActionExit (ActionEvent event) {
        Alert exitMsg = new Alert(Alert.AlertType.INFORMATION);
        exitMsg.setTitle("Warning");
        exitMsg.setHeaderText("Confirm your exit by pressing ok.");
        exitMsg.showAndWait();
        System.exit(0);
    }

    /**
     * Upon selecting a part and clicking modify button, brings user to modify part scene <br>
     * RUNTIME ERROR: Program would crash with a null pointer exception if the modify button was clicked with nothing
     * selected. A part needs to be selected in order to pass a value into the sendPart() method. This problem was
     * fixed by adding a conditional statement on line 186 to check if the selected item is null
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();
        ModifyPartController MPartController = loader.getController();
        MPartController.sendPart(partsTableView.getSelectionModel().getSelectedIndex(), partsTableView.getSelectionModel().getSelectedItem());
        if (!(partsTableView.getSelectionModel().getSelectedItem() == null)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Upon selecting a product and clicking modify button, brings user to modify product screen
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();
        ModifyProductController MProductController = loader.getController();
        MProductController.sendProduct(productsTableView.getSelectionModel().getSelectedIndex(), productsTableView.getSelectionModel().getSelectedItem());
        if (!(productsTableView.getSelectionModel().getSelectedItem() == null)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Searches for parts
     * @param event
     */
    @FXML
void mainScreenPartSearch(ActionEvent event) {
    String searchPart = partSearchTxt.getText();
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
     * Searches for products <br>
     * LOGICAL ERROR: In order for the search bar to support searches by ID and name, both of the overloaded versions of
     * the lookUpProduct() method had to be used. This was completed by declaring a boolean that determines if a query
     * is an integer (id) and if it is, it parses it and uses the lookUpProduct(id).
     * @param event
     */
    @FXML
    void mainScreenProductSearch(ActionEvent event) {
        String searchProduct = productSearchTxt.getText();
        ObservableList<Product> products = Inventory.lookUpProduct(searchProduct);
        boolean valueType = searchProduct.matches("[0-9]+");
        if (valueType) {
            int productId = Integer.parseInt(searchProduct);
            productsTableView.getSelectionModel().select(Inventory.lookUpProduct(productId));
        } else {
            if (products.isEmpty()) {
                Alert noParts = new Alert(Alert.AlertType.ERROR);
                noParts.setTitle("Error");
                noParts.setContentText("No product was found by that name.");
                noParts.showAndWait();
            } else if (products.size() > 1) {
                productsTableView.setItems(products);
            } else {
                productsTableView.getSelectionModel().select(Inventory.lookUpProduct(products.get(0).getId()));
            }
        }
    }

    /**
     * Initializes the main menu scene
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsTableView.setItems(Inventory.getAllParts());
        productsTableView.setItems(Inventory.getAllProducts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}