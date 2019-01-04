const projectAjaxUrl = "ajax/profile/projects/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: projectAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(projectAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: projectAjaxUrl,
        datatableOpts: {
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === 'display') {
                            return date.replace('T', ' ').substr(0, 16);
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "sum"
                },
                {
                    "render": renderEditBtn,
                    "defaultContent": "",
                    "orderable": false
                },
                {
                    "render": renderDeleteBtn,
                    "defaultContent": "",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-projectExcess", data.excess);
            },
        },
        updateTable: updateFilteredTable
    });
});