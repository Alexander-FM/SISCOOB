package modelo;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Alexander Fuentes Medina
 */
@Data
public class Equipo {
    private int idEquipo;
    private String codigoPatrimonio;
    private String ordenCompra;
    private String serieNumero;
    private String nombreBien;
    private Estado estado;
    private Marca marca;
    private Date fechaOc;
}
