package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.example.artefatto.DAO.ICategoriaImpl;
import org.example.artefatto.DAO.IProductoImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;

import java.io.IOException;

public class GestionCategoryShopPage {
    public GridPane ItemsContainer;
    public Label UserProfileName;
    @FXML
    public Label TituloCategoria;
    public ProgressIndicator spinner;
    @FXML
    private AnchorPane GCategoryShopPage;

    @FXML
    public void initialize() {
        usuarioActivo();
        categoriaActiva();
        generarCardsProductos();
    }

    @FXML
    private void handleButtonCategoryShopLinkClick() {
        spinner.setVisible(true); // Mostrar el spinner antes de empezar

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                SessionManager sM = new SessionManager();
                ICategoriaImpl ICat = new ICategoriaImpl();
                sM.setCategoriaInactiva(ICat.actualCategoria());

                Platform.runLater(() -> {
                    try {
                        new SceneSelector(GCategoryShopPage, "/org/example/artefatto/FirstShopPage.fxml");
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
                    System.out.println("❌ Error al cambiar de categoría");
                });
            }
        };

        new Thread(task).start();
    }


    private void usuarioActivo() {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        System.out.println(iUsuario.actualUser().getNombreUsuario());

        if (UserProfileName != null) {
            UserProfileName.setText(iUsuario.actualUser().getNombreUsuario());
        } else {
            System.out.println("❌ Error: UserProfileName es null");
        }
    }

    private void categoriaActiva(){
        ICategoriaImpl ICat = new ICategoriaImpl();
        System.out.println(ICat.actualCategoria().getNombre());

        if(TituloCategoria != null) {
            TituloCategoria.setText(ICat.actualCategoria().getNombre());
        }else {
            System.out.println("❌ Error: PanelTituloCategoria es null");
        }
    }

    private void styleCategoriaContainer() {
        ItemsContainer.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(ItemsContainer, Priority.ALWAYS);
    }

    private void generarCardsProductos() {
        IProductoImpl productoDAO = new IProductoImpl();
        ICategoriaImpl categoriaDAO = new ICategoriaImpl();
        Categoria categoriaActiva = categoriaDAO.actualCategoria();

        if (categoriaActiva == null) {
            System.out.println("⚠ No hay categoría activa");
            return;
        }

        int columna = 0;
        int fila = 0;

        for (Producto producto : productoDAO.getProductosPorCategoria(categoriaActiva)) {
            AnchorPane card = crearCardProducto(producto);

            ItemsContainer.add(card, columna, fila);

            columna++;
            if (columna > 3) {
                columna = 0;
                fila++;
            }
        }
    }

    private void handleButtonProductLinkClick() throws IOException {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario actualUser = iUsuario.actualUser();

        if(actualUser.getNombreUsuario().equalsIgnoreCase("invitado")){
            new SceneSelector(GCategoryShopPage, "/org/example/artefatto/MainPage.fxml");
        }else{
            new SceneSelector(GCategoryShopPage, "/org/example/artefatto/ProductInfoPage.fxml");
        }
    }

    private AnchorPane crearCardProducto(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/artefatto/CardItems.fxml"));
            AnchorPane card = loader.load();

            CardItems controller = loader.getController();
            controller.setDatosProducto(producto.getNombre(), producto.getImagen(), producto.getPrecio());
            controller.setOnVerMas(() -> {
                System.out.println("🛒 Has pulsado en producto: " + producto.getNombre());
                try {
                    SessionManager sM = new SessionManager();
                    sM.setProductoActivo(producto);
                    IProductoImpl IProd = new IProductoImpl();
                    IProd.actualizarProducto(producto);

                    handleButtonProductLinkClick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            return card;
        } catch (IOException e) {
            e.printStackTrace();
            return new AnchorPane(); // fallback
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
                            new SceneSelector(GCategoryShopPage, "/org/example/artefatto/UserPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Si es un invitado, redirigir a la página principal
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GCategoryShopPage, "/org/example/artefatto/MainPage.fxml");
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

    public void handleButtonCartShopLinkClick(ActionEvent actionEvent) throws IOException {
        spinner.setVisible(true); // Mostrar el spinner mientras se realiza la acción
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                IUsuarioImpl iUsuario = new IUsuarioImpl();
                Usuario actualUser = iUsuario.actualUser();

                if (actualUser.getNombreUsuario().equalsIgnoreCase("invitado")) {
                    // Si es invitado, redirigir a la página principal
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GCategoryShopPage, "/org/example/artefatto/MainPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Si no es invitado, redirigir a la página del carrito
                    SessionManager sM = new SessionManager();
                    ICategoriaImpl ICat = new ICategoriaImpl();
                    sM.setCategoriaInactiva(ICat.actualCategoria());
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GCategoryShopPage, "/org/example/artefatto/CartPage.fxml");
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

    @FXML
    private void infoAlert() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vista no disponible");
        alert.setHeaderText(null);
        alert.setContentText("Seccion en mantenimiento. Disculpe las molestias.");
        alert.showAndWait();
    }
}