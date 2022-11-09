package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DetalleFicha;
import modelo.Ficha;
import modelo.FichaDAO;
import modelo.FichaReporte;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Alexander Fuentes Medina
 */
@WebServlet(name = "srvReportes", urlPatterns = {"/srvReportes"})
public class srvReportes extends HttpServlet {

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
                case "eliminar":
                    eliminar(request, response);
                    break;
                case "listarReporteFichas":
                    listarReporteFichas(response);
                    break;
                case "exportarReporteFichas":
                    this.exportarReporteFichas(request, response);
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

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Ficha ficha;
        if (request.getParameter("id") != null) {
            ficha = new Ficha();
            ficha.setIdFicha(Integer.parseInt(request.getParameter("id")));
            try {
                FichaDAO dao = new FichaDAO();
                dao.anularFicha(ficha);
                this.printMessage("Se anulo la ficha de internamiento", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se obtuvo los parámetros", false, response);
        }
    }

    private void exportarReporteFichas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        try {
            InputStream logoEmpresa = this.getServletConfig()
                    .getServletContext()
                    .getResourceAsStream("reportesJasper/img/onpe_logo.png"),
                    logoFooter = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/img/check.png"),
                    reporteFichas = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/reporteFichasSiscoob.jasper");
            if (logoEmpresa != null && logoFooter != null && reporteFichas != null) {
                String jsonLista = request.getParameter("lista");
                Gson gson = new Gson();
                List<FichaReporte> reporteFicha = new ArrayList<>();
                List<FichaReporte> reporteFicha2 = new ArrayList<>();

                reporteFicha.add(new FichaReporte());
                reporteFicha2 = gson.fromJson(jsonLista, new TypeToken<List<FichaReporte>>() {
                }.getType());
                reporteFicha.addAll(reporteFicha2);

                JasperReport report = (JasperReport) JRLoader.loadObject(reporteFichas);
                JRBeanArrayDataSource ds = new JRBeanArrayDataSource(reporteFicha.toArray());

                Map<String, Object> parameters = new HashMap();
                parameters.put("ds", ds);
                parameters.put("logoEmpresa", logoEmpresa);
                parameters.put("logoOpcional", logoFooter);
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "inline; filename=ReporteFichasInternamiento.pdf");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush();
                out.close();
            } else {
                response.setContentType("text/plain");
                out.println("no se pudo generar el reporte");
                out.println("esto puede debrse a que la lista de datos no fue recibida o el archivo plantilla del reporte no se ha encontrado");
                out.println("contacte a soporte");
            }
        } catch (Exception e) {
            response.setContentType("text/plain");
            out.print("ocurrió un error al intentar generar el reporte:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listarReporteFichas(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            FichaDAO dao = new FichaDAO();
            List<FichaReporte> fr = dao.listarReporteFichas();
            Gson gson = new Gson();
            String json = gson.toJson(fr);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

}
