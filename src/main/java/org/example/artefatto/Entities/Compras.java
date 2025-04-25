package org.example.artefatto.Entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "compras")
public class Compras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long id_compra;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto id_producto;

    @Column(name = "pagado")
    private Boolean isPagado = false;

    @Column(name = "fecha_compra")
    private Date fecha_compra;

    // Constructor

    public Compras() {
    }

    public Compras(Long id_compra, Usuario id_usuario, Producto id_producto, Boolean isPagado, Date fecha_compra) {
        this.id_compra = id_compra;
        this.id_usuario = id_usuario;
        this.id_producto = id_producto;
        this.isPagado = isPagado;
        this.fecha_compra = fecha_compra;
    }

    // Getters y Setters

    public Long getId_compra() {
        return id_compra;
    }

    public void setId_compra(Long id_compra) {
        this.id_compra = id_compra;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Producto getId_producto() {
        return id_producto;
    }

    public void setId_producto(Producto id_producto) {
        this.id_producto = id_producto;
    }

    public Boolean getPagado() {
        return isPagado;
    }

    public void setPagado(Boolean pagado) {
        isPagado = pagado;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    // Método toString()

    @Override
    public String toString() {
        return "Compras{" +
                "id_compra=" + id_compra +
                ", id_usuario=" + id_usuario +
                ", id_producto=" + id_producto +
                ", isPagado=" + isPagado +
                ", fecha_compra=" + fecha_compra +
                '}';
    }
}
