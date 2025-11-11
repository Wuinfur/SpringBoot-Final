package com.utn.productos_api.dto;

import com.utn.productos_api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para devolver la información completa de un producto")
public class ProductoResponseDTO {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Mouse Gamer")
    private String nombre;

    @Schema(description = "Descripción detallada del producto", example = "Mouse óptico con 6 botones y 3200 DPI")
    private String descripcion;

    @Schema(description = "Precio unitario del producto", example = "45.50")
    private Double precio;

    @Schema(description = "Cantidad de stock disponible", example = "150")
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "ELECTRONICA")
    private Categoria categoria;
}