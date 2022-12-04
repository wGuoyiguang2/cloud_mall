
function initWaresTable(){
    $('#invoicetb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/admin/invoiceList',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        searchOnEnterKey: true,
        columns: [
            {
                field: 'venderId',
                title: '客户ID',
                align: 'venderId'
            },
            {
                field: 'venderName',
                title: '客户名称',
                align: 'venderName'
            },
            {
                field: 'pricePercent',
                title: '商品批发系数',
                align: 'pricePercent'
            },
            {
                field: 'opened',
                title: '已开票数量',
                align: 'opened'
            },
            {
                field: 'canceled',
                title: '已撤回发票数量',
                align: 'canceled'
            },
            {
                field: 'count',
                title: '开票记录',
                align: 'count',
                formatter: function(value, row, index) {
                    var e = '<a href="#" onclick="showVenderOrderList(\''+ row.venderId+ '\')">'+value+'张</a> ';
                    return e;
                }
            }
        ],
        //保存的使用
        onEditableSave: function (field, row, oldValue, $el) {
            //可进行异步操作
            $.ajax({
                type: "post",
                url: "/v1/tcmalladmin/operation/productedit",
                dataType: 'JSON',
                contentType : 'application/json',
                data : JSON.stringify(row),
                success: function (data) {
                    if (data.error == 0) {
                        parent.layer.msg("操作成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }
                },
                error: function () {
                    alert('编辑失败');
                },
                complete: function () {

                }

            });
            $('#invoicetb').bootstrapTable('refresh');
        },
        pagination:true
    });
}
function queryParams(params) {
    var venderId=$('#venderId').val();
    var name=$('#name').val();
    var pricePercent=$('#pricePercent').val();
    var param = {
        name:name,
        venderId:venderId,
        pricePercent:pricePercent,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initWaresTable();
}
initTable();

function searcher(flag) {
    if(flag==1){
        $('#pricePercent').val("");
    }else if(flag==2){
        $('#venderId').val("");
        $('#name').val("");
    }
    $('#invoicetb').bootstrapTable('destroy');
    initWaresTable();
}

function showVenderOrderList(id) {
    layer.open({
        type: 2,
        title: '发票列表',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/admin/invoiceHtml2?venderId=' + id
    });
}