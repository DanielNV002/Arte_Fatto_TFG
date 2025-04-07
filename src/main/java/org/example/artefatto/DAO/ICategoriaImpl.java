package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}

