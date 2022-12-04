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
    var venderId = $('#venderId').val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/sysVender/balance/reduce/"+venderId,
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("请求失败");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
                parent.location.reload();
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
			balance : {
				required : true,
                number : true,
                minNumber: $("#balance").val()
			}
		},
		messages : {
            balance : {
				required : icon + "请输入名称"
			}
		},
        min: $.validator.format("请输入不小于 {0} 的数值")
	})
}