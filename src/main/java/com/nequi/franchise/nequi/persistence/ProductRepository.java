package com.nequi.franchise.nequi.persistence;

import com.nequi.franchise.nequi.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p
        FROM Product p
        WHERE p.branchOffice.franchise.id = :franchiseId
        AND p.stock = (
            SELECT MAX(p2.stock)
            FROM Product p2
            WHERE p2.branchOffice.id = p.branchOffice.id
        )
    """)
    List<Product> findTopStockProductsByFranchiseId(@Param("franchiseId") Long franchiseId);
}