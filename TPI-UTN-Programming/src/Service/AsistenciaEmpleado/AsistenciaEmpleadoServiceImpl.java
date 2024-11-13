package Service.AsistenciaEmpleado;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class AsistenciaEmpleadoServiceImpl implements AsistenciaEmpleadoService {

    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public AsistenciaEmpleadoServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void tomarAsistenciaAEmpleadoporDNI() throws SQLException {
        String sql = "INSERT INTO AsistenciaEmpleado (DNI, FechaRegistrada , HoraRegistrada) VALUES (?, ? , ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        System.out.println("Indique DNI");
        int dni = scanner.nextInt();
        scanner.nextLine();

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Date fechaSQL = Date.valueOf(fechaActual);
        Time horaSQL = Time.valueOf(horaActual);

        statement.setInt(1, dni);
        statement.setDate(2, fechaSQL);
        statement.setTime(3, horaSQL);

        int filasAfectadas = statement.executeUpdate();

        if (filasAfectadas > 0){
            System.out.println("La asistencia del empleado con " + dni + " ha sido tomada con exito");
        }
        else{
            System.out.println("El socio con el DNI " + dni + " no está en la base de datos");
        }

        statement.close();
    }

    @Override
    public void mostrarAsistenciasEmpleadoPorDNI() throws SQLException{

        System.out.println("Ingrese el DNI del empleado:");
        int dni = scanner.nextInt();

        String sql = "SELECT ID_Asis_Empleado, DNI, FechaRegistrada, HoraRegistrada FROM AsistenciaEmpleado WHERE DNI = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, dni);

        ResultSet resultSet = statement.executeQuery();

        System.out.println("Asistencias para el empleado con DNI: " + dni);

        while (resultSet.next()) {
            int idAsistencia = resultSet.getInt("ID_Asis_Empleado");
            Date fecha = resultSet.getDate("FechaRegistrada");
            Time hora = resultSet.getTime("HoraRegistrada");

            System.out.println("ID Asistencia: " + idAsistencia + ", Fecha: " + fecha + ", Hora: " + hora);
        }

        resultSet.close();
        statement.close();
    }
}
