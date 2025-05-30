package com.nequi.franchise.controllers;

import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.dto.CreateFranchiseDTO;
import com.nequi.franchise.services.FranchiseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nequi/franchise")
public class FranchiseController {

    private final FranchiseService franchiseService;

    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody @Validated CreateFranchiseDTO body){
        franchiseService.create(body);

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateName(@RequestBody @Validated UpdateNameDTO body){
        franchiseService.updateName(body);

        return ResponseEntity.ok().build();
    }
}
