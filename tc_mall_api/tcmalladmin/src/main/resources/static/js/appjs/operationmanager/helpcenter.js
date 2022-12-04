/**
 *
 */
function initWaresTable() {
    $('#helptb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: true,      //是否启用排序
        sortOrder: "position",
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/helpCenter/list',
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        sidePagination: "server",
        queryParamsType:'limit',
        queryParams: queryParams,
        columns: [
            {
                field: 'typeId',
                title: '分类ID',
                align: 'typeId'
            },
            {
                field: 'typeName',
                title: '分类名称',
                align: 'typeName'
            },
            {
                field: 'status',
                title: '是否禁用',
                align: 'status',
                formatter: function (index,row) {
                    if(row.status==0){
                        return "禁用";
                    }else if(row.status==1){
                        return "启用";
                    }
                }
            },
            {
                field: 'typePosition',
                title: '位置',
                align: 'typePosition'
            },
            {
                field: 'qaid',
                title: '问答ID',
                align: 'qaid'
            },
            {
                field: 'qa',
                title: '问答内容',
                align: 'qa'
            },
            {
                title: '操作',
                align: 'center',
                valign: 'center',
                formatter: function (index,row) {
                    var e;
                    if(row.qaid==null||row.qaid==''){
                        e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑问答" onclick="editByType('+row.typeId+',\''+row.typeName+'\')"><i class="fa fa-edit"></i></a> ';
                    }else{
                        e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑问答" onclick="edit(\''
                            + row.qaid
                            + '\')"><i class="fa fa-edit"></i></a> ';
                    }
                    var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除问答"  mce_href="#" onclick="removeone('+row.qaid+',\''+row.typeId+'\')"><i class="fa fa-remove"></i></a> ';
                    return e+d;
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
                contentType: 'application/json',
                data: JSON.stringify(row),
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
            $('#helptb').bootstrapTable('destroy');
            initWaresTable();
        }
    });
}

function initTable() {
    initWaresTable();
}

function removeone(id,typeId) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : "/v1/tcmalladmin/helpCenter/delete",
            type : "post",
            data : {
                'id' : id,
                'typeId':typeId
            },
            success : function(r) {
                if (r.error == 0) {
                    layer.msg(r.msg);
                } else {
                    layer.msg(r.msg);
                }
                $('#helptb').bootstrapTable('destroy');
                initWaresTable();
            }
        });
    })
}
function searcher() {
    $('#helptb').bootstrapTable('destroy');
    initWaresTable();
}
function queryParams(params) {
    var param = {
        id:$("#answerId_search").val(),
        aid:$("#typeId_search").val(),
        title:$("#typeName_search").val(),
        intro:$("#answerContent_search").val(),
        limit : params.limit, // 页面大小
        offset : params.offset // 页码
    };
    return param;
}
initTable();


function editByType(typeId,typeName) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/helpCenter/edit?typeId='+typeId+'&typeName='+typeName
    });
}
function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/helpCenter/edit?id='+id+'&type=QA'
    });
}

function add() {
    layer.open({
        type : 2,
        title : '新增问答',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/helpCenter/add'
    });
}
function addType() {
    layer.open({
        type : 2,
        title : '新增分类',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/helpCenter/addType'
    });
}
