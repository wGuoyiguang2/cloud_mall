/**
 *
 */

function initSegmentTable(){
    var policyid = $('#policyid').val();
    $('#segmenttb').bootstrapTable({
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
        url: '/v1/tcmalladmin/pricepolicymanager/rate/list?policyid=' + policyid,
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        columns: [
            {
                checkbox : true
            },
            {
                field: 'id',
                title: 'ID',
                align: 'center'
            },
            {
                field: 'startRate',
                title: '起始比率(包含)',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value == null) {
                        return '负无穷';
                    }else {
                        return value;
                    }
                }
            },
            {
                field: 'endRate',
                title: '结束比率(不包含)',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value == null) {
                        return '正无穷';
                    }else {
                        return value;
                    }
                }
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'center',
            },
            {
                field: 'utime',
                title: '修改时间',
                align: 'center'
            },
        ],
    });
}

function initTable() {
    initSegmentTable();
}
initTable();

function queryParams(params) {
    var param = {
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function add(){
    var policyid = $('#policyid').val();
    layer.open({
        type : 2,
        title : '比率区间添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/pricepolicymanager/rate/add?policyid=" + policyid
    });
}

function batchRemove() {
    var policyid = $('#policyid').val();
    var rows = $('#segmenttb').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的分段");
        return;
    }
    layer.confirm("确认要删除选中的" + rows.length + "个分段吗?", {
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
            url : '/v1/tcmalladmin/pricepolicymanager/rate/remove/' + policyid,
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    $('#segmenttb').bootstrapTable('refresh');
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {});
}