package com.nutricional.tabela_nutricional.business;

import com.nutricional.tabela_nutricional.infraestrutura.entidades.Alimento;
import com.nutricional.tabela_nutricional.infraestrutura.repositorio.AlimentoRepositorio;
import org.springframework.stereotype.Service;

@Service
public class AlimentoService {
    private final AlimentoRepositorio repository;

    public AlimentoService(AlimentoRepositorio repository) {
        this.repository = repository;
    }

    public void salvarAlimento(Alimento alimento){
        repository.saveAndFlush(alimento);
    }

    // Busca por NOME (String) - Usado no GET
    public Alimento buscarAlimentoPorAlimento(String alimento){
        return repository.findByAlimento(alimento).orElseThrow(
                () -> new RuntimeException("Alimento não encontrado")
        );
    }

    // Busca por ID (Integer) - Usado internamente no PUT
    public Alimento buscarAlimentoPorId(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Alimento não encontrado")
        );
    }

    public void deletarAlimentoPorAlimento(String alimento){
        repository.deleteByAlimento(alimento);
    }

    // Altera por ID (Integer) - Usado no PUT
    public void atualizarAlimentoPorId(Integer id, Alimento alimentoAtualizacao){
        Alimento alimentoEntity = buscarAlimentoPorId(id);

        Alimento alimentoAtualizado = Alimento.builder()
                .id(alimentoEntity.getId())
                .alimento(alimentoAtualizacao.getAlimento() != null ? alimentoAtualizacao.getAlimento() : alimentoEntity.getAlimento())
                .calorias(alimentoAtualizacao.getCalorias() != null ? alimentoAtualizacao.getCalorias() : alimentoEntity.getCalorias())
                .natural(alimentoAtualizacao.getNatural() != null ? alimentoAtualizacao.getNatural() : alimentoEntity.getNatural())
                .build();

        repository.saveAndFlush(alimentoAtualizado);
    }
}