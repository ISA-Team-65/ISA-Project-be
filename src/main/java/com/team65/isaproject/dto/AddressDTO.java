package com.team65.isaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Integer id;
    private String country;
    private String city;
    private String street;
    private Integer streetNumber;
    private double latitude;
    private double longitude;
}