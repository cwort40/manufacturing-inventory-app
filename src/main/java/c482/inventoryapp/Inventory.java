package c482.inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static Part LookUpPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            while (part.getId() == partId) {
                return part;
            }
        }

        Alert noParts = new Alert(Alert.AlertType.WARNING);
        noParts.setTitle("Warning");
        noParts.setContentText(String.format("No part was found by the following %s.", partId));
        noParts.showAndWait();
        return null;
    }

    public static Product LookUpProduct(int productId) {
        for (Product product : getAllProducts()) {
            while (product.getId() == productId) {
                return product;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Product was not found");
        alert.show();
        return null;
    }

    public static ObservableList<Part> LookUpPart(String partName) {
        ObservableList<Part> partNameList = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partNameList.add(part);
            }
        }
        return partNameList;
    }

    public static ObservableList<Product> LookUpProduct(String productName) {
        ObservableList<Product> productNameList = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productNameList.add(product);
            }
        }
        return productNameList;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);

        boolean ans = allParts.contains(selectedPart);

        if (ans) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);

        boolean ans = allProducts.contains(selectedProduct);

        if (ans) {
            return true;
        }
        else {
            return false;
        }
    }
}

