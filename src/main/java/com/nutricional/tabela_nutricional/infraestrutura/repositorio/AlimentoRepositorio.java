package com.nutricional.tabela_nutricional.infraestrutura.repositorio;

import com.nutricional.tabela_nutricional.infraestrutura.entidades.Alimento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlimentoRepositorio extends JpaRepository<Alimento, Integer> {

    Optional<Alimento> findByAlimento(String alimento);

    @Transactional
    void deleteByAlimento(String alimento);
}