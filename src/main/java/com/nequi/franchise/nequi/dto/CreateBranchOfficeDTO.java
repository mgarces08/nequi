package com.nequi.franchise.nequi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBranchOfficeDTO(@NotBlank(message = "name is required") String name,
                                    @NotNull(message = "franchise_id is required") Long franchiseId) {
}
