$().ready(function() {

    $(".selectpicker").selectpicker({
        noneResultsText : '未匹配',
        noneSelectedText : '请选择'
    });

    $.validator.addMethod("actionParamDetailCheck",function(value, element){
        var action = $('#action option:selected') .val();
        if(action != 'OPEN_DETAIL'){
            return true;
        }else {
            if(value == null || isNaN(value) || value =='') return false;
            return true;
        }
    },"请确认action参数");         //验证错误信息

    $.validator.addMethod("actionParamCat0Check",function(value, element){
        var action = $('#action option:selected') .val();
        if(action != 'OPEN_CATEGORY'){
            return true;
        }else {
            if(value == null || isNaN(value)) return false;
            return true;
        }
    },"请确认action参数");         //验证错误信息
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
		url : "/v1/tcmalladmin/recommendmanager/add",
		data : $('#recommendForm').serialize(),// 你的formid
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
	$("#recommendForm").validate({
		rules : {
            name : {
				required : true
			},
            subName : {
                required : true
            },
            image : {
                required : true
            },
            action : {
                required : true
            },
            actionParamDetail : {
                actionParamDetailCheck: $("#actionParamDetail").val()
            },
            actionParamCat0 : {
                actionParamCat0Check: $("#actionParamCat0").val()
            },
            status : {
				required : true
			},

		},
		messages : {

            name : {
				required : icon + "请输入主标题"
			},
            subName : {
				required : icon + "请输入副标题"
			},
            image : {
                required : icon + "请填写图片地址"
            },
            action : {
                required : icon + "请选择Action"
            },
            actionParamDetail : {
                required : icon + "请输入商品ID"
            },
            actionParamCat0 : {
                required : icon + "请至少选择第一级分类"
            },
            status : {
				required : icon + "请选择是否启用"
			},
		}
	})
}

$("#actionParamDetail").blur(function() {
    var action = $('#action option:selected') .val();
    var actionParamDetail = $('#actionParamDetail').val();
	if(action == 'OPEN_DETAIL' && actionParamDetail != null) {
        $.ajax({
            type : "GET",
            url : "/v1/tcmalladmin/recommendmanager/getimagepath",
            data : {
                sku : actionParamDetail
            },
            success : function(result) {
                if (result != null) {
                    var imagePath = result.imagePath;
                    $('#image').val(imagePath);
                }
            }
        });
	}

});

$('#action').change(function() {
    var action = $(this).val();
    switch (action){
        case "OPEN_DETAIL":
            $('#actionLabel').show();
            $('#open_detail').show();
            $('#open_category').hide();
            $('#open_collection').hide();
            break;
        case "OPEN_CATEGORY":
            $('#actionLabel').show();
            $('#open_detail').hide();
            $('#open_category').show();
            $('#open_collection').hide();
            break;
        case "OPEN_COLLECTION":
            $('#actionLabel').show();
            $('#open_detail').hide();
            $('#open_category').hide();
            $('#open_collection').show();
            break;
        default:
            $('#actionLabel').hide();
            $('#open_detail').hide();
            $('#open_category').hide();
            $('#open_collection').hide();
    }
});

$('#actionParamCat0').change(function() {    // 一级下拉菜单选项改变事件
    var cat0 = $(this).val();
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat0.toString(), catClass:1},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#actionParamCat1").html("");
            $("#actionParamCat2").html("");
            var cat1s = "<option value=\"\">请选择</option>";;
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#actionParamCat1").append(cat1s);
            $('#actionParamCat1').selectpicker('refresh');
            $('#actionParamCat1').selectpicker('render');
            $("#actionParamCat2").append("<option value=\"\">请选择</option>");
            $('#actionParamCat2').selectpicker('refresh');
            $('#actionParamCat2').selectpicker('render');
        },
        error: function () { alert("请求失败"); }
    });
});
$('#actionParamCat1').change(function() {    // 二级下拉菜单选项改变事件
    var cat1 = $(this).val();
    $.ajax({
        type: "GET",
        url: "/v1/tcmalladmin/cat",
        data: { parentCategorys:cat1.toString(), catClass:2},
        dataType: "JSON",
        async: false,
        success: function (data) {
            $("#actionParamCat2").html("");
            var cat1s = "<option value=\"\">请选择</option>";
            for(var i = 0;i<data.length;i++){
                var cat1 = data[i];
                cat1s += "<option value=" + cat1["catId"] + ">" + cat1["name"] + "</option>";
            }
            $("#actionParamCat2").append(cat1s);
            $('#actionParamCat2').selectpicker('refresh');
            $('#actionParamCat2').selectpicker('render');
        },
        error: function () { alert("请求失败！"); }
    });
});

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
    $('#image').val(result.filePath);
}).on('fileerror', function(event, data, msg) {  //一个文件上传失败
    alert('文件上传失败！'+msg);
});