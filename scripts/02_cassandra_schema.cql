-- Cassandra Schema para datos de sensores (Time Series)
-- Optimizado para escrituras masivas y consultas por tiempo

CREATE KEYSPACE IF NOT EXISTS polyglot_ks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE polyglot_ks;

-- Tabla de Sensores
CREATE TABLE IF NOT EXISTS sensores (
    id TEXT PRIMARY KEY,
    ubicacion TEXT,
    pais TEXT,
    latitud DOUBLE,
    longitud DOUBLE,
    fecha_instalacion TIMESTAMP,
    activo BOOLEAN
);

-- Tabla de Mediciones (Time Series)
CREATE TABLE IF NOT EXISTS mediciones (
    sensor_id TEXT,
    fecha TIMESTAMP,
    id TEXT,
    temperatura DOUBLE,
    humedad DOUBLE,
    PRIMARY KEY (sensor_id, fecha)
) WITH CLUSTERING ORDER BY (fecha DESC);

-- Tabla de Alertas
CREATE TABLE IF NOT EXISTS alertas (
    id TEXT PRIMARY KEY,
    sensor_id TEXT,
    tipo TEXT,
    mensaje TEXT,
    fecha TIMESTAMP,
    resuelta BOOLEAN
);

-- Índices secundarios
CREATE INDEX IF NOT EXISTS idx_alertas_sensor ON alertas(sensor_id);
CREATE INDEX IF NOT EXISTS idx_alertas_fecha ON alertas(fecha);
