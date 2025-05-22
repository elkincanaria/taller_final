package com.example.cuentasservice.service;


import com.example.cuentasservice.dto.BancoDTO;
import com.example.cuentasservice.dto.CuentaDTO;
import com.example.cuentasservice.model.Cuenta;
import com.example.cuentasservice.repository.ICuentaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CuentaService {

    private final ICuentaRepository cuentaRepository;
    private final GetBanco getBanco;

    public CuentaService(ICuentaRepository cuentaRepository, GetBanco getBanco) {
        this.cuentaRepository = cuentaRepository;
        this.getBanco = getBanco;
    }

    public Flux<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    public Mono<Cuenta> getById(Long cuentaId) {
        return cuentaRepository.findById(cuentaId);
    }

    public Mono<Cuenta> create(CuentaDTO cuentaDTO) {
        return getBanco.getBanco(cuentaDTO.getBancoId())
                .flatMap(banco -> {
                    Cuenta cuenta = new Cuenta(
                            null,
                            cuentaDTO.getBancoId(),
                            cuentaDTO.getTipoCuenta(),
                            cuentaDTO.getSaldo(),
                            cuentaDTO.getSaldoSobregiro(),
                            cuentaDTO.getEstado()
                    );
                    return cuentaRepository.save(cuenta);
                });
    }

    public Mono<Cuenta> update(Cuenta cuenta) {
        return cuentaRepository.findById(cuenta.getNumeroCuenta())
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")))
                .flatMap(existing -> cuentaRepository.save(cuenta));
    }
}
