<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true" %>
<%
    if (request.getSession().getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <%@include file="plus/head.jsp" %>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <!-- Navbar -->
            <%@include file="plus/menuSuperior.jsp" %>            
            <!-- /.navbar -->

            <!-- Main Sidebar Container -->
            <%@include  file="plus/menuLateral.jsp"%>
            <!-- /. Main Sidebar Container -->

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <div class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1 class="m-0 text-dark">Reportes</h1>
                            </div><!-- /.col -->
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Reportes</a></li>
                                    <li class="breadcrumb-item active">Mostrar Reportes</li>
                                </ol>
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content-header -->
                <div class="modal fade" data-backdrop="static" data-keyboard="false" id="mdlDetalleFicha">
                    <input type="hidden" id="idPersona" value="0">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Detalle Ficha de Internamiento</h4>
                            </div>
                            <div class="modal-body">
                                <table id="tablaDetalleFicha" class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th class="text-center">Equipo</th>
                                            <th class="text-center">Marca</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th class="text-center">Equipo</th>
                                            <th class="text-center">Marca</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="modal-footer clearfix">                               
                                <button type="button" data-dismiss="modal" class="btn btn-danger">Cerrar</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- Main content -->
                <div class="content">
                    <div class="container-fluid">

                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                <div class="card card-primary card-outline">
                                    <div class="card-header">
                                        <h3 class="card-title">Listado De Fichas Internamiento</h3>
                                    </div>
                                    <!-- /.card-header -->
                                    <div class="card-body">
                                        <div class="form-group">
                                            <form target="_blank" action="../srvReportes" id="frmReporte" method="post">
                                                <input type="hidden" name="accion" id="accion">
                                                <button onclick="reporte('exportarReporteFichas')" type="button" class="btn btn-sm btn-outline-primary"><i class="fas fa-file-pdf"></i> Exportar PDF</button>
                                                <input type="hidden" name="lista" id="lista">
                                            </form>
                                        </div>
                                        <table id="tablaFichas" class="table table-responsive-lg table-bordered table-hover">
                                            <thead>
                                                <tr class="text-center">
                                                    <th>Id</th>
                                                    <th>N.?? Ficha</th>                                                
                                                    <th>T??cnico</th>                                                
                                                    <th>Usuario</th>
                                                    <th>Fecha de Registro</th>
                                                    <th>Estado</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                            <tfoot>
                                                <tr class="text-center">
                                                    <th>Id</th>
                                                    <th>N.?? Ficha</th>                                                
                                                    <th>T??cnico</th>                                                
                                                    <th>Usuario</th>
                                                    <th>Fecha de Registro</th>
                                                    <th>Estado</th>
                                                    <th>Acciones</th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                                <!-- /.card -->
                            </div>   
                        </div>
                        <!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <!-- Main Footer -->
            <%@include file="plus/footer.jsp" %>            
            <!-- /.Main Footer -->
        </div>
        <!-- ./wrapper -->

        <!-- REQUIRED SCRIPTS -->
        <!-- scripts -->
        <%@include file="plus/scripts.jsp" %>
        <script src="../js/scriptMostrarReportes.js"></script>
        <script src="../js/scriptAcceso.js" type="text/javascript"></script>
        <!-- /.scripts -->
    </body>
</html>
<%
    } else {
        response.sendRedirect("../index.jsp");
    }
%>