package com.team65.isaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private Integer id;
    private String companyName;
    private AddressDTO address;
    private Integer addressId;
    private String description;
    private double rating;
}
