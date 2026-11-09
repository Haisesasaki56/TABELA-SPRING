package com.nutricional.tabela_nutricional.infraestrutura.repositorio;

import com.nutricional.tabela_nutricional.infraestrutura.entidades.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlimentoRepositorio extends JpaRepository<Alimento, Integer> {
    Optional<Alimento> findByAlimento(String alimento);
    void deleteByAlimento(String alimento);
}