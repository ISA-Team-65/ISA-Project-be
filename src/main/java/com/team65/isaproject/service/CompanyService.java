package com.team65.isaproject.service;

import com.team65.isaproject.model.Company;
import com.team65.isaproject.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
//    Injektuje se automatski =D
//    public CompanyService(CompanyRepository companyRepository) {
//        this.companyRepository = companyRepository;
//    }
    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    public Company findById(Integer id){
       return companyRepository.findById(id).orElseGet(null);
    }

    public Company update(Company company){
        Company temp = findById(company.getId());
        if(temp != null){
            return companyRepository.save(temp);
        }
        return null;
    }

    public Company save(Company company){
        return companyRepository.save(company);
    }

    public List<Company> searchCompaniesByNameAndAddress(String prefix, String address) {
        return companyRepository.findByCompanyNameOrAddressContainingIgnoreCase(prefix, address);
    }
}
