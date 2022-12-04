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
		url :"/task/job/save",
		data : $('#signupForm').serialize(),
		async : false,
		error : function(request) {
			laryer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功");
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
			serviceName : {
				required : true
			},
            jobName : {
                required : true
            },
            jobGroup : {
                required : true
            },
		},
		messages : {
            serviceName : {
				required : icon + "请输入服务名"
			},
			jobName : {
                required : icon + "请输入任务名"
			},
            jobGroup : {
                required : icon + "请输入分组名"
            }
		}
	})
}