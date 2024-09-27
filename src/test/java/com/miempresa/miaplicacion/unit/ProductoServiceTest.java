package com.miempresa.miaplicacion.unit;

import com.miempresa.miaplicacion.model.Producto;
import com.miempresa.miaplicacion.repository.ProductoRepository;
import com.miempresa.miaplicacion.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    public void testListarTodos() {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");

        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        List<Producto> productos = productoService.listarTodos();

        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findAll();
    }
}
