module com.mycompany.proyecto_1_progra_ii {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.proyecto_1_progra_ii to javafx.fxml;
    exports com.mycompany.proyecto_1_progra_ii;
}
