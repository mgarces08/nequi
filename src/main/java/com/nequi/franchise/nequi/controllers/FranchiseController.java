package com.nequi.franchise.nequi.controllers;

import com.nequi.franchise.nequi.dto.CreateFranchiseDTO;
import com.nequi.franchise.nequi.dto.UpdateNameDTO;
import com.nequi.franchise.nequi.services.FranchiseService;
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
