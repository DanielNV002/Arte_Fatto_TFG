package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ICategoriaImpl implements ICategoria {

    @Override
    public List<Categoria> getCategoriesFromDatabase() {
        Transaction transaction = null;
        List<Categoria> categorias = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            categorias = session.createQuery("FROM Categoria", Categoria.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return categorias;
    }

    @Override
    public void actualizarCategoria(Categoria categoria) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Cargar la categoría existente desde la base de datos
            Categoria categoriaExistente = session.get(Categoria.class, categoria.getId_categoria());

            if (categoriaExistente != null) {
                // Actualizar los campos
                categoriaExistente.setNombre(categoria.getNombre());
                categoriaExistente.setImagen(categoria.getImagen());
                categoriaExistente.setActive(categoria.getActive());

                // Guardar cambios
                session.merge(categoriaExistente);
                transaction.commit();
                System.out.println("✅ Categoría actualizada correctamente: " + categoria.getNombre());
            } else {
                System.out.println("⚠ No se encontró ninguna categoría con ID: " + categoria.getId_categoria());
            }

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Categoria actualCategoria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Categoria WHERE isActive = :activa";
            Query<Categoria> query = session.createQuery(hql, Categoria.class);
            query.setParameter("activa", true);
            List<Categoria> categorias = query.getResultList();

            if (categorias.isEmpty()) return null;

            return categorias.getFirst(); // Devuelve la primera activa
        }
    }


}

