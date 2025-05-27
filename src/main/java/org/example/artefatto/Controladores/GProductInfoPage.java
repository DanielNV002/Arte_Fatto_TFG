package org.example.artefatto.Controladores;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.artefatto.DAO.ICategoriaImpl;
import org.example.artefatto.DAO.IComprasImpl;
import org.example.artefatto.DAO.IProductoImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Compras;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class GProductInfoPage {

    @FXML
    public AnchorPane GProductInfoPage;

    public Label UserProfileName;
    public ImageView productoImagen;
    public Label productoNombre;
    public Label productoDescripcion;
    public Label productoPrecio;
    public Label productoUserUpload;
    public Label labelAlCarrito;
    public ProgressIndicator spinner;

    @FXML
    public void initialize() {
        usuarioActivo();
        cargarProducto();
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

    @FXML
    private void handleButtonCategoryShopLinkClick() {
        // Mostrar el spinner y desactivar el botón
        spinner.setVisible(true);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    SessionManager sM = new SessionManager();
                    ICategoriaImpl ICat = new ICategoriaImpl();
                    IProductoImpl IProd = new IProductoImpl();
                    sM.setProductoInactivo(IProd.actualProducto());
                    ICat.actualCategoria();

                    // Cambiar la escena desde el hilo JavaFX
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GProductInfoPage, "/org/example/artefatto/CategoryShopPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                            spinner.setVisible(false);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        spinner.setVisible(false);
                    });
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    private void cargarProducto(){
        IProductoImpl IProd = new IProductoImpl();
        productoNombre.setText(IProd.actualProducto().getNombre());
        productoImagen.setImage(new Image(new File(IProd.actualProducto().getImagen()).toURI().toString()));
        productoDescripcion.setText(IProd.actualProducto().getDescripcion());
        productoPrecio.setText(IProd.actualProducto().getPrecio() + "€");
        productoUserUpload.setText(IProd.actualProducto().getUsuario().getNombreUsuario());
    }

    public void goToUserPage(MouseEvent mouseEvent) {
        spinner.setVisible(true);
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    try {
                        new SceneSelector(GProductInfoPage, "/org/example/artefatto/UserPage.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                        spinner.setVisible(false);
                    }
                });
                return null;
            }
        };
        new Thread(task).start();
    }

    public void handleButtonCartShopLinkClick(ActionEvent actionEvent) throws IOException {
        SessionManager sM = new SessionManager();
        ICategoriaImpl ICat = new ICategoriaImpl();
        IProductoImpl IProd = new IProductoImpl();
        sM.setProductoInactivo(IProd.actualProducto());
        sM.setCategoriaInactiva(ICat.actualCategoria());
        new SceneSelector(GProductInfoPage, "/org/example/artefatto/CartPage.fxml");
    }

    @FXML
    public void Comprar(ActionEvent actionEvent) {
        // Desactiva el botón inmediatamente
        Button button = (Button) actionEvent.getSource();
        button.setDisable(true);

        IUsuarioImpl iUsuario = new IUsuarioImpl();
        IComprasImpl iCompras = new IComprasImpl();
        IProductoImpl IProd = new IProductoImpl();
        Compras C = new Compras();
        C.setId_usuario(iUsuario.actualUser());
        C.setId_producto(IProd.actualProducto());
        C.setPagado(false);
        C.setFecha_compra(Date.valueOf(LocalDate.now()));

        iCompras.anadirCompra(C);
        setLabelAlCarrito();

        // Después de 1 segundo, habilita el botón de nuevo
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> button.setDisable(false)); // Rehabilita el botón
        pause.play();
    }

    private void setLabelAlCarrito() {
        labelAlCarrito.setText("Añadido al carrito");

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> labelAlCarrito.setText("")); // borra después de 1s
        pause.play();
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
