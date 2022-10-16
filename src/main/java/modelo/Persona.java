package modelo;

import lombok.Data;

/**
 *
 * @author Esmeralda Hernandez
 */
@Data
public class Persona {
    private int idPersona;
    private String nombres;
    private String apellidos;
    private Rol rol;
}
