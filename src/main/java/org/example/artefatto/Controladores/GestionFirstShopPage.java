package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.example.artefatto.DAO.ICategoriaImpl;

import java.io.File;
import java.io.IOException;

public class GestionFirstShopPage {
    public ScrollPane categoriaScroll;
    @FXML
    private GridPane categoriaContainer;

    public Label UserProfileName;
    @FXML
    private AnchorPane GFirstShopPage;

    @FXML
    public void initialize() {
        styleCategoriaContainer();
        generarCardsCategorias();
        usuarioActivo();
    }

    @FXML
    private void handleButtonFirstShopLinkClick() throws IOException {
        SessionManager sM = new SessionManager();
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        sM.logOut(iUsuario.actualUser());
        new SceneSelector(GFirstShopPage, "/org/example/artefatto/MainPage.fxml");
    }

    private void usuarioActivo(){
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        System.out.println(iUsuario.actualUser().getNombreUsuario());

        if (UserProfileName != null) {
            UserProfileName.setText(iUsuario.actualUser().getNombreUsuario());
        } else {
            System.out.println("❌ Error: UserProfileName es null");
        }
    }

    private void styleCategoriaContainer() {
        categoriaContainer.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(categoriaContainer, Priority.ALWAYS);
    }

    private void generarCardsCategorias() {
        ICategoriaImpl categoriaDAO = new ICategoriaImpl();

        int columna = 0;
        int fila = 0;

        for (Categoria categoria : categoriaDAO.getCategoriesFromDatabase()) {
            AnchorPane card = crearCardCategoria(categoria);

            // Añadir la card al GridPane, en la columna correcta
            categoriaContainer.add(card, columna, fila);

            // Ajustar columna y fila
            columna++;
            if (columna > 3) {  // Si hemos llegado a 4 columnas (índice 3)
                columna = 0;     // Reseteamos la columna
                fila++;          // Pasamos a la siguiente fila
            }
        }
    }


    private AnchorPane crearCardCategoria(Categoria categoria) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/artefatto/CardItems.fxml"));
            AnchorPane card = loader.load();

            CardItems controller = loader.getController();
            controller.setDatos(categoria.getNombre(), categoria.getImagen());
            controller.setOnVerMas(() -> {
                System.out.println("➡️ Has pulsado en: " + categoria.getNombre());
                // Aquí haces la navegación o lógica que quieras
            });

            return card;
        } catch (IOException e) {
            e.printStackTrace();
            return new AnchorPane(); // devuelve vacío si falla
        }
    }

}
