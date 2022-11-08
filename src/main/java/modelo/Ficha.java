package modelo;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Alexander Fuentes Medina
 */
@Data
public class Ficha {
    private int idFicha;
    private String numFicha;
    private Persona persona;
    private Usuario usuario;
    private Date fechaCreacion;
    private boolean estado;
}
