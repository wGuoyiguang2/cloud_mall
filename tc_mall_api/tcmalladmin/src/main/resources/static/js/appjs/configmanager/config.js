function initWaresTable(){
    $('#configtb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/configList0',
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
                field: 'key',
                title: '键',
                align: 'center'
            },
            {
                field: 'value',
                title: '值',
                align: 'center'
            },
            {
                field: 'type',
                title: '类型',
                align: 'center',
                formatter:function (value) {
                    if(value==1){
                        return "售后";
                    }
                }
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
                field: 'position',
                title: '位置',
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
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="editConfig(\''+row.id+'\')"><i class="fa fa-edit"></i></a> ';
                    var f = '<a class="btn btn-primary btn-sm ' + s_remove_h + '" href="#" mce_href="#" title="删除" onclick="removeConfig(\''+row.id+'\')"><i class="fa fa-remove"></i></a> ';
                    return e+f;
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
        }
    });
}
function queryParams(params) {
    var type = $('#type').val();
    var key = $('#key').val();
    var param = {
        type : type,
        key : key,
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
    $('#configtb').bootstrapTable('destroy');
    initWaresTable();
}

function addConfig() {
    layer.open({
        type : 2,
        title : '添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/v1/tcmalladmin/config/add'
    });
}
function editConfig(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/v1/tcmalladmin/config/edit?id='+id
    });
}
function removeConfig(id){
    if(!confirm('确定要删除选中的记录？')){
        return;
    }
        $.ajax({
        type: "get",
        url: '/v1/tcmalladmin/config/delete?id='+id,
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