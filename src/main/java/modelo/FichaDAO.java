package modelo;

import config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Fuentes Medina
 */
public class FichaDAO extends Conexion {

    public List<Ficha> listarFichas() throws Exception {
        List<Ficha> fichas = null;
        Ficha fi;
        ResultSet rs = null;
        String sql = "SELECT F.IDFICHA, F.NRO_FICHA, F.ESTADO, F.FECHA_CREACION, P.NOMBRES, P.APELLIDOS, PER.NOMBRES, PER.APELLIDOS "
                + "FROM ficha F INNER JOIN persona P ON F.IDPERSONA = P.IDPERSONA "
                + "INNER JOIN usuario U INNER JOIN persona PER ON U.IDPERSONA = PER.IDPERSONA "
                + "GROUP BY F.IDFICHA "
                + "ORDER BY F.FECHA_CREACION ASC";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            fichas = new ArrayList<>();
            while (rs.next() == true) {
                fi = new Ficha();
                fi.setIdFicha(rs.getInt("IDFICHA"));
                fi.setNumFicha(rs.getString("NRO_FICHA"));
                fi.setEstado(rs.getBoolean("ESTADO"));
                fi.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                /**
                 * Esto hace referencia a la persona que va a arreglar los equipos.
                 */
                fi.setPersona(new Persona());
                fi.getPersona().setNombres(rs.getString("NOMBRES"));
                fi.getPersona().setApellidos(rs.getString("APELLIDOS"));
                /**
                 * Esto hace referencia al usuario que registro la ficha de internamiento
                 */
                fi.getUsuario().setPersona(new Persona());
                fi.getUsuario().getPersona().setNombres(rs.getString("NOMBRES"));
                fi.getUsuario().getPersona().setApellidos(rs.getString("APELLIDOS"));
                fichas.add(fi);
            }
            this.cerrar(false);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
            this.cerrar(false);
        }
        return fichas;
    }

    public List<DetalleFicha> listarDetallesVentas(Ficha ficha) throws Exception {
        List<DetalleFicha> detalleFichas = null;
        DetalleFicha det;
        ResultSet rs = null;      
        String sql = "SELECT E.NOMBRE_BIEN, M.MARCA FROM fichadetalle FD INNER JOIN ficha F ON FD.IDFICHA = F.IDFICHA "
                + "INNER JOIN equipo E ON E.IDEQUIPO = FD.IDEQUIPO INNER JOIN marca M ON E.IDMARCA = M.IDMARCA "
                + "WHERE F.IDFICHA = '" + ficha.getIdFicha() + "'"
                + "GROUP BY E.NOMBRE_BIEN, M.MARCA";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            detalleFichas = new ArrayList<>();
            while (rs.next() == true) {
                det = new DetalleFicha();
                det.setEquipo(new Equipo());
                det.getEquipo().setNombreBien(rs.getString("NOMBRE_BIEN"));
                det.getEquipo().setMarca(new Marca());
                det.getEquipo().getMarca().setMarca(rs.getString("MARCA"));
                detalleFichas.add(det);
            }
            this.cerrar(false);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
            this.cerrar(false);
        }
        return detalleFichas;
    }
    
    public void anularFicha(Ficha ficha) throws Exception {
        String sql = "UPDATE Ficha SET ESTADO = 0 WHERE IDFICHA = '" + ficha.getIdFicha()+ "'";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(false);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }
}
