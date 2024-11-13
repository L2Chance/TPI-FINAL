package Service.Socio;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

import static Service.Pago.PagoServiceImpl.definirMembresia;
import static Service.Pago.PagoServiceImpl.efectuarPrimerPago;

public class SocioServiceImpl implements SocioService{
    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public SocioServiceImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void darDeAltaSocio() throws SQLException {
        String sql = "INSERT INTO socio (DNI, nombre, apellido, Telefono, FechaPago, EstadoPago, AsistenciasDisponibles, Membresia) VALUES (?, ?, ?, ?, ?, ?, ? , ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        System.out.println("Indique DNI");
        int dni = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Indique el nombre");
        String nombre = scanner.nextLine();

        System.out.println("Indique apellido");
        String apellido = scanner.nextLine();

        System.out.println("Indique telefono");
        String telefono = scanner.nextLine();

        System.out.println("Indique método de pago");
        String metodoPago = scanner.nextLine();

        System.out.println("Indique monto");
        double monto = scanner.nextDouble();
        scanner.nextLine();

        efectuarPrimerPago(dni, monto, metodoPago, connection, scanner);
        String membresia = definirMembresia(monto);

        LocalDate fechaActual = LocalDate.now();

        String estado = "Activo";

        int asistenciasDisponibles = 0;

        switch(membresia){
            case "BÁSICO":
                asistenciasDisponibles = 3;
                break;
            case "PREMIUM":
                asistenciasDisponibles = 5;
                break;
            case "VIP":
                asistenciasDisponibles = 7;
                break;
        }

        statement.setInt(1, dni);
        statement.setString(2, nombre);
        statement.setString(3, apellido);
        statement.setString(4, telefono);
        statement.setDate(5, java.sql.Date.valueOf(fechaActual));
        statement.setString(6, estado);
        statement.setInt(7, asistenciasDisponibles);
        statement.setString(8, membresia);

        statement.executeUpdate();
        System.out.println("Socio agregado correctamente");

        statement.close();
    }

    @Override
    public void mostrarSociosInactivos() throws SQLException {

        String sql = "SELECT DNI, nombre, apellido, Telefono, MetodoPago, FechaPago, EstadoPago FROM socio WHERE EstadoPago = 'Inactivo'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                // Mostrar los datos de cada socio inactivo
                int dni = resultSet.getInt("DNI");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("Telefono");
                String metodoPago = resultSet.getString("MetodoPago");
                Date fechaPago = resultSet.getDate("FechaPago");
                String estadoPago = resultSet.getString("EstadoPago");

                System.out.println("DNI: " + dni + ", Nombre: " + nombre + ", Apellido: " + apellido +
                        ", Teléfono: " + telefono + ", Método de pago: " + metodoPago +
                        ", Fecha de pago: " + fechaPago + ", Estado de pago: " + estadoPago);

            }

        }
    }

    @Override
    public void darDeBajaSocioPorDni() throws SQLException {
        String sql = "DELETE FROM socio WHERE DNI = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        System.out.println("Indique el DNI del socio");
        int dni = scanner.nextInt();
        scanner.nextLine();

        preparedStatement.setInt(1, dni);
        int filasAfectadas = preparedStatement.executeUpdate();
        preparedStatement.close();

        if (filasAfectadas > 0){
            System.out.println("El socio con DNI " + dni + " ha sido eliminado exitosamente.");
        }
        else{
            System.out.println("El socio con el DNI " + dni + " no está en la base de datos");
        }

    }

    @Override
    public void actualizarEstadoDePago() throws SQLException {
        String sqlSelect = "SELECT DNI, FechaPago FROM socio";
        PreparedStatement statement = connection.prepareStatement(sqlSelect);

        ResultSet resultSet = statement.executeQuery();

        LocalDate fechaActual = LocalDate.now();

        while (resultSet.next()) {
            int dni = resultSet.getInt("DNI");
            java.sql.Date fechaPagoSql = resultSet.getDate("FechaPago");

            if (fechaPagoSql != null) {
                LocalDate fechaPago = fechaPagoSql.toLocalDate();

                // Calculamos la diferencia de días
                long diasDiferencia = ChronoUnit.DAYS.between(fechaPago, fechaActual);

                // Si la diferencia es mayor a 30, actualizamos el EstadoPago
                if (diasDiferencia > 30) {
                    String sqlUpdate = "UPDATE socio SET EstadoPago = 'Inactivo' WHERE DNI = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate);

                    updateStatement.setInt(1, dni);
                    updateStatement.executeUpdate();

                    updateStatement.close();
                }
            }
        }

        resultSet.close();
        statement.close();
    }
}
