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
    void onActionSaveModifiedPart(ActionEvent event) {

    }
}