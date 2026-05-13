package com.example.demo.controller;

import com.example.demo.entity.Producto;
import com.example.demo.entity.Kardex;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.KardexRepository; // <-- Importación importante
import com.example.demo.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin("*")
public class InventarioController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private KardexRepository kardexRepository; // Inyectado correctamente arriba

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @PostMapping("/productos")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @DeleteMapping("/productos/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        try {
            productoRepository.deleteById(id);
        } catch (Exception e) {
            // Esto te dirá en la consola de IntelliJ exactamente por qué falla
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    @PostMapping("/movimiento")
    public Kardex registrar(@RequestBody Kardex movimiento) {
        return inventarioService.registrarMovimiento(movimiento);
    }

    @GetMapping("/productos/saldo/{id}")
    public Integer obtenerSaldo(@PathVariable Integer id) {
        Integer saldo = kardexRepository.findUltimoSaldoByProducto(id);
        return (saldo != null) ? saldo : 0;
    }
}