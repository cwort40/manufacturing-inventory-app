package c482.inventoryapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModifyPartController {

    Stage stage;
    Parent scene;
    private int partIndex = 0;

    @FXML
    private RadioButton ModifyPartOutsourcedCb;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private RadioButton modifyPartInHouseCb;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartMachineIdTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private ToggleGroup modifyPartTG;

    @FXML
    void onActionCancelModifiedPart(ActionEvent event) throws java.io.IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSaveModifiedPart(ActionEvent event) throws java.io.IOException {

        int id = Integer.parseInt(modifyPartIdTxt.getText());
        String partName = modifyPartNameTxt.getText();
        int partInv = Integer.parseInt(modifyPartInvTxt.getText());
        double partPrice = Double.parseDouble(modifyPartPriceTxt.getText());
        int partMax = Integer.parseInt(modifyPartMaxTxt.getText());
        int partMin = Integer.parseInt(modifyPartMinTxt.getText());

        String companyName;
        int machineId;
        

        if (ModifyPartOutsourcedCb.isSelected()) {
            companyName = modifyPartMachineIdTxt.getText();
            OutSourced addPart = new OutSourced(id, partName, partPrice, partInv, partMin, partMax, companyName);
            Inventory.updatePart(partIndex, addPart);
        }
        if (modifyPartInHouseCb.isSelected()) {
            machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
            InHouse addPart = new InHouse(id, partName, partPrice, partInv, partMin, partMax, machineId);
            Inventory.updatePart(partIndex, addPart);
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void sendPart(int index, Part selectedItem) {

        partIndex = index;

        modifyPartIdTxt.setText(String.valueOf(selectedItem.getId()));
        modifyPartNameTxt.setText(selectedItem.getName());
        modifyPartInvTxt.setText(String.valueOf(selectedItem.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(selectedItem.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(selectedItem.getMax()));
        modifyPartMinTxt.setText(String.valueOf(selectedItem.getMin()));

        if (selectedItem instanceof InHouse) {
            modifyPartMachineIdTxt.setText(String.valueOf(((InHouse) selectedItem).getMachineId()));
        }

        if (selectedItem instanceof OutSourced) {
            modifyPartMachineIdTxt.setText(String.valueOf(((OutSourced) selectedItem).getCompanyName()));
        }

    }
}