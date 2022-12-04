/**
 *
 */

function initCollectionsTable(){
    $('#productcollectiontb').bootstrapTable({
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
        url: '/v1/tcmalladmin/productcollectionmanager/productcollectionlist',
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
                field: 'name',
                title: '商品集名称',
                align: 'center'
            },
            {
                field: 'describe',
                title: '商品集描述',
                align: 'center'
            },
            {
                field: 'productCount',
                title: '商品集内商品数量',
                align: 'center'
            },
            {
                field: 'collectionPercent',
                title: '商品集零售价系数',
                align: 'center'
            },
            {
                field: 'percent',
                title: '全站零售价系数',
                align: 'center'
            },
            {
                field: 'status',
                title: '商品集状态',
                align: 'center',
                editable: {
                    type: 'select',
                    title: '商品集状态',
                    placement: 'left',
                    source:[{value:"0",text:"下线"},{value:"1",text:"上线"}],
                    formatter: function(value, row, index) {
                        if(value===0) {
                            return '下线';
                        }else{
                            return '上线';
                        }
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
                field: 'imagePath',
                title: '背景图',
                align: 'center',
                formatter: function(value, row, index) {
                    return '<a href="#" onclick="previewPicture(\''+row.id+'\')">'+"预览"+'</a>';
                }
            },
            {
                field: 'operation',
                title: '商品集内容',
                align: 'center',
                formatter: function(value, row, index) {
                    var a = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" title="添加"  mce_href="#" onclick="productAdd(\''
                        + row.id
                        + '\')"><i class="fa fa-plus"></i></a> ';
                    var s = '<a class="btn btn-primary btn-sm" href="#" title="查看"  mce_href="#" onclick="productList(\''
                        + row.id
                        + '\')"><i class="fa fa-eye"></i></a> ';
                    return a + s;
                }
            },
        ],
        //保存的使用
        onEditableSave: function (field, row, oldValue, $el) {
            //可进行异步操作
            $.ajax({
                type: "post",
                url: "/v1/tcmalladmin/productcollectionmanager/collectionedit",
                dataType: 'JSON',
                contentType : 'application/json',
                data : JSON.stringify(row),
                success: function (data) {
                    if (data.code == 0) {
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
            $('#productcollectiontb').bootstrapTable('refresh');
        },
        pagination:true
    });
}
function queryParams(params) {
    var searchId = "";
    var searchName = "";
    var searchType = $('#searchType').val();
    var searchContent = $('#searchContent').val();//商品名称
    if(searchType == 0){
        searchId = searchContent;
    }else {
        searchName = searchContent;
    }
    var param = {
        id : searchId,
        name : searchName,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initCollectionsTable();
}
initTable();

function searcher() {
    $('#productcollectiontb').bootstrapTable('destroy');
    initTable();
}

function add(){
    layer.open({
        type : 2,
        title : '商品集添加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/productcollectionmanager/add"
    });
}

function edit(){
    var row= $('#productcollectiontb').bootstrapTable('getSelections');

    if(row.length==1){
        layer.open({
            type : 2,
            title : '商品集修改',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '80%', '70%' ],
            content : "/v1/tcmalladmin/productcollectionmanager/edit?collectionId=" + row[0].id
        });

    }else{
        parent.layer.msg("请选择修改的商品集");
    }
}

function remove(){
    var row= $('#productcollectiontb').bootstrapTable('getSelections');

    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : "/v1/tcmalladmin/productcollectionmanager/remove",
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
                $('#productcollectiontb').bootstrapTable('refresh');
            }
        });
    })
}

function previewPicture(id){
    layer.open({
        type : 2,
        title : '商品集图片预览',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/productcollectionmanager/picture?id=" + id
    });
}

function productAdd(id) {
    layer.open({
        type : 2,
        title : '添加商品',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '80%', '70%' ],
        content : "/v1/tcmalladmin/productcollectionmanager/productadd?id=" + id
    });
}

function productList(id) {
    layer.open({
        type : 2,
        title : '商品集商品查看',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '80%', '70%' ],
        content : "/v1/tcmalladmin/productcollectionmanager/productofcollection/" + id
    });
}
