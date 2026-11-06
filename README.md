# Práctica RA2 - Sistema de reservas de vuelos

La práctica consiste en terminar un programa de consola en Java que hace reservas de vuelos contra una base de datos 
PostgreSQL. El `Main` ya nos lo daba el profesor y no se podía tocar, así que lo que he hecho es programar los DAO y 
rellenar los métodos de los servicios para que todo funcione.

## Ejercicio 1: Buscar el cliente y ver sus reservas

Lo primero que hace el programa es pedir un DNI.

- **`ClienteService.findByDNI`**: llama a `ClienteDAO.findByDni`, que hace un `SELECT` en la tabla `clientes` filtrando por el DNI. Si lo encuentra devuelve el cliente, y si no devuelve `null`. Así el `Main` sabe que tiene que pedir los datos para darlo de alta.
- **`ClienteService.saveCliente`**: hace el `INSERT` del cliente nuevo. Aquí tuve que recoger el `id_cliente` que genera la base de datos con `getGeneratedKeys()` y guardarlo en el objeto. Si no se hace, luego al guardar la reserva el cliente no tiene id y falla.
- **`ReservaService.findByCliente`**: saca todas las reservas del cliente ordenadas por fecha, para que el `Main` las enseñe por pantalla.
  Prueba: con el DNI `47123984A` sale Manuel González Pérez con su reserva W8X9K2.

## Ejercicio 2: Aeropuertos, vuelos y precio base

- **`AeropuertoService.findAll`**: devuelve todos los aeropuertos para que el usuario elija desde cuál sale.
- **`VueloService.findByOrigen`**: busca los vuelos que salen del aeropuerto elegido. He hecho un `JOIN` con la tabla `aeropuertos` para sacar también los datos del aeropuerto de destino en la misma consulta. No se muestran los vuelos con estado `CANCELADO` y salen ordenados por la hora de salida.
- **`VueloService.getPrecioBase`**: el precio base es el número de minutos que dura el vuelo multiplicado por 0,75 €. Los minutos los calculo con `Duration.between` entre la fecha de salida y la de llegada. Uso `BigDecimal` en vez de `double` porque es dinero y así no hay problemas con los decimales.
## Ejercicio 3: Asientos libres y cálculo del precio

- **`AsientoService.findDisponibles`**: saca los asientos del avión que hace ese vuelo y que todavía no están ocupados. Para saber si un asiento está ocupado uso un `NOT EXISTS` que mira si ya hay un billete para ese asiento en ese vuelo. Hay que filtrar por vuelo y no solo por avión, porque un mismo avión hace varios vuelos y un asiento puede estar ocupado en uno y libre en otro.
- Después, en el servicio, le pongo a cada asiento su suplemento según la clase:
    - Turista: 0 €
    - Premium Economy: 35 €
    - Business: 120 €
    - First: 250 €
- **`BilleteService.calculatePrecio`**: calcula el precio final siguiendo el orden del enunciado:
    1. Precio base del vuelo + suplemento del asiento.
    2. Si el pasajero tiene menos de 12 años el día del vuelo, se le quita un 20 %. La edad la calculo con `Period.between` entre la fecha de nacimiento y la fecha de salida del vuelo, no la de hoy.
    3. Si el cliente tiene más de 3 reservas previas, se le quita otro 10 % sobre el precio que ya lleva el descuento anterior.
    4. Al final se redondea a 2 decimales.

## Ejercicio 4: Guardar la reserva con una transacción

Este es el ejercicio más importante, porque al confirmar hay que guardar datos en tres tablas distintas: `reservas`, 
`pasajeros` y `billetes`. Si una de ellas falla no puede quedar nada a medias, por ejemplo una reserva sin billete.

Lo que he hecho en **`ReservaService.save`** es:

1. Pedir una conexión al pool y poner `setAutoCommit(false)` para empezar la transacción.
2. Generar el código de la reserva con `ReservaUtils` y comprobar en la base de datos que no existe ya. Si existe, se genera otro.
3. Guardar la reserva y recoger su id.
4. Por cada billete, guardar primero el pasajero (con el id de la reserva) y luego el billete (con el id del pasajero).
5. Si todo ha ido bien, `commit()`. Si salta una `SQLException`, `rollback()` y se vuelve a lanzar el error.
6. Al final, en el `finally`, vuelvo a poner el `autoCommit` a `true`, porque la conexión vuelve al pool y la puede usar otra parte del programa.
   Para que todo vaya en la misma transacción, los métodos `save` de `PasajeroDAO`, `BilleteDAO` y `ReservaDAO` reciben la conexión por parámetro y no la cierran. La cierra `ReservaService` al terminar.

---

## Pruebas que he hecho

- Buscar un cliente que existe (`47123984A`) y ver que salen sus reservas.
- Meter un DNI nuevo y comprobar que se crea el cliente y se guarda la reserva.
- Comprobar que los asientos que ya están vendidos no salen en la lista.
- Comprobar los precios con un adulto, con un niño.
- Provocar un error al guardar el billete y comprobar en la base de datos que no se había guardado ni la reserva ni el pasajero, o sea, que el rollback funciona.
 