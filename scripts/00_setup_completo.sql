-- ============================================================
-- SETUP COMPLETO PARA POLYGLOT PERSISTENCE APP
-- Ejecutar este script como root en MySQL
-- ============================================================

-- 1. CONFIGURAR USUARIO ROOT PARA CONEXIONES TCP/IP
-- Cambiar autenticación a mysql_native_password
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';

-- Crear usuario para 127.0.0.1 si no existe
CREATE USER IF NOT EXISTS 'root'@'127.0.0.1' IDENTIFIED WITH mysql_native_password BY 'admin';

-- Otorgar todos los privilegios
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'127.0.0.1' WITH GRANT OPTION;

-- Aplicar cambios
FLUSH PRIVILEGES;

-- ============================================================
-- 2. CREAR BASE DE DATOS Y TABLAS
-- ============================================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS polyglot_db;
USE polyglot_db;

-- Drop existing tables to avoid index conflicts
DROP TABLE IF EXISTS movimientos;
DROP TABLE IF EXISTS cuentas_corrientes;
DROP TABLE IF EXISTS facturas;
DROP TABLE IF EXISTS procesos;
DROP TABLE IF EXISTS usuarios;

-- Tabla de Usuarios (CORREGIDA para coincidir con Java)
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50) DEFAULT 'USER',
    estado VARCHAR(50) DEFAULT 'ACTIVO',
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Procesos
CREATE TABLE procesos (
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
CREATE TABLE facturas (
    id VARCHAR(36) PRIMARY KEY,
    usuario_id INT NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    descripcion TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Cuentas Corrientes
CREATE TABLE cuentas_corrientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT UNIQUE NOT NULL,
    saldo DECIMAL(10, 2) DEFAULT 0,
    limite DECIMAL(10, 2) DEFAULT 5000,
    fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Movimientos (Cuenta Corriente)
CREATE TABLE movimientos (
    id VARCHAR(36) PRIMARY KEY,
    cuenta_id INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas_corrientes(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Índices para optimización
CREATE INDEX idx_usuario_email ON usuarios(email);
CREATE INDEX idx_procesos_usuario ON procesos(usuario_id);
CREATE INDEX idx_facturas_usuario ON facturas(usuario_id);
CREATE INDEX idx_movimientos_cuenta ON movimientos(cuenta_id);

-- ============================================================
-- 3. VERIFICACIÓN
-- ============================================================

-- Verificar usuario root
SELECT user, host, plugin FROM mysql.user WHERE user='root';

-- Verificar base de datos
SHOW DATABASES LIKE 'polyglot_db';

-- Verificar tablas
USE polyglot_db;
SHOW TABLES;

-- Verificar estructura de usuarios
DESCRIBE usuarios;
