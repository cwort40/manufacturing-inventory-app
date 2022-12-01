package c482.inventoryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This program creates an inventory tracking app */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1220, 300);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        InHouse part1 = new InHouse(1, "wheel", 20.00, 4, 1, 10, 5);

        Inventory.addPart(part1);

        Product product1 = new Product(1, "car", 5000.00, 1, 0, 2);

        Inventory.addProduct(product1);
        //int id, String name, double price, int stock, int min, int max

        launch();
    }
}