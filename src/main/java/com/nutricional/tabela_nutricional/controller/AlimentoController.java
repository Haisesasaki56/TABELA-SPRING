package com.nutricional.tabela_nutricional.controller;

import com.nutricional.tabela_nutricional.business.AlimentoService;
import com.nutricional.tabela_nutricional.infraestrutura.entidades.Alimento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alimento")
@RequiredArgsConstructor
public class AlimentoController {

    private final AlimentoService alimentoService;

    @PostMapping
    public ResponseEntity<Void> salvarAlimento(@RequestBody Alimento alimento) {
        alimentoService.salvarAlimento(alimento);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Alimento> buscarAlimentoPorAlimento(@RequestParam String alimento) {
        return ResponseEntity.ok(alimentoService.buscarAlimentoPorAlimento(alimento));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAlimentoPorAlimento(@RequestParam String alimento) {
        alimentoService.deletarAlimentoPorAlimento(alimento);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarAlimentoporAlimento(@RequestParam String alimento,
                                                             @RequestBody Alimento alimentoAtualizacao) {
        alimentoService.atualizarAlimentoporAlimento(alimento, alimentoAtualizacao);
        return ResponseEntity.ok().build();
    }
}