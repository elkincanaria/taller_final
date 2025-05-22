package com.example.transaccionservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("transacciones")
public class Transaccion {

  @Id
  private Long id;
  private String type;
  private Long accountId;
  private BigDecimal amount;
  private String description;
  private LocalDateTime timestamp;

  // Constructor sin argumentos
  public Transaccion() {
  }

  // Constructor con todos los campos
  public Transaccion(Long id, String type, Long accountId, BigDecimal amount, String description, LocalDateTime timestamp) {
    this.id = id;
    this.type = type;
    this.accountId = accountId;
    this.amount = amount;
    this.description = description;
    this.timestamp = timestamp;
  }

  // Getters y Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "Transaccion{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", accountId=" + accountId +
            ", amount=" + amount +
            ", description='" + description + '\'' +
            ", timestamp=" + timestamp +
            '}';
  }
}
