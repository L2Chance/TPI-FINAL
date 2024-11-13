package Service.AsistenciaSocio;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class AsistenciaSocioServiceImpl implements AsistenciaSocioService {

    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public AsistenciaSocioServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void tomarAsistenciaASocioPorDni() throws SQLException {
        String sql = "INSERT INTO AsistenciaSocio (DNI, FechaRegistrada , HoraRegistrada) VALUES (?, ? , ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        System.out.println("Indique DNI");
        int dni = scanner.nextInt();
        scanner.nextLine();

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        Date fechaSQL = Date.valueOf(fechaActual);
        Time horaSQL = Time.valueOf(horaActual);

        int asistenciasDisponiblesActuales = consultarAsistenciasDisponibles(dni, connection);

        if (asistenciasDisponiblesActuales > 0){
            statement.setInt(1, dni);
            statement.setDate(2, fechaSQL);
            statement.setTime(3, horaSQL);
            statement.executeUpdate();
            actualizarAsistencias(dni, asistenciasDisponiblesActuales - 1, connection);
            System.out.println("Asistencia tomada correctamente");
            statement.close();
        }
        else{
            System.out.println("No tiene asistencias disponibles. Debe esperar hasta el comienzo de semana.");
        }
    }

    public static void actualizarAsistencias(int dni, int nuevaAsistencia, Connection connection) throws SQLException {
        String actualizarAsistencias = "UPDATE socio SET AsistenciasDisponibles = ? WHERE DNI = ?";
        PreparedStatement solicitud = connection.prepareStatement(actualizarAsistencias);

        solicitud.setInt(1, nuevaAsistencia);
        solicitud.setInt(2, dni);

        solicitud.executeUpdate();
        solicitud.close();
    }

    public static int consultarAsistenciasDisponibles(int dni, Connection connection) throws SQLException {
        String consultarAsistencias = "SELECT AsistenciasDisponibles FROM socio WHERE DNI = ?";
        PreparedStatement solicitud = connection.prepareStatement(consultarAsistencias);

        solicitud.setInt(1, dni);
        ResultSet resultado = solicitud.executeQuery();

        resultado.next();
        int asistenciasDisponibles = resultado.getInt("AsistenciasDisponibles");
        resultado.close();

        return asistenciasDisponibles;
    }

    @Override
    public void mostrarAsistenciasDisponiblesDeSocioPorDni() throws SQLException {
        System.out.println("Ingrese DNI");
        int dni = scanner.nextInt();
        scanner.nextLine();

        int asistenciasDisponibles = consultarAsistenciasDisponibles(dni, connection);

        System.out.println("El número de asistencias disponibles para el DNI: " + dni + " Es: " + asistenciasDisponibles);
    }

    @Override
    public void actualizarEstadoSemanal() throws SQLException {
        String sql = "SELECT DNI, Membresia FROM socio";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int dni = resultSet.getInt("DNI");
            String membresia = resultSet.getString("Membresia");

            int asistenciaRenovada = 0;

            switch(membresia){
                case "BÁSICO":
                    asistenciaRenovada = 3;
                    break;
                case "PREMIUM":
                    asistenciaRenovada = 5;
                    break;
                case "VIP":
                    asistenciaRenovada = 7;
                    break;
            }

            // Actualizamos el campo AsistenciasDisponibles de la tabla socio
            actualizarAsistencias(dni, asistenciaRenovada, connection);

            System.out.println("Estado semanal actualizado");
        }
    }
}
