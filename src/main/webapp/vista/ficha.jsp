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
                                <h1 class="m-0 text-dark">Fichas Internamiento</h1>
                            </div><!-- /.col -->
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Registros</a></li>
                                    <li class="breadcrumb-item active">Fichas</li>
                                </ol>
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content-header -->

                <!-- Main content -->
                <div class="content">
                    <div class="container-fluid">
                        <div class="card card-dark">
                            <div class="card-header">
                                <h3 class="card-title">Registrar Ficha de Internamiento</h3>
                                <div class="card-tools">
                                    <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                                    <button type="button" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times"></i></button>
                                </div> 
                            </div>
                            <div class="card-body">
                                <form class="form" id="frmCategorias">
                                    <input type="hidden" id="idFicha" value="0">
                                    <div class="row">
                                        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">N.º Ficha</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-file-signature"></i></span>
                                                    </div>
                                                    <input type="text" name="numFicha" id="numFicha" class="form-control" placeholder="Ingresar N.º Ficha">
                                                </div>
                                            </div>                                            
                                        </div>
                                        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3"> 
                                            <div class="form-group">
                                                <label id="etiqueta">Seleccionar Técnico</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-user"></i></span>
                                                    </div>
                                                    <select id="cboPersonas" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">                                               
                                                        <!-- Cargar desde la base de datos -->
                                                        <option>Cargando.....</option>
                                                    </select> 
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Fecha de Registro</label>
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="fas fa-calendar-day"></i></span>
                                                    </div>
                                                    <input type="date" name="numFicha" id="numFicha" class="form-control" placeholder="Ingresar N.º Ficha">
                                                </div>
                                            </div>                                            
                                        </div>
                                        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3">
                                            <div class="form-group">
                                                <label style="font-family: sans-serif">Estado</label>
                                                <div class="input-group">                                             
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">
                                                            <input type="checkbox" checked="" disabled="" name="chkEstadoFicha" id="chkEstadoCategoria">
                                                        </span>
                                                    </div>
                                                    <label type="text" class="form-control">Activo / Inactivo</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button id="btn-save" type="submit" class="btn btn-outline-dark float-right">Registrar Ficha <span class="fas fa-save"></span></button>
                                </form>

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
        <script src="../js/scriptFichas.js" type="text/javascript"></script>
        <!-- /.scripts -->
    </body>
</html>
<%
    } else {
        response.sendRedirect("../index.jsp");
    }
%>