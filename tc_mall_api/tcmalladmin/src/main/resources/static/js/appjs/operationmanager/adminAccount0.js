//初始化日期插件
$('.form_datetime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    minView: 1
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
        url: '/v1/tcmalladmin/adminAccountList1',
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
                field: 'needPayMoney',
                title: '待结算金额',
                align: 'needPayMoney'
            },
            {
                field: 'hasPaidMoney',
                title: '已结算金额',
                align: 'hasPaidMoney'
            },
            {
                field: 'sumPaidMoney',
                title: '累计结算金额',
                align: 'sumPaidMoney'
            },
            {
                field: 'settlementType',
                title: '结算类型',
                align: 'settlementType',
                formatter: function(value, row, index) {
                    if(value==1){
                        return "按月结算"
                    }else if(value==2){
                        return "预付款结算"
                    }
                }
            },
            {
                field: 'count',
                title: '账单明细',
                align: 'count',
                formatter: function(value, row, index) {
                    if(value==null){
                        value=0
                    }
                    var e = '<a href="#" onclick="showVenderAccountList(\''+ row.venderId+ '\')">'+value+'单</a> ';
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
            searcher();
        },
        pagination:true
    });
}
function queryParams(params) {
    var venderId0=$("#venderId0").val();
    var param = {
        venderId:venderId0,
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

function settleAccount(){
    var venderId=$("#venderId2").val();
    var startTime=$("#startMonth").val();
    var endTime=$("#endMonth").val();
    if(startTime==null||startTime==''||endTime==null||endTime==''||venderId==null||venderId==''){
        alert("开始时间/结束时间/客户ID不能为空！")
        return;
    }
    if(!confirm("确定要结算客户号为："+venderId+"的时间从"+startTime+"到"+endTime+"的账单？")){
        return;
    }
    $.ajax({
        type: "POST",
        url: "/v1/tcmalladmin/settleAdminAccount",
        data: {startTime:startTime, endTime:endTime,venderId:venderId},
        success: function(data){
            if(data.error==0){
                alert(data.msg);
                $('#accounttb').bootstrapTable('destroy');
                initWaresTable();
            }else{
                alert(data.msg);
            }
        }
    });
}

function showVenderAccountList(id) {
    layer.open({
        type: 2,
        title: '账单列表',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/accountAdminHtml?venderId=' + id
    });
}