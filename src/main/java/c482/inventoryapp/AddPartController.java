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

/**
 * Add part scene controller
 */
public class AddPartController {
    Stage stage;
    Parent scene;
    private static int idTotal = 1;

    @FXML
    private Label machineIdOrCompanyName;
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
     * Redirects user to main menu upon pressing cancel button <br>
     * RUNTIME ERROR: Caused by: Unhandled exception: java.io.IOException. I fixed this adding a
     * "throws java.io.IOException" statement that allows the program to load the main screen after pressing cancel.
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void OnActionCancelAddedPart(ActionEvent event) throws java.io.IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves part to inventory <br>
     * RUNTIME ERROR: Caused by: java.lang.NumberFormatException. This happened when invalid input was entered into the
     * fields. I fixed this by adding the try and catch statements to the block. This error would happen when invalid
     * info was input into the fields.
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionSaveAddedPart(ActionEvent event) throws java.io.IOException {
        String companyName;
        int machineId;
        int uniqueId = getNewId();
        String error = "";
        try {
            error = "name";
            String partName = addPartNameTxt.getText();
            error = "inventory";
            int partInv = Integer.parseInt(addPartInvTxt.getText());
            error = "price";
            double partPrice = Double.parseDouble(addPartPriceTxt.getText());
            error = "maximum";
            int partMax = Integer.parseInt(addPartMaxTxt.getText());
            error = "minimum";
            int partMin = Integer.parseInt(addPartMinTxt.getText());
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
            if (addPartOutsourcedCb.isSelected()) {
                error = "company name";
                companyName = addPartMachineIdTxt.getText();
                if (companyName.isBlank() || companyName.matches("^[0-9]+$")) {
                    generateAlert("company name");
                    return;
                }
                OutSourced addPart = new OutSourced(uniqueId, partName, partPrice, partInv, partMin, partMax, companyName);
                Inventory.addPart(addPart);
                addPart.setCompanyName(companyName);
            } else if (addPartInHouseCb.isSelected()) {
                error = "machine id";
                machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                InHouse addPart = new InHouse(uniqueId, partName, partPrice, partInv, partMin, partMax, machineId);
                Inventory.addPart(addPart);
                addPart.setMachineId(machineId);
            } else {
                Alert noDel = new Alert(Alert.AlertType.ERROR);
                noDel.setHeaderText("Could not delete ");
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
     * uses the local idTotal integer to generate a contiguous uniqueId for each part
     * @return
     */
    public static synchronized int getNewId() { return idTotal++; }
}