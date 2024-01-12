package com.dh.apiClinic.DTO;

import com.dh.apiClinic.entity.Vademecum;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@Getter
@Setter
public class MedicationDTO {

    private Long id;
    private Vademecum vademecum;



}
