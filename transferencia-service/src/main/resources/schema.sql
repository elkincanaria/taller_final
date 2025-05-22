CREATE TABLE IF NOT EXISTS transacciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_rastreo VARCHAR(36) NOT NULL UNIQUE,
    tipo_cuenta_origen VARCHAR(20) NOT NULL,
    banco_origen BIGINT NOT NULL,
    cuenta_origen VARCHAR(12) NOT NULL,
    tipo_cuenta_destino VARCHAR(20) NOT NULL,
    banco_destino BIGINT NOT NULL,
    cuenta_destino VARCHAR(12) NOT NULL,
    tipo_transaccion CHAR(1) NOT NULL, -- D: Depósito, C: Cargo
    monto DECIMAL(12, 4) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT
);