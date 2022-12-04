/**
 *
 */

function initProductsTable(){
    var collectionId = $('#collectionId').val();
    $('#producttb').bootstrapTable({
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
        url: '/v1/tcmalladmin/productcollectionmanager/productofcollectionlist/' + collectionId,
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
                field: 'sku',
                title: '商品ID',
                align: 'sku'
            },
            {
                field: 'name',
                title: '商品名',
                align: 'name'
            },
            {
                field: 'category',
                title: '分类',
                align: 'category'
            },
            {
                field: 'brandName',
                title: '品牌',
                align: 'brandName'
            },
            {
                field: 'imagePath',
                title: '图片',
                align: 'imagePath',
                formatter:function(value,row,index){
                    var s = '<a class = "view"  href="javascript:void(0)"><img style="width:40px;height:40px;"  src="'+value+'" /></a>';
                    return s;
                },
                events: 'operateEvents'
            },
            {
                field: 'videoPath',
                title: '短视频',
                align: 'center',
                formatter: function(value, row, index) {
                    return '<a href="#" onclick="previewVideo(\''+row.sku+'\')">'+"预览"+'</a>';
                }
            },
            {
                field: 'price',
                title: '零售价(元)',
                align: 'price',
            },
            {
                field: 'jdPrice',
                title: '京东价（元）',
                align: 'jdPrice'
            },
            {
                field: 'state',
                title: '商品状态',
                align: 'state',
                formatter: function(value, row, index) {
                    if(value===0) {
                        return '下架';
                    }else{
                        return '上架';
                    }
                }
            }
        ]
    });
    window.operateEvents = {
        'click .view': function (e, value, row, index) {
            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                area: ['auto', 'auto'],
                skin: 'layui-layer-nobg', //没有背景色
                shadeClose: true,
                content: '<img src="'+value+'"/>'
            });
        },
    };
}
function queryParams(params) {
    var search_sku = '';//商品sku
    var search_name = '';//商品名称
    var search_content = $('#searchContent').val();
    var search_type = $('#searchType').val();
    if(search_type == 0){
        search_sku = search_content;
    }else {
        search_name = search_content;
    }
    var param = {
        sku : search_sku,
        name : search_name,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initProductsTable();
}
initTable();

function searcher() {
    $('#producttb').bootstrapTable('destroy');
    initTable();
}

function previewVideo(sku){
    layer.open({
        type : 2,
        title : '短视频预览',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/operation/video?sku="+ sku
    });
}

function removeBatch() {
    var collectionId = $('#collectionId').val();
    var rows = $('#producttb').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的商品");
        return;
    }
    layer.confirm("确认要删除选中的" + rows.length + "个商品吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        var skus = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function(i, row) {
            skus[i] = row['sku'];
        });
        $.ajax({
            type : 'POST',
            data: JSON.stringify(skus),
            contentType: "application/json",
            url : '/v1/tcmalladmin/productcollectionmanager/productofcollection/remove/' + collectionId,
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    $('#producttb').bootstrapTable('refresh');
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {});
}
