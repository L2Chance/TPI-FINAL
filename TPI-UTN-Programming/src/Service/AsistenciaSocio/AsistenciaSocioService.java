package Service.AsistenciaSocio;

import java.sql.SQLException;

public interface AsistenciaSocioService {
    void tomarAsistenciaASocioPorDni() throws SQLException;
    void mostrarAsistenciasDisponiblesDeSocioPorDni() throws SQLException;
    void actualizarEstadoSemanal() throws SQLException;
}
