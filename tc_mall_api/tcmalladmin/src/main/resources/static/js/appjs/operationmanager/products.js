/**
 *
 */

function initWaresTable(){
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
        pageList: [10, 25, 50, 100, 500, 1000],
        url: '/v1/tcmalladmin/operation/productlist',
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
                align: 'center'
            },
            {
                field: 'name',
                title: '商品名',
                align: 'center'
            },
            {
                field: 'category',
                title: '分类',
                align: 'center'
            },
            {
                field: 'brandName',
                title: '品牌',
                align: 'center'
            },
            {
                field: 'imagePath',
                title: '图片',
                align: 'center',
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
                editable: {
                    type: 'text',
                    title: '零售价设置',
                    placement: 'left',
                    validate: function (v) {
                        if (isNaN(v)) return '零售价必须是数字';
                    }
                }
            },
            {
                field: 'tradePrice',
                title: '批发价（元）',
                align: 'center'
            },
            {
                field: 'jdPrice',
                title: '京东价（元）',
                align: 'center'
            },
            {
                field: 'state',
                title: '商品状态',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value===0) {
                        return '下架';
                    }else{
                        return '上架';
                    }
                }
            }
        ],
        //保存的使用
        onEditableSave: function (field, row, oldValue, $el) {
            if(s_edit_h == 'hidden'){
                parent.layer.msg("无操作权限");
                $('#producttb').bootstrapTable('refresh');
                return;
            }
            //可进行异步操作
            $.ajax({
                type: "post",
                url: "/v1/tcmalladmin/pricepolicymanager/product/save",
                dataType: 'JSON',
                contentType : 'application/json',
                data : JSON.stringify(row),
                success: function (data) {
                    if (data.code == 0) {
                        parent.layer.msg("操作成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }
                    $('#producttb').bootstrapTable('refresh');
                },
                error: function () {
                    alert('编辑失败');
                },
                complete: function () {

                }

            });
        },
        rowStyle: function(row,index){
            //这里['active', 'success', 'info', 'warning', 'danger']代表5中颜色
            var str = "";
            if (row.price > row.jdPrice){
                str = 'danger';
            }
            return { classes: str }
        },
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
    var search_sku = $('#sku_search').val();//商品sku
    var search_name = $('#name_search').val();//商品名称
    var search_brandName = $('#brandName_search').val();//商品品牌
    var search_cat0 = $('#cat0_search').val();
    var search_cat1 = $('#cat1_search').val();
    var search_cat2 = $('#cat2_search').val();
    var search_price_type = $('#priceType').val();
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
    initWaresTable();
}
initTable();

function searcher() {
    $('#producttb').bootstrapTable('destroy');
    initWaresTable();
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
});

function removeBatch() {
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
            url : '/v1/tcmalladmin/productremove/save',
            success : function(r) {
                if (r.code == 0) {
                    layer.msg("操作成功");
                    $('#producttb').bootstrapTable('refresh');
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {});
}

function exportFile() {
    var search_sku = $('#sku_search').val();//商品sku
    var search_name = $('#name_search').val();//商品名称
    var search_brandName = $('#brandName_search').val();//商品品牌
    var search_cat0 = $('#cat0_search').val();
    var search_cat1 = $('#cat1_search').val();
    var search_cat2 = $('#cat2_search').val();
    var params = '';
    params = params + 'sku=' + search_sku;
    params = params + '&name=' + search_name;
    params = params + '&brandName=' + search_brandName;
    params = params + '&cat0=' + search_cat0;
    params = params + '&cat1=' + search_cat1;
    params = params + '&cat2=' + search_cat2;
    params = params + '&state=1';

    var url = '/v1/tcmalladmin/operation/export?' + params;
    layer.msg("导出可能需要较长时间，请耐心等待");
    window.location.href = url;
}