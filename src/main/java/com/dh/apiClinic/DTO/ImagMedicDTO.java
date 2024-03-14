package com.dh.apiClinic.DTO;
import com.dh.apiClinic.entity.Medic;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Getter
@Setter

public class ImagMedicDTO {

    private Long id;

    private Medic medic;

    private byte[] imageData;

}
