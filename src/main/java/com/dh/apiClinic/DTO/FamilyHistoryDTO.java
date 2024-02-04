package com.dh.apiClinic.DTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@Getter
@Setter

public class  FamilyHistoryDTO {

        private Long id;
        private Boolean coronaryArteryDisease;
        private Boolean myocardialInfarction; //Infarto de miocardio
        private Boolean hypertension; //hipertension
        private Boolean asthma; //asma
        private Boolean copd; //epoc
        private Boolean tuberculosis; //tuberculosis
        private Boolean diabetes; //diabetes
        private Boolean obesity; //obesidad
        private Boolean metabolicSyndrome; //Sindrome metabolicos
        private Boolean anemia; //anemia
        private Boolean hemophilia; //hemofilia
        private Boolean stroke; //accidente cerebrovascular
        private Boolean alzheimer;
        private Boolean multipleSclerosis; //esclerosis múltiple
        private Boolean hemochromatosis; //hematocromatosis
        private Boolean cysticFibrosis; //fibrosis quistica
        private Boolean depression; //Depresion
        private Boolean schizophrenia; //esquizofrenia
        private Boolean rheumatoidArthritis; // artitis reumatoide
        private Boolean lupus;
        private Boolean celiacDisease; //Enf. celiaca
        private Boolean chronicKidneyDisease; //Enf. Renal cónico
        private Boolean diabeticNephropathy; //nefropatia diabética
        private Boolean breastCancer; //Cáncer de mama
        private Boolean colonCancer; //cáncer de colon
        private Boolean lungCancer; //Cancer e pulmón
        private Boolean inflammatoryBowelDisease; //Enfermedad intestinal
        private Boolean hypothyroidism; //Hipotiroidismo
        private String otherMedicalHistory;
    }
