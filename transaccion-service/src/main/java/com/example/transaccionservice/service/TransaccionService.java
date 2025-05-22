package com.example.transaccionservice.service;

import com.example.transaccionservice.dto.CuentaDTO;
import com.example.transaccionservice.dto.TransaccionDTO;
import com.example.transaccionservice.event.EventoTransferencia;
import com.example.transaccionservice.model.Transaccion;
import com.example.transaccionservice.repository.ITransaccionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransaccionService {

    private final ITransaccionRepository transactionRepository;
    private final CuentaClienteService cuentaclienteservice;
    private final EventoTransferencia transferEventPublisher;

    // Constructor explícito (sustituye @RequiredArgsConstructor)
    public TransaccionService(ITransaccionRepository transactionRepository,
                              CuentaClienteService cuentaclienteservice,
                              EventoTransferencia transferEventPublisher) {
        this.transactionRepository = transactionRepository;
        this.cuentaclienteservice = cuentaclienteservice;
        this.transferEventPublisher = transferEventPublisher;
    }

    public Flux<Transaccion> getAll() {
        return transactionRepository.findAll();
    }

    public Mono<Transaccion> create(Transaccion transaction) {
        transaction.setId(null);
        return transactionRepository.save(transaction);
    }

    public Mono<Void> makeTransaccion(TransaccionDTO transfer) {
        BigDecimal monto = transfer.getMonto();

        return cuentaclienteservice.getCuenta(transfer.getCuentaOrigen())
                .zipWith(cuentaclienteservice.getCuenta(transfer.getCuentaDestino()))
                .flatMap(tuple -> {
                    CuentaDTO origen = tuple.getT1();
                    CuentaDTO destino = tuple.getT2();

                    if (origen.getSaldo().compareTo(monto) < 0) {
                        return Mono.error(new RuntimeException("Saldo insuficiente para efectuar la transaccion"));
                    }

                    origen.setSaldo(origen.getSaldo().subtract(monto));
                    Transaccion withdrawal = new Transaccion(
                            null,
                            "RETIRO",
                            origen.getNumeroCuenta(),
                            monto,
                            "Transferir a " + destino.getNumeroCuenta(),
                            LocalDateTime.now()
                    );

                    if (origen.getBancoId().equals(destino.getBancoId())) {
                        destino.setSaldo(destino.getSaldo().add(monto));
                        Transaccion deposit = new Transaccion(
                                null,
                                "DEPOSITO",
                                destino.getNumeroCuenta(),
                                monto,
                                "Transfer from " + origen.getNumeroCuenta(),
                                LocalDateTime.now()
                        );

                        return Mono.when(
                                cuentaclienteservice.updateCuenta(origen),
                                transactionRepository.save(withdrawal),
                                cuentaclienteservice.updateCuenta(destino),
                                transactionRepository.save(deposit)
                        ).then();
                    } else {
                        Transaccion deposit = new Transaccion(
                                null,
                                "DEPOSITO",
                                destino.getNumeroCuenta(),
                                monto,
                                "Interbank transfer from " + origen.getNumeroCuenta(),
                                LocalDateTime.now()
                        );

                        return Mono.when(
                                cuentaclienteservice.updateCuenta(origen),
                                transactionRepository.save(withdrawal),
                                Mono.fromRunnable(() -> transferEventPublisher.publishPurchaseMadeEvent(deposit))
                        ).then();
                    }
                });
    }
}
