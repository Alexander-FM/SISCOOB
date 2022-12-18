/* global moment */
//Declaramos nuestras variables globlales
var tabla = $("table#tablaFichas"),
        tablaDetalleFicha = $("table#tablaDetalleFicha"),
        mdlDetalleFicha = $("#mdlDetalleFicha");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_reportes');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a_registros = $('#a_reportes');
    a_registros.attr('class', 'nav-link active');
    let a = $('#li_mostrarReportes').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Mostrar Reportes');
    tabla.on("click", ".btn-info", function () {
        const idFicha = $(this).parents("tr").children()[0].textContent;
        listarDetalleFichas(idFicha);
        mdlDetalleFicha.modal({backdrop: 'static', keyboard: false});
    });
    listarFichas();
});

/**
 * Esta función retorna una lista de fichas de internamiento
 * @returns {List} retorna una lista.
 */
function listarFichas() {
    $.ajax({
        url: "../srvReportes?accion=listarFichas",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            let tpl = "";
            for (var i = 0; i < data.length; i++) {
                const date = moment(data[i].fechaCreacion, 'll').format('L');// queda pendiente de solucionar
                console.log(date);
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].idFicha + "</td>"
                        + "<td>" + data[i].numFicha + "</td>"
                        + "<td>" + data[i].persona.nombres + ' ' + data[i].persona.apellidos + "</td>"
                        + "<td>" + data[i].usuario.persona.nombres + ' ' + data[i].usuario.persona.apellidos + "</td>"
                        + "<td>" + data[i].fechaCreacion + "</td>"
                        + "<td>" + (data[i].estado === true ? '<h5><span class =\"badge badge-success\">No Anulada</span></h5>' : '<h5><span class =\"badge badge-danger\">Anulada</span></h5>')
                        + "</td>"
                        + "<td nowrap><button title=\"Ver Detalle\" class=\"btn btn-info\">"
                        + "<span class=\"fa fa-eye\"></span></button> "
                        + "<button id=\"btnAnularFicha\" onclick=\"anularFicha('" + data[i].idFicha
                        + "')\" title=\"Anular Ficha\" class=\"btn btn-danger\">"
                        + "<span class=\"fa fa-trash\"></span></button></td>"
                        + "</tr>";
            }
            tabla.find("tbody").html(tpl);
            tabla.dataTable();
        }
    });
}
/**
 * Este función muestra el detalle de la ficha de internamiento en un modal
 * @param idFicha
 */
function listarDetalleFichas(idFicha) {
    $.ajax({
        url: "../srvReportes?accion=listarDetalleFichas",
        type: 'POST',
        dataType: 'json',
        data: {idFicha: idFicha},
        success: function (data) {
            let tablaA = '';
            data.forEach(df => {
                tablaA += '<tr class=\"text-center\">';
                tablaA += '<td>' + df.equipo.nombreBien + '</td>';
                tablaA += '<td>' + df.equipo.marca.marca + '</td>';
                tablaA += '</tr>';
            });
            tablaDetalleFicha.find("tbody").html(tablaA);
            tablaDetalleFicha.dataTable();
        }
    });
}

/**
 * Este función anula la ficha de internamiento.
 * @param idFicha
 */
function anularFicha(idFicha) {
    swal({
        title: "Está seguro que desea anular la ficha de internamiento?",
        text: "Una vez anulada ya no se podrá deshacer los cambios",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "Si, confirmar!",
        cancelButtonText: "No, cancelar!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        url: "../srvReportes?accion=eliminar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idFicha},
                        success: function (data) {
                            swal("Buen Trabajo !", data.msj, "success");
                            listarFichas();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function reporte(accion) {
    $.get('../srvReportes?accion=listarReporteFichas', function (r) {
        if (r) {
            $('#accion').val(accion);
            $('#lista').val(JSON.stringify(r));
            $('#frmReporte').submit();
        } else {
            swal("Error", "el reporte no se ha generado debido a un error del servicio: " + r, "error");
            //alert('el reporte no se ha generado debido a un error del servicio:' + r);
        }
    });
}