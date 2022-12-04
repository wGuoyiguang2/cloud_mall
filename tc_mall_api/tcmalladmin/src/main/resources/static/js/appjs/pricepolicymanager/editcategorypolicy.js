/**
 *
 */

function initCategoryTable(){
    var policyid = $('#policyid').val();
    $('#categorytb').bootstrapTable({
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
        url: '/v1/tcmalladmin/pricepolicymanager/category/list?policyid=' + policyid,
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        columns: [
            {
                checkbox : true
            },
            {
                field: 'id',
                title: 'ID',
                align: 'id'
            },
            {
                field: 'cat0Name',
                title: '一级分类',
                align: 'cat0Name'
            },
            {
                field: 'cat1Name',
                title: '二级分类',
                align: 'cat1Name'
            },
            {
                field: 'cat2Name',
                title: '三级分类',
                align: 'cat2Name'
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
        ],
    });
}
function queryParams(params) {
    var cat0 = $('#cat0').val();
    var cat1 = $('#cat1').val();
    var cat2 = $('#cat2').val();
    var param = {
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    if(cat0 != ""){
        params.cat0 = cat0;
    }
    if(cat1 != ""){
        params.cat1 = cat1;
    }
    if(cat2 != ""){
        params.cat2 = cat2;
    }
    return param;
}

function initTable() {
    initCategoryTable();
}
initTable();

function searcher() {
    $('#categorytb').bootstrapTable('refresh');
}


function add(){
    var policyid = $('#policyid').val();
    layer.open({
        type : 2,
        title : '分类添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/pricepolicymanager/category/add?policyid=" + policyid
    });
}

function batchRemove() {
    var policyid = $('#policyid').val();
    var rows = $('#categorytb').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的分类");
        return;
    }
    layer.confirm("确认要删除选中的" + rows.length + "个分类吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function(i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type : 'POST',
            data: JSON.stringify(ids),
            contentType: "application/json",
            url : '/v1/tcmalladmin/pricepolicymanager/category/remove/' + policyid,
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    $('#categorytb').bootstrapTable('refresh');
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {});
}

$('#cat0').change(function() {    // 一级下拉菜单选项改变事件
    var cat0 = $(this).val();
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat0.toString(), catClass:1},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#cat1").html("");
            var cat1s = "<option value=\"\">请选择</option>";;
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#cat1").append(cat1s);
            $('#cat1').selectpicker('refresh');
            $('#cat1').selectpicker('render');
        },
        error: function () { alert("请求失败"); }
    });
});
$('#cat1').change(function() {    // 二级下拉菜单选项改变事件
    var cat1 = $(this).val();
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat1.toString(), catClass:2},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#cat2").html("");
            var cat1s = "<option value=\"\">请选择</option>";
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#cat2").append(cat1s);
            $('#cat2').selectpicker('refresh');
            $('#cat2').selectpicker('render');
        },
        error: function () { alert("请求失败！"); }
    });
});
