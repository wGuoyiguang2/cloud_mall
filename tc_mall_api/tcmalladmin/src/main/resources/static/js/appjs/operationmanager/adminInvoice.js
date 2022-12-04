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
        url: '/v1/tcmalladmin/admin/invoiceList2',
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
                field: 'invoiceId',
                title: '发票ID',
                align: 'invoiceId'
            },
            {
                field: 'productName',
                title: '发票明细',
                align: 'productName'
            },
            {
                field: 'orderNo',
                title: '订单号',
                align: 'orderNo'
            },
            {
                field: 'payNo',
                title: '支付订单号',
                align: 'payNo'
            },
            {
                field: 'invoiceHead',
                title: '发票抬头',
                align: 'invoiceHead'
            },
            {
                field: 'email',
                title: '邮寄地址',
                align: 'email'
            },
            {
                field: 'invoiceType',
                title: '修改状态',
                align: 'invoiceType',
                formatter: function(value, row, index) {
                    if(value=='2'){
                        return "已修改";
                    }else{
                        return "未修改";
                    }
                }
            },
            {
                field: 'createTime',
                title: '创建时间',
                align: 'createTime'
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
    var venderid=$('#venderId').val();
    var orderNo = $('#orderNo').val();
    var invoiceId = $('#invoiceId').val();
    var productId = $('#productId').val();
    var invoiceHead = $('#invoiceHead').val();
    var param = {
        venderid:venderid,
        orderNo : orderNo,
        invoiceId : invoiceId,
        productId : productId,
        invoiceHead : invoiceHead,
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
    $('#invoicetb').bootstrapTable('destroy');
    initWaresTable();
}
