package com.nequi.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductDTO(@NotBlank(message = "name is required") String name,
                               @NotNull(message = "stock is required") Integer stock,
                               @NotNull(message = "branch_office_id is required") Long branchOfficeId) {
}
