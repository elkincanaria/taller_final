package com.example.transaccionservice.event;

import com.example.transaccionservice.repository.ITransaccionRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.stream.Collectors;

@GrpcService
public class EventoTransferencia extends TransaccionServiceGrpc.TransaccionServiceImplBase {

    private final ITransaccionRepository transaccionRepository;

    // Constructor explícito (reemplaza @RequiredArgsConstructor)
    public EventoTransferencia(ITransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public void getTransactionsByAccountId(AccountRequest request, StreamObserver<TransactionsResponse> responseObserver) {
        TransactionsResponse response = TransactionsResponse.newBuilder()
                .addAllTransactions(
                        transaccionRepository.findByAccountId(request.getAccountId())
                                .map(transaction -> Transaction.newBuilder()
                                        .setId(transaction.getId())
                                        .setType(transaction.getType())
                                        .setAccountId(transaction.getAccountId())
                                        .setAmount(transaction.getAmount().doubleValue())
                                        .setDescription(transaction.getDescription())
                                        .setTimestamp(transaction.getTimestamp().toString())
                                        .build())
                                .collect(Collectors.toList())
                                .block()
                ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
