package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.util.List;

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
}
