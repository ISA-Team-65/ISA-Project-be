package com.team65.isaproject.controller;

import com.team65.isaproject.dto.CompanyDTO;
import com.team65.isaproject.model.Company;
import com.team65.isaproject.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody CompanyDTO companyDTO)
    {
        Company company = new Company();
        company.setCompanyName(companyDTO.getCompanyName());
        company.setAddress(companyDTO.getAddress());
        company.setDescription(companyDTO.getDescription());
        company.setRating(companyDTO.getRating());

        company = companyService.save(company);

        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id)
    {
        Company company = companyService.findOne(id);

        if (company == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }
}
