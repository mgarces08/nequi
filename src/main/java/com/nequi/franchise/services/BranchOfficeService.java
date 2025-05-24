package com.nequi.franchise.services;

import com.nequi.franchise.dto.CreateBranchOfficeDTO;
import com.nequi.franchise.dto.UpdateNameDTO;
import com.nequi.franchise.exceptions.BadRequestException;
import com.nequi.franchise.persistence.BranchOfficeRepository;
import com.nequi.franchise.persistence.entities.BranchOffice;
import com.nequi.franchise.persistence.entities.Franchise;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BranchOfficeService {

    private final BranchOfficeRepository branchOfficeRepository;
    private final FranchiseService franchiseService;

    public BranchOfficeService(BranchOfficeRepository branchOfficeRepository, FranchiseService franchiseService) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.franchiseService = franchiseService;
    }

    public void create(CreateBranchOfficeDTO body) {
        Optional<Franchise> franchise = franchiseService.findById(body.franchiseId());

        if (franchise.isEmpty()) {
            throw new BadRequestException("Franchise not found");
        }

        BranchOffice branchOffice = new BranchOffice();
        branchOffice.setName(body.name());
        branchOffice.setFranchise(franchise.get());

        branchOfficeRepository.save(branchOffice);
    }

    public Optional<BranchOffice> findById(Long id) {
        return branchOfficeRepository.findById(id);
    }

    public void updateName(UpdateNameDTO body) {
        BranchOffice branchOffice = branchOfficeRepository.findById(body.id())
                                                          .orElseThrow(() -> new BadRequestException("BranchOffice not found"));

        branchOffice.setName(body.name());
        branchOfficeRepository.save(branchOffice);
    }
}
