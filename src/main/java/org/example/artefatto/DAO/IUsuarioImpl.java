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
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.persist(usuario);
            transaction.commit();

            System.out.println("✅ Usuario registrado correctamente: " + usuario.getNombreUsuario());

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    System.err.println("⚠ Error durante rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println("❌ Error al registrar usuario: " + usuario.getNombreUsuario());
            e.printStackTrace();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    @Override
    public Usuario comprobarUsuario(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Usuario WHERE nombreUsuario = :username";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);
            query.setParameter("username", username);

            Usuario usuario = query.uniqueResult();

            if (usuario != null && usuario.getContrasena().equals(password)) {
                return usuario;
            } else {
                return null;
            }
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
                usuarioExistente.setConectado(usuario.isConectado());

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

    @Override
    public void eliminarCuenta(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) {
            throw new IllegalArgumentException("El usuario o su ID no pueden ser nulos");
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Eliminar compras del usuario
            session.createQuery("DELETE FROM Compras WHERE id_usuario.idUsuario = :idUsuario")
                    .setParameter("idUsuario", usuario.getIdUsuario())
                    .executeUpdate();

            // Eliminar usuario
            Usuario usuarioExistente = session.get(Usuario.class, usuario.getIdUsuario());
            if (usuarioExistente != null) {
                session.remove(usuarioExistente);
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
