
function createPreview(layout, layoutId) {
    var widthCell = 15;
    var heightCell = 15;
    for(i=0;i<layout.layoutdetails.length;i++){
        var layouts  = layout.layoutsize.split("*");
        var item = layout.layoutdetails[i];

        var html = $('.bbc').html();
        var x1 =item.x*widthCell;
        var y1 =item.y*heightCell;
        var w1 = item.w*widthCell;
        var h1 = item.h*heightCell;
        var layoutStr = item.x + " " + item.y + " " + item.w + " " + item.h;
        var a11 = "<div class='abc' style='width:" + w1 + "px;height:" + h1
            + "px;left:" + x1 + "px;top:" + y1 + "px;float:left;' data-layoutid="
            + layoutId + " data-layout='" + layoutStr+ "' id=" + i + " onclick=editRecommend(" + i + ")></div>";

        $('.bbc').html(html + a11);
    }
    updateBackground(layoutId)
}

function updateBackground(layoutId){
    $.ajax({
        url : '/v1/tcmalladmin/recommendmanager/recommendlist',
        type : "get",
        data : {
            'layoutId' : layoutId
        },
        success : function(r) {

            if (r.total > 0) {
                var recommendlist = r.rows;
                var i = 0;
                for(i=0;i<r.total;i++){
                    var recommend = recommendlist[i];
                    var position = recommend.position;
                    var d=document.getElementById(position);
                    d.style.backgroundImage ='url(' + recommend.image + ')';
                    d.style.backgroundRepeat='no-repeat';
                    d.style.backgroundSize='100% 100%';
                }
            } else {
                //alert("recommend is null");
            }
        }
    });
}

function editRecommend(id) {
    if(s_add_h == 'hidden'){
        layer.msg("无推荐位编辑权限");
        return false;
    }
    var d=document.getElementById(id);
    var layoutId = d.dataset.layoutid;
    var layout = d.dataset.layout;
    layer.open({
        type : 2,
        title : '推荐位编辑',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '50%', '60%' ],
        content : "/v1/tcmalladmin/recommendmanager/add?layoutId=" + layoutId + "&layout=" + layout + "&position=" + id
    });
}