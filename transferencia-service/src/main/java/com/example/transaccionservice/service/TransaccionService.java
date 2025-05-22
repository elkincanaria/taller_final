package com.example.transaccionservice.service;

import com.example.transaccionservice.dto.TransaccionDTO;
import com.example.transaccionservice.model.Transaccion;
import com.example.transaccionservice.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final WebClient webClient;
    private final MessageSender messageSender;

    public TransaccionService(TransaccionRepository transaccionRepository, WebClient.Builder webClientBuilder, MessageSender messageSender) {
        this.transaccionRepository = transaccionRepository;
        this.webClient = webClientBuilder.baseUrl("http://cuenta-service").build();
        this.messageSender = messageSender;
    }

    public TransaccionService(TransaccionRepository transaccionRepository, WebClient.Builder webClientBuilder) {
        this.transaccionRepository = transaccionRepository;
        this.webClient = webClientBuilder.baseUrl("http://cuenta-service").build();
    }

    public Mono<Transaccion> create(TransaccionDTO dto) {
        return validarCuentas(dto)
                .flatMap(valid -> {
                    if (!valid) return Mono.error(new RuntimeException("Cuenta origen o destino no válida"));

                    return validarSaldoSuficiente(dto.getCuentaOrigen(), dto.getMonto())
                            .flatMap(saldoOk -> {
                                if (!saldoOk) return Mono.error(new RuntimeException("Fondos insuficientes en cuenta origen"));

                                // Crear transacción de retiro
                                Transaccion retiro = new Transaccion();
                                retiro.setNumeroRastreo(UUID.randomUUID().toString());
                                retiro.setTipoCuentaOrigen(dto.getTipoCuentaOrigen());
                                retiro.setBancoOrigen(dto.getBancoOrigen());
                                retiro.setCuentaOrigen(dto.getCuentaOrigen());
                                retiro.setTipoCuentaDestino(dto.getTipoCuentaDestino());
                                retiro.setBancoDestino(dto.getBancoDestino());
                                retiro.setCuentaDestino(dto.getCuentaDestino());
                                retiro.setTipoTransaccion("D");
                                retiro.setMonto(dto.getMonto());
                                retiro.setFecha(LocalDateTime.now());

                                if (dto.getBancoOrigen().equals(dto.getBancoDestino())) {
                                    // Mismo banco → retiro y depósito
                                    Transaccion deposito = new Transaccion();
                                    deposito.setNumeroRastreo(retiro.getNumeroRastreo());
                                    deposito.setTipoCuentaOrigen(dto.getTipoCuentaOrigen());
                                    deposito.setBancoOrigen(dto.getBancoOrigen());
                                    deposito.setCuentaOrigen(dto.getCuentaOrigen());
                                    deposito.setTipoCuentaDestino(dto.getTipoCuentaDestino());
                                    deposito.setBancoDestino(dto.getBancoDestino());
                                    deposito.setCuentaDestino(dto.getCuentaDestino());
                                    deposito.setTipoTransaccion("C");
                                    deposito.setMonto(dto.getMonto());
                                    deposito.setFecha(LocalDateTime.now());

                                    return transaccionRepository.saveAll(Flux.just(retiro, deposito))
                                            .then(Mono.just(retiro));
                                } else {
                                    // Bancos diferentes → solo retiro, luego encolar depósito
                                    return transaccionRepository.save(retiro)
                                            .doOnSuccess(saved -> {
                                                TransaccionDTO depositoMsg = new TransaccionDTO();
                                                depositoMsg.setTipoCuentaOrigen(dto.getTipoCuentaOrigen());
                                                depositoMsg.setBancoOrigen(dto.getBancoOrigen());
                                                depositoMsg.setCuentaOrigen(dto.getCuentaOrigen());
                                                depositoMsg.setTipoCuentaDestino(dto.getTipoCuentaDestino());
                                                depositoMsg.setBancoDestino(dto.getBancoDestino());
                                                depositoMsg.setCuentaDestino(dto.getCuentaDestino());
                                                depositoMsg.setTipoTransaccion("C"); // Crédito
                                                depositoMsg.setMonto(dto.getMonto());

                                                messageSender.sendTransaccionInterbancaria(depositoMsg);
                                            });
                                }
                            });
                });
    }

    private Mono<Boolean> validarCuentas(TransaccionDTO dto) {
        Mono<Boolean> origenValida = webClient.get()
                .uri("/api/cuentas/{id}", dto.getCuentaOrigen())
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorResume(e -> Mono.just(false));

        Mono<Boolean> destinoValida = webClient.get()
                .uri("/api/cuentas/{id}", dto.getCuentaDestino())
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorResume(e -> Mono.just(false));

        return Mono.zip(origenValida, destinoValida)
                .map(tuple -> tuple.getT1() && tuple.getT2());
    }

    private Mono<Boolean> validarSaldoSuficiente(Long cuentaOrigen, BigDecimal montoSolicitado) {
        return transaccionRepository.findAll()
                .filter(t -> cuentaOrigen.equals(t.getCuentaOrigen()) || cuentaOrigen.equals(t.getCuentaDestino()))
                .collectList()
                .map(transacciones -> {
                    BigDecimal saldo = BigDecimal.ZERO;
                    for (Transaccion t : transacciones) {
                        if (cuentaOrigen.equals(t.getCuentaDestino()) && "C".equals(t.getTipoTransaccion())) {
                            saldo = saldo.add(t.getMonto());
                        } else if (cuentaOrigen.equals(t.getCuentaOrigen()) && "D".equals(t.getTipoTransaccion())) {
                            saldo = saldo.subtract(t.getMonto());
                        }
                    }
                    return saldo.compareTo(montoSolicitado) >= 0;
                });
    }

    public Mono<Transaccion> getById(Long id) {
        return transaccionRepository.findById(id);
    }

    public Flux<Transaccion> getAll() {
        return transaccionRepository.findAll();
    }

    public Mono<Transaccion> update(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }
}
