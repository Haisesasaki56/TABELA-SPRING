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

    // Corrigido: Busca pelo NOME do alimento (String) para bater com o AlimentoService
    @GetMapping
    public ResponseEntity<Alimento> buscarAlimentoPorAlimento(@RequestParam String alimento) {
        return ResponseEntity.ok(alimentoService.buscarAlimentoPorAlimento(alimento));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAlimentoPorAlimento(@RequestParam String alimento) {
        alimentoService.deletarAlimentoPorAlimento(alimento);
        return ResponseEntity.ok().build();
    }

    // Mantido: Alteração pelo ID (Integer) do alimento
    @PutMapping
    public ResponseEntity<Void> atualizarAlimentoPorId(@RequestParam Integer id,
                                                       @RequestBody Alimento alimento) {
        alimentoService.atualizarAlimentoPorId(id, alimento);
        return ResponseEntity.ok().build();
    }
}