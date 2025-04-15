package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class IMainAppImpl implements IMainApp {
    @Override
    public void generarCategoriasBase() {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String[] nombres = {"DIY", "Cartas", "Ilustraciones", "Pociones"};

            int imgIndex = 1;
            for (String nombre : nombres) {
                // Verifica si ya existe
                Long count = session.createQuery(
                                "SELECT COUNT(c) FROM Categoria c WHERE c.nombre = :nombre", Long.class)
                        .setParameter("nombre", nombre)
                        .uniqueResult();

                if (count != null && count == 0) {
                    Categoria categoria = new Categoria();
                    categoria.setNombre(nombre);

                    // Cargar imagen (si existe el archivo)
                    String path = "src/main/resources/img/Categorias/Categoria00" + imgIndex + ".jpg";
                    try {
                        categoria.setImagen(path);
                    } catch (Exception e) {
                        System.out.println("⚠ No se pudo cargar la imagen: " + path);
                    }

                    session.persist(categoria);
                }

                imgIndex++;
            }

            transaction.commit();
        } catch (Exception e) {
            try {
                if (transaction != null && transaction.isActive()) transaction.rollback();
            } catch (Exception rollbackEx) {
                System.out.println("Error durante rollback: " + rollbackEx.getMessage());
            }
            e.printStackTrace();
        }
    }

    @Override
    public void anadirProducto(String nombre, Usuario usuario, Categoria categoria, double precio, boolean disponible, String imagen) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Verificar si ya existe un producto con el mismo nombre (opcional)
            Long count = session.createQuery(
                            "SELECT COUNT(p) FROM Producto p WHERE p.nombre = :nombre", Long.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            if (count != null && count > 0) {
                System.out.println("⚠ Ya existe un producto con el nombre: " + nombre);
                return;
            }

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setUsuario(usuario);
            producto.setCategoria(categoria);
            producto.setPrecio(precio);
            producto.setDisponible(disponible);
            producto.setImagen(imagen);  // Asegúrate de pasar la ruta correcta

            session.persist(producto);
            transaction.commit();
            System.out.println("✅ Producto añadido correctamente: " + nombre);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void anadirUsuario(Long idUsuario, String apellido, String contrasena, String correo,
                              String direccion, String nombre, String nombreUsuario,
                              String profileImage, boolean conectado) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Verificar si ya existe un usuario con el mismo correo o nombreUsuario
            Long count = session.createQuery(
                            "SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo OR u.nombreUsuario = :nombreUsuario", Long.class)
                    .setParameter("correo", correo)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .uniqueResult();

            if (count != null && count > 0) {
                System.out.println("⚠ Ya existe un usuario con ese correo o nombre de usuario.");
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario.setApellido(apellido);
            usuario.setContrasena(contrasena);
            usuario.setCorreo(correo);
            usuario.setDireccion(direccion);
            usuario.setNombre(nombre);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setProfileImage(profileImage);
            usuario.setConectado(conectado);

            session.persist(usuario);
            transaction.commit();
            System.out.println("✅ Usuario añadido correctamente: " + nombreUsuario);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

}
