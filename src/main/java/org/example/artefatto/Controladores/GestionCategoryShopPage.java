package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.example.artefatto.DAO.ICategoriaImpl;
import org.example.artefatto.DAO.IProductoImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;

import java.io.IOException;

public class GestionCategoryShopPage {
    public GridPane ItemsContainer;
    public Label UserProfileName;
    @FXML
    public Label TituloCategoria;
    @FXML
    private AnchorPane GCategoryShopPage;

    @FXML
    public void initialize() {
        usuarioActivo();
        categoriaActiva();
        generarCardsProductos();
    }

    @FXML
    private void handleButtonCategoryShopLinkClick() throws IOException {
        SessionManager sM = new SessionManager();
        ICategoriaImpl ICat = new ICategoriaImpl();
        sM.setCategoriaInactiva(ICat.actualCategoria());
        new SceneSelector(GCategoryShopPage, "/org/example/artefatto/FirstShopPage.fxml");
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

    private AnchorPane crearCardProducto(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/artefatto/CardItems.fxml"));
            AnchorPane card = loader.load();

            CardItems controller = loader.getController();
            controller.setDatosProducto(producto.getNombre(), producto.getImagen(), producto.getPrecio());
            controller.setOnVerMas(() -> {
                System.out.println("🛒 Has pulsado en producto: " + producto.getNombre());
                // Aquí puedes cargar una vista de detalle o lo que necesites
            });

            return card;
        } catch (IOException e) {
            e.printStackTrace();
            return new AnchorPane(); // fallback
        }
    }
}
