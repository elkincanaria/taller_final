package com.example.transaccionservice.controller;

import com.example.transaccionservice.dto.TransaccionDTO;
import com.example.transaccionservice.model.Transaccion;
import com.example.transaccionservice.service.TransaccionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transaccion")
public class TransaccionController {
  private final TransaccionService transaccionService;

  public TransaccionController(TransaccionService transaccionService) {
    this.transaccionService = transaccionService;
  }

  @GetMapping
  public Flux<Transaccion> getAll() {
    return transaccionService.getAll();
  }

  @GetMapping("/{transaccionId}")
  public Mono<Transaccion> getById(@PathVariable Long transaccionId) {
    return transaccionService.getById(transaccionId);
  }

  @PostMapping
  public Mono<Transaccion> create(@RequestBody TransaccionDTO transaccionDTO) {
    Transaccion transaccion = new Transaccion(null, transaccionDTO.getName(), transaccionDTO.getDescription(), transaccionDTO.getCountry());
    return transaccionService.create(transaccion);
  }

  @PutMapping
  public Mono<Transaccion> update(@RequestBody Transaccion transaccion) {
    return transaccionService.update(transaccion);
  }
}
