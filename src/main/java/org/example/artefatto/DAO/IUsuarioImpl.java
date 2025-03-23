package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class IUsuarioImpl implements IUsuario {
    @Override
    public void addUsuario(Usuario usuario) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();  // Iniciar transacción
            session.persist(usuario);  // Guardar el usuario en la base de datos
            transaction.commit();  // Confirmar transacción
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Revertir en caso de error
            }
            e.printStackTrace(); // Manejo básico de errores
        }
    }

    @Override
    public Usuario comprobarUsuario(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Usuario WHERE nombreUsuario = :username AND contrasena = :password";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            Usuario usuario = query.uniqueResult();  // Obtener un solo resultado
            return usuario;  // Si existe, retorna true; si no, false
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
