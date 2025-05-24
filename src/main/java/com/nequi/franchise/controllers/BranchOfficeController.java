package com.nequi.franchise.controllers;

import com.nequi.franchise.dto.CreateBranchOfficeDTO;
import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.services.BranchOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nequi/branch-office")
public class BranchOfficeController {

    private final BranchOfficeService branchOfficeService;

    public BranchOfficeController(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody @Validated CreateBranchOfficeDTO body){
        branchOfficeService.create(body);

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateName(@RequestBody @Validated UpdateNameDTO body){
        branchOfficeService.updateName(body);

        return ResponseEntity.ok().build();
    }
}
