$().ready(function() {

    $(".selectpicker").selectpicker({
        noneResultsText : '未匹配',
        noneSelectedText : '请选择'
    });
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
    var catId = $("#catId").val();
    var catType = $("#catType").val();
    if(catId === ""){
        parent.layer.alert("请选择分类");
        return;
    }
	$.ajax({
		cache : true,
		type : "POST",
		url : "/v1/tcmalladmin/categorypicture/save",
		data : $('#categoryPictureForm').serialize(),// 你的formid
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
    $("#categoryPictureForm").validate({
        rules : {
            catId : {
                required : true
            }
        },
        messages : {
            catId : {
                required : icon + "请选择分类"
            }
        }
    })
}

$('#cat0').change(function() {    // 一级下拉菜单选项改变事件
    var cat0 = $(this).val();
    $("#catId").val(cat0);
    $("#catType").val(0);
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat0.toString(), catClass:1},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#cat1").html("");
            $("#cat2").html("");
            var cat1s = "<option value=\"\">请选择</option>";;
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#cat1").append(cat1s);
            $('#cat1').selectpicker('refresh');
            $('#cat1').selectpicker('render');
            $("#cat2").append("<option value=\"\">请选择</option>");
            $('#cat2').selectpicker('refresh');
            $('#cat2').selectpicker('render');
        },
        error: function () { alert("请求失败"); }
    });
});
$('#cat1').change(function() {    // 二级下拉菜单选项改变事件
    var cat1 = $(this).val();
    $("#catId").val(cat1);
    $("#catType").val(1);
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat1.toString(), catClass:2},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#cat2").html("");
            var cat1s = "<option value=\"\">请选择</option>";
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#cat2").append(cat1s);
            $('#cat2').selectpicker('refresh');
            $('#cat2').selectpicker('render');
        },
        error: function () { alert("请求失败！"); }
    });
});

$('#cat2').change(function() {    // 二级下拉菜单选项改变事件
    var cat2 = $(this).val();
    $("#catId").val(cat2);
    $("#catType").val(2);
});

$('#file-pic-icon').fileinput({//初始化上传文件框
    showUpload : true,
    showRemove : false,
    uploadAsync: true,
    uploadLabel: "上传",//设置上传按钮的汉字
    uploadClass: "btn btn-primary",//设置上传按钮样式
    showCaption: false,//是否显示标题
    language: "zh",//配置语言
    uploadUrl: "/v1/tcmalladmin/fileupload/upload",
    //minImageWidth: 10,                                    //图片的最小宽度
    //minImageHeight: 10,                                   //图片的最小高度
    maxImageWidth: 38,                                  //图片的最大宽度
    maxImageHeight: 38,                                 //图片的最大高度
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
    $('#icon').val(result.filePath);
}).on('fileerror', function(event, data, msg) {  //一个文件上传失败
    alert('文件上传失败！'+msg);
});

$('#file-pic-background').fileinput({//初始化上传文件框
    showUpload : true,
    showRemove : false,
    uploadAsync: true,
    uploadLabel: "上传",//设置上传按钮的汉字
    uploadClass: "btn btn-primary",//设置上传按钮样式
    showCaption: false,//是否显示标题
    language: "zh",//配置语言
    uploadUrl: "/v1/tcmalladmin/fileupload/upload",
    //minImageWidth: 10,                                    //图片的最小宽度
    //minImageHeight: 10,                                   //图片的最小高度
    maxImageWidth: 276,                                  //图片的最大宽度
    maxImageHeight: 81,                                 //图片的最大高度
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
    $('#background').val(result.filePath);
}).on('fileerror', function(event, data, msg) {  //一个文件上传失败
    alert('文件上传失败！'+msg);
});

$('#file-pic-picture').fileinput({//初始化上传文件框
    showUpload : true,
    showRemove : false,
    uploadAsync: true,
    uploadLabel: "上传",//设置上传按钮的汉字
    uploadClass: "btn btn-primary",//设置上传按钮样式
    showCaption: false,//是否显示标题
    language: "zh",//配置语言
    uploadUrl: "/v1/tcmalladmin/fileupload/upload",
    //minImageWidth: 10,                                    //图片的最小宽度
    //minImageHeight: 10,                                   //图片的最小高度
    maxImageWidth: 190,                                  //图片的最大宽度
    maxImageHeight: 190,                                 //图片的最大高度
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
    $('#picture').val(result.filePath);
}).on('fileerror', function(event, data, msg) {  //一个文件上传失败
    alert('文件上传失败！'+msg);
});