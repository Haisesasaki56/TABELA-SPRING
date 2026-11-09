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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alimento")
    private String alimento;

    @Column(name = "calorias")
    private Double calorias;

    @Column(name = "eh-natural")
    private Boolean natural;
}