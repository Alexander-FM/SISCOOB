package modelo;

import config.Conexion;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
        String sql = "SELECT F.IDFICHA, F.NRO_FICHA, F.ESTADO, F.FECHA_CREACION, "
                + "P.NOMBRES AS TECNICO_NOMBRE, P.APELLIDOS AS  TECNICO_APELLIDO, "
                + "PER.NOMBRES  AS USUARIO_NOMBRE, PER.APELLIDOS  AS USUARIO_APELLIDO "
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
                fi.setPersona(Persona.builder()
                        .nombres(rs.getString("TECNICO_NOMBRE"))
                        .apellidos(rs.getString("TECNICO_APELLIDO"))
                        .build());
                fi.setUsuario(Usuario.builder()
                        .persona(Persona.builder().nombres(rs.getString("USUARIO_NOMBRE"))
                                .apellidos(rs.getString("USUARIO_APELLIDO"))
                                .build())
                        .build());
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
        String sql = "UPDATE Ficha SET ESTADO = 0 WHERE IDFICHA = '" + ficha.getIdFicha() + "'";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(false);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

    public List<FichaReporte> listarReporteFichas() throws Exception {
        List<FichaReporte> fichaRepo = null;
        FichaReporte fi;
        ResultSet rs = null;
        String sql = "SELECT F.NRO_FICHA, P.NOMBRES, P.APELLIDOS, EQUI.NOMBRE_BIEN, "
                + "M.MARCA, E.ESTADO AS ESTADO_EQUIPO, F.ESTADO FROM ficha F "
                + "INNER JOIN fichadetalle FD ON F.IDFICHA = FD.IDFICHA "
                + "INNER JOIN equipo EQUI ON EQUI.IDEQUIPO = FD.IDEQUIPO "
                + "INNER JOIN MARCA M ON EQUI.IDMARCA = M.IDMARCA "
                + "INNER JOIN estado E ON E.IDESTADO = EQUI.IDESTADO "
                + "INNER JOIN persona P ON P.IDPERSONA = F.IDPERSONA";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            fichaRepo = new ArrayList<>();
            while (rs.next() == true) {
                fi = new FichaReporte();
                fi.setNumFicha(rs.getString("NRO_FICHA"));
                fi.setTecnico(rs.getString("NOMBRES") + " " + rs.getString("APELLIDOS"));
                fi.setEquipo(rs.getString("NOMBRE_BIEN"));
                fi.setMarcaEquipo(rs.getString("MARCA"));
                fi.setEstadoEquipo(rs.getString("ESTADO_EQUIPO"));
                fi.setEstadoFicha(rs.getBoolean("ESTADO"));
                fichaRepo.add(fi);
            }
            this.cerrar(false);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        } finally {
            this.cerrar(false);
        }
        return fichaRepo;
    }

    public List<Persona> listarTécnicos() throws Exception {
        List<Persona> personas;
        Persona person;
        ResultSet rs;
        String sql = "SELECT P.IDPERSONA, P.NOMBRES, P.APELLIDOS, R.ROL FROM persona P INNER JOIN ROL R "
                + "ON P.IDROL = R.IDROL "
                + "WHERE R.ROL = 'Técnico'";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            personas = new ArrayList<>();
            while (rs.next() == true) {
                person = new Persona();
                person.setIdPersona(rs.getInt("IDPERSONA"));
                person.setNombres(rs.getString("NOMBRES"));
                person.setApellidos(rs.getString("APELLIDOS"));
                person.setRol(new Rol());
                person.getRol().setRol((rs.getString("ROL")));
                personas.add(person);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return personas;
    }

    public List<Equipo> listarEquiposObsoletos() throws Exception {
        List<Equipo> equipo;
        Equipo equi;
        ResultSet rs;
        String sql = "SELECT E.IDEQUIPO, E.NOMBRE_BIEN, M.MARCA, ES.ESTADO FROM equipo E INNER JOIN marca M "
                + "ON E.IDMARCA = M.IDMARCA INNER JOIN estado ES ON ES.IDESTADO = E.IDESTADO "
                + "WHERE ES.ESTADO != 'Operativo sin observaciones' AND ES.ESTADO != 'Sin Revisar'";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            equipo = new ArrayList<>();
            while (rs.next() == true) {
                equi = new Equipo();
                equi.setIdEquipo(rs.getInt("IDEQUIPO"));
                equi.setNombreBien(rs.getString("NOMBRE_BIEN"));
                equi.setMarca(new Marca());
                equi.getMarca().setMarca((rs.getString("MARCA")));
                equi.setEstado(new Estado());
                equi.getEstado().setEstado(rs.getString("ESTADO"));
                equipo.add(equi);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return equipo;
    }

    public Equipo listarEquiposById(int id) {
        String sql = "SELECT E.IDEQUIPO, E.NOMBRE_BIEN, M.MARCA, ES.ESTADO FROM equipo E INNER JOIN marca M "
                + "ON E.IDMARCA = M.IDMARCA INNER JOIN estado ES ON ES.IDESTADO = E.IDESTADO "
                + "WHERE E.IDEQUIPO = " + id;
        ResultSet rs = null;
        Equipo equi = null;
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            while (rs.next()) {
                equi = new Equipo();
                equi.setIdEquipo(rs.getInt("IDEQUIPO"));
                equi.setNombreBien(rs.getString("NOMBRE_BIEN"));
                equi.setMarca(new Marca());
                equi.getMarca().setMarca((rs.getString("MARCA")));
                equi.setEstado(new Estado());
                equi.getEstado().setEstado(rs.getString("ESTADO"));
            }
            this.cerrar(false);
        } catch (Exception e) {
            System.out.println("Error al obtener añadir el producto al detalle de la ficha de internamiento" + e.getMessage());
        }
        return equi;
    }

    public void registrar(Ficha ficha) throws Exception {
        ResultSet rs = null;
        int codigoFicha;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(ficha.getFechaCreacion());
        String sql = "INSERT INTO Ficha(NRO_FICHA, IDPERSONA, IDUSUARIO, FECHA_CREACION, ESTADO)"
                + "VALUES('" + ficha.getNumFicha() + "', '"
                + ficha.getPersona().getIdPersona() + "', '"
                + ficha.getUsuario().getIdUsuario() + "', '"
                + fecha + "', "
                + (ficha.isEstado() == true ? "1" : "0") + ")";

        try {
            this.conectar(true);
            this.ejecutarOrden(sql);
            rs = this.ejecutarOrdenDatos("SELECT @@IDENTITY AS Codigo"); //OBTENER EL CÓDIGO GENERADO
            rs.next();
            codigoFicha = rs.getInt("Codigo");
            rs.close();
            for (DetalleFicha detalle : ficha.getDetalleFicha()) {
                sql = "INSERT INTO FichaDetalle(IDEQUIPO, IDFICHA) "
                        + "VALUES(" + detalle.getEquipo().getIdEquipo() + ", "
                        + codigoFicha + ")";
                this.ejecutarOrden(sql);//INSERTA EL DETALLE DE LA FICHA DE INTERNAMIENTO
            }
            this.cerrar(true);
        } catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }
}
