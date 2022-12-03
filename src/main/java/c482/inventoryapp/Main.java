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

//        InHouse part1 = new InHouse(1, "wheel", 20.00, 4, 1, 10, 5);
//        InHouse part2 = new InHouse(2, "wheel", 20.00, 4, 1, 10, 5);
//        InHouse part3 = new InHouse(3, "wheel", 20.00, 4, 1, 10, 5);
//        InHouse part4 = new InHouse(4, "wheel", 20.00, 4, 1, 10, 5);
//
//        Inventory.addPart(part1);
//        Inventory.addPart(part2);
//        Inventory.addPart(part3);
//        Inventory.addPart(part4);
//
//        Product product1 = new Product(1, "car", 5000.00, 1, 0, 2);
//        Product product2 = new Product(2, "car", 5000.00, 1, 0, 2);
//        Product product3 = new Product(3, "car", 5000.00, 1, 0, 2);
//        Product product4 = new Product(4, "car", 5000.00, 1, 0, 2);
//
//        Inventory.addProduct(product1);
//        Inventory.addProduct(product2);
//        Inventory.addProduct(product3);
//        Inventory.addProduct(product4);


        launch();
    }
}