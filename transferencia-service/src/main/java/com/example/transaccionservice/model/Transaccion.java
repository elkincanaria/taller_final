package com.example.transaccionservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("transacciones")
public class Transaccion {

  @Id
  private Long id;

  @Column("numero_rastreo")
  private String numeroRastreo;

  @Column("tipo_cta_or")
  private String tipoCuentaOrigen;

  @Column("banco_or")
  private String bancoOrigen;

  @Column("cuenta_or")
  private Long cuentaOrigen;

  @Column("tipo_cta_des")
  private String tipoCuentaDestino;

  @Column("banco_des")
  private String bancoDestino;

  @Column("cuenta_des")
  private Long cuentaDestino;

  @Column("tipo_trx")
  private String tipoTransaccion; // "D" o "C"

  private BigDecimal monto;

  private LocalDateTime fecha;

  // Getters y setters (puedes usar Lombok para reducir código)
  // Constructor vacío
  public Transaccion() {}

  // Constructor completo
  public Transaccion(Long id, String numeroRastreo, String tipoCuentaOrigen, String bancoOrigen, Long cuentaOrigen,
                     String tipoCuentaDestino, String bancoDestino, Long cuentaDestino,
                     String tipoTransaccion, BigDecimal monto, LocalDateTime fecha) {
    this.id = id;
    this.numeroRastreo = numeroRastreo;
    this.tipoCuentaOrigen = tipoCuentaOrigen;
    this.bancoOrigen = bancoOrigen;
    this.cuentaOrigen = cuentaOrigen;
    this.tipoCuentaDestino = tipoCuentaDestino;
    this.bancoDestino = bancoDestino;
    this.cuentaDestino = cuentaDestino;
    this.tipoTransaccion = tipoTransaccion;
    this.monto = monto;
    this.fecha = fecha;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumeroRastreo() {
    return numeroRastreo;
  }

  public void setNumeroRastreo(String numeroRastreo) {
    this.numeroRastreo = numeroRastreo;
  }

  public String getTipoCuentaOrigen() {
    return tipoCuentaOrigen;
  }

  public void setTipoCuentaOrigen(String tipoCuentaOrigen) {
    this.tipoCuentaOrigen = tipoCuentaOrigen;
  }

  public String getBancoOrigen() {
    return bancoOrigen;
  }

  public void setBancoOrigen(String bancoOrigen) {
    this.bancoOrigen = bancoOrigen;
  }

  public Long getCuentaOrigen() {
    return cuentaOrigen;
  }

  public void setCuentaOrigen(Long cuentaOrigen) {
    this.cuentaOrigen = cuentaOrigen;
  }

  public String getTipoCuentaDestino() {
    return tipoCuentaDestino;
  }

  public void setTipoCuentaDestino(String tipoCuentaDestino) {
    this.tipoCuentaDestino = tipoCuentaDestino;
  }

  public String getBancoDestino() {
    return bancoDestino;
  }

  public void setBancoDestino(String bancoDestino) {
    this.bancoDestino = bancoDestino;
  }

  public Long getCuentaDestino() {
    return cuentaDestino;
  }

  public void setCuentaDestino(Long cuentaDestino) {
    this.cuentaDestino = cuentaDestino;
  }

  public String getTipoTransaccion() {
    return tipoTransaccion;
  }

  public void setTipoTransaccion(String tipoTransaccion) {
    this.tipoTransaccion = tipoTransaccion;
  }

  public BigDecimal getMonto() {
    return monto;
  }

  public void setMonto(BigDecimal monto) {
    this.monto = monto;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public void setFecha(LocalDateTime fecha) {
    this.fecha = fecha;
  }
}