package com.dh.apiClinic.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@Getter
@Setter
public class PersonalHistoryDTO {

    private Long id;

    // Enfermedades Crónicas
    private boolean diabetes;
    private boolean hypertension;
    private boolean coronaryArteryDisease;

    // Enfermedades Respiratorias
    private boolean bronchialAsthma;
    private boolean bronchopulmonaryDisease;

    // Enfermedades Psiquiátricas
    private boolean psychopathy;

    // Alergias
    private String allergies;

    // Enfermedades Infecciosas
    private boolean tuberculosis;
    private boolean sexuallyTransmittedInfection; // E.T.S

    // Enfermedades Metabólicas y Endocrinas
    private boolean gout;
    private boolean endocrineDisorders;

    // Enfermedades Nefrológicas y Urológicas
    private boolean nephropathies;
    private boolean uropathies;

    // Enfermedades Hematológicas
    private boolean hematopathies;

    // Enfermedades Gastrointestinales
    private boolean ulcer;
    private boolean hepatitis;

    // Otros Antecedentes
    private boolean fever;
    private String otherMedicalHistory; // Puedes almacenar otros antecedentes como una cadena de texto

    private String cancer;

}
