package org.educa.util;

import java.security.SecureRandom;

public final class ReservaUtils {
    private static final String CARACTERES_CODIGO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LONGITUD_CODIGO = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private ReservaUtils() {
        throw new IllegalStateException("Clase de utilidades");
    }

    /**
     * Genera un código de reserva aleatorio de 6 caracteres alfanuméricos en mayúsculas
     *
     * @return el código generado
     */
    public static String generarCodigoReserva() {
        StringBuilder codigo = new StringBuilder(LONGITUD_CODIGO);
        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            codigo.append(CARACTERES_CODIGO.charAt(RANDOM.nextInt(CARACTERES_CODIGO.length())));
        }
        return codigo.toString();
    }
}
