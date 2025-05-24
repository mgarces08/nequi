package com.nequi.franchise.persistence;

import com.nequi.franchise.persistence.entities.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Long> {}