package Service.AsistenciaEmpleado;

import java.sql.SQLException;

public interface AsistenciaEmpleadoService {
    void tomarAsistenciaAEmpleadoporDNI() throws SQLException;
    void mostrarAsistenciasEmpleadoPorDNI() throws SQLException;
}
