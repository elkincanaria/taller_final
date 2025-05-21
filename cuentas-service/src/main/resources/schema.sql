CREATE TABLE IF NOT EXISTS cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(12) NOT NULL UNIQUE,
    banco_id BIGINT NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo DECIMAL(12, 4) NOT NULL DEFAULT 0.00,
    saldoSobregiro DECIMAL(12, 4) NOT NULL DEFAULT 0.00,
    estado VARCHAR(2) NOT NULL
);

INSERT INTO cuentas (numero_cuenta, banco_id, tipo_cuenta, saldo, saldoSobregiro, estado) VALUES ('100000000001', 1, 'Ahorros', 1500.00, 0.00, ' A')

INSERT INTO cuentas (numero_cuenta, banco_id, tipo_cuenta, saldo, saldoSobregiro, estado) VALUES ('100000000002', 2, 'Corriente', 500.00, 200.00, ' A')

INSERT INTO cuentas (numero_cuenta, banco_id, tipo_cuenta, saldo, saldoSobregiro, estado) VALUES ('100000000003', 1, 'Ahorros', 250.75, 0.00, ' I')

INSERT INTO cuentas (numero_cuenta, banco_id, tipo_cuenta, saldo, saldoSobregiro, estado) VALUES ('100000000004', 3, 'Corriente', 10000.00, 1000.00, ' A')

INSERT INTO cuentas (numero_cuenta, banco_id, tipo_cuenta, saldo, saldoSobregiro, estado) VALUES ('100000000005', 2, 'Ahorros', 300.00, 0.00, ' C')

INSERT INTO cuentas (numero_cuenta, banco_id, tipo_cuenta, saldo, saldoSobregiro, estado) VALUES ('100000000006', 3, 'Corriente', 0.00, 500.00, ' E')