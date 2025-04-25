package org.example.artefatto.Util;

import org.example.artefatto.DAO.ICategoriaImpl;
import org.example.artefatto.DAO.IProductoImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Entities.Usuario;

public class SessionManager {

    private final IUsuarioImpl usuarioDAO = new IUsuarioImpl();
    private final ICategoriaImpl categoriaDAO = new ICategoriaImpl();
    private final IProductoImpl productoDAO = new IProductoImpl();

    public void logOut(Usuario usuario){
        usuario.setConectado(false);
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void logIn(Usuario usuario){
        usuario.setConectado(true);
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void setCategoriaActiva(Categoria categoria){
        categoria.setActive(true);
        categoriaDAO.actualizarCategoria(categoria);
    }

    public void setCategoriaInactiva(Categoria categoria){
        categoria.setActive(false);
        categoriaDAO.actualizarCategoria(categoria);
    }

    public void setProductoActivo(Producto producto){
        producto.setActive(true);
        productoDAO.actualizarProducto(producto);
    }

    public void setProductoInactivo(Producto producto){
        producto.setActive(false);
        productoDAO.actualizarProducto(producto);
    }
}
