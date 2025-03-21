package org.example.artefatto.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.example.artefatto.MainApplication;

import java.io.IOException;
import java.net.URL;

public class SceneSelector {

    public SceneSelector(AnchorPane currentAnchorPane, String fxml) throws IOException {
        // Verificar la ruta del recurso FXML
        URL fxmlURL = MainApplication.class.getResource(fxml);
        if (fxmlURL == null) {
            System.out.println("No se encontró el archivo FXML en la ruta: " + fxml);
            throw new IOException("No se pudo encontrar el archivo FXML: " + fxml);
        }

        // Cargar el archivo FXML
        AnchorPane nextAnchorPane = FXMLLoader.load(fxmlURL);

        // Limpiar el AnchorPane actual y agregar el nuevo contenido
        currentAnchorPane.getChildren().clear();  // Usar clear() para eliminar todos los hijos
        currentAnchorPane.getChildren().add(nextAnchorPane);  // Agregar el nuevo AnchorPane
    }
}

