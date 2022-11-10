$(document).ready(() => {
    // BLOQUEAR OPCIONES DEPENDE DEL ROL
    let rol = $("#rol_persona").val();
    if (rol === 'Verificador' || rol === 'Consolidador') {
        $('#li_fichas').remove();
        $('#li_grupo_reportes').remove();
    }
    if (rol === 'Técnico') {
        $('#li_fichas').remove();
        $('#li_personas').remove();
        $('#li_usuarios').remove();
        $('#li_grupo_reportes').remove();
    }
});


