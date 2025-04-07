package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class IProductoImpl implements IProducto {
    @Override
    public List<Producto> obtenerProductos() {
        Transaction transaction = null;
        List<Producto> productos = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            productos = session.createQuery("FROM Producto", Producto.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return productos;
    }
}
