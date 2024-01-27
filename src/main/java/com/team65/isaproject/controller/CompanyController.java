package com.team65.isaproject.controller;

import com.team65.isaproject.dto.CompanyDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.Company;
import com.team65.isaproject.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/companies")
@RequiredArgsConstructor
@Tag(name = "Company")
public class CompanyController {

    private final CompanyService companyService;
    private final Mapper<Company, CompanyDTO> mapper;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        //ovde bi isla validacija
        if(companyDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Company company = mapper.mapToModel(companyDTO, Company.class);
        company = companyService.save(company);
        return new ResponseEntity<>(mapper.mapToDto(company, CompanyDTO.class), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole( 'SYSTEM_ADMIN', 'USER', 'COMPANY_ADMIN')")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id)
    {
        Company company = companyService.findById(id);

        if (company == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.mapToDto(company, CompanyDTO.class), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO companyDTO) {

        Company company = companyService.findById(companyDTO.getId());
//        user = userService.update(userDTOMapper.);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

//        company.setAddress(companyDTO.getAddress());
        company.setAddressId(companyDTO.getAddressId());
        company.setCompanyName(companyDTO.getCompanyName());
        company.setDescription(companyDTO.getDescription());
        company.setRating(companyDTO.getRating());

        company = companyService.save(company);
        return new ResponseEntity<>(mapper.mapToDto(company, CompanyDTO.class), HttpStatus.OK);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<CompanyDTO>> searchCompaniesByNameAndAddress(@RequestParam String prefix, @RequestParam String address) {
//        List<Company> companies = companyService.searchCompaniesByNameAndAddress(prefix, address);
//        List<CompanyDTO> companyDTOS = new ArrayList<>();
//        for (Company company : companies) {
//            CompanyDTO companyDTO = mapper.mapToDto(company, CompanyDTO.class);
//            companyDTOS.add(companyDTO);
//        }
//        return new ResponseEntity<>(companyDTOS, HttpStatus.OK);
//    }
}