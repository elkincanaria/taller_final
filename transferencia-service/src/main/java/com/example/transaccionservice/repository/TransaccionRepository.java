package com.example.transaccionservice.repository;

import com.example.transaccionservice.model.Transaccion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends ReactiveCrudRepository<Transaccion, Long> {
}
