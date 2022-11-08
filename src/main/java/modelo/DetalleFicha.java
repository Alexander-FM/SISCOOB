package modelo;

import lombok.Data;

/**
 *
 * @author Alexander Fuentes Medina
 */
@Data
public class DetalleFicha {
    private int idDetalleFicha;
    private Ficha ficha;
    private Equipo equipo;
}
