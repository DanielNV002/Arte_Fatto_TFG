package org.example.artefatto.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.artefatto.DAO.IComprasImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Compras;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.SceneSelector;

import java.io.IOException;
import java.util.List;

public class GestionCart {

    @FXML
    public AnchorPane GCartPage;

    public Label UserProfileName;
    @FXML
    public ScrollPane scrollPanel;
    public Pane PanelInfoDatosCompra;
    @FXML
    private VBox PanelInfo;

    @FXML
    public void initialize() {
        usuarioActivo();
        cargarCompras();
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

    public void goToUserPage(MouseEvent mouseEvent) throws IOException {
        new SceneSelector(GCartPage, "/org/example/artefatto/UserPage.fxml");
    }

    public void handleButtonCartShopLinkClick(ActionEvent actionEvent) throws IOException {
        new SceneSelector(GCartPage, "/org/example/artefatto/FirstShopPage.fxml");
    }

    public void mostrarComprasEnPanel(List<Compras> compras, VBox panelInfo) {
        panelInfo.getChildren().clear(); // Limpiar el contenido anterior
        PanelInfoDatosCompra.getChildren().clear(); // Limpiar el contenido anterior

        for (Compras compra : compras) {
            Producto producto = compra.getId_producto();
            if (producto != null) {

                // Label de nombre del producto
                Label labelNombre = new Label(String.format("Producto: %-20s", producto.getNombre()));
                labelNombre.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333; -fx-font-weight: bold;");
                labelNombre.setMinWidth(500);

                // Label de precio
                Label labelPrecio = new Label(String.format("%.2f€", producto.getPrecio()));
                labelPrecio.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333; -fx-font-weight: bold;");
                labelPrecio.setMinWidth(100);
                labelPrecio.setAlignment(Pos.CENTER_RIGHT);

                // Botón eliminar
                Button btnEliminar = new Button("X");
                btnEliminar.setStyle("-fx-background-color: #ed81dd; -fx-text-fill: white;");
                btnEliminar.setCursor(Cursor.HAND);

                // Hover effect
                btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle("-fx-background-color: #d16ac9; -fx-text-fill: white;"));
                btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle("-fx-background-color: #ed81dd; -fx-text-fill: white;"));

                // HBox como fila
                HBox hbox = new HBox(20, labelNombre, labelPrecio, btnEliminar);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPadding(new Insets(5));
                hbox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc;");
                hbox.setPrefWidth(750);

                // Acción del botón
                btnEliminar.setOnAction(e -> {
                    panelInfo.getChildren().remove(hbox);
                    IComprasImpl iCompras = new IComprasImpl();
                    iCompras.eliminarCompra(compra);
                    cargarCompras(); // refrescar
                });

                panelInfo.getChildren().add(hbox);
            }
        }

        Double total = 0.0;
        for (Compras compra : compras) {
            Producto producto = compra.getId_producto();
            if (producto != null) {
                total += producto.getPrecio();
            }
        }
        Label labelTotal = new Label(String.format("Total: %.2f€", total));
        labelTotal.setStyle("-fx-font-size: 25px; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
        labelTotal.setAlignment(Pos.CENTER_LEFT);
        labelTotal.setPadding(new Insets(10));
        PanelInfoDatosCompra.getChildren().add(labelTotal);
    }

    public void cargarCompras() {
        IComprasImpl iCompras = new IComprasImpl();
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario idUsuario = iUsuario.actualUser();
        List<Compras> compras = iCompras.listaDeCompras(idUsuario.getIdUsuario());
        mostrarComprasEnPanel(compras, PanelInfo);
    }


    public void Pagar(ActionEvent actionEvent) {

        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario uActivo = iUsuario.actualUser();
        IComprasImpl iCompras = new IComprasImpl();

        List<Compras> compras = iCompras.listaDeCompras(uActivo.getIdUsuario());
        for (Compras compra : compras) {
            iCompras.compraCompletada(compra);
        }
        cargarCompras();
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
