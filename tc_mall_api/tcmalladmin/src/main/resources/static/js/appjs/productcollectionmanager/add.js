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
		url : "/v1/tcmalladmin/productcollectionmanager/save",
		data : $('#collectionForm').serialize(),// 你的formid
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
	$("#collectionForm").validate({
		rules : {
            name : {
				required : true
			},
            describe : {
                required : true
            },
            imagePath : {
                required : true
            },
            status : {
				required : true
			},

		},
		messages : {

            name : {
				required : icon + "请输入商品集名称"
			},
            describe : {
				required : icon + "请输入商品集描述"
			},
            imagePath : {
                required : icon + "请上传背景图片"
            },
            status : {
				required : icon + "请选择是否启用"
			},
		}
	})
}

$('#file-pic').fileinput({//初始化上传文件框
    showUpload : true,
    showRemove : false,
    uploadAsync: true,
    uploadLabel: "上传",//设置上传按钮的汉字
    uploadClass: "btn btn-primary",//设置上传按钮样式
    showCaption: false,//是否显示标题
    language: "zh",//配置语言
    uploadUrl: "/v1/tcmalladmin/fileupload/upload",
    maxFileSize : 0,
    maxFileCount: 1,/*允许最大上传数，可以多个，当前设置单个*/
    enctype: 'multipart/form-data',
    allowedPreviewTypes : [ 'image' ],
    allowedFileExtensions : ["jpg", "png"],/*上传文件格式*/
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    dropZoneTitle: "请通过拖拽图片文件放到这里",
    dropZoneClickTitle: "或者点击此区域添加图片",
    //uploadExtraData: {"id": id},//这个是外带数据
    showBrowse: false,
    browseOnZoneClick: true,
    slugCallback : function(filename) {
        return filename.replace('(', '_').replace(']', '_');
    }
}).on("fileuploaded", function(event, data, previewId, index) {
    var result = data.response;
    $('#imagePath').val(result.filePath);
}).on('fileerror', function(event, data, msg) {  //一个文件上传失败
    alert('文件上传失败！'+msg);
});