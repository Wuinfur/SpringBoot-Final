package com.utn.productos_api.controller;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos") // Ruta base [cite: 125]
@Tag(name = "Gestión de Productos", description = "Endpoints para API de Productos") // Documentación Swagger [cite: 177]
public class ProductoController {

    private final ProductoService productoService;

    // Inyección del servicio [cite: 126]
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = { // <-- 1. Se envuelven en @ApiResponses
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    }) // <-- 2. Se cierra el @ApiResponses
    @PostMapping //
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        ProductoResponseDTO productoCreado = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED); // Código 201
    }

@Operation(summary = "Listar todos los productos")
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de productos obtenida") })
@GetMapping // [cite: 128]
public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
    List<ProductoResponseDTO> dtos = productoService.obtenerTodos();
    return new ResponseEntity<>(dtos, HttpStatus.OK);
}

@Operation(summary = "Obtener un producto por su ID")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
})
@GetMapping("/{id}") // [cite: 134]
public ResponseEntity<ProductoResponseDTO> obtenerProductoPorId(@PathVariable Long id) {
    ProductoResponseDTO productoEncontrado = productoService.obtenerPorId(id);
    return new ResponseEntity<>(productoEncontrado, HttpStatus.OK);
}

@Operation(summary = "Filtrar productos por categoría")
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Productos filtrados") })
@GetMapping("/categoria/{categoria}") // [cite: 134]
public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable Categoria categoria) {
    List<ProductoResponseDTO> dtos = productoService.obtenerPorCategoria(categoria);
    return new ResponseEntity<>(dtos, HttpStatus.OK);
}

@Operation(summary = "Actualizar un producto completo por ID")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
})
@PutMapping("/{id}") // [cite: 134]
public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
    ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
    return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
}

@Operation(summary = "Actualizar solo el stock de un producto por ID")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (stock negativo)"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
})
@PatchMapping("/{id}/stock") // [cite: 134]
public ResponseEntity<ProductoResponseDTO> actualizarStock(@PathVariable Long id, @Valid @RequestBody ActualizarStockDTO stockDTO) {
    ProductoResponseDTO productoActualizado = productoService.actualizarStock(id, stockDTO);
    return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
}

@Operation(summary = "Eliminar un producto por ID")
@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
@ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
@DeleteMapping("/{id}") // [cite: 134]
public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
    productoService.eliminarProducto(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204 [cite: 134, 140]
}
}