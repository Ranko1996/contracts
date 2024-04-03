package com.contracts.contracts.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String kupac;
    private String brojUgovora;
    private LocalDate datumAkontacije;
    private LocalDate rokIsporuke;
    private String status;

//    @OneToMany(mappedBy = "facilityId", cascade = CascadeType.ALL)
//    private List<Animal> animals;

    @OneToMany(mappedBy = "contractId", cascade = CascadeType.ALL)
    private Set<Product> proizvodi;
}
