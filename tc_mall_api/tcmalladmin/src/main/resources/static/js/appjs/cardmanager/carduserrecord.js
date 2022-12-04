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
        url: '/v1/tcmalladmin/cardUserRecordList',
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
                field: 'batchNo',
                title: '批次号',
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
                field: 'cardName',
                title: '卡名称',
                align: 'center'
            },
            {
                field: 'cardType',
                title: '卡类型',
                align: 'center',
                formatter:function(value){
                    if(value==1){
                        return '实物卡';
                    }else if(value==2){
                        return '虚拟卡';
                    }
                }
            },
            {
                field: 'faceValue',
                title: '面额',
                align: 'center'
            },
            {
                field: 'balance',
                title: '卡余额',
                align: 'center'
            },
            {
                field: 'bindUser',
                title: '绑定账号',
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
    var cardNo=$('#cardNo').val();
    var batchNo = $('#batchNo').val();
    var cardName = $('#cardName').val();
    var cardType = $('#cardType').val();
    var status = $('#status').val();
    var createType = $('#createType').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    var sTime = $('#sTime').val();
    var eTime = $('#eTime').val();
    var param = {
        cardNo:cardNo,
        batchNo:batchNo,
        cardName : cardName,
        cardType:cardType,
        status:status,
        createType : createType,
        startTime:startTime,
        endTime:endTime,
        sTime:sTime,
        eTime:eTime,
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