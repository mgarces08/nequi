package com.nequi.franchise.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateStockProductDTO(@NotNull(message = "id is required") Long id,
                                    @NotNull(message = "stock is required") Integer stock) {
}
