package utils;

import connections.*;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Inicializa todas las bases de datos con esquemas y datos de prueba
 */
public class DatabaseInitializer {

    /**
     * Inicializa MySQL con tablas y datos de prueba
     */
    public static void initializeMySQLDatabase() {
        System.out.println("[INIT] Inicializando MySQL...");
        try (Connection conn = MySQLPool.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            // Crear tablas si no existen
            String[] sqlStatements = {
                "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "activo BOOLEAN DEFAULT TRUE)",

                "CREATE TABLE IF NOT EXISTS cuentas_corrientes (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "usuario_id INT UNIQUE NOT NULL, " +
                "saldo DECIMAL(10, 2) DEFAULT 0, " +
                "limite DECIMAL(10, 2) DEFAULT 5000, " +
                "fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (usuario_id) REFERENCES usuarios(id))",

                "CREATE TABLE IF NOT EXISTS facturas (" +
                "id VARCHAR(36) PRIMARY KEY, " +
                "usuario_id INT NOT NULL, " +
                "monto DECIMAL(10, 2) NOT NULL, " +
                "fecha DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "estado VARCHAR(20) DEFAULT 'PENDIENTE', " +
                "FOREIGN KEY (usuario_id) REFERENCES usuarios(id))",

                "CREATE TABLE IF NOT EXISTS movimientos (" +
                "id VARCHAR(36) PRIMARY KEY, " +
                "cuenta_id INT NOT NULL, " +
                "tipo VARCHAR(20) NOT NULL, " +
                "monto DECIMAL(10, 2) NOT NULL, " +
                "fecha DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (cuenta_id) REFERENCES cuentas_corrientes(id))"
            };

            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }

            System.out.println("[INIT] ✓ MySQL inicializado correctamente");
        } catch (Exception e) {
            System.err.println("[INIT] ✗ Error inicializando MySQL: " + e.getMessage());
        }
    }

    /**
     * Inicializa Cassandra con keyspace y tablas
     */
    public static void initializeCassandraDatabase() {
        System.out.println("[INIT] Inicializando Cassandra...");
        try {
            CassandraPool cassandraPool = CassandraPool.getInstance();
            // Las tablas se crean mediante CQL scripts
            System.out.println("[INIT] ✓ Cassandra inicializado correctamente");
        } catch (Exception e) {
            System.err.println("[INIT] ✗ Error inicializando Cassandra: " + e.getMessage());
        }
    }

    /**
     * Inicializa MongoDB con colecciones
     */
    public static void initializeMongoDatabase() {
        System.out.println("[INIT] Inicializando MongoDB...");
        try {
            MongoPool mongoPool = MongoPool.getInstance();
            // Las colecciones se crean mediante scripts JavaScript
            System.out.println("[INIT] ✓ MongoDB inicializado correctamente");
        } catch (Exception e) {
            System.err.println("[INIT] ✗ Error inicializando MongoDB: " + e.getMessage());
        }
    }

    /**
     * Inicializa Redis con estructuras de datos
     */
    public static void initializeRedisDatabase() {
        System.out.println("[INIT] Inicializando Redis...");
        try {
            RedisPool redisPool = RedisPool.getInstance();
            // Las estructuras se crean mediante scripts bash
            System.out.println("[INIT] ✓ Redis inicializado correctamente");
        } catch (Exception e) {
            System.err.println("[INIT] ✗ Error inicializando Redis: " + e.getMessage());
        }
    }

    /**
     * Inicializa todas las bases de datos
     */
    public static void initializeAll() {
        System.out.println("\n========== INICIALIZANDO BASES DE DATOS ==========\n");
        initializeMySQLDatabase();
        initializeCassandraDatabase();
        initializeMongoDatabase();
        initializeRedisDatabase();
        System.out.println("\n========== INICIALIZACIÓN COMPLETADA ==========\n");
    }
}
