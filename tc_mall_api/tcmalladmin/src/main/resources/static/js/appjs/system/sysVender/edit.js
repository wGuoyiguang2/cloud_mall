$().ready(function() {
    $.validator.addMethod("minNumber",function(value, element){
        var returnVal = true;
        inputZ=value;
        var ArrMen= inputZ.split(".");    //截取字符串
        if(ArrMen.length==2){
            if(ArrMen[1].length>2){    //判断小数点后面的字符串长度
                returnVal = false;
                return false;
            }
        }
        return returnVal;
    },"小数点后最多为两位");         //验证错误信息
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/sysVender/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
            'venderSettlement.pricePercent' : {
                required : true,
                number : true,
                min : 1,
                minNumber: $("#pricePercent").val()
            },
            'venderSettlement.settlementAccount' : {
                required : true
            },
            'venderPayment[0].appId' : {
                required : "#wxPayType:checked",
            },
            'venderPayment[0].publicKey' : {
                required : "#wxPayType:checked",
            },
            'venderPayment[0].privateKey' : {
                required : "#wxPayType:checked",
            },
            'venderPayment[1].appId' : {
                required : "#zfbPayType:checked",
            },
            'venderPayment[1].mchId' : {
                required : "#zfbPayType:checked",
            },
            'venderPayment[1].publicKey' : {
                required : "#zfbPayType:checked",
            },
            'venderPayment[1].privateKey' : {
                required : "#zfbPayType:checked",
            },
			orderNum:{
				required : true,
				digits : true
			},
			delFlag:{
				required : true,
				digits : true,
				range:[0,1]
			}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			},
            'venderSettlement.pricePercent' : {
                required : icon + "请输入批发价系数"
            },
            'venderSettlement.settlementAccount' : {
                required : icon + "请输入扣款账户"
            },
            'venderPayment[0].appId' : {
                required : icon + "请输入微信appId"
            },
            'venderPayment[0].publicKey' : {
                required : icon + "请输入微信publicKey"
            },
            'venderPayment[0].privateKey' : {
                required : icon + "请输入微信privateKey"
            },
            'venderPayment[1].appId' : {
                required : icon + "请输入支付宝appId"
            },
            'venderPayment[1].mchId' : {
                required : icon + "请输入支付宝mchId"
            },
            'venderPayment[1].publicKey' : {
                required : icon + "请输入支付宝publicKey"
            },
            'venderPayment[1].privateKey' : {
                required : icon + "请输入支付宝privateKey"
            },
			orderNum : {
				required: icon + "请输入排序号"
			},
			delFlag:{
				required: icon + "请输入状态"
			}
		}
	})
}

$("input[name='venderSettlement.settlementType']").click(function(){
    var type = $("input[name='venderSettlement.settlementType']:checked").val();
    if(type == 1){//按月结算
        $("#periodSetting").show();
    }else {
        $("#periodSetting").hide();
    }
    if(type == 2){//按月结算
        $("#settlementAccount").hide();
        $("#balance").show();
    }else {
        $("#settlementAccount").show();
        $("#balance").hide();
    }
});

function addBalance(venderId) {
    layer.open({
        type : 2,
        title : '增加预存款',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/system/sysVender/balance/add/' + venderId
    });
}

function reduceBalance(venderId) {
    layer.open({
        type : 2,
        title : '减少预存款',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : '/system/sysVender/balance/reduce/' + venderId
    });
}