function operateStart(){
    var intro=$("#intro").val()
    if(intro==null||intro==''){
        alert("请填写操作原因！")
        return;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : "/v1/tcmalladmin/cardbatch/start?batchNo="+$("#batchNo").text()+"&intro="+intro,
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
function operatePause(){
    var intro=$("#intro").val()
    if(intro==null||intro==''){
        alert("请填写操作原因！")
        return;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : "/v1/tcmalladmin/cardbatch/pause?batchNo="+$("#batchNo").text()+"&intro="+intro,
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
    var url="/v1/tcmalladmin/cardbatch/export?batchNo="+$("#batchNo").text()+"&intro="+intro;
    window.open(url);
    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    parent.layer.close(index);
}
function operateAbandon(){
    if(!confirm('是否确定废弃次批购物卡,废弃后无法恢复？')){
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
        url : "/v1/tcmalladmin/cardbatch/abandon?batchNo="+$("#batchNo").text()+"&intro="+intro,
        error : function(request) {
            parent.layer.alert(data.msg);
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