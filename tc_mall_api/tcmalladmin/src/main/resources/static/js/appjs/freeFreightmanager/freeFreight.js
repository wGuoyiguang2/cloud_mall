
function initWaresTable() {
    $('#freeFreighttb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: true,      //是否启用排序
        sortOrder: "position",
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/freeFreight/list',
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        sidePagination: "server",
        queryParamsType:'limit',
        responseHandler:function(data){
          if(data!=null&&data.rows!=null&&data.rows.length>0){
              $("#toolbar").hide();
          }
          return data;
        },
        columns: [
            {
                field: 'id',
                title: 'ID',
                align: 'center'
            },
            {
                field: 'price',
                title: '价格',
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
            },
            {
                title: '操作',
                align: 'center',
                valign: 'center',
                formatter: function (index,row) {
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="editFreeFreight(\''+row.id+'\')"><i class="fa fa-edit"></i></a> ';
                    return e;
                }
            }
        ]
    });
}

function initTable() {
    initWaresTable();
}

function searcher() {
    $('#freeFreighttb').bootstrapTable('destroy');
    initWaresTable();
}
initTable();
function editFreeFreight(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/freeFreight/edit?id='+id
    });
}

function addFreeFreight() {
    layer.open({
        type : 2,
        title : '添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/freeFreight/add'
    });
}
