package com.team65.isaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {

    private List<DeliveryEquipmentDTO> equipment;
    private String deliveryDate;

}
