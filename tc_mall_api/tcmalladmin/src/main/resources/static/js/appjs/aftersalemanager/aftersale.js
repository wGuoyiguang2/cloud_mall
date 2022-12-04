function initWaresTable(){
    $('#aftersaletb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/afterSaleList0',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        searchOnEnterKey: true,
        columns: [
            {
                field: 'id',
                title: 'ID',
                align: 'center'
            },
            {
                field: 'userId',
                title: '用户ID',
                align: 'center'
            },
            {
                field: 'orderSn',
                title: '订单号',
                align: 'center'
            },
            {
                field: 'childTradeNo',
                title: '京东订单号',
                align: 'center'
            },
            {
                field: 'seq',
                title: '售后序号',
                align: 'center'
            },
            {
                field: 'serviceNo',
                title: '服务单号',
                align: 'center'
            },
            {
                field: 'name',
                title: '联系人',
                align: 'center'
            },
            {
                field: 'phone',
                title: '电话',
                align: 'center'
            },
            {
                field: 'productId',
                title: '商品ID',
                align: 'center'
            },
            {
                field: 'productName',
                title: '商品名称',
                align: 'center'
            },
            {
                field: 'price',
                title: '商品价格',
                align: 'center'
            },
            {
                field: 'count',
                title: '商品数量',
                align: 'center'
            },
            {
                field: 'serviceType',
                title: '服务类型',
                align: 'center',
                formatter:function (value) {
                    if(value==10){
                        return "退货";
                    }else if(value==20){
                        return "换货";
                    }else if(value==30){
                        return "维修";
                    }
                }
            },
            {
                field: 'reason',
                title: '退货原因',
                align: 'center'
            },
            {
                field: 'backType',
                title: '退回方式',
                align: 'center',
                formatter:function (value) {
                    if(value==4){
                        return "上门取件";
                    }else if(value==40){
                        return "客户发货";
                    }else if(value==7){
                        return "客户送货";
                    }
                }
            },
            {
                field: 'returnType',
                title: '返件方式',
                align: 'center',
                formatter:function (value) {
                    if(value==10){
                        return "自营配送";
                    }else if(value==20){
                        return "第三方配送";
                    }
                }
            },
            {
                field: 'backAddr',
                title: '取货地址',
                align: 'center'
            },
            {
                field: 'returnAddr',
                title: '收货地址',
                align: 'center'
            },
            {
                field: 'status',
                title: '售后状态',
                align: 'center',
                formatter:function (value) {
                    if(value==10){
                        return "申请阶段";
                    }else if(value==20){
                        return "审核不通过";
                    }else if(value==21){
                        return "客服审核";
                    }else if(value==22){
                        return "商家审核";
                    }else if(value==31){
                        return "京东收货";
                    }else if(value==32){
                        return "商家收货";
                    }else if(value==33){
                        return "京东处理";
                    }else if(value==34){
                        return "商家处理";
                    }else if(value==40){
                        return "用户确认";
                    }else if(value==50){
                        return "完成";
                    }else if(value==60){
                        return "取消";
                    }else if(value==100){
                        return "申请售后失败";
                    }
                }
            },
            {
                field: 'remarks',
                title: '描述',
                align: 'center'
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'center'
            },
            {
                field: 'utime',
                title: '更新时间',
                align: 'center'
            }
        ]
    });
}
function queryParams(params) {
    var orderSn = $('#orderSn').val();
    var userId=$('#userId').val();
    var childTradeNo=$("#childTradeNo").val();
    var param = {
        childTradeNo:childTradeNo,
        userId:userId,
        orderSn : orderSn,
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
    $('#aftersaletb').bootstrapTable('destroy');
    initWaresTable();
}