$().ready(function() {
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
		url : "/v1/tcmalladmin/config/edit",
		data : $('#configForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("请求失败");
		},
		success : function(data) {
			if (data.error == 0) {
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
	$("#configForm").validate({
        rules : {
            key : {
                required : true
            },
            value : {
                required : true
            },
            type : {
                required : true
            },
            status : {
                required : true
            },
            position : {
                required : true,
                range:[1,999]
            }

        },
        messages : {

            key : {
                required : icon+"请输入key"
            },
            value : {
                required : icon+"请输入值"
            },
            type : {
                required : icon+"请选择类型"
            },
            status : {
                required : icon+"请选择是否启用"
            },
            position : {
                required : icon+"请输入位置"
            }
        }
	})
}