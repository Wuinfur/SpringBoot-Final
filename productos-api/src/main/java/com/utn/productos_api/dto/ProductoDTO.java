package com.utn.productos_api.dto;

import com.utn.productos_api.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar un producto")
public class ProductoDTO {

    @Schema(description = "Nombre del producto", example = "Mouse Gamer", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(description = "Descripción detallada del producto", example = "Mouse óptico con 6 botones y 3200 DPI")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @Schema(description = "Precio unitario del producto", example = "45.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser como mínimo 0.01") // Usamos @DecimalMin para Doubles
    private Double precio;

    @Schema(description = "Cantidad de stock disponible", example = "150", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "ELECTRONICA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La categoría no puede ser nula")
    private Categoria categoria;
}