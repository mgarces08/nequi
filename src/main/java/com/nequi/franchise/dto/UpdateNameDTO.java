package com.nequi.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateNameDTO(@NotNull(message = "id is required") Long id,
                            @NotBlank(message = "name is required") String name) {
}
