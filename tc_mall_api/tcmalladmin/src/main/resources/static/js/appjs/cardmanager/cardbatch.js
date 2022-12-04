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
    $('#cardbatchtb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/cardBatchList',
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
                field: 'venderid',
                title: '大客户ID',
                align: 'center'
            },
            {
                field: 'batchNo',
                title: '卡批次号',
                align: 'center',
                formatter:function(value){
                    return '<u onclick="listCard(\''+value+'\')" style="color:blue;cursor:pointer">'+value+'</u>';
                }
            },
            {
                field: 'cardName',
                title: '卡名称',
                align: 'center'
            },
            {
                field: 'createType',
                title: '创建规则',
                align: 'center',
                formatter:function(value){
                    if(value=='1'){
                        return "手动创建"
                    }else if(value=='2'){
                        return "自动创建"
                    }
                }
            },
            {
                field: 'cardType',
                title: '卡类型',
                align: 'center',
                formatter:function(value){
                    if(value=='1'){
                        return "实物卡"
                    }else if(value=='2'){
                        return "虚拟卡"
                    }
                }
            },
            {
                field: 'bindVender',
                title: '是否绑定大客户',
                align: 'center',
                formatter:function(value){
                    if(value=='0'){
                        return "否"
                    }else if(value=='1'){
                        return "是"
                    }
                }
            },
            {
                field: 'count',
                title: '生成数量',
                align: 'center'
            },
            {
                field: 'faceValue',
                title: '单张金额',
                align: 'center'
            },
            {
                field: 'sumMoney',
                title: '总金额',
                align: 'center'
            },
            {
                field: 'usedMoney',
                title: '已用金额',
                align: 'center'
            },
            {
                field: 'balance',
                title: '剩余金额',
                align: 'center'
            },
            {
                field: 'bindCount',
                title: '已绑定数量',
                align: 'center'
            },
            {
                field: 'unBindCount',
                title: '未绑定数量',
                align: 'center'
            },
            {
                field: 'activedCount',
                title: '已激活数量',
                align: 'center'
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'center'
            },
            {
                field: 'stime',
                title: '有效期',
                align: 'center',
                formatter:function(index,row){
                    return row.stime+' - '+row.etime;
                }
            },
            {
                title: '操作',
                align: 'center',
                valign: 'center',
                formatter: function (index,row) {
                    var a = '<u class="btn btn-primary btn-sm ' + s_operate_h + '" href="#" mce_href="#" title="操作" onclick="operate(\''+row.id+'\')"><i class="fa fa-edit"></i></u> ';
                    return a;
                }
            }
        ],
    });
}
function operate(id){
    layer.open({
        type : 2,
        title : '操作',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardbatch/operateHtml?id='+id
    });
}
function operationRecord(){
    layer.open({
        type : 2,
        title : '操作历史记录',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardAdminRecordHtml'
    });
}
function listCard(batchNo){
    layer.open({
        type : 2,
        title : '购物卡列表',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardBatch/cardList?batchNo='+batchNo
    });
}
function addCard(){
    layer.open({
        type : 2,
        title : '创建购物卡',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/addCardHtml'
    });
}
function editCardBatch(id){
    layer.open({
        type : 2,
        title : '购物卡批次操作记录',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/batchRecordHtml?id='+id
    });
}
function queryParams(params) {
    var batchNo = $('#batchNo').val();
    var cardName = $('#cardName').val();
    var cardType = $('#cardType').val();
    var createType = $('#createType').val();
    var stime = $('#sTime').val();
    var etime = $('#eTime').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    var faceValue = $('#faceValue').val();
    var venderId=$('#venderId').val();
    var bindVender = $('#bindVender').val();
    var param = {
        bindVender:bindVender,
        venderId:venderId,
        batchNo : batchNo,
        cardName : cardName,
        cardType : cardType,
        createType : createType,
        stime : stime,
        etime : etime,
        startTime : startTime,
        endTime : endTime,
        faceValue : faceValue,
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
    $('#cardbatchtb').bootstrapTable('destroy');
    initWaresTable();
}