package com.nequi.franchise.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateFranchiseDTO(@NotBlank(message = "name is required") String name) {
}
