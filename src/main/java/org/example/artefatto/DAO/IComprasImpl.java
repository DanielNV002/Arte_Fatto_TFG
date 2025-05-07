package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Compras;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class IComprasImpl implements ICompras {

    @Override
    public void anadirCompra(Compras compra) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(compra);
            transaction.commit();
            System.out.println("✅ Compra añadida correctamente con ID: " + compra.getId_compra());

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Compras> listaDeCompras(Long id_usuario) {
        Transaction transaction = null;
        List<Compras> compras = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL modificado para utilizar el campo 'isPagado'
            String hql = "FROM Compras c WHERE c.id_usuario.idUsuario = :idUsuario AND c.isPagado = false";
            compras = session.createQuery(hql, Compras.class)
                    .setParameter("idUsuario", id_usuario)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return compras;
    }


    @Override
    public void compraCompletada(Compras compra) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Compras compraExistente = session.get(Compras.class, compra.getId_compra());

            if (compraExistente != null) {
                compraExistente.setPagado(true);
                session.merge(compraExistente);
                transaction.commit();
                System.out.println("✅ Compra marcada como pagada con ID: " + compra.getId_compra());
            } else {
                System.out.println("⚠ No se encontró la compra con ID: " + compra.getId_compra());
            }

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCompra(Compras compra) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Asegúrate de que el objeto esté en estado persistente antes de eliminar
            Compras compraAEliminar = session.get(Compras.class, compra.getId_compra());
            if (compraAEliminar != null) {
                session.remove(compraAEliminar);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
