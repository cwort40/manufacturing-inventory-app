package c482.inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Inventory model class
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to inventory
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product to inventory
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Retrieves all parts from inventory
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves all products from inventory
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Looks up part by ID
     * @param partId
     * @return part
     */
    public static Part lookUpPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            while (part.getId() == partId) {
                return part;
            }
        }
        Alert noParts = new Alert(Alert.AlertType.ERROR);
        noParts.setTitle("Error");
        noParts.setContentText(String.format("Part was not found"));
        noParts.showAndWait();
        return null;
    }

    /**
     * Looks up product by ID
     * @param productId
     * @return product
     */
    public static Product lookUpProduct(int productId) {
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

    /**
     * Looks up part by name
     * @param partName
     * @return part
     */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> partNameList = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partNameList.add(part);
            }
        } return partNameList;
    }

    /**
     * Looks up product by name
     * @param productName
     * @return product
     */
    public static ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> productNameList = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productNameList.add(product);
            }
        } return productNameList;
    }

    /**
     * Updates part with new part by index
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * updates product with new product by index
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * deletes part passed in
     * @param selectedPart
     * @return boolean
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        boolean ans = allParts.contains(selectedPart);
        if (ans) { return true; } else { return false; }
    }

    /**
     * deletes product passed in
     * @param selectedProduct
     * @return boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        boolean ans = allProducts.contains(selectedProduct);
        if (ans) { return true; } else { return false; }
    }
}

