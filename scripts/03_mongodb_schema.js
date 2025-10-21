const db = require("mongodb").MongoClient.connect("mongodb://localhost:27017").db("yourDatabaseName")

// MongoDB Collections para datos flexibles
// Mensajes, alertas y registros de auditoría

db.createCollection("mensajes", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["_id", "remitente", "destinatario", "contenido", "fecha"],
      properties: {
        _id: { bsonType: "objectId" },
        remitente: { bsonType: "int" },
        destinatario: { bsonType: "int" },
        contenido: { bsonType: "string" },
        fecha: { bsonType: "date" },
        leido: { bsonType: "bool" },
      },
    },
  },
})

db.createCollection("alertas", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["_id", "sensor_id", "tipo", "mensaje", "fecha"],
      properties: {
        _id: { bsonType: "objectId" },
        sensor_id: { bsonType: "string" },
        tipo: { bsonType: "string" },
        mensaje: { bsonType: "string" },
        fecha: { bsonType: "date" },
        resuelta: { bsonType: "bool" },
      },
    },
  },
})

db.createCollection("auditoria", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["_id", "usuario_id", "accion", "fecha"],
      properties: {
        _id: { bsonType: "objectId" },
        usuario_id: { bsonType: "int" },
        accion: { bsonType: "string" },
        detalles: { bsonType: "object" },
        fecha: { bsonType: "date" },
      },
    },
  },
})

// Crear índices
db.mensajes.createIndex({ remitente: 1, fecha: -1 })
db.mensajes.createIndex({ destinatario: 1, leido: 1 })
db.alertas.createIndex({ sensor_id: 1, fecha: -1 })
db.auditoria.createIndex({ usuario_id: 1, fecha: -1 })
