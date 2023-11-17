package com.team65.isaproject.mapper;

import com.team65.isaproject.dto.CompanyDTO;
import com.team65.isaproject.model.Company;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyDTOMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public CompanyDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Company fromDTOtoCompany(CompanyDTO companyDTO){
        return modelMapper.map(companyDTO, Company.class);
    }

    public static CompanyDTO fromCompanytoDTO(Company companyDTO){
        return modelMapper.map(companyDTO, CompanyDTO.class);
    }
}
