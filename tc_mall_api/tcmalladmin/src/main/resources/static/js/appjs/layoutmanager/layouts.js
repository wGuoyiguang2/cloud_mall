/**
 *
 */

function initLayoutTable(){
    $('#layouttb').bootstrapTable({
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
        url: '/v1/tcmalladmin/layoutmanager/layoutlist',
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
                field: 'parentTitle',
                title: '父布局',
                align: 'center'
            },
            {
                field: 'title',
                title: '布局名称',
                align: 'center'
            },
            {
                field: 'id',
                title: '布局预览',
                align: 'center',
                formatter: function(value, row, index) {
                    return '<a href="#" onclick="previewLayout(\''+row.id+'\')">'+"预览"+'</a>';
                }
            },
            {
                field: 'position',
                title: '布局位置',
                align: 'center',
            },
            {
                field: 'showTitle',
                title: '是否显示标题',
                align: 'center',
                formatter : function(item, index) {
                    if (item == '0') {
                        return '<span class="label label-danger">隐藏</span>';
                    } else if (item == '1') {
                        return '<span class="label label-primary">显示</span>';
                    }
                }
            },
            {
                field: 'status',
                title: '是否启用',
                align: 'center',
                formatter : function(item, index) {
                    if (item == '0') {
                        return '<span class="label label-danger">禁用</span>';
                    } else if (item == '1') {
                        return '<span class="label label-primary">启用</span>';
                    }
                }
            },
            {
                field: 'share',
                title: '共享',
                align: 'center',
                formatter : function(item, index) {
                    if (item == '0') {
                        return '<span class="label label-danger">私有</span>';
                    } else if (item == '1') {
                        return '<span class="label label-primary">共享</span>';
                    }
                }
            },
            {
                field: 'autoSync',
                title: '自动同步',
                align: 'center',
                formatter : function(item, index) {
                    if (item == '0') {
                        return '<span class="label label-danger">否</span>';
                    } else if (item == '1') {
                        return '<span class="label label-primary">是</span>';
                    }
                }
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
                field : '操作',
                align : 'center',
                valign : 'center',
                formatter : function(value, row, index) {
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                        + row.id
                        + '\')"><i class="fa fa-edit"></i></a> ';

                    var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="removeone(\''
                        + row.id
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    return e + d;
                }
            }
        ]
    });
}
function queryParams(params) {
    var search_name = $('#searchName').val();//名称
    var param = {
        title : search_name,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initLayoutTable();
}
initTable();

function searcher() {
    $('#layouttb').bootstrapTable('destroy');
    initTable();
}

function previewLayout(id){
    layer.open({
        type : 2,
        title : '布局预览',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '80%', '70%' ],
        content : "/v1/tcmalladmin/layoutmanager/layoutpreview?id="+ id
    });
}

function add() {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/layoutmanager/add"
    });
}

function copy() {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/layoutmanager/copy"
    });
}

function removeone(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : '/v1/tcmalladmin/layoutmanager/remove',
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

function edit(id) {
    layer.open({
        type : 2,
        title : '编辑',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/layoutmanager/edit?id=' + id
    });
}