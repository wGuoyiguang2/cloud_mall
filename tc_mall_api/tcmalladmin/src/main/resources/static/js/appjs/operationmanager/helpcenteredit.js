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
		url : "/v1/tcmalladmin/helpCenter/edit",
		data : $('#helpcenterForm').serialize(),// 你的formid
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
	$("#helpcenterForm").validate({
		rules : {
            intro : {
				required : true
			},
			position:{
            	required:true,
                range:[1,999]
			}

		},
		messages : {

            intro : {
				required : icon + "请输入问答内容"
			},
            position:{
                required:icon+"请输入布局位置"
            }
		}
	})
}