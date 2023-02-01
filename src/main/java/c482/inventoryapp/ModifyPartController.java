package c482.inventoryapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Modify part scene controller
 */
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
    private Label machineIdOrCompanyName;
    @FXML
    private ToggleGroup modifyPartTG;

    /**
     * Changes machineIdOrCompanyName label to "Company Name"
     * @param event
     */
    @FXML
    void OnActionOutsourced(ActionEvent event) {
        machineIdOrCompanyName.setText("Company Name");
    }

    /**
     * Changes machineIdOrCompanyName label to "Machine ID"
     * @param event
     */
    @FXML
    void OnActionInHouse(ActionEvent event) {
        machineIdOrCompanyName.setText("Machine ID");
    }

    /**
     * Redirects user to main menu upon pressing cancel button
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionCancelModifiedPart(ActionEvent event) throws java.io.IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves modified part to the inventory <br>
     * LOGICAL ERROR: Since Part is an abstract class, the program needs to save the part as an InHouse instance or
     * OutSourced instance. This is done using conditional statements. If the InHouse condition is met, the program
     * retrieves the machineId and then adds the part to the inventory. The same goes if the condition is met for the
     * OutSourced instance.
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionSaveModifiedPart(ActionEvent event) throws java.io.IOException {
        String companyName;
        int machineId;
        int id = Integer.parseInt(modifyPartIdTxt.getText());
        String error = "";
        try {
            error = "name";
            String partName = modifyPartNameTxt.getText();
            error = "inventory";
            int partInv = Integer.parseInt(modifyPartInvTxt.getText());
            error = "price";
            double partPrice = Double.parseDouble(modifyPartPriceTxt.getText());
            error = "maximum";
            int partMax = Integer.parseInt(modifyPartMaxTxt.getText());
            error = "minimum";
            int partMin = Integer.parseInt(modifyPartMinTxt.getText());
            if (partName.isBlank() || partName.matches("^[0-9]+$")) {
                generateAlert("name");
                return;
            }
            if (partMax <= partMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Try again");
                alert.setContentText("Minimum amount cannot be more than maximum amount");
                alert.showAndWait();
                return;
            } else if (partInv > partMax || partInv < partMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Try again");
                alert.setContentText("Inventory amount has to be between the minimum and maximum amounts");
                alert.showAndWait();
                return;
            }
            if (ModifyPartOutsourcedCb.isSelected()) {
                error = "company name";
                companyName = modifyPartMachineIdTxt.getText();
                if (companyName.isBlank() || companyName.matches("^[0-9]+$")) {
                    generateAlert("company name");
                    return;
                }
                OutSourced addPart = new OutSourced(id, partName, partPrice, partInv, partMin, partMax, companyName);
                Inventory.updatePart(partIndex, addPart);
            }
            if (modifyPartInHouseCb.isSelected()) {
                error = "machine id";
                machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
                InHouse addPart = new InHouse(id, partName, partPrice, partInv, partMin, partMax, machineId);
                Inventory.updatePart(partIndex, addPart);
            }
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
     * Takes the selected part from the main menu and populates the fields with its existing properties. <br>
     * LOGICAL ERROR: The sendPart() method has to be able to differentiate between InHouse instances and OutSourced
     * instances. It does so by using the instanceof operator
     * @param index
     * @param selectedItem
     */
    public void sendPart(int index, Part selectedItem) {
        partIndex = index;
        if (selectedItem != null) {
            modifyPartIdTxt.setText(String.valueOf(selectedItem.getId()));
            modifyPartNameTxt.setText(selectedItem.getName());
            modifyPartInvTxt.setText(String.valueOf(selectedItem.getStock()));
            modifyPartPriceTxt.setText(String.valueOf(selectedItem.getPrice()));
            modifyPartMaxTxt.setText(String.valueOf(selectedItem.getMax()));
            modifyPartMinTxt.setText(String.valueOf(selectedItem.getMin()));
            if (selectedItem instanceof InHouse) {
                modifyPartMachineIdTxt.setText(String.valueOf(((InHouse) selectedItem).getMachineId()));
                machineIdOrCompanyName.setText("Machine ID");
                modifyPartInHouseCb.setSelected(true);
            }
            if (selectedItem instanceof OutSourced) {
                modifyPartMachineIdTxt.setText(String.valueOf(((OutSourced) selectedItem).getCompanyName()));
                machineIdOrCompanyName.setText("Company Name");
                ModifyPartOutsourcedCb.setSelected(true);
            }
        } else {
            Alert noPart = new Alert(Alert.AlertType.WARNING);
            noPart.setHeaderText("Warning");
            noPart.setContentText("No part was selected.");
            noPart.showAndWait();
        }
    }
}