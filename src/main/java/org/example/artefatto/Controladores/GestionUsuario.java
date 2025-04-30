package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;

import java.io.IOException;
import java.util.Optional;

public class GestionUsuario {
    public AnchorPane GUserPage;
    public Label UserProfileName;
    public Label IdProfileName;
    public Label GmailProfileName;
    public TextField TFieldName;
    public TextField TFieldSurname;
    public TextField TFieldEmail;
    public TextField TFieldDireccion;
    public TextField TFieldApodo;

    @FXML
    public void initialize() {
        usuarioActivo();
    }

    @FXML
    private void handleButtonCategoryShopLinkClick() throws IOException {
        new SceneSelector(GUserPage, "/org/example/artefatto/FirstShopPage.fxml");
    }

    private void usuarioActivo(){
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        System.out.println(iUsuario.actualUser().getNombreUsuario());

        if (UserProfileName != null) {
            UserProfileName.setText(iUsuario.actualUser().getNombreUsuario());
            IdProfileName.setText("ID:" + iUsuario.actualUser().getIdUsuario());
            GmailProfileName.setText(iUsuario.actualUser().getCorreo());
            TFieldName.setText(iUsuario.actualUser().getNombre());
            TFieldSurname.setText(iUsuario.actualUser().getApellido());
            TFieldEmail.setText(iUsuario.actualUser().getCorreo());
            TFieldDireccion.setText(iUsuario.actualUser().getDireccion());
            TFieldApodo.setText(iUsuario.actualUser().getNombreUsuario());
        } else {
            System.out.println("❌ Error: UserProfileName es null");
        }
    }

    @FXML
    private void guardarUsuario(){
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario U = new Usuario();

        U.setIdUsuario(iUsuario.actualUser().getIdUsuario());
        U.setNombre(TFieldName.getText());
        U.setApellido(TFieldSurname.getText());
        U.setCorreo(TFieldEmail.getText());
        U.setDireccion(TFieldDireccion.getText());
        U.setNombreUsuario(TFieldApodo.getText());

        iUsuario.actualizarUsuario(U);
    }

    @FXML
    private void cerrarSesion() throws IOException {
        SessionManager sM = new SessionManager();
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        sM.logOut(iUsuario.actualUser());
        new SceneSelector(GUserPage, "/org/example/artefatto/MainPage.fxml");
    }

    @FXML
    private void eliminarCuenta() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar la cuenta?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            // Aquí puedes poner la lógica de eliminación de cuenta si la hay
            IUsuarioImpl iUsuario = new IUsuarioImpl();
            iUsuario.eliminarCuenta(iUsuario.actualUser());

            // Cambiar de escena
            new SceneSelector(GUserPage, "/org/example/artefatto/MainPage.fxml");
        }
    }
}
