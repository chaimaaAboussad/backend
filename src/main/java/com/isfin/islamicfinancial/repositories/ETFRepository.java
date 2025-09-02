package com.isfin.islamicfinancial.repositories;

import com.isfin.islamicfinancial.entities.ETF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ETFRepository extends JpaRepository<ETF, Long> {
}
