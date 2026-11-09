package com.nutricional.tabela_nutricional.business;

import com.nutricional.tabela_nutricional.infraestrutura.entidades.Alimento;
import com.nutricional.tabela_nutricional.infraestrutura.repositorio.AlimentoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlimentoService {

    private final AlimentoRepositorio repository;

    public AlimentoService(AlimentoRepositorio repository) {
        this.repository = repository;
    }

    public void salvarAlimento(Alimento alimento) {
        alimento.setId(null);
        repository.save(alimento);
    }

    public List<Alimento> buscarTodosAlimentos() {
        return repository.findAll();
    }

    public Alimento buscarAlimentoPorAlimento(String alimento) {
        return repository.findByAlimento(alimento).orElseThrow(
                () -> new RuntimeException("Alimento não encontrado")
        );
    }

    public Alimento buscarAlimentoPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Alimento não encontrado")
        );
    }

    @Transactional
    public void deletarAlimentoPorId(Integer id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deletarAlimentoPorAlimento(String alimento) {
        repository.deleteByAlimento(alimento);
    }

    public void atualizarAlimentoPorId(Integer id, Alimento alimentoAtualizacao) {
        Alimento alimentoEntity = buscarAlimentoPorId(id);

        if (alimentoAtualizacao.getAlimento() != null) {
            alimentoEntity.setAlimento(alimentoAtualizacao.getAlimento());
        }
        if (alimentoAtualizacao.getCalorias() != null) {
            alimentoEntity.setCalorias(alimentoAtualizacao.getCalorias());
        }
        if (alimentoAtualizacao.getNatural() != null) {
            alimentoEntity.setNatural(alimentoAtualizacao.getNatural());
        }

        repository.save(alimentoEntity);
    }
}