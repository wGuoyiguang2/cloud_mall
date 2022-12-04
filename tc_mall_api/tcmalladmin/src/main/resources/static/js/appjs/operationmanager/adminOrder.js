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
            id: 'orderId',
            code: 'orderNo',
            parentCode: 'parentId',
            type: "GET", // 请求数据的ajax类型
            url: '/v1/tcmalladmin/admin/orderList', // 请求数据的ajax的url
            ajaxParams: params,
            expandColumn: '1',// 在哪一列上面显示展开按钮
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
                    field: 'orderId',
                    title: '订单ID',
                    valign : 'center',
                    align : 'left',
                    width : '100px'
                },
                {
                    field: 'orderNo',
                    title: '订单号',
                    valign : 'center',
                    align : 'left',
                    width : '300px'
                },
                {
                    field: 'tradeNo',
                    title: '京东订单号',
                    valign : 'center',
                    align : 'left',
                    width : '200px'
                },
                {
                    field: 'payNo',
                    title: '支付订单号',
                    valign: 'center',
                    align : 'left',
                    width : '200px'
                },
                {
                    field: 'productId',
                    title: '商品ID',
                    valign : 'center',
                    align : 'left',
                    width : '200px'
                },
                {
                    field: 'productName',
                    title: '商品名称',
                    valign : 'center',
                    align : 'left',
                    width : '200px'
                },
                {
                    field: 'productPrice',
                    title: '商品金额',
                    valign : 'center',
                    align : 'left',
                    width : '100px'
                },
                {
                    field: 'payPrice',
                    title: '支付金额',
                    valign : 'center',
                    align : 'left',
                    width : '100px'
                },
                {
                    field: 'cardPrice',
                    title: '卡支付金额',
                    valign : 'center',
                    align : 'left',
                    width : '100px'
                },
                {
                    field: 'payStatus',
                    title: '支付状态',
                    valign : 'center',
                    align:'left',
                    width : '100px',
                    formatter: function (value, row) {
                        if (value.payStatus==0) {
                            return "未支付";
                        } else if (value.payStatus==1) {
                            return "已支付";
                        } else if (value.payStatus==2) {
                            return "已退款";
                        }
                    }
                },
                {
                    field: 'orderState',
                    title: '订单状态',
                    valign : 'center',
                    align:'left',
                    width : '100px',
                    formatter: function (value, row) {
                        if(value.parentId==0){
                            if (value.orderState == 1) {
                                return "待支付";
                            } else if (value.orderState == 2) {
                                return "已取消";
                            } else if (value.orderState == 3) {
                                return "待收货";
                            } else if (value.orderState == 4) {
                                return "已完成";
                            } else if (value.orderState == 5) {
                                return "售后中";
                            } else if (value.orderState == 6) {
                                return "售后完成";
                            }
                        }else {
                            if (1 == value) {
                                return "待支付";
                            } else if (2 == value) {
                                return "已取消";
                            } else if (3 == value) {
                                return "待收货";
                            } else if (4 == value) {
                                return "拒收";
                            } else if (5 == value) {
                                return "已完成";
                            }
                        }
                    }
                },
                {
                    field: 'address',
                    title: '收货地址',
                    valign : 'center',
                    align : 'left',
                    width : '200px'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    valign : 'center',
                    align : 'left',
                    width : '200px'
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
    var orderNo = $('#orderNo').val();
    var payNo = $('#payNo').val();
    var productId = $('#productId').val();
    var payStatus = $('#payStatus').val();
    var venderId=$('#venderid').val();
    var orderState = $('#orderState').val();
    var param = {
        orderState:orderState,
        orderNo : orderNo,
        payNo : payNo,
        productId : productId,
        payStatus : payStatus,
        venderid:venderId,
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

