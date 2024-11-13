import Service.AsistenciaEmpleado.AsistenciaEmpleadoService;
import Service.AsistenciaEmpleado.AsistenciaEmpleadoServiceImpl;
import Service.AsistenciaSocio.AsistenciaSocioService;
import Service.AsistenciaSocio.AsistenciaSocioServiceImpl;
import Service.Empleado.EmpleadoServiceImpl;
import Service.Pago.PagoService;
import Service.Pago.PagoServiceImpl;
import Service.Socio.SocioService;
import Service.Socio.SocioServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        EmpleadoServiceImpl empleadoService = new EmpleadoServiceImpl(connection);
        SocioService socioService = new SocioServiceImpl(connection);
        PagoService pagoService = new PagoServiceImpl(connection);
        AsistenciaEmpleadoService asistenciaEmpleadoService = new AsistenciaEmpleadoServiceImpl(connection);
        AsistenciaSocioService asistenciaSocioService = new AsistenciaSocioServiceImpl(connection);

        boolean continuar = true;

        while (continuar) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Menú Empleados");
            System.out.println("2. Menú Socios");
            System.out.println("3. Salir");

            int opcionPrincipal = -1;
            try {
                System.out.println("Indique lo que quiere hacer:");
                opcionPrincipal = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine();
                continue;
            }

            switch (opcionPrincipal) {
                case 1:
                    boolean continuarEmpleados = true;
                    while (continuarEmpleados) {
                        System.out.println("\nMenú Empleados:");
                        System.out.println("1. Agregar empleado");
                        System.out.println("2. Modificar empleado por DNI");
                        System.out.println("3. Eliminar empleado por DNI");
                        System.out.println("4. Ver lista de empleados ");
                        System.out.println("5. Tomar Asistencia a Empleado");
                        System.out.println("6. Mostrar asistencias de empleados");
                        System.out.println("7. Volver al menú principal");

                        int opcionEmpleado = -1;
                        try {
                            System.out.println("Indique lo que quiere hacer:");
                            opcionEmpleado = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debe ingresar un número entero.");
                            scanner.nextLine();
                            continue;
                        }
                            switch (opcionEmpleado) {
                                case 1:
                                    empleadoService.agregarEmpleado();
                                    break;
                                case 2:
                                    empleadoService.modificarEmpleadoPorDni();
                                    break;
                                case 3:
                                    empleadoService.eliminarEmpleadoPorDni();
                                    break;
                                case 4:
                                    empleadoService.mostrarTodosLosEmpleados();
                                    break;
                                case 5:
                                    asistenciaEmpleadoService.tomarAsistenciaAEmpleadoporDNI();
                                    break;
                                case 6:
                                    asistenciaEmpleadoService.mostrarAsistenciasEmpleadoPorDNI();
                                    break;
                                case 7:
                                    continuarEmpleados = false;
                                    break;
                                default:
                                    System.out.println("Esa opción no está disponible en el menú.");
                            }
                    }
                    break;

                case 2:
                    boolean continuarSocios = true;
                    while (continuarSocios) {
                        System.out.println("\nMenú Socios:");
                        System.out.println("7. Agregar socio");
                        System.out.println("8. Mostrar socios inactivos");
                        System.out.println("9. Dar de baja socio por DNI");
                        System.out.println("10. Actualizar estado de pagos");
                        System.out.println("11. Efectuar pago de membresia mensual");
                        System.out.println("12. Tomar Asistencia a Socio por DNI");
                        System.out.println("13. Mostrar asistencia disponible de socio por DNI");
                        System.out.println("14. Actualizar asistencia semanal");
                        System.out.println("15. Volver al menú principal");
                        int opcionSocio = -1;
                        try {
                            System.out.println("Indique lo que quiere hacer:");
                            opcionSocio = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Debe ingresar un número entero.");
                            scanner.nextLine();
                            continue;
                        }
                            switch (opcionSocio) {
                                case 7:
                                    socioService.darDeAltaSocio();
                                    break;
                                case 8:
                                    socioService.mostrarSociosInactivos();
                                    break;
                                case 9:
                                    socioService.darDeBajaSocioPorDni();
                                    break;
                                case 10:
                                    socioService.actualizarEstadoDePago();
                                    break;
                                case 11:
                                    pagoService.efectuarPago();
                                    break;
                                case 12:
                                    asistenciaSocioService.tomarAsistenciaASocioPorDni();
                                    break;
                                case 13:
                                    asistenciaSocioService.mostrarAsistenciasDisponiblesDeSocioPorDni();
                                    break;
                                case 14:
                                    asistenciaSocioService.actualizarEstadoSemanal();
                                    break;
                                case 15:
                                    continuarSocios = false;
                                    break;
                                default:
                                    System.out.println("Esa opción no está disponible en el menú.");
                            }
                    }
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor elija una opción entre 1 y 3.");
            }
        }
        System.out.println("Programa finalizado.");
        scanner.close();
        connection.close();
    }
}