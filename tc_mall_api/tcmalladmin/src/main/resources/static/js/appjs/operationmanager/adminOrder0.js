
function initWaresTable(){
    $('#ordertb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/admin/venderOrderList',
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
                field: 'payType',
                title: '结算方式',
                align: 'payType',
                formatter: function(value, row, index) {
                    if(value==0){
                        return "实时结算";
                    }else if(value==1){
                        return "按月结算";
                    }else if(value==2){
                        return "预付款";
                    }
                }
            },
            {
                field: 'soldMoney',
                title: '累计结算金额',
                align: 'soldMoney',
                formatter: function(value, row, index) {
                    if(value==null){
                        return "0.00";
                    }else{
                        return Number(value).toFixed(2);
                    }
                }
            },
            {
                field: 'accountBalance',
                title: '账户剩余金额',
                align: 'accountBalance',
                formatter: function(value, row, index) {
                    if(value==null){
                        return "0.00";
                    }else{
                        return Number(value).toFixed(2);
                    }
                }
            },
            {
                field: 'paidMoney',
                title: '已结算金额',
                align: 'paidMoney',
                formatter: function(value, row, index) {
                    if(value==null){
                        return "0.00";
                    }else{
                        return Number(value).toFixed(2);
                    }
                }
            },
            {
                field: 'unPaidMoney',
                title: '未结算金额',
                align: 'unPaidMoney',
                formatter: function(value, row, index) {
                    if(value==null){
                        return "0.00";
                    }else{
                        return Number(value).toFixed(2);
                    }
                }
            },
            {
                field: 'orderCount',
                title: '订单记录',
                align: 'orderCount',
                formatter: function(value, row, index) {
                    if(value==null){
                        value=0
                    }
                    var e = '<a href="#" onclick="showVenderOrderList(\''+ row.venderId+ '\')">'+value+'单</a> ';
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
            searcher()
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
    $('#ordertb').bootstrapTable('destroy');
    initWaresTable();
}

function showVenderOrderList(id) {
    layer.open({
        type: 2,
        title: '订单列表',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/admin/orderHtml2?venderId=' + id
    });
}