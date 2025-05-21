CREATE TABLE IF NOT EXISTS bancos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    country VARCHAR(50) NOT NULL,
);

INSERT INTO banks(name, description) VALUES ('Bancolombia','Somos un grupo financiero líder, con más de 146 años de experiencia y que evoluciona para asumir los desafíos que trae un entorno en constante transformación.', 'Colombia');
INSERT INTO banks(name, description) VALUES ('NU','Banco NU', 'Colombia');
INSERT INTO banks(name, description) VALUES ('Occidente','Banco de occidente', 'Colombia');
INSERT INTO banks(name, description) VALUES ('BBVA','Banco BBVA', 'España');
INSERT INTO banks(name, description) VALUES ('Pichincha','Banco pichincha' 'Ecuador');
INSERT INTO banks(name, description) VALUES ('CityBank','Banco de Nueva York' 'Estados Unidos');