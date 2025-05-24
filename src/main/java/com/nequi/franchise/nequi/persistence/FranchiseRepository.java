package com.nequi.franchise.nequi.persistence;

import com.nequi.franchise.nequi.persistence.entities.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {}

