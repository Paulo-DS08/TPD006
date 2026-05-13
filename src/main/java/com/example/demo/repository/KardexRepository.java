package com.example.demo.repository;

import com.example.demo.entity.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KardexRepository extends JpaRepository<Kardex, Integer> {

    // Esta es la consulta que necesita el Controller para mostrar los saldos
    @Query(value = "SELECT saldProd FROM kardex WHERE codiProd = :id ORDER BY codiKard DESC LIMIT 1", nativeQuery = true)
    Integer findUltimoSaldoByProducto(@Param("id") Integer id);
}