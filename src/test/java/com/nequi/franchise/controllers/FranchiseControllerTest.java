package com.nequi.franchise.controllers;

import com.nequi.franchise.dto.CreateFranchiseDTO;
import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.services.FranchiseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FranchiseController.class)
class FranchiseControllerTest {

    @MockBean private FranchiseService franchiseService;
    @Autowired private MockMvc mvc;

    @Test
    @DisplayName("Testing Create Franchise")
    void testCreate() throws Exception {
        mvc.perform(post("/nequi/franchise")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content("{\"name\": \"Test\"}"))
           .andExpect(status().isOk());

        ArgumentCaptor<CreateFranchiseDTO> argumentCaptor = ArgumentCaptor.forClass(CreateFranchiseDTO.class);
        verify(franchiseService).create(argumentCaptor.capture());
        verifyNoMoreInteractions(franchiseService);

        Assertions.assertEquals("Test", argumentCaptor.getValue().name());
    }

    @Test
    @DisplayName("Testing Update Name Franchise")
    void testUpdateName() throws Exception {
        mvc.perform(put("/nequi/franchise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Cambio\"}"))
                .andExpect(status().isOk());

        ArgumentCaptor<UpdateNameDTO> argumentCaptor = ArgumentCaptor.forClass(UpdateNameDTO.class);
        verify(franchiseService).updateName(argumentCaptor.capture());
        verifyNoMoreInteractions(franchiseService);

        Assertions.assertEquals("Cambio", argumentCaptor.getValue().name());
        Assertions.assertEquals(1, argumentCaptor.getValue().id());
    }
}
