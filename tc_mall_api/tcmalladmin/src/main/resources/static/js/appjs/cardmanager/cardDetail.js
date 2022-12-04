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
    $('#cardtb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/cardDetailList',
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
                title: '序号',
                align: 'center'
            },
            {
                field: 'orderNo',
                title: '订单号',
                align: 'center'
            },
            {
                field: 'orderMoney',
                title: '订单金额',
                align: 'center'
            },
            {
                field: 'cashMoney',
                title: '现金',
                align: 'center'
            },
            {
                field: 'cardMoney',
                title: '购物卡',
                align: 'center'
            },
            {
                field: 'operateType',
                title: '操作类型',
                align: 'center'
            },
            {
                field: 'ctime',
                title: '操作时间',
                align: 'center'
            }
        ]
    });
}
function queryParams(params) {
    var cardNo = $('#cardNo').val();
    var param = {
        cardNo : cardNo,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

initWaresTable();

function searcher() {
    $('#cardtb').bootstrapTable('destroy');
    initWaresTable();
}