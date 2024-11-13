package Service.Socio;

import java.sql.SQLException;

public interface SocioService {
    void darDeAltaSocio() throws SQLException;
    void mostrarSociosInactivos() throws SQLException;
    void darDeBajaSocioPorDni() throws SQLException;
    void actualizarEstadoDePago() throws SQLException;
}
