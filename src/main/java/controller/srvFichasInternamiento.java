/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DetalleFicha;
import modelo.Ficha;
import modelo.FichaDAO;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "srvFichasInternamiento", urlPatterns = {"/srvFichasInternamiento"})
public class srvFichasInternamiento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "listarFichas":
                    listarFichas(response);
                    break;
                case "listarDetalleFichas":
                    listarDetalleFichas(request, response);
                    break;
                case "registrar":
                    registrar(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
                case "leer":
                    leer(request, response);
                    break;
                case "eliminar":
                    eliminar(request, response);
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

    private void listarFichas(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            FichaDAO dao = new FichaDAO();
            List<Ficha> ven = dao.listarFichas();
            Gson gson = new Gson();
            String json = gson.toJson(ven);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }
    private void printError(String msjError, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"msj\": \"" + msjError + "\"}");
    }

    private void printMessage(String msj, boolean rpt, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"rpt\": " + rpt + ", \"msj\": \"" + msj + "\"}");
    }

    private void listarDetalleFichas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FichaDAO dao;
        Ficha ficha;
        List<DetalleFicha> detalles;
        if (request.getParameter("idFicha") != null) {
            ficha = new Ficha();
            ficha.setIdFicha(Integer.parseInt(request.getParameter("idFicha")));
            try {
                dao = new FichaDAO();
                detalles = dao.listarDetallesVentas(ficha);
                String json = new Gson().toJson(detalles);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro de la ficha", false, response);
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void leer(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Ficha venta;
        if (request.getParameter("id") != null) {
            venta = new Ficha();
            venta.setIdFicha(Integer.parseInt(request.getParameter("id")));
            try {
                FichaDAO dao = new FichaDAO();
                dao.anularFicha(venta);
                this.printMessage("Se anulo la ficha de internamiento", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se obtuvo los parámetros", false, response);
        }
    }

}
