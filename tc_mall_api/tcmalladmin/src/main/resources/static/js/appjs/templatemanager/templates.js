/**
 *
 */

function initTemplateTable(){
    $('#templatetb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        singleSelect : false, // 设置为true将禁止多选
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageNumber:1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/templatemanager/templatelist',
        sidePagination: "server",
        queryParamsType:'limit',
        queryParams: queryParams,
        columns: [
            {
                field: 'id',
                title: 'ID',
                align: 'center'
            },
            {
                field: 'name',
                title: '模板名称',
                align: 'center'
            },
            {
                field: 'code',
                title: 'code',
                align: 'center'
            },
            {
                field: 'layout',
                title: '布局',
                align: 'center',
                formatter: function(value, row, index) {
                    return '<a href="#" onclick="previewTemplate(\''+row.id+'\')">'+"预览"+'</a>';
                }
            },
            {
                field: 'recNum',
                title: '推荐位数量',
                align: 'center',
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'center',
            },
            {
                field: 'utime',
                title: '修改时间',
                align: 'center',
            },
            {
                title : '操作',
                field : 'operation',
                align : 'center',
                valign : 'center',
                formatter : function(value, row, index) {
                    var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="removeone(\''
                        + row.id
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    return d;
                }
            }
        ]
    });
}
function queryParams(params) {
    var search_name = $('#searchName').val();//名称
    var param = {
        name : search_name,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initTemplateTable();
}
initTable();

function searcher() {
    $('#templatetb').bootstrapTable('destroy');
    initTemplateTable();
}

function previewTemplate(id){
    layer.open({
        type : 2,
        title : '模板预览',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/templatemanager/templatepreview?id="+ id
    });
}

function add() {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/templatemanager/add"
    });
}

function removeone(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : '/v1/tcmalladmin/templatemanager/remove',
            type : "get",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    searcher();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}