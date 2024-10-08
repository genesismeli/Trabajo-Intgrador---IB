package com.dh.apiClinic.enums;

public enum Speciality {

    ALERGIA_E_INMUNOLOGIA_PEDIATRICA("ALERGIA E INMUOLOGIA PEDIATRICA"),
    ALERGIA_E_INMULOGIA("ALERGIA E INMUNOLOGIA"),
    ANATOMIA_PATOLOGICA("ANATOMIA PATOLOGICA"),
    ANESTESILOGIA("ANESTESIOLOGIA"),
    ANGIOLOGIA_GRAL_Y_HEMODINAMIA("ANGIOLOGIA GENERAL Y HEMODINAMIA"),
    CARDIOLOGIA("CARDIOLOGIA"),
    CARDIOLOGIA_INFANTIL("CARDIOLOGIA INFANTIL"),
    CIRUGIA_CARDIOVASCULAR_INFANTIL("CIRUGIA CARDIOVASCULAR INFANTIL"),
    CIRUGIA_CARDIOVASCULAR("CIRUGIA CARDIOVASCULAR"),
    CIRUGIA_DE_CABEZA_Y_CUELLO("CIRUGIA DE CABEZA Y CUELLO"),
    CIRUGIA_DE_TORAX("CIRUGIA DE TORAX"),
    CIRUGIA_GENERAL("CIRUGIA GENERAL"),
    CIRUGA_INFANTIL("CIRUGIA INFANTIL"),
    CIRUGIA_PLASTICA("CIRUGIA PLASTICA"),
    CIRUGIA_VASCULAR_PERIFERICA("CIRUGIA VASCULAR PERIFERICA"),
    CLINICA_MEDICA("CLINICA MEDICA"),
    COLOPROCTOLOGIA("COLOPROCTOLOGÍA"),
    DERMATOLOGIA_PEDIATRICA("DERMATOLOGIA PEDIATRICA"),
    DERMATOLOGIA("DERMATOLOGIA"),
    DIAGNOSTICO_POR_IMAGENES("DIAGNOSTICO POR IMAGENES"),
    ELECTRO_FISIOLOGIA_CARDIACA("ELECTRO FSIOLOGIA CARDIACA"),
    EMERGENTOLOGIA("EMERGENTOLOGIA"),
    ENDOCRINOLOGIA("ENDOCRINOLOGIA"),
    ENDOCRINOLOGIA_INFANTIL("ENDOCRINOLOGIA INFANTIL"),
    FARMACOLOGIA_CLINICA("FARMACOLOGIA CLINICA"),
    FISIATRA("FISIATRA"),
    GASTROENTEROLOGIA("GASTROENTEROLOGIA"),
    GASTROENTEROLOGIA_INFANTIL("GASTROENTEROLOGIA INFANTIL"),
    GENETICA_MEDICA("GENETICA MEDICA"),
    GERIATRIA("GERIATRIA"),
    GINECOLOGIA("GINECOLOGIA"),
    HEMATOLOGIA("HEMATOLOGIA"),
    HEMATO_ONCOLOGIA_PEDIATRICA("HEMATO-ONCOLOGIA PEDIATRICA"),
    HEMOTERAPIA_E_INMUNOHEMATOLOGIA("HEMOTERAPIA E INMUNOHEMATOLOGIA"),
    HEMATOLOGIA_PEDIATRICA("HEMATOLOGIA PEDIATRICA"),
    HEPATOLOGIA("HEPATOLOGIA"),
    INFECTOLOGIA("INFECTOLOGIA"),
    INFECTOLOGIA_INFANTIL("INFECTOLOGIA INFANTIL"),
    MEDICINA_DEL_DEPORTE("MEDICINA DEL DEPORTE"),
    MEDICINA_DEL_TRABAJO("MEDICINA DEL TRABAJO"),
    MEDICINA_GRAL_MEDICINA_DE_FAMILIA("MEDICINA GRAL Y/O MEDICINA DE FAMILIA"),
    MEDICINA_LEGAL("MEDICINA LEGAL"),
    MEDICINA_NUCLEAR("MEDICINA NUCLEAR"),
    MEDICINA_PALIATIVA("MEDICINA PALIATIVA"),
    NEFROLOGIA("NEFROLOGIA"),
    NEFROLOGIA_INFANTIL("NEFROLOGIA INFANTIL"),
    NEONATOLOGIA("NEONATOLOGIA"),
    NEUMONOLOGIA("NEUMONOLOGIA"),
    NEUMONOLOGIA_INFANTIL("NEUMONOLOGIA INFANTIL"),
    NEUROCIRUGIA("NEUROCIRUGIA"),
    NEUROLOGIA("NEUROLOGIA"),
    NEUROLOGIA_INFANTIL("NEUROLOGIA INFANTIL"),
    NURICION("NUTRICION"),
    OBSTETRICIA("OBSTETRICIA"),
    OFTALMOLOGIA("OFTALMOLOGIA"),
    ONCOLOGIA("ONCOLOGIA"),
    ORTOPEDIA_Y_TRAUTAMOLOGIA_INFANTIL("ORTOPEDIA Y TRAMATOLOGIA INFANTIL"),
    ORTOPEDIA_Y_TRAUMATOLOGIA("ORTOPEDIA Y TRAUMATOLOGIA"),
    OTORRNOLARINGOLOGIA("OTORRINOLARINGOLOGIA"),
    PEDIATRIA("PEDIATRIA"),
    PSIQUIATRIA_IFANTO_JUVENIL("PSIQUIATRIA INFANTO JUVENIL"),
    PSIQUIATRIA("PSIQUIATRIA"),
    RADIOTERAPIA("RADIOTERAPIA"),
    REUMATOLOGIA("REUMATOLOGIA"),
    REUMATOLOGIA_INFANTIL("REUMATOLOGIA INFANTL"),
    TERAPIA_INTENSIVA("TERAPIA INTENSIVA"),
    TERAPIA_INTENSIVA_INFANTIL("TERAPIA INTENSIVA INFANTIL"),
    TOCOGINECOLOGIA("TOCOGINECOLOGIA"),
    TOXICOLOGIA("TOXICOLOGIA"),
    UROLOGIA("UROLOGIA");


    private final String value;

    Speciality(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
