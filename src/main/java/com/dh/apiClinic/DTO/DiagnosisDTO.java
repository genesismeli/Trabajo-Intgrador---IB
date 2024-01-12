package com.dh.apiClinic.DTO;

import com.dh.apiClinic.entity.CodeCie10;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class DiagnosisDTO {

    private Long id;
    //private String code;
    //private String description;
    private CodeCie10 codeCie10;
    //private DiagnosisStatus status;
    private String notes;


}
