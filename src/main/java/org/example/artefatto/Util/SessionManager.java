package org.example.artefatto.Util;

import org.example.artefatto.DAO.ICategoriaImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Usuario;

public class SessionManager {

    public void logOut(Usuario usuario){
        IUsuarioImpl IUsu = new IUsuarioImpl();
        usuario.setConectado(false);
        IUsu.actualizarUsuario(usuario);
    }

    public void logIn(Usuario usuario){
        IUsuarioImpl IUsu = new IUsuarioImpl();
        usuario.setConectado(true);
        IUsu.actualizarUsuario(usuario);
    }

    public void setCategoriaActiva(Categoria categoria){
        ICategoriaImpl ICat = new ICategoriaImpl();
        categoria.setActive(true);
        ICat.actualizarCategoria(categoria);
    }

    public void setCategoriaInactiva(Categoria categoria){
        ICategoriaImpl ICat = new ICategoriaImpl();
        categoria.setActive(false);
        ICat.actualizarCategoria(categoria);
    }
}
