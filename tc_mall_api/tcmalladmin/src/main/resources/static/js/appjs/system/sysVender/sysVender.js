
var prefix = "/system/sysVender"
function initTable() {
    initVenderTable();
}
initTable();

function initVenderTable() {
	$('#venderTable')
		.bootstrapTable(
			{
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
                url: prefix + '/list',
                queryParamsType:'limit',
                queryParams: queryParams,
                sidePagination: "server",
                strictSearch: true,
                minimumCountColumns: 2,
                clickToSelect: true,
                searchOnEnterKey: true,
				columns : [
					{
						title : 'ID',
						field : 'venderId',
						align : 'center',
						valign : 'center',
						width : '50px',
					},
					{
						field : 'name',
						title : '厂商名称',
                        valign : 'center',
					},
                    {
                        field : 'venderSettlement',
                        title : '批发价系数',
                        align : 'center',
                        valign : 'center',
                        formatter : function(value, index){
                        	return value.pricePercent;
                        }
                    },
                    {
                        field : 'venderSettlement',
                        title : '结算方式',
                        align : 'center',
                        valign : 'center',
                        witth :20,
                        formatter : function(value, index){
                        	var type = value.settlementType;
                        	if(type == 0){
                        		return "实时结算";
                        	}else if(type == 1){
                        		return "按月结算";
                        	}else if(type == 2){
                        		return "预付款";
                        	}else{
                        		return "-";
                        	}
                        }
                    },
                    {
                        field : 'venderSettlement',
                        title : '扣款账户',
                        align : 'center',
                        valign : 'center',
                        formatter : function(value, index){
                        	return value.settlementAccount;
                        }
                    },
                    {
                        field : 'venderSettlement',
                        title : '结算周期',
                        align : 'center',
                        valign : 'center',
                        formatter : function(value, index) {
                            var type = value.settlementType;
                            var period = value.settlementPeriod;
                            if (type == 1) {
                            	if (period == 0) {
                                	return "自动扣款";
                            	} else if (period == 1) {
                                	return "线下结算";
                            	} else {
                                	return "-";
                            	}
                        	}else {
                            	return "-";
							}
                        }
                    },
                    {
                        field : 'venderSettlement',
                        title : '预存款余额',
                        align : 'center',
                        valign : 'center',
                        formatter : function(value, index){
                            return value.balance;

                        }
                    },
					{
						field : 'orderNum',
						title : '排序',
                        align : 'center',
                        valign : 'center',
					},
					{
						field : 'delFlag',
						title : '状态',
						align : 'center',
                        valign : 'center',
						formatter : function(item, index) {
							if (item == '0') {
								return '<span class="label label-danger">禁用</span>';
							} else if (item == '1') {
								return '<span class="label label-primary">正常</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'venderId',
						align : 'center',
                        valign : 'center',
						formatter : function(item, index) {
							var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ item
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="removeone(\''
								+ item
								+ '\')"><i class="fa fa-remove"></i></a> ';
							return e + d;
						}
					} ]
			});
}

function reLoad() {
    $('#venderTable').bootstrapTable('destroy');
    initTable();
}
function add(pId) {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '50%', '60%' ],
		content : prefix + '/add/' + pId
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '50%', '60%' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function removeone(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'venderId' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}

function queryParams(params) {
    var searchName = $('#searchName').val();//厂商名称
    var param = {
        name : searchName,
        limit : params.limit, // 页面大小
        offset : params.offset, // 页码
    };
    return param;
}

function detailFormatter(index, row) {
    var html = [];
    var venderPayment = row['venderPayment'];
    html.push('<p><b>客户端支付信息：</b></p>');
    html.push('------------------------');
    $.each(venderPayment, function (key, value) {
        if(value['payType'] == 1){
            html.push('<p><b>微信appId' + ':</b> ' + value['appId'] + '</p>');
            html.push('<p><b>微信privateKey' + ':</b> ' + value['privateKey'] + '</p>');
            html.push('<p><b>微信publicKey' + ':</b> ' + value['publicKey'] + '</p>');
            html.push('<p><b>状态' + ':</b> ' + (value['status']==0? "未启用":"启用") + '</p>');
        }else if(value['payType'] == 2){
            html.push('<p><b>支付宝appId' + ':</b> ' + value['appId'] + '</p>');
            html.push('<p><b>支付宝mchId' + ':</b> ' + value['mchId'] + '</p>');
            html.push('<p><b>支付宝privateKey' + ':</b> ' + value['privateKey'] + '</p>');
            html.push('<p><b>支付宝publicKey' + ':</b> ' + value['publicKey'] + '</p>');
            html.push('<p><b>状态' + ':</b> ' + (value['status']==0? "未启用":"启用") + '</p>');
        }
        html.push('------------------------');
    });

    return html.join('');
}


