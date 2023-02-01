package c482.inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product model class
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Retrieves ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves stock
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Retrieves minimum
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets minimum
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Retrieves maximum
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets maximum
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds associated part to product
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deleted selected associated part
     * @param selectedAssociatedPart
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        boolean ans = associatedParts.contains(selectedAssociatedPart);
        if (ans) { return true; } else { return false; }
    }

    /**
     * Retrieves all associated parts of a product
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
