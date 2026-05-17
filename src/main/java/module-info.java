module com.example.energyexpensetrackerjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.energyexpensetrackerjava to javafx.fxml;
    exports com.example.energyexpensetrackerjava;
}