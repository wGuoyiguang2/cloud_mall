var pageSize0=10;
$(document).ready(function () {
    load(1,pageSize0)
});
var load = function (pageNum,pageSize) {
    $(".fixed-table-container").empty();
    $(".fixed-table-container").html("<table id=\"ordertb\"></table>");
    var params=createParams(pageNum,pageSize);
    $('#ordertb').bootstrapTreeTable(
            {
                //id: 'id',
                code: 'orderSn',
                parentCode: 'parentId',
                type: "GET", // 请求数据的ajax类型
                url: '/v1/tcmalladmin/orderRefundList0', // 请求数据的ajax的url
                ajaxParams: params,
                expandColumn: '0',// 在哪一列上面显示展开按钮
                striped: true, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: false, // 是否全部展开
                success:function(data){
                    if(data!=null){
                        $("#startIndex").text(data.startIndex)
                        $("#endIndex").text(data.endIndex)
                        $("#total").text(data.total)
                        pageLimit(pageNum,pageSize,data.total);
                        return data.rows;
                    }
                },
                columns: [
                    {
                        field: 'orderSn',
                        title: '订单号',
                        valign : 'center',
                        align : 'center',
                        width : '14%'
                    },
                    {
                        field: 'unqId',
                        title: '退款ID',
                        valign : 'center',
                        align : 'center',
                        width : '10%',
                        formatter: function (value, row) {
                            if(value.parentId==0){
                                return '-';
                            }else if(value.parentId!=0){
                                return value.unqId;
                            }
                        }
                    },
                    {
                        field: 'tradeNo',
                        title: '京东订单号',
                        valign : 'center',
                        align : 'center',
                        width : '10%'
                    },
                    {
                        field: 'payOrderSn',
                        title: '支付订单号',
                        valign: 'center',
                        align : 'center',
                        width : '10%'
                    },
                    {
                        field: 'productId',
                        title: '商品ID',
                        valign : 'center',
                        align : 'center',
                        width : '6%',
                        formatter: function (value, row) {
                            if(value.parentId==0){
                                return '-';
                            }else if(value.parentId!=0){
                                return value.productId;
                            }
                        }
                    },
                    {
                        field: 'productName',
                        title: '商品名称',
                        valign : 'center',
                        align : 'center',
                        width : '10%',
                        formatter: function (value, row) {
                            if(value.parentId==0){
                                return '-';
                            }else if(value.parentId!=0){
                                return value.productName;
                            }
                        }
                    },
                    {
                        field: 'price',
                        title: '商品金额',
                        valign : 'center',
                        align : 'center',
                        width : '4%'
                    },
                    {
                        field: 'payPrice',
                        title: '支付金额',
                        valign : 'center',
                        align : 'center',
                        width : '4%'
                    },
                    {
                        field: 'cardPrice',
                        title: '卡支付金额',
                        valign : 'center',
                        align : 'left',
                        width : '4%',
                        formatter: function (value, row) {
                            if(value.parentId!=0){
                                return '-';
                            }else if(value.parentId==0){
                                return value.cardPrice;
                            }
                        }
                    },
                    {
                        field: 'refundStatus',
                        title: '退款状态',
                        valign : 'center',
                        align:'center',
                        width : '4%',
                        formatter: function (value, row) {
                            if(value.parentId!=0){
                                if (value.refundStatus==0) {
                                    return "失败";
                                } else if (value.refundStatus==1) {
                                    return "成功";
                                }
                            }else if(value.parentId==0){
                                return '-';
                            }
                        }
                    },
                    {
                        field: 'failedReason',
                        title: '失败原因',
                        valign : 'center',
                        align : 'center',
                        width : '10%',
                        formatter: function (value, row) {
                            if(value.parentId==0){
                                return '-';
                            }else if(value.parentId!=0){
                                return value.failedReason;
                            }
                        }
                    },
                    {
                        title: '操作',
                        align: 'center',
                        valign: 'center',
                        width:'14%',
                        formatter: function (value,index,row) {
                            if(value.parentId==0){
                                return '-';
                            }
                            var d = '<a class=" ' + s_vender_h + '" href="#"  mce_href="#" onclick="refundVender(\''+value.parentId+'\',\''+value.unqId+'\')"><u>退款至大客户</u></a> ';
                            var e = '<a class=" ' + s_user_h + '" href="#"  mce_href="#" onclick="refundUser(\''+value.parentId+'\',\''+value.unqId+'\')"><u>退款至用户</u></a> ';
                            if(value.failedReason=='退款至用户失败'){
                                return e;
                            }
                            if(value.failedReason=='退款至大客户失败'){
                                return d;
                            }
                        }
                    }]
            });
}
function pageLimit(pageNum,pageSize,total){
    var pageCount=Math.ceil(total/pageSize)
    if(pageCount==0){
        return;
    }
        $('#pageLimit').bootstrapPaginator({
            currentPage: pageNum,//当前的请求页面。
            totalPages: pageCount,//一共多少页。
            size: "normal",//应该是页眉的大小。
            bootstrapMajorVersion: 3,//bootstrap的版本要求。
            alignment:"right",
            numberOfPages:pageSize,//一页列出多少数据。
            itemTexts: function (type, page, current) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
                switch (type) {
                        case "first": return "<a href='javascript:void(0)' onclick='load(1,"+pageSize+")'>首页</a>";
                        case "prev": return "<a href='javascript:void(0)' onclick='load("+(Number(pageNum)-Number(1))+","+pageSize+")'>上一页</a>";
                        case "next": return "<a href='javascript:void(0)' onclick='load("+page+","+pageSize+")'>下一页</a>";
                        case "last": return "<a href='javascript:void(0)' onclick='load("+pageCount+","+pageSize+")'>末页</a>";
                        case "page": return "<a href='javascript:void(0)' onclick='load("+page+","+pageSize+")'>"+page+"</a>";
                         }
             }
     });
}
function createParams(pageNum,pageSize) {
    var startIndex=(pageNum-1)*pageSize;
    var orderSn = $('#orderSn').val();
    var failedReason = $('#failedReason').val();
    var productId = $('#productId').val();
    var refundStatus = $('#refundStatus').val();
    var param = {
        refundStatus:refundStatus,
        orderSn : orderSn,
        productId : productId,
        failedReason : failedReason,
        limit : pageSize, // 页面大小
        offset : startIndex, // 开始位置
    };
    return param;
}


function searcher() {
    $(".fixed-table-container").empty();
    $(".fixed-table-container").html("<table id=\"ordertb\"></table>");
    $('#ordertb').bootstrapTable('destroy');
    load(1,pageSize0);
}
function refundVender(orderSn,eventId){
    $.ajax({
        type: "post",
        url: "/v1/bms/order/refund/retry/vender?orderSn="+orderSn+"&eventId="+eventId,
        success: function (data) {
            if (data.error == 0) {
                parent.layer.msg("操作成功");
                searcher();
            } else {
                parent.layer.alert(data.msg)
            }
        }

    });
}
function refundUser(orderSn,eventId){
    $.ajax({
        type: "post",
        url: "/v1/bms/order/refund/retry/user?orderSn="+orderSn+"&eventId="+eventId,
        success: function (data) {
            if (data.error == 0) {
                parent.layer.msg("操作成功");
                searcher();
            } else {
                parent.layer.alert(data.msg)
            }
        }
    });
}