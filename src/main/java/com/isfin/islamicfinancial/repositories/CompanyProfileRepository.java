package com.isfin.islamicfinancial.repositories;

import com.isfin.islamicfinancial.entities.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, String> {
}
