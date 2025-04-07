package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;

import java.io.IOException;

public class GestionInicioSesion {
    @FXML
    private AnchorPane GIniciarSesion;

    @FXML
    private TextField TFieldUsername;
    @FXML
    private TextField TFieldContrasena;
    @FXML
    private PasswordField TFieldContrasena1;

    @FXML
    private void handleButtonRegistroLinkClick() throws IOException {
        new SceneSelector(GIniciarSesion, "/org/example/artefatto/RegisterPage.fxml");
    }
    @FXML
    private void handleExitButtonClick() {
        // Cerrar la aplicación usando Platform.exit() o primaryStage.close()
        Platform.exit();  // Esto cierra toda la aplicación
        System.exit(0);  // Esto garantiza que la aplicación termine correctamente
    }
    @FXML
    private void iniciarSesion() throws IOException {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario actualUser = iUsuario.comprobarUsuario(TFieldUsername.getText(), TFieldContrasena.getText());
        if(actualUser == null){
            TFieldUsername.setText("DATOS INCORRECTOS");
            TFieldContrasena1.setText("");
        }else if(actualUser.getNombreUsuario().equals(TFieldUsername.getText()) && actualUser.getContrasena().equals(TFieldContrasena.getText())){
            SessionManager sM = new SessionManager();
            sM.logIn(actualUser);
            new SceneSelector(GIniciarSesion, "/org/example/artefatto/FirstShopPage.fxml");
        }
    }
}
