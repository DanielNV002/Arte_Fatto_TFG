package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Usuario;

public interface IUsuario {
    // Metodo para agregar un usuario
    void addUsuario(Usuario usuario);

    // Metodo para comprobar si el usuario existe
    Usuario comprobarUsuario(String username, String password);

    //Actualizar el usuario
    void actualizarUsuario(Usuario usuario);

    //Buscar Usuario activo
    Usuario actualUser();

    //Eliminar cuenta
    void eliminarCuenta(Usuario usuario);

}
