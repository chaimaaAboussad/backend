package com.isfin.islamicfinancial.repositories;

import com.isfin.islamicfinancial.entities.Company;
import com.isfin.islamicfinancial.entities.ShariahCompliance;
import com.isfin.islamicfinancial.models.ShariahStandard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface ShariahComplianceRepository extends JpaRepository<ShariahCompliance, Long> {

    List<ShariahCompliance> findAllByCompanyId(Long companyId);
    Optional<ShariahCompliance> findByCompanyAndStandard(Company company, ShariahStandard standard);
}
