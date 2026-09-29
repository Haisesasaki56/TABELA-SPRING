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
        repository.saveAndFlush(alimento); // Ajustado de saveAllAndFlush igual ao vídeo
    }

    public Alimento buscarAlimentoPorAlimento(String alimento){
        return repository.findByAlimento(alimento).orElseThrow(
                () -> new RuntimeException("Alimento não encontrado")
        );
    }

    public void deletarAlimentoPorAlimento(String alimento){
        repository.deleteByAlimento(alimento);
    }

    // Adaptado exatamente como no vídeo: (Busca, Objeto com novos dados)
    public void atualizarAlimentoporAlimento(String alimento, Alimento alimentoAtualizacao){

        // 1. Busca a entidade
        Alimento alimentoEntity = buscarAlimentoPorAlimento(alimento);

        // 2. Monta o objeto atualizado usando Builder e operador ternário, igual ao vídeo
        Alimento alimentoAtualizado = Alimento.builder()
                .alimento(alimentoAtualizacao.getAlimento() != null ? alimentoAtualizacao.getAlimento() : alimentoEntity.getAlimento())
                .calorias(alimentoAtualizacao.getCalorias() != null ? alimentoAtualizacao.getCalorias() : alimentoEntity.getCalorias())
                .natural(alimentoAtualizacao.getNatural() != null ? alimentoAtualizacao.getNatural() : alimentoEntity.getNatural())
                .id(alimentoEntity.getId())
                .build();

        // 3. Salva
        repository.saveAndFlush(alimentoAtualizado);
    }
}