package com.example.transferenciaservice.mensajeria;

import com.example.transaccionservice.dto.TransaccionDTO;
import com.example.transaccionservice.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransferenciasListener {

    @RabbitListener(queues = RabbitConfig.TRANSFERENCIAS_QUEUE)
    public void recibirTransaccion(TransaccionDTO deposito) {
        // Aquí procesas el depósito interbancario: agregar impuesto, guardar en BD, etc.
        System.out.println("Recibido depósito interbancario: " + deposito);
    }
}
