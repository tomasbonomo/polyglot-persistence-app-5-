package repository;

import modelo.Mensaje;
import connections.MongoPool;
import exceptions.ErrorConexionMongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MensajeMongoDAO {
    private static MensajeMongoDAO instance;
    private static final String COLLECTION_NAME = "mensajes";

    private MensajeMongoDAO() {}

    public static MensajeMongoDAO getInstance() {
        if (instance == null) {
            instance = new MensajeMongoDAO();
        }
        return instance;
    }

    public void crear(Mensaje mensaje) throws ErrorConexionMongoException {
        try {
            MongoDatabase db = MongoPool.getInstance().getDatabase();
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            Document doc = new Document()
                    .append("remitenteId", mensaje.getRemitenteId())
                    .append("destinatarioId", mensaje.getDestinatarioId())
                    .append("contenido", mensaje.getContenido())
                    .append("tipo", mensaje.getTipo())
                    .append("fechaHora", mensaje.getFechaHora().toString());

            collection.insertOne(doc);
            mensaje.setId(doc.getObjectId("_id").toString());
        } catch (Exception e) {
            throw new ErrorConexionMongoException("Error al crear mensaje", e);
        }
    }

    public void guardar(Mensaje mensaje) throws ErrorConexionMongoException {
        crear(mensaje);
    }

    public List<Mensaje> obtenerPorDestinatario(Integer destinatarioId) throws ErrorConexionMongoException {
        List<Mensaje> mensajes = new ArrayList<>();
        try {
            MongoDatabase db = MongoPool.getInstance().getDatabase();
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

            for (Document doc : collection.find(Filters.eq("destinatarioId", destinatarioId))) {
                mensajes.add(mapearMensaje(doc));
            }
        } catch (Exception e) {
            throw new ErrorConexionMongoException("Error al obtener mensajes", e);
        }
        return mensajes;
    }

    private Mensaje mapearMensaje(Document doc) {
        Mensaje mensaje = new Mensaje();
        mensaje.setId(doc.getObjectId("_id").toString());
        mensaje.setRemitenteId(doc.getInteger("remitenteId"));
        mensaje.setDestinatarioId(doc.getInteger("destinatarioId"));
        mensaje.setContenido(doc.getString("contenido"));
        mensaje.setTipo(doc.getString("tipo"));
        return mensaje;
    }
}
