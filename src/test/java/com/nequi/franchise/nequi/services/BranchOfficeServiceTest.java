package com.nequi.franchise.nequi.services;

import com.nequi.franchise.nequi.dto.CreateBranchOfficeDTO;
import com.nequi.franchise.nequi.dto.UpdateNameDTO;
import com.nequi.franchise.nequi.exceptions.BadRequestException;
import com.nequi.franchise.nequi.persistence.BranchOfficeRepository;
import com.nequi.franchise.nequi.persistence.entities.BranchOffice;
import com.nequi.franchise.nequi.persistence.entities.Franchise;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchOfficeServiceTest {

    @Mock
    private BranchOfficeRepository branchOfficeRepository;
    @Mock
    private FranchiseService franchiseService;
    @InjectMocks
    private BranchOfficeService branchOfficeService;

    @Test
    @DisplayName("Test Create Branch Office Success")
    void testCreateBranchOfficeSuccess() {
        CreateBranchOfficeDTO dto = new CreateBranchOfficeDTO("Test", 1L);
        Franchise franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Franchise Name");
        BranchOffice expectedBranchOffice = new BranchOffice();
        expectedBranchOffice.setName(dto.name());
        expectedBranchOffice.setFranchise(franchise);

        when(franchiseService.findById(1L)).thenReturn(Optional.of(franchise));

        branchOfficeService.create(dto);

        ArgumentCaptor<BranchOffice> argumentCaptor = ArgumentCaptor.forClass(BranchOffice.class);
        verify(branchOfficeRepository).save(argumentCaptor.capture());
        verify(franchiseService).findById(1L);
        verifyNoMoreInteractions(franchiseService);
        verifyNoMoreInteractions(branchOfficeRepository);
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveAssertion()
                                                        .isEqualTo(expectedBranchOffice);
    }

    @Test
    @DisplayName("Test Create Branch Office Not Franchise")
    void testCreateProductNotBranchOffice() {
        CreateBranchOfficeDTO dto = new CreateBranchOfficeDTO("Test", 1L);
        when(franchiseService.findById(1L)).thenReturn(Optional.empty());

        BadRequestException exception = Assert.assertThrows(BadRequestException.class, () -> branchOfficeService.create(dto));

        assertEquals("Franchise not found", exception.getMessage());
        verifyNoMoreInteractions(franchiseService);
        verifyNoInteractions(branchOfficeRepository);
    }

    @Test
    @DisplayName("Test Find Branch Office By Id")
    void testFindBranchOfficeByIdSuccess() {
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setId(1L);
        when(branchOfficeRepository.findById(1L)).thenReturn(Optional.of(branchOffice));

        Optional<BranchOffice> response = branchOfficeService.findById(1L);

        verify(branchOfficeRepository).findById(1L);
        verifyNoMoreInteractions(branchOfficeRepository);
        Assertions.assertThat(response.get()).usingRecursiveAssertion()
                                             .isEqualTo(branchOffice);
    }

    @Test
    @DisplayName("Test Update Name Success")
    void testUpdateNameSuccess() {
        UpdateNameDTO dto = new UpdateNameDTO(1L, "Cambio nombre");
        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setId(1L);
        branchOffice.setName("Test");

        BranchOffice expectedBranchOffice = new BranchOffice();
        expectedBranchOffice.setId(1L);
        expectedBranchOffice.setName("Cambio nombre");

        when(branchOfficeRepository.findById(1L)).thenReturn(Optional.of(branchOffice));

        branchOfficeService.updateName(dto);

        ArgumentCaptor<BranchOffice> argumentCaptor = ArgumentCaptor.forClass(BranchOffice.class);
        verify(branchOfficeRepository).findById(1L);
        verify(branchOfficeRepository).save(argumentCaptor.capture());
        verifyNoInteractions(franchiseService);
        verifyNoMoreInteractions(branchOfficeRepository);
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveAssertion()
                .isEqualTo(expectedBranchOffice);
    }

    @Test
    @DisplayName("Test Update Name Not Exist")
    void testUpdateNameNotExist() {
        UpdateNameDTO dto = new UpdateNameDTO(1L, "Cambio nombre");
        when(branchOfficeRepository.findById(1L)).thenReturn(Optional.empty());

        BadRequestException exception = Assert.assertThrows(BadRequestException.class, () -> branchOfficeService.updateName(dto));

        assertEquals("BranchOffice not found", exception.getMessage());
        verifyNoMoreInteractions(branchOfficeRepository);
        verifyNoInteractions(franchiseService);
    }
}
