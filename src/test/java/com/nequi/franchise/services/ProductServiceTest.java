package com.nequi.franchise.services;

import com.nequi.franchise.dto.CreateProductDTO;
import com.nequi.franchise.dto.ProductDTO;
import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.exceptions.BadRequestException;
import com.nequi.franchise.dto.UpdateStockProductDTO;
import com.nequi.franchise.persistence.ProductRepository;
import com.nequi.franchise.persistence.entities.BranchOffice;
import com.nequi.franchise.persistence.entities.Product;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private BranchOfficeService branchOfficeService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Test Create Product Success")
    void testCreateProductSuccess() {
        CreateProductDTO dto = new CreateProductDTO("Test", 10, 1L);
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setId(1L);
        branchOffice.setName("Branch Office Name");
        Product expectedProduct = new Product();
        expectedProduct.setName(dto.name());
        expectedProduct.setStock(dto.stock());
        expectedProduct.setBranchOffice(branchOffice);

        when(branchOfficeService.findById(1L)).thenReturn(Optional.of(branchOffice));

        productService.create(dto);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(argumentCaptor.capture());
        verify(branchOfficeService).findById(1L);
        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(branchOfficeService);
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveAssertion()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Test Create Product Not Branch Office")
    void testCreateProductNotBranchOffice() {
        CreateProductDTO dto = new CreateProductDTO("Test", 10, 1L);
        when(branchOfficeService.findById(1L)).thenReturn(Optional.empty());

        BadRequestException exception = Assert.assertThrows(BadRequestException.class, () -> productService.create(dto));

        assertEquals("BranchOffice not found", exception.getMessage());
        verifyNoMoreInteractions(branchOfficeService);
        verifyNoInteractions(productRepository);
    }

    @Test
    @DisplayName("Test Update Stock Success")
    void testUpdateStockSuccess() {
        UpdateStockProductDTO dto = new UpdateStockProductDTO(1L, 10);
        Product product = new Product();
        product.setId(1L);
        product.setName("Test");
        product.setStock(5);

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Test");
        expectedProduct.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateStock(dto);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).findById(1L);
        verify(productRepository).save(argumentCaptor.capture());
        verifyNoInteractions(branchOfficeService);
        verifyNoMoreInteractions(productRepository);
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveAssertion()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Test Update Stock Product Not Exist")
    void testUpdateStockProductNotExist() {
        UpdateStockProductDTO dto = new UpdateStockProductDTO(1L, 10);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        BadRequestException exception = Assert.assertThrows(BadRequestException.class, () -> productService.updateStock(dto));

        assertEquals("Product not found", exception.getMessage());
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(branchOfficeService);
    }

    @Test
    @DisplayName("Test Delete Product Success")
    void testDeleteProductSuccess() {
        productService.delete(1L);

        verify(productRepository).deleteById(1L);
        verifyNoInteractions(branchOfficeService);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("Test Update Name Success")
    void testUpdateNameSuccess() {
        UpdateNameDTO dto = new UpdateNameDTO(1L, "Cambio nombre");
        Product product = new Product();
        product.setId(1L);
        product.setName("Test");
        product.setStock(10);

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Cambio nombre");
        expectedProduct.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateName(dto);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).findById(1L);
        verify(productRepository).save(argumentCaptor.capture());
        verifyNoInteractions(branchOfficeService);
        verifyNoMoreInteractions(productRepository);
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveAssertion()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Test Update Name Not Exist")
    void testUpdateNameNotExist() {
        UpdateNameDTO dto = new UpdateNameDTO(1L, "Cambio nombre");
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        BadRequestException exception = Assert.assertThrows(BadRequestException.class, () -> productService.updateName(dto));

        assertEquals("Product not found", exception.getMessage());
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(branchOfficeService);
    }

    @Test
    @DisplayName("Test GetTopStockProductsByFranchise")
    void testGetTopStockProductsByFranchise() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setId(1L);
        branchOffice.setName("Sucursal");
        Product product = new Product();
        product.setId(1L);
        product.setName("Producto");
        product.setStock(10);
        product.setBranchOffice(branchOffice);
        ProductDTO expectedProduct = new ProductDTO(1L, "Producto", 10, "Sucursal");

        when(productRepository.findTopStockProductsByFranchiseId(1L)).thenReturn(List.of(product));

        List<ProductDTO> response = productService.getTopStockProductsByFranchise(1L);

        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(branchOfficeService);

        Assertions.assertThat(response).usingRecursiveAssertion()
                .isEqualTo(List.of(expectedProduct));
    }
}
