package c482.inventoryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This program creates an inventory tracking app <br>
 * FUTURE ENHANCEMENT: I would create a database for this program to store part/product information for offline storage
 * of data. This enhancement would be added using a database with MySQL or a similar tool. The advantage of data
 * being stored offline would be helpful to the user since they would not have to re-enter information every time
 * that they wanted to use the program.*/
public class Main extends Application {
    /**
     * Loads main menu
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1220, 300);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * JAVADOCS IS STORED IN A SEPARATE FOLDER CALLED "JAVADOCS" IN ~/InventoryApp/javadoc <br>
     * Starts program
     * @param args
     */
    public static void main(String[] args) { launch(); }
}