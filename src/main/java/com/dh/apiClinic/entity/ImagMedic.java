package com.dh.apiClinic.entity;
import lombok.Data;
import javax.persistence.*;


@Data
@Entity
@Table(name = "firm_medic")
public class ImagMedic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "medic_id")
    private Medic medic;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;


}
