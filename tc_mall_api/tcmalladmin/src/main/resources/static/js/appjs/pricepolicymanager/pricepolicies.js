/**
 *
 */

function initPircePolicyTable(){
    $('#pricepolicytb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageNumber:1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/pricepolicymanager/pricepolicylist',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        columns: [
            {
                field: 'id',
                title: 'ID',
                align: 'id'
            },
            {
                field: 'name',
                title: '零售价策略名称',
                align: 'name'
            },
            {
                field: 'type',
                title: '零售价策略类型',
                align: 'type',
                formatter: function(value, row, index) {
                    if(value===0) {
                        return '全站';
                    }else if(value==1){
                        return '分类';
                    }else if(value==2){
                        return '商品集';
                    }else if(value==3) {
                        return '毛利率';
                    }
                }
            },
            {
                field: 'percent',
                title: '零售价格系数',
                align: 'percent',
                editable: {
                    type: 'text',
                    title: '零售价系数',
                    placement: 'left',
                    validate: function (v) {
                        if (isNaN(v)) return '零售价系数必须是数字';
                        var ArrMen= v.split(".");    //截取字符串
                        if(v < 1){
                            return '零售价系数必须大于1';
                        }
                        if(ArrMen.length==2){
                            if(ArrMen[1].length>2){    //判断小数点后面的字符串长度
                                return "零售价系数最多两位小数";
                            }
                        }
                    }
                }
            },
            {
                field: 'status',
                title: '状态',
                align: 'status',
                editable: {
                    type: 'select',
                    title: '策略状态',
                    placement: 'left',
                    source:[{value:"0",text:"禁用"},{value:"1",text:"启用"}],
                    formatter: function(value, row, index) {
                        if(value===0) {
                            return '禁用';
                        }else{
                            return '启用';
                        }
                    }
                }
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'ctime',
            },
            {
                field: 'utime',
                title: '修改时间',
                align: 'utime'
            },
            {
                field: 'id',
                title: '操作',
                align: 'id',
                formatter: function(value, row, index) {
                    var e = '<a class="btn btn-primary btn-sm" href="#" title="编辑策略内容"  mce_href="#" onclick="policyEdit(\''
                        + row.id
                        + '\')"><i class="fa fa-edit"></i></a> ';
                    var r = '<a class="btn btn-primary btn-sm ' + s_remove_h + '" href="#" title="删除策略"  mce_href="#" onclick="policyRemove(\''
                        + row.id
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    if(row.type != 0){
                        return e + r;
                    }else{
                        return r;
                    }

                }
            },

        ],
        //保存的使用
        onEditableSave: function (field, row, oldValue, $el) {
            if(s_edit_h == 'hidden'){
                parent.layer.msg("无操作权限");
                $('#pricepolicytb').bootstrapTable('refresh');
                return;
            }
            //可进行异步操作
            $.ajax({
                type: "post",
                url: "/v1/tcmalladmin/pricepolicymanager/edit",
                dataType: 'JSON',
                contentType : 'application/json',
                data : JSON.stringify(row),
                success: function (data) {
                    if (data.code == 0) {
                        parent.layer.msg("操作成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }
                    $('#pricepolicytb').bootstrapTable('refresh');
                },
                error: function () {
                    alert('编辑失败');
                },
                complete: function () {

                }

            });
            $('#pricepolicytb').bootstrapTable('refresh');
        }
    });
}
function queryParams(params) {
    var search_content = $('#searchContent').val();
    var param = {
        name : search_content,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initPircePolicyTable();
}
initTable();

function searcher() {
    $('#pricepolicytb').bootstrapTable('destroy');
    initTable();
}


function add(){
    layer.open({
        type : 2,
        title : '零售策略添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/pricepolicymanager/add"
    });
}

function policyEdit(id) {
    layer.open({
        type : 2,
        title : '零售策略编辑',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/pricepolicymanager/policyedit?id="+id
    });
}

function policyRemove(id) {
    layer.confirm("确认要删除此零售价策略吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        $.ajax({
            type : 'POST',
            data : {
                'id' : id
            },
            url : '/v1/tcmalladmin/pricepolicymanager/remove',
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    $('#pricepolicytb').bootstrapTable('refresh');
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {});
}
