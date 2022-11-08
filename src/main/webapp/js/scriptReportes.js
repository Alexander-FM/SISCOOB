/* global moment */
var tabla = $("table#tablaFichas"),
        tablaDetalleFicha = $("table#tablaDetalleFicha"),
        mdlDetalleFicha = $("table#mdlDetalleFicha");
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
        url: "../srvFichasInternamiento?accion=listarFichas",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            let tpl = "";
            for (var i = 0; i < data.length; i++) {
                const date = moment(data[i].fechaCreacion).format('DD-MM-YYYY');
                console.log(date);
                tpl += "<tr>"
                        + "<td>" + data[i].idFicha + "</td>"
                        + "<td>" + data[i].numFicha + "</td>"
                        + "<td>" + data[i].persona.nombres + ' ' + data[i].persona.apellidos + "</td>"
                        + "<td>" + data[i].usuario.persona.nombres + ' ' + data[i].usuario.persona.apellidos + "</td>"
                        + "<td>" + data[i].fechaCreacion + "</td>"
                        + "<td>" + (data[i].estado === true
                                ? '<span class =\"label label-success\">No Anulada</span>'
                                : '<span class =\"label label-danger\">Anulada</span>')
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
        url: "../srvFichasInternamiento?accion=listarDetalleFichas",
        type: 'POST',
        dataType: 'json',
        data: {idFicha: idFicha},
        success: function (data) {
            let tablaA = '';
            data.forEach(df => {
                tablaA += '<tr>';
                tablaA += '<td>' + df.equipo.nombreBien + '</td>';
                tablaA += '<td>' + df.marca.marca + '</td>';
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
                        url: "../srvFichasInternamiento?accion=eliminar",
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