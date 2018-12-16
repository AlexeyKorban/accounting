function makeEditable() {
    $(".delete").click(function () {
        deleteRow($(this).attr("id"));
    })
}

function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () {
            updateTable();
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    var form = $("#detailsFrom");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: from.serialize(),
        success: function () {
            $("#editFow").modal("hide");
            updateTable();

        }
    });
}