package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Equipo;
import modelo.EquiposFichaInternamiento;
import modelo.Ficha;
import modelo.FichaDAO;
import modelo.Persona;

/**
 *
 * @author Alexander Fuentes Medina
 */
@WebServlet(name = "srvFichas", urlPatterns = {"/srvFichas"})
public class srvFichas extends HttpServlet {

    List<EquiposFichaInternamiento> listaFichaInternamiento = new ArrayList<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "registrar":
                    registrar(request, response);
                    break;
                case "listarTecnicos":
                    listarTecnicos(response);
                    break;
                case "listarEquiposObsoletos":
                    listarEquiposObsoletos(response);
                    break;
                case "agregarEquiposFicha":
                    agregarEquiposFicha(request, response);
                    break;
                case "eliminarEquiposFicha":
                    eliminarEquiposFicha(request, response);
                    break;
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void registrar(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listarTecnicos(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            FichaDAO dao = new FichaDAO();
            List<Persona> persona = dao.listarTécnicos();
            Gson gson = new Gson();
            String json = gson.toJson(persona);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void printError(String msjError, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"msj\": \"" + msjError + "\"}");
    }

    private void listarEquiposObsoletos(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            FichaDAO dao = new FichaDAO();
            List<Equipo> equipo = dao.listarEquiposObsoletos();
            Gson gson = new Gson();
            String json = gson.toJson(equipo);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void agregarEquiposFicha(HttpServletRequest request, HttpServletResponse response) {
        FichaDAO dao;
        int pos = 0;
        int id = Integer.parseInt(request.getParameter("cod"));
        try {
            dao = new FichaDAO();
            if (listaFichaInternamiento.size() > 0) {
                for (int i = 0; i < listaFichaInternamiento.size(); i++) {
                    if (listaFichaInternamiento.get(i).getIdEquipo() == id) {
                        pos = i;
                    }
                }
                if (listaFichaInternamiento.get(pos).getIdEquipo() == id) {
                    //NO HACE FALTA TRATARLO.
                } else {
                    Equipo e = dao.listarEquiposById(id);
                    EquiposFichaInternamiento efi = new EquiposFichaInternamiento();
                    efi.setIdEquipo(e.getIdEquipo());
                    efi.setEquipo(e.getNombreBien());
                    efi.setMarca(e.getMarca().getMarca());
                    efi.setEstado(e.getEstado().getEstado());
                    listaFichaInternamiento.add(efi);
                }
            } else {
                Equipo e = dao.listarEquiposById(id);
                EquiposFichaInternamiento efi = new EquiposFichaInternamiento();
                efi.setIdEquipo(e.getIdEquipo());
                efi.setEquipo(e.getNombreBien());
                efi.setMarca(e.getMarca().getMarca());
                efi.setEstado(e.getEstado().getEstado());
                listaFichaInternamiento.add(efi);
            }
            response.sendRedirect("/SISCOOB/vista/ficha.jsp");
        } catch (IOException e) {
            System.out.println("No se pudo agregar el equipo al detalle: " + e.getLocalizedMessage());
        }
    }

    private void eliminarEquiposFicha(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int id = Integer.parseInt(request.getParameter("id"));
        for (int i = 0; i < listaFichaInternamiento.size(); i++) {
            if (listaFichaInternamiento.get(i).getIdEquipo() == id) {
                listaFichaInternamiento.remove(i);
            }
        }
        response.sendRedirect("/SISCOOB/vista/ficha.jsp");
    }
}
