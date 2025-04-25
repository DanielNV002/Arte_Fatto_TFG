package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.HibernateUtil;
import org.example.artefatto.Util.SessionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

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

    @Override
    public void actualizarUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Buscar al usuario en la base de datos por ID
            Usuario usuarioExistente = session.get(Usuario.class, usuario.getIdUsuario());

            if (usuarioExistente != null) {
                // Actualizar todos los campos
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setApellido(usuario.getApellido());
                usuarioExistente.setCorreo(usuario.getCorreo());
                usuarioExistente.setDireccion(usuario.getDireccion());
                usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
                // Si hay más campos obligatorios, agrégalos aquí también

                session.merge(usuarioExistente);
            }

            session.getTransaction().commit();
        }
    }


    @Override
    public Usuario actualUser() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Usuario WHERE conectado = :conectado";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);
            query.setParameter("conectado", true);
            List<Usuario> usuarios = query.getResultList();
            if(usuarios.isEmpty()) return null;
            return usuarios.getFirst();
        }
    }
}
