package com.example.demo.service;

import com.example.demo.entity.Kardex;
import com.example.demo.repository.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService {

    @Autowired
    private KardexRepository kardexRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Para Visualizar Saldos (WebSocket)

    /**
     * OPERACIÓN REGISTRO DE MOVIMIENTO
     * Esta es la lógica central de tu pizarra (Imagen 2)
     */
    @Transactional
    public Kardex registrarMovimiento(Kardex nuevoMovimiento) {

        // 1. Obtener el último saldo registrado para este producto
        // Si no existe ningún registro previo, empezamos en 0
        Integer saldoAnterior = kardexRepository.findUltimoSaldoByProducto(nuevoMovimiento.getProducto().getCodiProd());
        if (saldoAnterior == null) {
            saldoAnterior = 0;
        }

        // 2. Calcular el nuevo saldo basado en el tipoOper (1: Agrega, 0: Resta)
        int saldoCalculado;
        if (nuevoMovimiento.getTipoOper() == 1) {
            saldoCalculado = saldoAnterior + nuevoMovimiento.getCantProd();
        } else {
            saldoCalculado = saldoAnterior - nuevoMovimiento.getCantProd();
        }

        // 3. Asignar el saldo calculado al registro actual
        nuevoMovimiento.setSaldProd(saldoCalculado);

        // 4. Guardar en la Base de Datos (Tabla Kardex)
        Kardex guardado = kardexRepository.save(nuevoMovimiento);

        // 5. VISUALIZAR SALDOS (WebSocket)
        // Enviamos el objeto guardado al canal "/topic/saldos" para que el frontend se actualice solo
        messagingTemplate.convertAndSend("/topic/saldos", guardado);

        return guardado;
    }
}