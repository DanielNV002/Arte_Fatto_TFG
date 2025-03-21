package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.artefatto.Util.SceneSelector;

import java.io.IOException;


public class GestionMainApplication {
    @FXML
    private AnchorPane GIniciarSesion;
    @FXML
    private AnchorPane GRegistro;

    @FXML
    private TextField TFieldUsername;
    @FXML
    private TextField TFieldContrasena;

    @FXML
    private void initialize() {

        if (TFieldUsername != null) {
            TFieldUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    TFieldUsernameOnFocusGained();
                } else {
                    TFieldUsernameOnFocusLost();
                }
            });
        } else {
            System.out.println("ERROR: TFieldUsername es NULL");
        }

        if (TFieldContrasena != null) {
            TFieldContrasena.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    TFieldContrasenaOnFocusGained();
                } else {
                    TFieldContrasenaOnFocusLost();
                }
            });
        } else {
            System.out.println("ERROR: TFieldContrasena es NULL");
        }
    }

    @FXML
    private void handleExitButtonClick() {
        // Cerrar la aplicación usando Platform.exit() o primaryStage.close()
        Platform.exit();  // Esto cierra toda la aplicación
        System.exit(0);  // Esto garantiza que la aplicación termine correctamente
    }
    @FXML
    private void handleBackRegisterButtonClick() throws IOException {
        new SceneSelector(GRegistro, "/org/example/artefatto/MainPage.fxml");
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

    @FXML
    private void handleButtonRegistroLinkClick() throws IOException {
        new SceneSelector(GIniciarSesion, "/org/example/artefatto/RegisterPage.fxml");
    }


}