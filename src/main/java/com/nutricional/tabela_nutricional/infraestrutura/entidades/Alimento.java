package com.nutricional.tabela_nutricional.infraestrutura.entidades;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "alimento")
@Entity
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "alimento", unique = true)
    private String alimento;

    @Column(name = "calorias")
    private Double calorias;

    @Column (name = "natural")
    private Boolean natural;

}