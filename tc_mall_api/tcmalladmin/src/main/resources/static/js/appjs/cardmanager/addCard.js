//初始化日期插件
$('.form_datetime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1
});
$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
    var stime=$("#stime").val();
    var etime=$("#etime").val();
    stime = stime.replace(/-/g,"/");
    stime = stime.replace(/(\.\d+)?/g,"");
    etime = etime.replace(/-/g,"/");
    etime = etime.replace(/(\.\d+)?/g,"");
    var sDate=new Date(stime);
    var eDate=new Date(etime);
    if(eDate<sDate){
        alert('结束时间不能小于开始时间！')
        return;
    }
    if(stime==''||etime==''||stime==null||etime==null){
        alert("请输入开始时间和结束时间！")
        return;
    }
    var str='stime='+stime+'&etime='+etime+'&'+$('#cardbatchForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/v1/tcmalladmin/cardbatch/addCard",
		data : str,// 你的formid
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
function cardSelectChange(){
    if($("#createType").val()=='1'){
        $("#batchDiv").show();
    }else{
        $("#batchDiv").hide();
    }
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#cardbatchForm").validate({
        rules : {
            cardName : {
                required : true
            },
            count : {
                required : true,
                range:[1,99999999]
            },
            bindVender:{
                required:true
            },
            faceValue : {
                required : true,
				number:true
            },
            stime : {
                required : true
            },
            etime : {
                required : true
            },
            company : {
                required : true
            },
            venderid : {
                required : true
            }
        },
        messages : {
            cardName : {
                required : icon+"请输入卡名称"
            },
            count : {
                required : icon+"请输入卡数量"
            },
            faceValue : {
                required : icon+"请输入卡面值"
            },
            stime : {
                required : icon+"请输入开始时间"
            },
            bindVender:{
                required:icon+"请选择是否绑定大客户"
            },
            etime : {
                required : icon+"请输入结束时间"
            },
            company : {
                required : icon+"请输入制卡公司"
            },
            venderid : {
                required : icon+"请输入大客户ID"
            }
        }
	})
}