package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.Company;
import com.isfin.islamicfinancial.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id){
        return companyRepository.findById(id);
    }

    public Company saveCompany(Company company){
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id){
        companyRepository.deleteById(id);
    }
}
