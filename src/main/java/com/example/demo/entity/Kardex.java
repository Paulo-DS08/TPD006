package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "kardex")
@Data
public class Kardex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codiKard;

    private Integer tipoOper; // 1: Suma, 0: Resta

    @ManyToOne
    @JoinColumn(name = "codiProd")
    private Producto producto;

    private Integer cantProd;

    private Integer saldProd;

    // --- AGREGA ESTO MANUALMENTE PARA ELIMINAR LOS ERRORES ROJOS ---

    public Integer getTipoOper() {
        return tipoOper;
    }

    public void setTipoOper(Integer tipoOper) {
        this.tipoOper = tipoOper;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantProd() {
        return cantProd;
    }

    public void setCantProd(Integer cantProd) {
        this.cantProd = cantProd;
    }

    public Integer getSaldProd() {
        return saldProd;
    }

    public void setSaldProd(Integer saldProd) {
        this.saldProd = saldProd;
    }
}