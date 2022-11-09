package modelo;

public class FichaReporte {
    private String numFicha;
    private String tecnico;
    private String equipo;
    private String marcaEquipo;
    private String estadoEquipo;
    private boolean estadoFicha;

    public String getNumFicha() {
        return numFicha;
    }

    public void setNumFicha(String numFicha) {
        this.numFicha = numFicha;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getMarcaEquipo() {
        return marcaEquipo;
    }

    public void setMarcaEquipo(String marcaEquipo) {
        this.marcaEquipo = marcaEquipo;
    }

    public String getEstadoEquipo() {
        return estadoEquipo;
    }

    public void setEstadoEquipo(String estadoEquipo) {
        this.estadoEquipo = estadoEquipo;
    }

    public boolean isEstadoFicha() {
        return estadoFicha;
    }

    public void setEstadoFicha(boolean estadoFicha) {
        this.estadoFicha = estadoFicha;
    }
    
    public String getEstadoFichaString() {
        return this.estadoFicha ? "Anulado" : "No Anulado";
    }

}
