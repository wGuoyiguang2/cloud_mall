function operateStart(){
    var intro=$("#intro").val()
    if(intro==null||intro==''){
        alert("请填写操作原因！")
        return;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : "/v1/tcmalladmin/card/start?cardNos="+$("#cardNos").text()+"&intro="+intro,
        error : function(request) {
            parent.layer.alert("请求失败");
        },
        success : function(data) {
            if (data.error == 0) {
                parent.layer.msg(data.msg);
                parent.searcher();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
}
function operateBind(){
    var intro=$("#intro").val()
    var bindUser=$("#bindUser").val();
    if(intro==null||intro==''){
        alert("请填写操作原因！")
        return;
    }
    if(bindUser==null||bindUser==''){
        alert("请填写绑定账号！")
        return;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : "/v1/tcmalladmin/card/bind?cardNos="+$("#cardNos").text()+"&intro="+intro+"&bindUser="+bindUser,
        error : function(request) {
            parent.layer.alert("请求失败");
        },
        success : function(data) {
            if (data.error == 0) {
                parent.layer.msg(data.msg);
                parent.searcher();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
}
function operateExport(){
    var intro=$("#intro").val()
    if(intro==null||intro==''){
        alert("请填写操作原因！")
        return;
    }
    var url="/v1/tcmalladmin/card/export?cardNos="+$("#cardNos").text()+"&intro="+intro;
    window.open(url);
    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    parent.layer.close(index);
}
function operateAbandon(){
    if(!confirm('是否确定废弃所选购物卡,废弃后无法恢复？')){
        return;
    }
    var intro=$("#intro").val()
    if(intro==null||intro==''){
        alert("请填写操作原因！")
        return;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : "/v1/tcmalladmin/card/abandon?cardNos="+$("#cardNos").text()+"&intro="+intro,
        error : function(request) {
            parent.layer.alert("请求失败");
        },
        success : function(data) {
            if (data.error == 0) {
                parent.layer.msg(data.msg);
                parent.searcher();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
}