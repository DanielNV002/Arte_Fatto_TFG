package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Usuario;

import java.util.List;

public interface IUsuario {
    // Metodo para agregar un usuario
    void addUsuario(Usuario usuario);

    // Metodo para comprobar si el usuario existe
    Usuario comprobarUsuario(String username, String password);

}
