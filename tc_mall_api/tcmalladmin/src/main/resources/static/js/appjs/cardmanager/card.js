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
        url: '/v1/tcmalladmin/cardList',
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
                align: 'center'
            },
            {
                field: 'cardNo',
                title: '卡号',
                align: 'center',
                formatter:function(value,row){
                    return '<u onclick="cardDetail(\''+row.batchNo+'\',\''+row.cardNo+'\')" style="color:blue;cursor:pointer">'+row.cardNo+'</u>';
                }
            },
            {
                field: 'passwd',
                title: '密码',
                align: 'center'
            },
            {
                field: 'bindUser',
                title: '绑定账号',
                align: 'center'
            },
            {
                field: 'bindVender',
                title: '是否绑定大客户',
                align: 'center'
            },
            {
                field: 'balance',
                title: '卡余额',
                align: 'center'
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'center'
            },
            {
                field: 'bindType',
                title: '绑定方式',
                align: 'center'
            },
            {
                field: 'status',
                title: '状态',
                align: 'center'
            }
        ]
    });
}
function queryParams(params) {
    var status = $('#status').val();
    var cardNo = $('#cardNo').val();
    var bindUser = $('#bindUser').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    var batchNo = $('#batchNo').val();
    var bindVender = $('#bindVender').val();
    var cardNoStart=$('#cardNoStart').val();
    var cardNoEnd=$('#cardNoEnd').val();
    var param = {
        bindVender:bindVender,
        status:status,
        batchNo:batchNo,
        cardNo : cardNo,
        cardNoStart:cardNoStart,
        cardNoEnd:cardNoEnd,
        bindUser : bindUser,
        startTime:startTime,
        endTime:endTime,
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
function getSelectedCardNos(){
    var rows = $('#cardtb').bootstrapTable('getSelections');
    if(rows==null){
        return null;
    }
    var str='';
    for(var i=0;i<rows.length;i++){
        if(i==0){
            str=str+rows[i].cardNo;
        }else {
            str = str +","+ rows[i].cardNo;
        }
    }
    return str;
}
function cardStart() {
    var cardNos=getSelectedCardNos();
    if(cardNos==null||cardNos==''){
        alert("请选择购物卡！")
        return;
    }
    layer.open({
        type : 2,
        title : '激活',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardStartHtml?cardNos='+cardNos
    });
}
function cardBind() {
    var cardNos=getSelectedCardNos();
    if(cardNos==null||cardNos==''){
        alert("请选择购物卡！")
        return;
    }
    layer.open({
        type : 2,
        title : '绑定',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardBindHtml?cardNos='+cardNos
    });
}
function operationRecord(){
    layer.open({
        type : 2,
        title : '操作历史记录',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardAdminRecordHtml?batchNo='+$("#batchNo").val()
    });
}
function cardAbandon() {
    var cardNos=getSelectedCardNos();
    if(cardNos==null||cardNos==''){
        alert("请选择购物卡！")
        return;
    }
    layer.open({
        type : 2,
        title : '废弃',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardAbandonHtml?cardNos='+cardNos
    });
}
function cardExport() {
    var cardNos=getSelectedCardNos();
    if(cardNos==null||cardNos==''){
        alert("请选择购物卡！")
        return;
    }
    layer.open({
        type : 2,
        title : '导出',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardExprotHtml?cardNos='+cardNos
    });
}

function cardDetail(batchNo,cardNo){
    layer.open({
        type : 2,
        title : '购物卡详情',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/card/detail?cardNo='+cardNo+'&batchNo='+batchNo
    });
}