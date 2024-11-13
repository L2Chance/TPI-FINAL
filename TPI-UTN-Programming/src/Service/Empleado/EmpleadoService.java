package Service.Empleado;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface EmpleadoService {
    void agregarEmpleado() throws SQLException;
    void modificarEmpleadoPorDni() throws SQLException;
    void eliminarEmpleadoPorDni() throws SQLException;
    void mostrarTodosLosEmpleados() throws SQLException;
}
