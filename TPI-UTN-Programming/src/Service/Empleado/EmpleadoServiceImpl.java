package Service.Empleado;

import java.lang.runtime.SwitchBootstraps;
import java.sql.*;
import java.util.Scanner;

public class EmpleadoServiceImpl implements EmpleadoService{
    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public EmpleadoServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void agregarEmpleado() throws SQLException {
        String sql = "INSERT INTO empleados (DNI, nombre, apellido, cargo) VALUES (?, ? , ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        System.out.println("Indique DNI");
        int dni = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Indique el nombre");
        String nombre = scanner.nextLine();

        System.out.println("Indique apellido");
        String apellido = scanner.nextLine();

        System.out.println("Indique cargo");
        String cargo = scanner.nextLine();

        statement.setInt(1, dni);
        statement.setString(2, nombre);
        statement.setString(3, apellido);
        statement.setString(4, cargo);

        statement.executeUpdate();
        statement.close();
        System.out.println("Empleado agregado correctamente");
    }

    @Override
    public void modificarEmpleadoPorDni() throws SQLException {
        System.out.println("Indique el DNI del empleado que desea modificar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Apellido");
        System.out.println("3. Cargo");

        int option = scanner.nextInt();
        scanner.nextLine();

        String sql = null;
        PreparedStatement statement = null;

        switch(option) {
            case 1:
                System.out.println("Indique el nuevo nombre:");
                String nombre = scanner.nextLine();
                sql = "UPDATE empleados SET nombre = ? WHERE DNI = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, nombre);
                break;
            case 2:
                System.out.println("Indique el nuevo apellido:");
                String apellido = scanner.nextLine();
                sql = "UPDATE empleados SET apellido = ? WHERE DNI = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, apellido);
                break;
            case 3:
                System.out.println("Indique el nuevo cargo:");
                String cargo = scanner.nextLine();
                sql = "UPDATE empleados SET cargo = ? WHERE DNI = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, cargo);
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        statement.setInt(2, dni);

        statement.executeUpdate();
        statement.close();

        System.out.println("Modificación realizada con exito!");
    }

    @Override
    public void eliminarEmpleadoPorDni() throws SQLException {
        System.out.println("Indique el DNI del empleado que desea eliminar:");
        int dni = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM empleados WHERE DNI = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, dni);

        int filasAfectadas = statement.executeUpdate();

        if (filasAfectadas > 0){
            System.out.println("Empleado con DNI " + dni + " eliminado correctamente.");
        }
        else{
            System.out.println("El empleado con el DNI " + dni + " no está en la base de datos");
        }

        statement.close();
    }

    @Override
    public void mostrarTodosLosEmpleados() throws SQLException {

        String sql = "SELECT * FROM empleados";
        PreparedStatement statement = connection.prepareStatement(sql);


        ResultSet resultSet = statement.executeQuery();

        System.out.println("Lista de Empleados:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int dni = resultSet.getInt("DNI");
            String nombre = resultSet.getString("nombre");
            String apellido = resultSet.getString("apellido");
            String cargo = resultSet.getString("cargo");


            System.out.println("ID: " + id + ", DNI: " + dni + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Cargo: " + cargo);
        }

        resultSet.close();
        statement.close();
    }


}
