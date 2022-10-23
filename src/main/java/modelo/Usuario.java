package modelo;

import lombok.Data;
/**
 * 
 * @author Alexander Fuentes Medina
 */
@Data
public class Usuario {
   private int idUsuario;
   private String usuario;
   private String clave;
   private boolean estado;
   private Persona persona; 
   private String nombresCompletos;
}
