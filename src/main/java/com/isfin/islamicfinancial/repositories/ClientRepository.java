package com.isfin.islamicfinancial.repositories;

import com.isfin.islamicfinancial.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
