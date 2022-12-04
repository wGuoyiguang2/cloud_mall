/**
 *
 */

function initWaresTable() {
    $('#producttb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/contactUsList',
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        sidePagination: "server",
        queryParamsType:'limit',
        queryParams: queryParams,
        columns: [
            {
                field: 'id',
                title: 'ID',
                align: 'id'
            },
            {
                field: 'hotline',
                title: '热线电话',
                align: 'hotline'
            },
            {
                field: 'url',
                title: '二维码地址',
                align: 'url'
            },
            {
                title: '操作',
                align: 'center',
                valign: 'center',
                formatter: function (index,row) {
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                        + row.id
                        + '\')"><i class="fa fa-edit"></i></a> ';
                    return e;
                }
            }
        ]
    });
}

function initTable() {
    initWaresTable();
}

function queryParams(params) {
    var param = {
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}
initTable();

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/contactus/edit?id=' + id
    });
}