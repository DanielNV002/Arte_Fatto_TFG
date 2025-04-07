package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class CardItems {

    @FXML
    private ImageView categoriaImagen;

    @FXML
    private Label categoriaNombre;

    @FXML
    private Button verMasButton;

    public void setDatos(String nombre, String rutaImagen) {
        categoriaNombre.setText(nombre);

        File file = new File(rutaImagen);
        if (file.exists()) {
            categoriaImagen.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println("⚠️ Imagen no encontrada: " + rutaImagen);
            categoriaImagen.setImage(new Image("/org/example/artefatto/assets/default.png"));
        }
    }

    public void setOnVerMas(Runnable accion) {
        verMasButton.setOnAction(e -> accion.run());
    }
}
