package com.nequi.franchise.nequi.controllers;

import com.nequi.franchise.nequi.dto.CreateProductDTO;
import com.nequi.franchise.nequi.dto.ProductDTO;
import com.nequi.franchise.nequi.dto.UpdateNameDTO;
import com.nequi.franchise.nequi.dto.UpdateStockProductDTO;
import com.nequi.franchise.nequi.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nequi/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody @Validated CreateProductDTO body){
        productService.create(body);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateStock(@RequestBody @Validated UpdateStockProductDTO body) {
        productService.updateStock(body);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-name")
    public ResponseEntity<Void> updateName(@RequestBody @Validated UpdateNameDTO body) {
        productService.updateName(body);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/top-stock-by-franchise/{franchiseId}")
    public ResponseEntity<List<ProductDTO>> getTopStockProductsByFranchise(@PathVariable Long franchiseId) {
        return ResponseEntity.ok(productService.getTopStockProductsByFranchise(franchiseId));
    }
}
