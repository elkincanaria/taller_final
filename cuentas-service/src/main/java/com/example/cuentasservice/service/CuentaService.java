package com.example.cuentasservice.service;

package com.example.cuentasservice.service;

import com.example.cuentasservice.dto.BancoDTO;
import com.example.cuentasservice.dto.CuentaDTO;
import com.example.cuentasservice.model.Cuentas;
import com.example.cuentasservice.repository.ICuentasRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CuentasService {

    private final ICuentasRepository cuentasRepository;
    private final GetBanco getBanco;

    public CuentasService(ICuentasRepository cuentasRepository, GetBanco getBanco) {
        this.cuentasRepository = cuentasRepository;
        this.getBanco = getBanco;
    }

    public Flux<Cuentas> getAll() {
        return cuentasRepository.findAll();
    }

    public Mono<Cuentas> getById(Long cuentaId) {
        return cuentasRepository.findById(cuentaId);
    }

    public Mono<Cuentas> create(CuentaDTO cuentaDTO) {
        return getBanco.getBanco(cuentaDTO.getBancoId())
                .flatMap(banco -> {
                    Cuentas cuenta = new Cuentas(
                            null,
                            cuentaDTO.getNumeroCuenta(),
                            cuentaDTO.getBancoId(),
                            cuentaDTO.getTipoCuenta(),
                            cuentaDTO.getSaldo(),
                            cuentaDTO.getSaldoSobregiro(),
                            cuentaDTO.getEstado()
                    );
                    return cuentasRepository.save(cuenta);
                });
    }

    public Mono<Cuentas> update(Cuentas cuenta) {
        return cuentasRepository.findById(cuenta.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")))
                .flatMap(existing -> cuentasRepository.save(cuenta));
    }
}
