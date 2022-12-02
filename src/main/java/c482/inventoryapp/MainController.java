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

    @FXML
    void onActionAddPart(ActionEvent event) throws java.io.IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setTitle("Add Part");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws java.io.IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (partsTableView.getSelectionModel().isEmpty()) {
            Alert noSelection = new Alert(Alert.AlertType.WARNING);
            noSelection.setTitle("Warning");
            noSelection.setHeaderText("Please select a part to delete");
            noSelection.showAndWait();
        } else if (!(partsTableView.getSelectionModel().isEmpty())) {
            Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDel.setTitle("Confirmation");
            confirmDel.setHeaderText("Delete " + selectedPart.getName() + "?");
            Optional<ButtonType> result = confirmDel.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean ans = Inventory.deletePart(selectedPart);
                    Inventory.deletePart(selectedPart);
                    if (!ans) {
                        Alert noDel = new Alert(Alert.AlertType.ERROR);
                        noDel.setHeaderText("Could not delete " + selectedPart.getName());
                    }
                }
            }
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (productsTableView.getSelectionModel().isEmpty()) {
            Alert noSelection = new Alert(Alert.AlertType.WARNING);
            noSelection.setTitle("Warning");
            noSelection.setHeaderText("Please select a product to delete");
            noSelection.showAndWait();
        } else if (!(productsTableView.getSelectionModel().isEmpty())) {
            Alert confirmDel = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDel.setTitle("Confirmation");
            confirmDel.setHeaderText("Delete " + selectedProduct.getName() + "?");
            Optional<ButtonType> result = confirmDel.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean ans = Inventory.deleteProduct(selectedProduct);
                Inventory.deleteProduct(selectedProduct);
                if (!ans) {
                    Alert noDel = new Alert(Alert.AlertType.ERROR);
                    noDel.setHeaderText("Could not delete " + selectedProduct.getName());
                }
            }

        }
    }

    @FXML
    void onActionExit (ActionEvent event) {

        System.exit(0);

    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws java.io.IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();

        ModifyPartController MPartController = loader.getController();
        MPartController.sendPart(partsTableView.getSelectionModel().getSelectedIndex(), partsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws java.io.IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();

        ModifyProductController MProductController = loader.getController();
        MProductController.sendProduct(productsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Modify Product");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void mainScreenPartSearch(ActionEvent event) {
        String searchTxt = partSearchTxt.getText();
        ObservableList<Part> results = Inventory.LookUpPart(searchTxt);
        try {
            while (results.size() == 0 ) {
                int partId = Integer.parseInt(searchTxt);
                results.add(Inventory.LookUpPart(partId));
            }
            if (results.size() == 1) {
                partsTableView.getSelectionModel().select(selectPart(results.get(0).getId()));
            } else if (results.size() > 1)
            partsTableView.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part was not found");
            noParts.showAndWait();
        }
    }

    @FXML
    void mainScreenProductSearch(ActionEvent event) {
//        String searchTxt = productSearchTxt.getText();
//        ObservableList<Product> results = Inventory.LookUpProduct(searchTxt);
//        try {
//            while (results.size() == 0 ) {
//                int partId = Integer.parseInt(searchTxt);
//                results.add(Inventory.LookUpProduct(partId));
//            }
//            productsTableView.setItems(results);
//        } catch (NumberFormatException e) {
//            Alert noParts = new Alert(Alert.AlertType.ERROR);
//            noParts.setTitle("Error Message");
//            noParts.setContentText("Part was not found");
//            noParts.showAndWait();
//        }
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

        productsTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));




    }
}