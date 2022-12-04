function initWaresTable(){
    $('#hotsearchtb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/hotsearchList',
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
                field: 'keyword',
                title: '关键词',
                align: 'center'
            },
            {
                field: 'position',
                title: '位置',
                align: 'center'
            },
            {
                field: 'status',
                title: '是否禁用',
                align: 'center',
                formatter:function (value) {
                    if(value==0){
                        return "禁用";
                    }else if(value==1){
                        return "启用";
                    }
                }
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
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="editConfig(\''+row.id+'\')"><i class="fa fa-edit"></i></a> ';
                    var f = '<a class="btn btn-primary btn-sm ' + s_remove_h + '" href="#" mce_href="#" title="删除" onclick="removeConfig(\''+row.id+'\')"><i class="fa fa-remove"></i></a> ';
                    return e+f;
                }
            }
        ]
    });
}
function queryParams(params) {
    var keyword = $('#keyword').val();
    var param = {
        keyword : keyword,
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
    $('#hotsearchtb').bootstrapTable('destroy');
    initWaresTable();
}

function addConfig() {
    layer.open({
        type : 2,
        title : '添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/hotsearch/add'
    });
}
function editConfig(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/hotsearch/edit?id='+id
    });
}
function removeConfig(id){
    if(!confirm('确定要删除选中的记录？')){
        return;
    }
        $.ajax({
        type: "get",
        url: '/v1/tcmalladmin/hotsearch/delete?id='+id,
        success: function (data) {
            if (data.error == 0) {
                parent.layer.msg("删除成功");
            } else {
                parent.layer.alert(data.msg)
            }
        }
    });
    searcher();
}