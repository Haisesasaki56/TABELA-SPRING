package com.nutricional.tabela_nutricional.controller;

import com.nutricional.tabela_nutricional.business.AlimentoService;
import com.nutricional.tabela_nutricional.infraestrutura.entidades.Alimento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alimentos")
@RequiredArgsConstructor
public class AlimentoController {

    private final AlimentoService alimentoService;

    @PostMapping
    public ResponseEntity<Void> salvarAlimento(@RequestBody Alimento alimento) {
        alimentoService.salvarAlimento(alimento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Alimento>> buscarTodosAlimentos() {
        return ResponseEntity.ok(alimentoService.buscarTodosAlimentos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Alimento> buscarAlimentoPorAlimento(@RequestParam String alimento) {
        return ResponseEntity.ok(alimentoService.buscarAlimentoPorAlimento(alimento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAlimentoPorId(@PathVariable Integer id,
                                                       @RequestBody Alimento alimento) {
        alimentoService.atualizarAlimentoPorId(id, alimento);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAlimentoPorId(@PathVariable Integer id) {
        alimentoService.deletarAlimentoPorId(id);
        return ResponseEntity.noContent().build();
    }
}