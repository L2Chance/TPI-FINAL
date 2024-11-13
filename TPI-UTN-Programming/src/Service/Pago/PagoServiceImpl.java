package Service.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class PagoServiceImpl implements PagoService {

    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public PagoServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void efectuarPago() throws SQLException {
        String sql = "INSERT INTO Pago (DNI, FechaPago, monto, MetodoPago) VALUES (?, ? , ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        System.out.println("Indique DNI");
        int dni = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Indique monto");
        double monto = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Indique método de pago ('Efectivo'-'Transferencia')");
        String metodoPago = scanner.nextLine();

        LocalDate fechaActual = LocalDate.now();

        statement.setInt(1, dni);
        statement.setDate(2, java.sql.Date.valueOf(fechaActual));
        statement.setDouble(3, monto);
        statement.setString(4, metodoPago);

        // Ejecutamos los cambios

        statement.executeUpdate();
        statement.close();

        System.out.println("Pago guardado correctamente!");

        String actualizarMembresia = "UPDATE socio SET Membresia = ? SET AsistenciasDisponibles = ? WHERE dni = ?";
        PreparedStatement statement2 = connection.prepareStatement(actualizarMembresia);

        String nuevaMembresia = definirMembresia(monto);
        int asistenciasDisponibles = 0;

        switch(nuevaMembresia){
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

        statement2.setString(1, nuevaMembresia);
        statement2.setInt(2, asistenciasDisponibles);
        statement2.setInt(3, dni);

        statement2.executeUpdate();
        statement2.close();
    }

    public static String definirMembresia(double monto){
        if (monto == 15000){
            return "BÁSICO";
        }
        else{
            if (monto == 30000){
                return "PREMIUM";
            }
            else return "VIP";
        }
    }

    public static void efectuarPrimerPago(int dni, double monto, String metodoPago, Connection connection, Scanner scanner) throws SQLException {
        String sql = "INSERT INTO Pago (DNI, FechaPago, monto, MetodoPago) VALUES (?, ? , ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        LocalDate fechaActual = LocalDate.now();

        statement.setInt(1, dni);
        statement.setDate(2, java.sql.Date.valueOf(fechaActual));
        statement.setDouble(3, monto);
        statement.setString(4, metodoPago);

        statement.executeUpdate();
        statement.close();
    }

}
