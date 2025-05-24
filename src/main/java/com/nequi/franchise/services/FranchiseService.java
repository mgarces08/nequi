package com.nequi.franchise.services;

import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.dto.CreateFranchiseDTO;
import com.nequi.franchise.exceptions.BadRequestException;
import com.nequi.franchise.persistence.FranchiseRepository;
import com.nequi.franchise.persistence.entities.Franchise;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public FranchiseService(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public void create(CreateFranchiseDTO body) {
        Franchise franchise = new Franchise();
        franchise.setName(body.name());
        franchiseRepository.save(franchise);
    }

    public Optional<Franchise> findById(Long id) {
        return franchiseRepository.findById(id);
    }

    public void updateName(UpdateNameDTO body) {
        Franchise franchise = franchiseRepository.findById(body.id())
                                                 .orElseThrow(() -> new BadRequestException("Franchise not found"));

        franchise.setName(body.name());
        franchiseRepository.save(franchise);
    }
}
