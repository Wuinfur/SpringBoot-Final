package com.utn.productos_api.service;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.exception.ProductoNotFoundException; // 1. Importa la excepción personalizada
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.model.Producto;
import com.utn.productos_api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // --- MÉTODOS PÚBLICOS (Hablan con DTOs) ---

    /**
     * Crea un nuevo producto en la base de datos.
     * @param productoDTO El DTO con la información para crear el producto.
     * @return El DTO del producto recién creado (con su ID).
     */
    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO) {
        // 1. Convierte el DTO a una Entidad
        Producto producto = convertirAEntidad(productoDTO);

        // 2. Guarda la Entidad en la BD
        Producto productoGuardado = productoRepository.save(producto);

        // 3. Convierte la Entidad guardada a un DTO de respuesta
        return convertirAResponseDTO(productoGuardado);
    }

    /**
     * Obtiene una lista de todos los productos.
     * @return Una lista de DTOs de respuesta.
     */
    public List<ProductoResponseDTO> obtenerTodos() {
        List<Producto> productos = productoRepository.findAll();

        // Convierte cada Producto de la lista a un ProductoResponseDTO
        return productos.stream()
                .map(this::convertirAResponseDTO) // Llama al helper por cada ítem
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto específico por su ID.
     * @param id El ID del producto a buscar.
     * @return El DTO del producto encontrado.
     * @throws ProductoNotFoundException si el producto no existe.
     */
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id)); // 2. Usa la excepción personalizada

        return convertirAResponseDTO(producto);
    }

    /**
     * Obtiene una lista de productos filtrados por categoría.
     * @param categoria La categoría por la cual filtrar.
     * @return Una lista de DTOs de respuesta.
     */
    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria) {
        List<Producto> productos = productoRepository.findByCategoria(categoria);

        return productos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un producto completo por su ID.
     * @param id El ID del producto a actualizar.
     * @param productoDTO El DTO con la nueva información.
     * @return El DTO del producto ya actualizado.
     * @throws ProductoNotFoundException si el producto no existe.
     */
    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        // 1. Busca el producto existente
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id)); // 2. Usa la excepción

        // 2. Actualiza los campos desde el DTO
        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setStock(productoDTO.getStock());
        productoExistente.setCategoria(productoDTO.getCategoria());

        // 3. Guarda y convierte la respuesta
        Producto productoActualizado = productoRepository.save(productoExistente);
        return convertirAResponseDTO(productoActualizado);
    }

    /**
     * Actualiza únicamente el stock de un producto.
     * @param id El ID del producto a actualizar.
     * @param stockDTO El DTO que contiene el nuevo stock.
     * @return El DTO del producto actualizado.
     * @throws ProductoNotFoundException si el producto no existe.
     */
    public ProductoResponseDTO actualizarStock(Long id, ActualizarStockDTO stockDTO) {
        // 1. Busca el producto existente
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id)); // 2. Usa la excepción

        // 2. Actualiza solo el stock
        productoExistente.setStock(stockDTO.getStock());

        // 3. Guarda y convierte la respuesta
        Producto productoActualizado = productoRepository.save(productoExistente);
        return convertirAResponseDTO(productoActualizado);
    }

    /**
     * Elimina un producto por su ID.
     * @param id El ID del producto a eliminar.
     * @throws ProductoNotFoundException si el producto no existe.
     */
    public void eliminarProducto(Long id) {
        // 1. Verifica que el producto exista
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id)); // 2. Usa la excepción

        // 2. Si existe, lo elimina
        productoRepository.delete(productoExistente);
    }


    // --- MÉTODOS AUXILIARES PRIVADOS (Conversión) ---

    /**
     * Convierte una Entidad Producto a un DTO de Respuesta.
     * @param producto La entidad a convertir.
     * @return El DTO de respuesta.
     */
    private ProductoResponseDTO convertirAResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria());
        return dto;
    }

    /**
     * Convierte un DTO de creación/actualización a una Entidad Producto.
     * @param dto El DTO a convertir.
     * @return La Entidad (sin ID, lista para ser guardada).
     */
    private Producto convertirAEntidad(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        return producto;
    }
}