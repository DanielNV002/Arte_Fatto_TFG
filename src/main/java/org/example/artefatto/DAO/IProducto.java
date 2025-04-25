package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public interface IProducto {

    List<Producto> obtenerProductos();

    List<Producto> getProductosDesdeBD();

    List<Producto> getProductosPorCategoria(Categoria categoria);

    void actualizarProducto(Producto producto);

    Producto actualProducto();
}
