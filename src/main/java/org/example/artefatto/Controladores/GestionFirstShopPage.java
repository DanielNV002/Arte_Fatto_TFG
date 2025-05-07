package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    public ProgressIndicator spinner;
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
    private void handleButtonFirstShopLinkClick() {
        spinner.setVisible(true); // Mostrar spinner antes de empezar
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                SessionManager sM = new SessionManager();
                IUsuarioImpl iUsuario = new IUsuarioImpl();
                sM.logOut(iUsuario.actualUser());

                // Cambiar de escena debe hacerse en el hilo de JavaFX
                Platform.runLater(() -> {
                    try {
                        new SceneSelector(GFirstShopPage, "/org/example/artefatto/MainPage.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return null;
            }
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    spinner.setVisible(false);
                    System.out.println("❌ Error al cerrar sesión");
                });
            }
        };
        new Thread(task).start();
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

    public void goToUserPage(MouseEvent mouseEvent) {
        spinner.setVisible(true); // Mostrar el spinner
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                IUsuarioImpl iUsuario = new IUsuarioImpl();
                Usuario actualUser = iUsuario.actualUser();

                // Si no es invitado, redirigir a la página de usuario
                if (!actualUser.getNombreUsuario().equalsIgnoreCase("invitado")) {
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GFirstShopPage, "/org/example/artefatto/UserPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Si es un invitado, redirigir a la página principal
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GFirstShopPage, "/org/example/artefatto/MainPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                return null;
            }
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner si falla
                    System.out.println("❌ Error al cambiar la escena");
                });
            }
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner después de la transición
                });
            }
        };
        new Thread(task).start(); // Ejecutar el task en un hilo aparte
    }
}
