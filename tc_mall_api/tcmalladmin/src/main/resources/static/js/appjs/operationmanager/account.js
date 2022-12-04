//初始化日期插件
$('.form_datetime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1
});
function initWaresTable(){
    $('#accounttb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/accountList',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        searchOnEnterKey: true,
        columns: [
            /*{
                checkbox : true
            },
            {
                field: 'venderId',
                title: '客户ID',
                align: 'venderId'
            },*/
            {
                field: 'orderNo',
                title: '订单号',
                align: 'orderNo'
            },
            {
                field: 'orderPrice',
                title: '订单金额',
                align: 'orderPrice'
            },
            {
                field: 'agreePrice',
                title: '协议价',
                align: 'agreePrice'
            },
            {
                field: 'freight',
                title: '运费',
                align: 'freight'
            },
            {
                field: 'payPrice',
                title: '支付金额',
                align: 'payPrice'
            },
            {
                field: 'cardPrice',
                title: '卡支付金额',
                align: 'center'
            },
            {
                field: 'tradeType',
                title: '交易类型',
                align: 'tradeType',
                formatter: function(value, row, index) {
                    if(value=='0'){
                        return "扣款";
                    }else if(value=='1'){
                        return "退款";
                    }
                }
            },
            {
                field: 'isSettle',
                title: '是否已结算',
                align: 'isSettle',
                formatter: function(value, row, index) {
                    if(value=='0'){
                        return "未结算";
                    }else if(value=='1'){
                        return "已结算";
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
            $('#accounttb').bootstrapTable('refresh');
        },
        pagination:true
    });
}
function queryParams(params) {
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    var orderNo = $('#orderNo').val();
    var isSettle = $('#isSettle').val();
    var param = {
        startTime:startTime,
        endTime:endTime,
        orderNo : orderNo,
        isSettle : isSettle,
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
    $('#accounttb').bootstrapTable('destroy');
    initWaresTable();
}