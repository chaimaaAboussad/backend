package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.FinancialRatios;
import com.isfin.islamicfinancial.repositories.FinancialRatiosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialRatiosService {

    private final FinancialRatiosRepository financialRatiosRepository;

    @Autowired
    public FinancialRatiosService(FinancialRatiosRepository financialRatiosRepository){
        this.financialRatiosRepository = financialRatiosRepository;
    }

    public List<FinancialRatios> getAllFinancialRatios(){
        return financialRatiosRepository.findAll();
    }

    public Optional<FinancialRatios> getFinancialRatiosById(Long id){
        return financialRatiosRepository.findById(id);
    }

    public FinancialRatios saveFinancialRatios(FinancialRatios financialRatios){
        return financialRatiosRepository.save(financialRatios);
    }

    public void deleteFinancialRatios(Long id){
        financialRatiosRepository.deleteById(id);
    }
}
