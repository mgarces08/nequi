package com.nequi.franchise.services;

import com.nequi.franchise.dto.CreateProductDTO;
import com.nequi.franchise.dto.ProductDTO;
import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.dto.UpdateStockProductDTO;
import com.nequi.franchise.exceptions.BadRequestException;
import com.nequi.franchise.persistence.ProductRepository;
import com.nequi.franchise.persistence.entities.BranchOffice;
import com.nequi.franchise.persistence.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BranchOfficeService branchOfficeService;

    public ProductService(ProductRepository productRepository, BranchOfficeService branchOfficeService) {
        this.productRepository = productRepository;
        this.branchOfficeService = branchOfficeService;
    }

    public void create(CreateProductDTO body) {
        Optional<BranchOffice> branchOffice = branchOfficeService.findById(body.branchOfficeId());

        if (branchOffice.isEmpty()) {
            throw new BadRequestException("BranchOffice not found");
        }

        Product product = new Product();
        product.setName(body.name());
        product.setStock(body.stock());
        product.setBranchOffice(branchOffice.get());
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public void updateStock(UpdateStockProductDTO body) {
        Product product = productRepository.findById(body.id())
                                           .orElseThrow(() -> new BadRequestException("Product not found"));

        product.setStock(body.stock());
        productRepository.save(product);
    }

    public void updateName(UpdateNameDTO body) {
        Product product = productRepository.findById(body.id())
                                           .orElseThrow(() -> new BadRequestException("Product not found"));

        product.setName(body.name());
        productRepository.save(product);
    }

    public List<ProductDTO> getTopStockProductsByFranchise(Long franchiseId) {
        List<Product> products = productRepository.findTopStockProductsByFranchiseId(franchiseId);

        return products.stream()
                       .map(p -> new ProductDTO(p.getId(), p.getName(), p.getStock(), p.getBranchOffice().getName()))
                       .toList();
    }
}
