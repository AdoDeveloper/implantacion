package com.miempresa.miaplicacion.controller;

import com.miempresa.miaplicacion.model.Producto;
import com.miempresa.miaplicacion.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "index";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Producto producto) {
        return "crear";
    }

    @PostMapping("/crear")
    public String crearProducto(@Valid Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "crear";
        }
        productoService.guardar(producto);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        model.addAttribute("producto", producto);
        return "editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable("id") Long id, @Valid Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            producto.setId(id);
            return "editar";
        }
        productoService.guardar(producto);
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminar(id);
        return "redirect:/";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        model.addAttribute("producto", producto);
        return "detalle";
    }
}
