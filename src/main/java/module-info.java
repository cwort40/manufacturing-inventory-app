module c482.inventoryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens c482.inventoryapp to javafx.fxml;
    exports c482.inventoryapp;
}