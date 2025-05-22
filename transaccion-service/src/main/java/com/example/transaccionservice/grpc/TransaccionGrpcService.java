package com.example.transaccionservice.grpc;

import com.example.transaccionservice.model.Transaccion;
import com.example.transaccionservice.repository.ITransaccionRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;

@GrpcService
public class TransaccionGrpcService extends TransaccionServiceGrpc.TransaccionServiceImplBase {

    private final ITransaccionRepository ITransaccionRepository;

    public TransaccionGrpcService(ITransaccionRepository ITransaccionRepository) {
        this.ITransaccionRepository = ITransaccionRepository;
    }

    @Override
    public void getHistorialPorCuenta(CuentaRequest request, StreamObserver<HistorialResponse> responseObserver) {
        Flux<Transaccion> transacciones = ITransaccionRepository.findAll()
                .filter(t -> request.getCuentaId() == t.getCuentaOrigen() || request.getCuentaId() == t.getCuentaDestino());

        transacciones.collectList().map(lista -> {
            HistorialResponse.Builder responseBuilder = HistorialResponse.newBuilder();
            for (Transaccion t : lista) {
                responseBuilder.addTransacciones(TransaccionItem.newBuilder()
                        .setNumeroRastreo(t.getNumeroRastreo())
                        .setTipoTransaccion(t.getTipoTransaccion())
                        .setMonto(t.getMonto().doubleValue())
                        .setFecha(t.getFecha().toString())
                        .build());
            }
            return responseBuilder.build();
        }).subscribe(response -> {
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, error -> {
            responseObserver.onError(error);
        });
    }
}
