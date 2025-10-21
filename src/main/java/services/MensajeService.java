package services;

import modelo.Mensaje;
import repository.MensajeMongoDAO;
import exceptions.ErrorConexionMongoException;
import java.util.List;

public class MensajeService {
    private static MensajeService instance;

    private MensajeService() {}

    public static MensajeService getInstance() {
        if (instance == null) {
            instance = new MensajeService();
        }
        return instance;
    }

    public void enviarMensaje(Integer remitenteId, Integer destinatarioId, String contenido, String tipo) 
            throws ErrorConexionMongoException {
        Mensaje mensaje = new Mensaje(remitenteId, destinatarioId, contenido, tipo);
        MensajeMongoDAO.getInstance().crear(mensaje);
    }

    public List<Mensaje> obtenerMensajes(Integer usuarioId) throws ErrorConexionMongoException {
        return MensajeMongoDAO.getInstance().obtenerPorDestinatario(usuarioId);
    }
}
