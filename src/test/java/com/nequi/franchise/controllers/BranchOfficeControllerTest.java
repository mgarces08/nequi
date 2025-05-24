package com.nequi.franchise.controllers;

import com.nequi.franchise.dto.CreateBranchOfficeDTO;
import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.services.BranchOfficeService;
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

@WebMvcTest(BranchOfficeController.class)
class BranchOfficeControllerTest {

    @MockBean private BranchOfficeService branchOfficeService;
    @Autowired private MockMvc mvc;

    @Test
    @DisplayName("Testing Create Branch Office")
    void testCreate() throws Exception {
        mvc.perform(post("/nequi/branch-office")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content("{\"name\": \"Test\", \"franchise_id\": 1}"))
           .andExpect(status().isOk());

        ArgumentCaptor<CreateBranchOfficeDTO> argumentCaptor = ArgumentCaptor.forClass(CreateBranchOfficeDTO.class);
        verify(branchOfficeService).create(argumentCaptor.capture());
        verifyNoMoreInteractions(branchOfficeService);

        Assertions.assertEquals("Test", argumentCaptor.getValue().name());
        Assertions.assertEquals(1L, argumentCaptor.getValue().franchiseId());
    }

    @Test
    @DisplayName("Testing Update Name Branch Office")
    void testUpdateName() throws Exception {
        mvc.perform(put("/nequi/branch-office")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Cambio\"}"))
                .andExpect(status().isOk());

        ArgumentCaptor<UpdateNameDTO> argumentCaptor = ArgumentCaptor.forClass(UpdateNameDTO.class);
        verify(branchOfficeService).updateName(argumentCaptor.capture());
        verifyNoMoreInteractions(branchOfficeService);

        Assertions.assertEquals("Cambio", argumentCaptor.getValue().name());
        Assertions.assertEquals(1, argumentCaptor.getValue().id());
    }
}
