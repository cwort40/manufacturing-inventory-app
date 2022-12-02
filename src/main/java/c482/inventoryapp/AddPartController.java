package c482.inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class AddPartController {

    Stage stage;
    Parent scene;

    @FXML
    private Label machineIdOrCompanyName;

    @FXML
    private TextField addPartIdTxt;

    @FXML
    private RadioButton addPartInHouseCb;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartMachineIdTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private RadioButton addPartOutsourcedCb;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private ToggleGroup addPartTG;

    @FXML
    void OnActionOutsourced(ActionEvent event) throws java.io.IOException {
        machineIdOrCompanyName.setText("Company Name");
    }

    @FXML
    void OnActionInHouse(ActionEvent event) throws java.io.IOException {
        machineIdOrCompanyName.setText("Machine ID");
    }


    @FXML
    void OnActionCancelAddedPart(ActionEvent event) throws java.io.IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSaveAddedPart(ActionEvent event) throws java.io.IOException {

        //TODO: fix unique id generator

        ObservableList<Integer> list = FXCollections.observableArrayList();
        int i;
        for (i = 0; i < 100; i++) list.add(i);
        FXCollections.shuffle(list);

        int uniqueId = list.get(i);
        String partName = addPartNameTxt.getText();
        int partInv = Integer.parseInt(addPartInvTxt.getText());
        double partPrice = Double.parseDouble(addPartPriceTxt.getText());
        int partMax = Integer.parseInt(addPartMaxTxt.getText());
        int partMin = Integer.parseInt(addPartMinTxt.getText());

        String companyName;
        int machineId;


        if (addPartOutsourcedCb.isSelected()) {
            companyName = addPartMachineIdTxt.getText();
            OutSourced addPart = new OutSourced(uniqueId, partName, partPrice, partInv, partMin, partMax, companyName);
            Inventory.addPart(addPart);
        }
        if (addPartInHouseCb.isSelected()) {
            machineId = Integer.parseInt(addPartMachineIdTxt.getText());
            InHouse addPart = new InHouse(uniqueId, partName, partPrice, partInv, partMin, partMax, machineId);
            Inventory.addPart(addPart);
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();

    }
}