package com.team65.isaproject.service;

import com.team65.isaproject.model.Company;
import com.team65.isaproject.repository.CompanyReposiotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyReposiotory companyReposiotory;

    public Company save(Company company)
    {
        return companyReposiotory.save(company);
    }

    public Company findOne(Integer id)
    {
        return companyReposiotory.findById(id).orElseGet(null);
    }
}
