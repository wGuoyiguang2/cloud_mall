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
		url : "/v1/tcmalladmin/hotsearch/add",
		data : $('#hotsearchForm').serialize(),// 你的formid
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
	$("#hotsearchForm").validate({
        rules : {
            keyword : {
                required : true
            },
            position : {
                required : true,
                range:[1,999]
            },
            status : {
                required : true
            }

        },
        messages : {

            keyword : {
                required : icon+"请输入keyword"
            },
            position : {
                required : icon+"请输入position"
            },
            status : {
                required : icon+"请输入status"
            }
        }
	})
}