package com.nequi.franchise.controllers;

import com.nequi.franchise.dto.CreateProductDTO;
import com.nequi.franchise.dto.ProductDTO;
import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.dto.UpdateStockProductDTO;
import com.nequi.franchise.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean private ProductService productService;
    @Autowired private MockMvc mvc;

    @Test
    @DisplayName("Testing Create Product")
    void testCreate() throws Exception {
        mvc.perform(post("/nequi/product")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content("{\"name\": \"Test\", \"stock\": 10, \"branch_office_id\": 1}"))
           .andExpect(status().isOk());

        ArgumentCaptor<CreateProductDTO> argumentCaptor = ArgumentCaptor.forClass(CreateProductDTO.class);
        verify(productService).create(argumentCaptor.capture());
        verifyNoMoreInteractions(productService);

        Assertions.assertEquals("Test", argumentCaptor.getValue().name());
        Assertions.assertEquals(10, argumentCaptor.getValue().stock());
        Assertions.assertEquals(1, argumentCaptor.getValue().branchOfficeId());
    }

    @Test
    @DisplayName("Testing Delete Product")
    void testDelete() throws Exception {
        mvc.perform(delete("/nequi/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService).delete(1L);
        verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("Testing Update Stock Product")
    void testUpdateStock() throws Exception {
        mvc.perform(put("/nequi/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"stock\": 15}"))
                .andExpect(status().isOk());

        ArgumentCaptor<UpdateStockProductDTO> argumentCaptor = ArgumentCaptor.forClass(UpdateStockProductDTO.class);
        verify(productService).updateStock(argumentCaptor.capture());
        verifyNoMoreInteractions(productService);

        Assertions.assertEquals(15, argumentCaptor.getValue().stock());
        Assertions.assertEquals(1, argumentCaptor.getValue().id());
    }

    @Test
    @DisplayName("Testing Update Name Product")
    void testUpdateName() throws Exception {
        mvc.perform(put("/nequi/product/update-name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Cambio\"}"))
                .andExpect(status().isOk());

        ArgumentCaptor<UpdateNameDTO> argumentCaptor = ArgumentCaptor.forClass(UpdateNameDTO.class);
        verify(productService).updateName(argumentCaptor.capture());
        verifyNoMoreInteractions(productService);

        Assertions.assertEquals("Cambio", argumentCaptor.getValue().name());
        Assertions.assertEquals(1, argumentCaptor.getValue().id());
    }

    @Test
    @DisplayName("Testing getTopStockProductsByFranchise")
    void testGetTopStockProductsByFranchise() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Producto", 10, "Sucursal");
        when(productService.getTopStockProductsByFranchise(1L)).thenReturn(List.of(productDTO));

        mvc.perform(get("/nequi/product/top-stock-by-franchise/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"name\": \"Producto\",\"stock\": 10,\"branch_office_name\": \"Sucursal\"}]"));

        verify(productService).getTopStockProductsByFranchise(1L);
    }
}
