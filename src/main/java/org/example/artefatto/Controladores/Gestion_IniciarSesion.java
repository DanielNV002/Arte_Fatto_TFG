package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Gestion_IniciarSesion {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}