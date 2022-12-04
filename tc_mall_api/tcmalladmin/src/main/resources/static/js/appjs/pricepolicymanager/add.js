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
        save();
    }
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/v1/tcmalladmin/pricepolicymanager/save",
		data : $('#pricepolicyForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("请求失败");
		},
		success : function(data) {
			if (data.code == 0) {
                parent.location.reload();
				parent.layer.msg("操作成功");
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
	$("#pricepolicyForm").validate({
		rules : {
            name : {
				required : true
			},
            percent : {
                required : true,
                number : true,
                min : 1,
                minNumber: $("#pricePercent").val()
            },
            type : {
                required : true
            },
            status : {
				required : true
			},

		},
		messages : {

            name : {
				required : icon + "请输入零售价策略名称"
			},
            percent : {
				required : icon + "请输入零售价策略系数"
			},
            type : {
                required : icon + "请选择零售价策略类型"
            },
            status : {
				required : icon + "请选择是否启用"
			},
		}
	})
}