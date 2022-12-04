function initWaresTable(){
    $('#salestb').bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',    //工具按钮用哪个容器
        striped: true,      //是否显示行间隔色
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageList: [10, 25, 50, 100],
        url: '/v1/tcmalladmin/sales/list0',
        queryParamsType:'limit',
        queryParams: queryParams,
        sidePagination: "server",
        strictSearch: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        searchOnEnterKey: true,
        columns: [
            {
                field: 'productId',
                title: '商品ID',
                align: 'center'
            },
            {
                field: 'productName',
                title: '商品名称',
                align: 'center'
            },
            {
                field: 'brandName',
                title: '所属品牌',
                align: 'center'
            },
            {
                field: 'category',
                title: '所属分类',
                align: 'center'
            },
            {
                field: 'productPrice',
                title: '商品金额',
                align: 'center'
            },
            {
                field: 'payPrice',
                title: '支付金额',
                align: 'center',
                formatter:function(value){
                    if(value==null||value==''){
                        return 0.00;
                    }
                    return Number(value).toFixed(2);
                }
            },
            {
                field: 'agreePrice',
                title: '商品批发价',
                align: 'center'
            },
            {
                field: 'productStatus',
                title: '商品状态',
                align: 'center',
                formatter:function(value){
                    if(value==0){
                        return "下架";
                    }else if(value==1){
                        return "上架";
                    }
                }
            },
            {
                field: 'count',
                title: '商品销售总量',
                align: 'center'
            }
        ]
    });
}
function queryParams(params) {
    var productName = $('#productName').val();
    var productId = $('#productId').val();
    var productStatus = $('#productStatus').val();
    var brandName=$("#brandName").val();
    var category=$("#category").val();
    var param = {
        productName : productName,
        brandName : brandName,
        category : category,
        productId : productId,
        productStatus : productStatus,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function initTable() {
    initWaresTable();
}
initTable();

function searcher(flag) {
    /*if(flag==1){
        $("#brandName").val("");
        $("#category").val("");
        $("#productStatus").empty();
    }else if(flag==2){
        $("#productId").val("");
        $("#productName").val("")
        $("#productStatus").empty();
    }else if(flag==3){
        $("#productId").val("");
        $("#productName").val("")
        $("#brandName").val("");
        $("#category").val("");
    }*/
    $('#salestb').bootstrapTable('destroy');
    initWaresTable();
}