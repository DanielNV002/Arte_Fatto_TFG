package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.artefatto.DAO.ICategoriaImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;

import java.io.IOException;

public class GestionFirstShopPage {
    public Label UserProfileName;
    @FXML
    private GridPane categoriaContainer;
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

    private void handleButtonCategoryLinkClick() throws IOException {
        new SceneSelector(GFirstShopPage, "/org/example/artefatto/CategoryShopPage.fxml");
    }

    private void usuarioActivo() {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario actualUser = iUsuario.actualUser();

        if (UserProfileName != null) {
            UserProfileName.setText(actualUser.getNombreUsuario());
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
                try {
                    SessionManager sM = new SessionManager();
                    sM.setCategoriaActiva(categoria);
                    ICategoriaImpl ICat = new ICategoriaImpl();
                    ICat.actualizarCategoria(categoria);

                    handleButtonCategoryLinkClick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            return card;
        } catch (IOException e) {
            e.printStackTrace();
            return new AnchorPane(); // devuelve vacío si falla
        }
    }

    public void goToUserPage(MouseEvent mouseEvent) throws IOException {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario actualUser = iUsuario.actualUser();

        if(actualUser.getNombreUsuario().equalsIgnoreCase("invitado")){
            new SceneSelector(GFirstShopPage, "/org/example/artefatto/MainPage.fxml");
        }else{
            new SceneSelector(GFirstShopPage, "/org/example/artefatto/UserPage.fxml");
        }
    }
}
