package org.educa.service;

import org.educa.entity.BilleteEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class BilleteService {
    private static final int EDAD_INFANTIL = 12;
    private static final int MIN_RESERVAS_DESCUENTO = 3;
    private static final BigDecimal FACTOR_DESCUENTO_INFANTIL = new BigDecimal("0.80");
    private static final BigDecimal FACTOR_DESCUENTO_RESERVAS = new BigDecimal("0.90");
    private final VueloService vueloService = new VueloService();

    public void calculatePrecio(BilleteEntity billete, int numReservas) {
        BigDecimal precio = vueloService.getPrecioBase(billete.getVuelo())
                .add(billete.getAsiento().getSuplemento());

        if (esMenor(billete.getPasajero().getFecNac(), billete.getVuelo().getFecSalida().toLocalDate())) {
            precio = precio.multiply(FACTOR_DESCUENTO_INFANTIL);
        }

        if (numReservas > MIN_RESERVAS_DESCUENTO) {
            precio = precio.multiply(FACTOR_DESCUENTO_RESERVAS);
        }
        billete.setPrecio(precio.setScale(2, RoundingMode.HALF_UP));
    }


    public void save(Connection connection, BilleteEntity billete) throws SQLException {
    }

    private boolean esMenor(LocalDate fecNac, LocalDate fecSalida) {
        return Period.between(fecNac, fecSalida).getYears() < EDAD_INFANTIL;
    }
}
