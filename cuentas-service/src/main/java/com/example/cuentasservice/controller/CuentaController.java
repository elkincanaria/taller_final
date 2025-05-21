package com.example.cuentasservice.controller;

import com.example.cuentasservice.model.Cuenta;
import com.example.cuentasservice.service.CuentasService;
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
@RequestMapping("/api/cuentas")
public class CuentaController {
  private final CuentaService cuentaService;

  @GetMapping
  public Flux<Cuenta> getAll() {
    return cuentaService.getAll();
  }

  @GetMapping("/{cuentaId}")
  public Mono<Cuenta> getById(@PathVariable Long cuentaId) {
    return cuentaService.getById(cuentaId);
  }

  @PostMapping
  public Mono<Cuentas> create(@RequestBody CuentaDTO cuentaDTO) {
    return cuentaService.create(cuentaDTO);
  }
  @PutMapping
  public Mono<Cuenta> update(@RequestBody Cuenta cuenta) {
    return cuentaService.update(cuenta);
  }

}
