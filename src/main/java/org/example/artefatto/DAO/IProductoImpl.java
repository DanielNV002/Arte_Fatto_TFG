package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
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
    @Override
    public List<Producto> getProductosDesdeBD() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producto", Producto.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Producto> getProductosPorCategoria(Categoria categoria) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Producto WHERE categoria = :categoria";
            return session.createQuery(hql, Producto.class)
                    .setParameter("categoria", categoria)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
