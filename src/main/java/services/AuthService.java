package services;

import modelo.Usuario;
import repository.UsuarioMySQLRepository;
import repository.SesionRedisDAO;
import exceptions.ErrorConexionMySQLException;
import exceptions.ErrorConexionRedisException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AuthService {
    private static AuthService instance;

    private AuthService() {}

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public String login(String email, String password) throws ErrorConexionMySQLException, ErrorConexionRedisException {
        Usuario usuario = UsuarioMySQLRepository.getInstance().obtenerPorEmail(email);
        if (usuario == null) {
            return null;
        }

        String passwordHash = hashPassword(password);
        if (!usuario.getPasswordHash().equals(passwordHash)) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        SesionRedisDAO.getInstance().guardarSesion(token, usuario.getId(), usuario.getRol());
        return token;
    }

    public void registrar(String nombre, String email, String password, String rol) 
            throws ErrorConexionMySQLException {
        Usuario usuario = new Usuario(nombre, email, hashPassword(password), rol);
        UsuarioMySQLRepository.getInstance().crear(usuario);
    }

    public Usuario validarToken(String token) throws ErrorConexionRedisException, ErrorConexionMySQLException {
        var sesion = SesionRedisDAO.getInstance().obtenerSesion(token);
        if (sesion == null) {
            return null;
        }
        Integer usuarioId = sesion.getInt("usuarioId");
        return UsuarioMySQLRepository.getInstance().obtenerPorId(usuarioId);
    }

    public void logout(String token) throws ErrorConexionRedisException {
        SesionRedisDAO.getInstance().eliminarSesion(token);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
