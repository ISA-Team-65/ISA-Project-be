package com.team65.isaproject.service;

import com.team65.isaproject.model.Company;
import com.team65.isaproject.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

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

//    public List<Company> searchCompaniesByNameAndAddress(String prefix, String address) {
//        return companyRepository.findByCompanyNameOrAddressContainingIgnoreCase(prefix, address);
//    }
}
