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