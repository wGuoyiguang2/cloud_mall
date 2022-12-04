var player = videojs('example-video', {"poster": "","controls": "true"}, function(){

    this.on('play',function(){
        console.log('正在播放');
    });

    //暂停--播放完毕后也会暂停
    this.on('pause',function(){
        console.log("暂停中")
    });

    // 结束
    this.on('ended', function() {
        console.log('结束');
    })

});

function save() {
    var sku = $("#sku").val();
    var videoPath = $("#videoPath").val();
    if(videoPath == '') {
        layer.msg("请输入短视频地址");
        return;
    }
    layer.confirm('确定要提交短视频地址:' + videoPath +'吗?', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : '/v1/tcmalladmin/productinfo/video/add',
            type : "get",
            data : {
                'sku' : sku,
                'videoPath' : videoPath
            },
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}