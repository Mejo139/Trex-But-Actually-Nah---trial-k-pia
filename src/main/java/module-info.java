module com.example.trex_but_actually_nah {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trex_but_actually_nah to javafx.fxml;
    exports com.example.trex_but_actually_nah;
}