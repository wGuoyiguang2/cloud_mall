/**
 *
 */

function initDateTime() {
    $('.form_datetime').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        showMeridian: 1
    });
}

function initRecommendHistoryTable(){
    $('#recommendhistorytb').bootstrapTable({
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
        url: '/v1/tcmalladmin/recommendhistory/list',
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
                field: 'sku',
                title: '商品ID',
                align: 'center'
            },
            {
                field: 'name',
                title: '商品名称',
                align: 'center'
            },
            {
                field: 'brandName',
                title: '品牌',
                align: 'center'
            },
            {
                field: 'category',
                title: '分类',
                align: 'center'
            },
            {
                field: 'price',
                title: '商品金额:单位/元',
                align: 'center'
            },
            {
                field: 'utime',
                title: '推荐日期',
                align: 'center'
            },
            {
                field: 'state',
                title: '商品状态',
                align: 'center',
                formatter: function(value, row, index) {
                    if(value===0) {
                        return '下线';
                    }else{
                        return '上线';
                    }
                }
            },
            {
                field: 'sales',
                title: '商品销售总量',
                align: 'center'
            },
            {
                field: 'status',
                title: '推荐状态',
                align: 'center',
                editable: {
                    type: 'select',
                    title: '操作',
                    placement: 'left',
                    source:[{value:"0",text:"取消"},{value:"1",text:"推荐"}],
                    formatter: function(value, row, index) {
                        if(value===0) {
                            return '推荐';
                        }else{
                            return '取消';
                        }
                    }
                }
            },
        ],
        //保存的使用
        onEditableSave: function (field, row, oldValue, $el) {
            //可进行异步操作
            $.ajax({
                type: "post",
                url: "/v1/tcmalladmin/recommendhistory/edit",
                dataType: 'JSON',
                contentType : 'application/json',
                data : JSON.stringify(row),
                success: function (data) {
                    if (data.code == 0) {
                        parent.layer.msg("操作成功");
                        $('#recommendhistorytb').bootstrapTable('refresh');
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
        },
        pagination:true
    });
}

function queryParams(params) {
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var searchType = $("#searchType").val();
    var searchContent = $("#searchContent").val();
    var param = {
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    if(startTime != null && startTime != ""){
        param.startTime = startTime;
    }
    if(endTime != null && endTime != ""){
        param.endTime = endTime;
    }
    if(searchContent != ""){
        if(searchType == "0"){
            if(!isNaN(searchContent)){
                param.sku = searchContent;
            }else {
                alert("请输入正确的商品ID");
            }
        }else {
            param.name = searchContent;
        }
    }
    return param;
}

function initTable() {
    initRecommendHistoryTable();
    initDateTime();
}
initTable();

function searcher() {
    $('#recommendhistorytb').bootstrapTable('destroy');
    initTable();
}

$('#period').change(function() {    // 一级下拉菜单选项改变事件
    var period = $(this).val();
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/recommendhistory/period/update",
        data: { period:period},
        dataType: "JSON",
        async: false,
        success: function (data) {
            if (data.code == 0) {
                layer.msg("操作成功");

            } else {
                layer.alert(data.msg)
            }
        },
        error: function () { layer.alert("请求失败"); }
    });
});
