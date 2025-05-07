package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class CardItems {

    public ProgressIndicator spinner;
    @FXML
    private ImageView categoriaImagen;

    @FXML
    private Label categoriaNombre;

    @FXML
    private Button verMasButton;

    // ✅ Para categorías
    public void setDatos(String nombre, String rutaImagen) {
        setNombreYImagen(nombre, rutaImagen);
    }

    // ✅ Para productos (nombre + imagen + precio opcional)
    public void setDatosProducto(String nombre, String rutaImagen, Double precio) {
        setNombreYImagen(nombre, rutaImagen);
    }

    // 🔁 Método común para evitar duplicación
    private void setNombreYImagen(String nombre, String rutaImagen) {
        categoriaNombre.setText(nombre);

        File file = new File(rutaImagen);
        if (file.exists()) {
            categoriaImagen.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println("⚠️ Imagen no encontrada: " + rutaImagen);
            //categoriaImagen.setImage(new Image("src/main/resources/img/Product/default.jpg"));
        }
    }

    // Mismo para ambas
    public void setOnVerMas(Runnable accionPesada) {
        verMasButton.setOnAction(e -> {
            verMasButton.setDisable(true);
            spinner.setVisible(true); // Mostrar antes de que la UI se bloquee

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        // Aquí iría el trabajo pesado si lo tuvieras separado
                        // Simular con Thread.sleep o similar si es necesario
                        Thread.sleep(300); // solo para forzar actualización del spinner

                        // Ejecuta la acción principal en el hilo de JavaFX
                        Platform.runLater(() -> {
                            accionPesada.run();
                            spinner.setVisible(false);
                            verMasButton.setDisable(false);
                        });

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void failed() {
                    Platform.runLater(() -> {
                        spinner.setVisible(false);
                        verMasButton.setDisable(false);
                        System.out.println("❌ Error al ejecutar acción del botón");
                    });
                }
            };

            new Thread(task).start();
        });
    }

}
