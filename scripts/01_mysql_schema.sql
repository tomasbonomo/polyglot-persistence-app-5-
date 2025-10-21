-- MySQL Schema para Persistencia Poliglota
-- Tablas ACID para datos críticos

CREATE DATABASE IF NOT EXISTS polyglot_db;
USE polyglot_db;

-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Procesos
CREATE TABLE IF NOT EXISTS procesos (
    id VARCHAR(36) PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descripcion TEXT,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_completacion DATETIME,
    resultado TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Facturas
CREATE TABLE IF NOT EXISTS facturas (
    id VARCHAR(36) PRIMARY KEY,
    usuario_id INT NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    descripcion TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Movimientos (Cuenta Corriente)
CREATE TABLE IF NOT EXISTS movimientos (
    id VARCHAR(36) PRIMARY KEY,
    cuenta_id INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas_corrientes(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Cuentas Corrientes
CREATE TABLE IF NOT EXISTS cuentas_corrientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT UNIQUE NOT NULL,
    saldo DECIMAL(10, 2) DEFAULT 0,
    limite DECIMAL(10, 2) DEFAULT 5000,
    fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Índices para optimización
CREATE INDEX idx_usuario_email ON usuarios(email);
CREATE INDEX idx_procesos_usuario ON procesos(usuario_id);
CREATE INDEX idx_facturas_usuario ON facturas(usuario_id);
CREATE INDEX idx_movimientos_cuenta ON movimientos(cuenta_id);
