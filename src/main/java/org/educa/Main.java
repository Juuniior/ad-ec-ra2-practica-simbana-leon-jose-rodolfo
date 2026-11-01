package org.educa;

import org.educa.entity.*;
import org.educa.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Servicios
            ClienteService clienteService = new ClienteService();
            ReservaService reservaService = new ReservaService();
            BilleteService billeteService = new BilleteService();
            AeropuertoService aeropuertoService = new AeropuertoService();
            VueloService vueloService = new VueloService();
            AsientoService asientoService = new AsientoService();

            System.out.println("===== SISTEMA DE RESERVAS DE VUELOS =====");

            // === 1. Solicitar DNI ===
            System.out.print("Introduce el DNI del cliente: ");
            String dni = sc.nextLine().trim();

            ClienteEntity cliente = clienteService.findByDNI(dni);
            List<ReservaEntity> reservasPrevias = new ArrayList<>();

            if (cliente == null) {
                System.out.println("Cliente no encontrado. Procedemos a registrarlo.");

                cliente = new ClienteEntity();
                cliente.setDni(dni);
                System.out.print("Nombre: ");
                cliente.setNombre(sc.nextLine().trim());
                System.out.print("Primer apellido: ");
                cliente.setPrimerApellido(sc.nextLine().trim());
                System.out.print("Segundo apellido: ");
                cliente.setSegundoApellido(sc.nextLine().trim());
                System.out.print("Email: ");
                cliente.setEmail(sc.nextLine().trim());
                System.out.print("Teléfono: ");
                cliente.setTelefono(sc.nextLine().trim());

                clienteService.saveCliente(cliente);
                System.out.println("Cliente registrado correctamente.");
            } else {
                System.out.println("\nCliente encontrado: " + cliente.getNombre() + " " + cliente.getPrimerApellido() +
                        " " + cliente.getSegundoApellido());
                System.out.println("Mostrando reservas previas...");

                reservasPrevias = reservaService.findByCliente(cliente);
                if (reservasPrevias.isEmpty()) {
                    System.out.println("No tiene reservas previas.");
                } else {
                    for (ReservaEntity r : reservasPrevias) {
                        System.out.println(" - Reserva " + r.getCodReserva() +
                                " | Fecha: " + r.getFecReserva().format(formatterHora) +
                                " | Estado: " + r.getEstado());
                    }
                }
            }

            // === 2. Proceso de nueva reserva ===
            System.out.println("\n=== NUEVA RESERVA ===");

            // === Selección de aeropuerto de origen ===
            System.out.println("\nSeleccione el aeropuerto de origen:");
            List<AeropuertoEntity> aeropuertos = aeropuertoService.findAll();
            for (int i = 0; i < aeropuertos.size(); i++) {
                AeropuertoEntity a = aeropuertos.get(i);
                System.out.println((i + 1) + ". " + a.getCodIata() + " - " + a.getNombre() + " (" + a.getCiudad() + ")");
            }
            int opcAeropuerto = leerOpcion(sc, aeropuertos.size());
            AeropuertoEntity origen = aeropuertos.get(opcAeropuerto - 1);

            // === Vuelos disponibles ===
            List<VueloEntity> vuelos = vueloService.findByOrigen(origen);
            if (vuelos.isEmpty()) {
                System.out.println("\nNo hay vuelos disponibles desde " + origen.getCiudad() + ".");
                return;
            }
            System.out.println("\nVuelos disponibles desde " + origen.getCiudad() + ":");
            for (int i = 0; i < vuelos.size(); i++) {
                VueloEntity v = vuelos.get(i);
                System.out.println((i + 1) + ". " + v.getNumVuelo() + " " + origen.getCodIata() + " -> " +
                        v.getDestino().getCodIata() + " (" + v.getDestino().getCiudad() + ") | Salida: " +
                        v.getFecSalida().format(formatterHora) + " | Llegada: " + v.getFecLlegada().format(formatterHora) +
                        " (" + vueloService.getPrecioBase(v) + "€)");
            }
            int opcVuelo = leerOpcion(sc, vuelos.size());
            VueloEntity vuelo = vuelos.get(opcVuelo - 1);

            // === Selección de asiento ===
            List<AsientoEntity> asientos = asientoService.findDisponibles(vuelo);
            if (asientos.isEmpty()) {
                System.out.println("\nEl vuelo " + vuelo.getNumVuelo() + " está completo.");
                return;
            }
            System.out.println("\nSeleccione el asiento:");
            for (int i = 0; i < asientos.size(); i++) {
                AsientoEntity asiento = asientos.get(i);
                System.out.println((i + 1) + ". " + asiento.getCodAsiento() + " - " + asiento.getClase() +
                        " (+" + asiento.getSuplemento() + "€)");
            }
            int opcAsiento = leerOpcion(sc, asientos.size());
            AsientoEntity asiento = asientos.get(opcAsiento - 1);

            // === Datos del pasajero (el propio cliente) ===
            LocalDate fecNac;
            do {
                fecNac = leerFecha(sc, "Introduce la fecha de nacimiento del pasajero (dd/MM/yyyy): ", formatter);
                if (!fecNac.isBefore(LocalDate.now())) {
                    System.out.println("❌ La fecha de nacimiento debe ser anterior al día de hoy.");
                }
            } while (!fecNac.isBefore(LocalDate.now()));

            PasajeroEntity pasajero = new PasajeroEntity(null, null, cliente.getDni(), cliente.getNombre(),
                    cliente.getPrimerApellido(), cliente.getSegundoApellido(), cliente.getEmail(),
                    cliente.getTelefono(), fecNac);

            // === Cálculo de precio ===
            BilleteEntity billete = new BilleteEntity();
            billete.setPasajero(pasajero);
            billete.setVuelo(vuelo);
            billete.setAsiento(asiento);
            billeteService.calculatePrecio(billete, reservasPrevias.size());

            ReservaEntity reserva = new ReservaEntity();
            reserva.setCliente(cliente);
            reserva.getBilletes().add(billete);

            // === Mostrar resumen ===
            System.out.println("\n=== RESUMEN DE LA RESERVA ===");
            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getPrimerApellido());
            System.out.println("Vuelo: " + vuelo.getNumVuelo() + " " + origen.getCiudad() + " -> " +
                    vuelo.getDestino().getCiudad() + " (" + vuelo.getFecSalida().format(formatterHora) + ")");
            System.out.println("Asiento: " + asiento.getCodAsiento() + " - " + asiento.getClase());
            System.out.println("Precio total: " + billete.getPrecio() + "€");

            System.out.println("¿Desea confirmar la reserva? (y/n)");
            String confirm = sc.nextLine();
            if ("y".equalsIgnoreCase(confirm)) {
                // === Guardar reserva ===
                reservaService.save(reserva);
                System.out.println("\n✅ Reserva " + reserva.getCodReserva() +
                        " registrada correctamente. Gracias por volar con nosotros.");
            } else {
                System.out.println("\n✖️ Reserva cancelada. Gracias por usar nuestro sistema.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // === MÉTODOS AUXILIARES ===

    private static int leerOpcion(Scanner sc, int max) {
        int opcion = -1;
        while (opcion == -1) {
            System.out.println("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                if (opcion < 1 || opcion > max) {
                    System.out.println("⚠️ Debe introducir un número entre " + 1 + " y " + max + ".");
                    opcion = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Valor inválido. Introduce un número.");
                opcion = -1;
            }
        }
        return opcion;
    }

    private static LocalDate leerFecha(Scanner sc, String mensaje, DateTimeFormatter formatter) {
        while (true) {
            System.out.println(mensaje);
            try {
                return LocalDate.parse(sc.nextLine().trim(), formatter);
            } catch (Exception e) {
                System.out.println("⚠️  Formato inválido. Usa el formato dd/MM/yyyy.");
            }
        }
    }
}
