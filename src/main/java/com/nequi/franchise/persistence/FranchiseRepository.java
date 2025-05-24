package com.nequi.franchise.persistence;

import com.nequi.franchise.persistence.entities.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {}

