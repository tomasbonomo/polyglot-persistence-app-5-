# Polyglot Persistence - Sistema de Gestión de Sensores

Aplicación Java de escritorio (Swing) que demuestra el uso de múltiples motores de bases de datos en una sola aplicación.

## Características

- **Persistencia Poliglota**: Integración de 4 motores de BD diferentes
  - **MySQL**: Datos transaccionales (usuarios, facturas, cuentas)
  - **Cassandra**: Time series (sensores, mediciones)
  - **Redis**: Caché y sesiones
  - **MongoDB**: Datos flexibles (mensajes, alertas)

- **Interfaz Gráfica**: Swing con múltiples pestañas
- **Simulador de Sensores**: Genera mediciones en tiempo real
- **Gestión de Usuarios**: Autenticación y autorización
- **Sistema de Facturación**: Facturas y cuenta corriente

## Requisitos Previos

- Java 11+
- Maven 3.6+
- MySQL 5.7+
- Cassandra 3.11+
- Redis 5.0+
- MongoDB 4.0+

## Instalación

### 1. Clonar el repositorio
\`\`\`bash
git clone <repository-url>
cd polyglot-persistence-app
\`\`\`

### 2. Configurar bases de datos

#### MySQL
\`\`\`bash
mysql -u root -p < scripts/01_mysql_schema.sql
\`\`\`

#### Cassandra
\`\`\`bash
cqlsh < scripts/02_cassandra_schema.cql
\`\`\`

#### MongoDB
\`\`\`bash
mongo < scripts/03_mongodb_schema.js
\`\`\`

#### Redis
\`\`\`bash
bash scripts/04_redis_init.sh
\`\`\`

### 3. Actualizar configuración
Editar `src/main/resources/application.properties` con tus credenciales:

```properties
mysql.host=localhost
mysql.user=root
mysql.password=tu_password

cassandra.host=127.0.0.1
redis.host=localhost
mongodb.url=mongodb://127.0.0.1:27017
