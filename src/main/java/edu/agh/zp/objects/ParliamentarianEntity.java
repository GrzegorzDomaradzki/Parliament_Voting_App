package edu.agh.zp.objects;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "Parliamentarian")

public class ParliamentarianEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "parliamentarianID")
    private long parliamentarianID;

    @NotNull
    @UniqueElements
    @Column(name="idCardNumber")
    private String idCardNumber;

    @NotNull
    @Column(name="politicalGroup")
    private String politicalGroup;

    @NotNull
    @Column(name="chamberOfDeputies")
    private String chamberOfDeputies;

    @OneToOne
    @NotNull
    @JoinColumn(name="politicianID")
    private PoliticianEntity politicianID;


}
