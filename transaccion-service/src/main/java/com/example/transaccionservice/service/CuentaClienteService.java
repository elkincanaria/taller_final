package com.example.transaccionservice.service;

import com.example.transaccionservice.dto.CuentaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CuentaClienteService {

    private final WebClient.Builder webClientBuilder;

    @Value("${account.service.url}")
    private String accountsServiceUrl;

    public CuentaClienteService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<CuentaDTO> getCuenta(Long cuenta) {
        return webClientBuilder
                .build()
                .get()
                .uri(accountsServiceUrl + "/api/cuentas/" + cuenta)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(new RuntimeException("Cuenta no existe")))
                .bodyToMono(CuentaDTO.class);
    }

    public Mono<CuentaDTO> updateCuenta(CuentaDTO cuenta) {
        return webClientBuilder
                .build()
                .put()
                .uri(accountsServiceUrl + "/api/cuentas")
                .bodyValue(cuenta)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> Mono.error(new RuntimeException("cuenta no existe")))
                .bodyToMono(CuentaDTO.class);
    }
}
