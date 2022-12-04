/**
 *
 */

function initWaresTable(){
    $('#productremovetb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageNumber:1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/productremove/list',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        searchOnEnterKey: true,
        columns: [
            {
                checkbox : true
            },
            {
                field: 'id',
                title: 'ID',
                align: 'id'
            },
            {
                field: 'sku',
                title: '商品ID',
                align: 'sku'
            },
            {
                field: 'name',
                title: '商品名',
                align: 'name'
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'ctime',
            },
            {
                field: 'utime',
                title: '修改时间',
                align: 'utime'
            },
        ]
    });
}
function queryParams(params) {
    var search_name = $('#name_search').val();//商品名称
    var param = {
        name : search_name,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initWaresTable();
}
initTable();

function searcher() {
    $('#productremovetb').bootstrapTable('destroy');
    initTable();
}

function batchRemove() {
    var rows = $('#productremovetb').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要还原的商品");
        return;
    }
    layer.confirm("确认要还原选中的" + rows.length + "个商品吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function(i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type : 'POST',
            data: JSON.stringify(ids),
            contentType: "application/json",
            url : '/v1/tcmalladmin/productremove/remove',
            success : function(r) {
                if (r.code == 0) {
                    layer.msg("操作成功");
                    $('#productremovetb').bootstrapTable('refresh');
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {});
}