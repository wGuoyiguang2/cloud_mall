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
initWaresTable();
function initWaresTable(){
    $('#cardAdminRecordtb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/bms/cardBatchRecord/list',
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
                field: 'operateType',
                title: '操作',
                align: 'center',
                formatter: function (value) {
                    if (value == 1) {
                        return "创建"
                    } else if (value == 2) {
                        return "激活"
                    } else if (value == 3) {
                        return "绑定"
                    } else if (value == 4) {
                        return "暂停"
                    } else if (value == 5) {
                        return "废弃"
                    } else if (value == 6) {
                        return "导出"
                    }
                }
            },
            {
                field: 'ctime',
                title: '操作时间',
                align: 'center'
            },
            {
                field: 'operator',
                title: '操作人',
                align: 'center'
            },
            {
                title: '查看',
                align: 'center',
                valign: 'center',
                formatter: function (index,row) {
                    var a = '<u class="' + s_detail_h + '" href="#" mce_href="#" title="查看" onclick="detail(\''+row.id+'\')" style="color: blue;cursor:pointer">查看</u> ';
                    return a;
                }
            }
        ]
    });
}

function detail(id){
    layer.open({
        type : 2,
        title : '查看',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/cardBatchAdminRecord/detailHtml?id='+id
    });
}

function searcher() {
    $('#cardAdminRecordtb').bootstrapTable('destroy');
    initWaresTable();
}
function queryParams(params) {
    var batchNo = $('#batchNo').val();
    var batchFlag = $('#batchFlag').val();
    var operateType = $('#operateType').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    var param = {
        batchNo : batchNo,
        batchFlag : batchFlag,
        operateType : operateType,
        startTime : startTime,
        endTime : endTime,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}