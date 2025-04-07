package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import org.example.artefatto.DAO.IMainApp;
import org.example.artefatto.DAO.IMainAppImpl;


public class GestionMainApplication {

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleExitButtonClick() {
        // Cerrar la aplicación usando Platform.exit() o primaryStage.close()
        Platform.exit();  // Esto cierra toda la aplicación
        System.exit(0);  // Esto garantiza que la aplicación termine correctamente
    }

}