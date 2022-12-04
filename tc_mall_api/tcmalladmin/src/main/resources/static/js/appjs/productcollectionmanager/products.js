/**
 *
 */

function initProductsTable(){
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
        url: '/v1/tcmalladmin/productcollectionmanager/productlist',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        searchOnEnterKey: true,
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
    var search_brandName = $('#brandName_search').val();//商品品牌
    var search_cat0 = $('#cat0_search').val();
    var search_cat1 = $('#cat1_search').val();
    var search_cat2 = $('#cat2_search').val();
    var search_content = $('#search_content').val();
    var search_type = $('#search_type').val();
    if(search_type == 0){
        search_sku = search_content;
    }else {
        search_name = search_content;
    }
    var param = {
        sku : search_sku,
        name : search_name,
        brandName : search_brandName,
        cat0 : search_cat0,
        cat1 : search_cat1,
        cat2 : search_cat2,
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

$('#cat0_search').change(function() {    // 一级下拉菜单选项改变事件
    var cat0 = $(this).val();
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat0.toString(), catClass:1},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#cat1_search").html("");
            $("#cat2_search").html("");
            var cat1s = "<option value=\"\">请选择</option>";;
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#cat1_search").append(cat1s);
            $('#cat1_search').selectpicker('refresh');
            $('#cat1_search').selectpicker('render');
            $("#cat2_search").append("<option value=\"\">请选择</option>");
            $('#cat2_search').selectpicker('refresh');
            $('#cat2_search').selectpicker('render');
        },
        error: function () { alert("请求失败"); }
    });
    updateBrandNams();

});
$('#cat1_search').change(function() {    // 二级下拉菜单选项改变事件
    var cat1 = $(this).val();
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat1.toString(), catClass:2},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#cat2_search").html("");
            var cat1s = "<option value=\"\">请选择</option>";
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#cat2_search").append(cat1s);
            $('#cat2_search').selectpicker('refresh');
            $('#cat2_search').selectpicker('render');
        },
        error: function () { alert("请求失败！"); }
    });
    updateBrandNams();
});

$('#cat2_search').change(function() {
    updateBrandNams();
});

function addProduct() {
    var collectionId = $('#collectionId').val();
    var rows = $('#producttb').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要添加的商品");
        return;
    }
    layer.confirm("确认要添加选中的" + rows.length + "个商品吗?", {
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
            url : '/v1/tcmalladmin/productcollectionmanager/addproduct2collection/' + collectionId,
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

function updateBrandNams() {
    var cat0 = $("#cat0_search").val();
    var cat1 = $("#cat1_search").val();
    var cat2 = $("#cat2_search").val();
    console.log("cat0 : " + cat0 + " cat1:" + cat1 + " cat2:" + cat2);
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/productcollectionmanager/brandnamebycat",
        data: { cat0:cat0, cat1:cat1, cat2:cat2},
        dataType: "JSON",
        async: false,
        success: function (data) {
            var rows = data.rows;
            $("#brandName_search").html("");
            var brandNames = "<option value=\"\">请选择</option>";
            for(var i = 0;i<rows.length;i++){
                var name = rows[i];
                brandNames += "<option value=" + name + ">" + name + "</option>";
            }
            $("#brandName_search").append(brandNames);
            $('#brandName_search').selectpicker('refresh');
        },
        error: function () { alert("请求失败！"); }
    });
}