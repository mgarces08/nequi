package com.nequi.franchise.services;

import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.exceptions.BadRequestException;
import com.nequi.franchise.dto.CreateFranchiseDTO;
import com.nequi.franchise.persistence.FranchiseRepository;
import com.nequi.franchise.persistence.entities.Franchise;
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
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;
    @InjectMocks
    private FranchiseService franchiseService;

    @Test
    @DisplayName("Test Create Franchise Success")
    void testCreateFranchiseSuccess() {
        CreateFranchiseDTO dto = new CreateFranchiseDTO("Test");
        Franchise expectedFranchise = new Franchise();
        expectedFranchise.setName(dto.name());

        franchiseService.create(dto);

        ArgumentCaptor<Franchise> argumentCaptor = ArgumentCaptor.forClass(Franchise.class);
        verify(franchiseRepository).save(argumentCaptor.capture());
        verifyNoMoreInteractions(franchiseRepository);
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveAssertion()
                                                        .isEqualTo(expectedFranchise);
    }

    @Test
    @DisplayName("Test Find Product By Id")
    void testFindProductByIdSuccess() {
        Franchise franchise = new Franchise();
        franchise.setId(1L);
        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));

        Optional<Franchise> response = franchiseService.findById(1L);

        verify(franchiseRepository).findById(1L);
        verifyNoMoreInteractions(franchiseRepository);
        Assertions.assertThat(response.get()).usingRecursiveAssertion()
                                             .isEqualTo(franchise);
    }

    @Test
    @DisplayName("Test Update Name Success")
    void testUpdateNameSuccess() {
        UpdateNameDTO dto = new UpdateNameDTO(1L, "Cambio nombre");
        Franchise franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Test");

        Franchise expectedFranchise = new Franchise();
        expectedFranchise.setId(1L);
        expectedFranchise.setName("Cambio nombre");

        when(franchiseRepository.findById(1L)).thenReturn(Optional.of(franchise));

        franchiseService.updateName(dto);

        ArgumentCaptor<Franchise> argumentCaptor = ArgumentCaptor.forClass(Franchise.class);
        verify(franchiseRepository).findById(1L);
        verify(franchiseRepository).save(argumentCaptor.capture());
        verifyNoMoreInteractions(franchiseRepository);
        Assertions.assertThat(argumentCaptor.getValue()).usingRecursiveAssertion()
                .isEqualTo(expectedFranchise);
    }

    @Test
    @DisplayName("Test Update Name Not Exist")
    void testUpdateNameNotExist() {
        UpdateNameDTO dto = new UpdateNameDTO(1L, "Cambio nombre");
        when(franchiseRepository.findById(1L)).thenReturn(Optional.empty());

        BadRequestException exception = Assert.assertThrows(BadRequestException.class, () -> franchiseService.updateName(dto));

        assertEquals("Franchise not found", exception.getMessage());
        verifyNoMoreInteractions(franchiseRepository);
    }
}
