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
		url : "/v1/tcmalladmin/helpCenter/add",
		data : $('#helpcenterForm').serialize(),// 你的formid
		async : false,
		error : function(request) {

			parent.layer.alert("请求失败");
		},
		success : function(data) {
			if (data.error == 0) {
				parent.layer.msg("操作成功");
				parent.searcher();
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
	$("#helpcenterForm").validate({
		rules : {
            QA : {
				required : true
			},
            typeId : {
                required : true
            }

		},
		messages : {

			QA : {
				required : icon + "请输入问答内容"
			},
            typeId : {
				required : icon + "请选择分类"
			}
		}
	})
}