package com.example.transaccionservice.dto;

import java.math.BigDecimal;

public class TransaccionDTO {

  private String tipoCuentaOrigen;
  private String bancoOrigen;
  private Long cuentaOrigen;
  private String tipoCuentaDestino;
  private String bancoDestino;
  private Long cuentaDestino;
  private String tipoTransaccion; // "D" o "C"
  private BigDecimal monto;

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
}
