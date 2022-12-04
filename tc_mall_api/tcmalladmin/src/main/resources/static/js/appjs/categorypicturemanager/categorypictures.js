/**
 *
 */

function initCategoryPictureTable(){
    $('#categorypicturetb').bootstrapTable({
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
        url: '/v1/tcmalladmin/categorypicture/list',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        searchOnEnterKey: true,
        singleSelect : true, // 单选checkbox
        columns: [
            {
                checkbox: true
            },
            {
                field: 'id',
                title: 'ID',
                align: 'center'
            },
            {
                field: 'catId',
                title: '分类ID',
                align: 'center'
            },
            {
                field: 'catName',
                title: '分类名称',
                align: 'center'
            },
            {
                field: 'catType',
                title: '分类类型',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value === 0){
                        return "一级分类";
                    }else if(value === 1){
                        return "二级分类";
                    }else if(value === 2){
                        return "三级分类";
                    }else {
                        return "未知分类级别";
                    }
                }
            },
            {
                field: 'icon',
                title: '分类icon',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value != ""){
                        return '<a href="#" onclick="previewIcon(\''+row.id+'\')">'+"预览"+'</a>';
                    }else {
                        return "-";
                    }

                }
            },
            {
                field: 'background',
                title: '分类底图',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value != ""){
                        return '<a href="#" onclick="previewBackground(\''+row.id+'\')">'+"预览"+'</a>';
                    }else {
                        return "-";
                    }

                }
            },
            {
                field: 'picture',
                title: '分类图片',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value != ""){
                        return '<a href="#" onclick="previewPicture(\''+row.id+'\')">'+"预览"+'</a>';
                    }else {
                        return "-";
                    }

                }
            },
            {
                field: 'shared',
                title: '共享',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value===0) {
                        return '私有';
                    }else{
                        return '共享';
                    }
                }
            },
            {
                field: 'status',
                title: '状态',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value===0) {
                        return '禁用';
                    }else{
                        return '启用';
                    }
                }
            },
            {
                field: 'ctime',
                title: '创建时间',
                align: 'center',
                visible: false
            },
            {
                field: 'utime',
                title: '更新时间',
                align: 'center',
                visible: false
            }
        ],
    });
}
function queryParams(params) {
    var catId = $("#catId_search").val();
    var catName = $("#name_search").val();
    var catType = $("#catType_search").val();
    var status = $("#status_search").val();
    var param = {
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    if(catId != ''){
        param.catId = catId;
    }
    if(catName != ''){
        param.name = catName;
    }
    if(catType != ''){
        param.catType = catType;
    }
    if(status != ''){
        param.status = status;
    }
    return param;
}

function initTable() {
    initCategoryPictureTable();
}
initTable();

function searcher() {
    $('#categorypicturetb').bootstrapTable('destroy');
    initTable();
}

function add(){
    layer.open({
        type : 2,
        title : '分类图片添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/categorypicture/add"
    });
}

function edit(){
    var row= $('#categorypicturetb').bootstrapTable('getSelections');
    if(row.length <= 0){
        layer.msg("请选择修改的数据");
        return;
    }
    layer.open({
        type : 2,
        title : '分类图片修改',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/categorypicture/edit?id="+row[0].id
    });
}

function remove(){
    var row= $('#categorypicturetb').bootstrapTable('getSelections');

    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : "/v1/tcmalladmin/categorypicture/remove",
            type : "post",
            data : {
                'id' : row[0].id
            },
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                } else {
                    layer.msg(r.msg);
                }
                $('#categorypicturetb').bootstrapTable('refresh');
            }
        });
    })
}

function previewIcon(id){
    layer.open({
        type : 2,
        title : '分类Icon预览',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/categorypicture/icon?id=" + id
    });
}

function previewPicture(id){
    layer.open({
        type : 2,
        title : '分类图片预览',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/categorypicture/picture?id=" + id
    });
}

function previewBackground(id){
    layer.open({
        type : 2,
        title : '分类背景图预览',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/categorypicture/background?id=" + id
    });
}
