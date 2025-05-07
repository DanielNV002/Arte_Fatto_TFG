package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
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
    private Button btnIniciarSesion;

    @FXML
    private ProgressIndicator spinner;
    @FXML
    private void handleButtonRegistroLinkClick() throws IOException {
        new SceneSelector(GIniciarSesion, "/org/example/artefatto/RegisterPage.fxml");
    }

    @FXML
    public void handleButtonInvitadoLinkClick(ActionEvent actionEvent) throws IOException {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario actualUser = iUsuario.comprobarUsuario("invitado", "invitado");
        if(actualUser == null){
            TFieldUsername.setText("DATOS INCORRECTOS");
            TFieldContrasena1.setText("");
        }else if(actualUser.getNombreUsuario().equals("invitado") && actualUser.getContrasena().equals("invitado")){
            SessionManager sM = new SessionManager();
            sM.logIn(actualUser);
            new SceneSelector(GIniciarSesion, "/org/example/artefatto/FirstShopPage.fxml");
        }
    }

    @FXML
    private void handleExitButtonClick() {
        // Cerrar la aplicación usando Platform.exit() o primaryStage.close()
        Platform.exit();  // Esto cierra toda la aplicación
        System.exit(0);  // Esto garantiza que la aplicación termine correctamente
    }

    @FXML
    private void iniciarSesion() {
        String usuario = TFieldUsername.getText();
        String contrasena = TFieldContrasena.getText();

        // Mostrar carga y desactivar botón
        spinner.setVisible(true);
        btnIniciarSesion.setDisable(true);

        Task<Usuario> task = new Task<>() {
            @Override
            protected Usuario call() {
                IUsuarioImpl iUsuario = new IUsuarioImpl();
                return iUsuario.comprobarUsuario(usuario, contrasena);
            }
        };

        task.setOnSucceeded(event -> {
            Usuario actualUser = task.getValue();
            spinner.setVisible(false);
            btnIniciarSesion.setDisable(false);

            if (actualUser == null) {
                TFieldUsername.setText("DATOS INCORRECTOS");
                TFieldContrasena1.setText("");
            } else if (actualUser.getNombreUsuario().equals(usuario) &&
                    actualUser.getContrasena().equals(contrasena)) {
                SessionManager sM = new SessionManager();
                sM.logIn(actualUser);
                try {
                    new SceneSelector(GIniciarSesion, "/org/example/artefatto/FirstShopPage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        task.setOnFailed(event -> {
            spinner.setVisible(false);
            btnIniciarSesion.setDisable(false);
            System.out.println("Error al iniciar sesión: " + task.getException());
        });

        new Thread(task).start();
    }

}
