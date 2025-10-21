package repository;

import modelo.Factura;
import connections.MySQLPool;
import exceptions.ErrorConexionMySQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaMySQLRepository {
    private static FacturaMySQLRepository instance;

    private FacturaMySQLRepository() {}

    public static FacturaMySQLRepository getInstance() {
        if (instance == null) {
            instance = new FacturaMySQLRepository();
        }
        return instance;
    }

    public void crear(Factura factura) throws ErrorConexionMySQLException {
        String sql = "INSERT INTO facturas (usuario_id, fecha_emision, monto, estado, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MySQLPool.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, factura.getUsuarioId());
            stmt.setTimestamp(2, Timestamp.valueOf(factura.getFechaEmision()));
            stmt.setDouble(3, factura.getMonto());
            stmt.setString(4, factura.getEstado());
            stmt.setString(5, factura.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ErrorConexionMySQLException("Error al crear factura", e);
        }
    }

    public void guardar(Factura factura) throws ErrorConexionMySQLException {
        crear(factura);
    }

    public List<Factura> obtenerPorUsuario(Integer usuarioId) throws ErrorConexionMySQLException {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE usuario_id = ?";
        try (Connection conn = MySQLPool.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                facturas.add(mapearFactura(rs));
            }
        } catch (SQLException e) {
            throw new ErrorConexionMySQLException("Error al obtener facturas", e);
        }
        return facturas;
    }

    private Factura mapearFactura(ResultSet rs) throws SQLException {
        Factura factura = new Factura();
        factura.setId(rs.getInt("id"));
        factura.setUsuarioId(rs.getInt("usuario_id"));
        factura.setFechaEmision(rs.getTimestamp("fecha_emision").toLocalDateTime());
        factura.setMonto(rs.getDouble("monto"));
        factura.setEstado(rs.getString("estado"));
        factura.setDescripcion(rs.getString("descripcion"));
        return factura;
    }
}
