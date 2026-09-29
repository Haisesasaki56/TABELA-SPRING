package com.nutricional.tabela_nutricional.infraestrutura.repositorio;

// Importações adicionadas para evitar erros de compilação
import com.nutricional.tabela_nutricional.infraestrutura.entidades.Alimento;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepositorio extends JpaRepository<Alimento, Integer> {

    Optional<Alimento> findByAlimento(String alimento);

    @Transactional
    void deleteByAlimento(String alimento);
}