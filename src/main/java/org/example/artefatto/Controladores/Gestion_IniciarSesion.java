package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Gestion_IniciarSesion {
    @FXML
    private Button ButtonExit;
    @FXML
    private TextField TFieldUsername;
    @FXML
    private TextField TFieldContrasena;

    @FXML
    private void initialize() {
        ButtonExit.setOnMouseClicked(event -> handleExitButtonClick());

        // Usamos un FocusListener en lugar de MouseClicked para el comportamiento de los campos
        TFieldUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {  // Cuando el campo obtiene el foco
                TFieldUsernameOnFocusGained();
            } else {  // Cuando el campo pierde el foco
                TFieldUsernameOnFocusLost();
            }
        });

        TFieldContrasena.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {  // Cuando el campo obtiene el foco
                TFieldContrasenaOnFocusGained();
            } else {  // Cuando el campo pierde el foco
                TFieldContrasenaOnFocusLost();
            }
        });
    }

    @FXML
    private void handleExitButtonClick() {
        // Cerrar la aplicación usando Platform.exit() o primaryStage.close()
        Platform.exit();  // Esto cierra toda la aplicación
        System.exit(0);  // Esto garantiza que la aplicación termine correctamente
    }
    // Métodos para gestionar el enfoque de los campos
    private void TFieldUsernameOnFocusGained() {
        if (TFieldUsername.getText().equals("Username")) {
            TFieldUsername.setText("");  // Limpiar el texto si es "Username"
        }
    }

    private void TFieldUsernameOnFocusLost() {
        if (TFieldUsername.getText().isEmpty()) {
            TFieldUsername.setText("Username");  // Restaurar "Username" si está vacío
        }
    }

    private void TFieldContrasenaOnFocusGained() {
        if (TFieldContrasena.getText().equals("Password")) {
            TFieldContrasena.setText("");  // Limpiar el texto si es "Password"
        }
    }

    private void TFieldContrasenaOnFocusLost() {
        if (TFieldContrasena.getText().isEmpty()) {
            TFieldContrasena.setText("Password");  // Restaurar "Password" si está vacío
        }
    }
}