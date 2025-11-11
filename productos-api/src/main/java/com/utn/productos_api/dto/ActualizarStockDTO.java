package com.utn.productos_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para actualizar parcialmente el stock de un producto")
public class ActualizarStockDTO {

    @Schema(description = "El nuevo valor total del stock", example = "200", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}