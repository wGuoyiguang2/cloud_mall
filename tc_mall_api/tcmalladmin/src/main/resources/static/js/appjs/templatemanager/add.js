$().ready(function() {
    console.log("selectable");
    // 合并单元格按钮
    $("#btnMerge").click(function(){
        var $t = $("#t");
        if ($("table", $t).length > 0) {
            alert("不支持嵌套表格！");
            return;
        }
        var sigDel = "sign4delete";  // 删除标记，用作类名
        var sigSel = "ui-selected";  // 选中标记，用作类名

        var rmin = 10000, cmin = 10000;
        var rmax = 0    , cmax = 0    ;
        var rnum        , cnum        ;
        // 计算起始和跨距
        var hasSpan = false;
        $("th,td", $t).filter("." + sigSel).each(function(){
            if(typeof($(this).attr("rowspan"))!="undefined" || typeof($(this).attr("colspan"))!="undefined"){
                hasSpan = true;
            }
            var ridx = Number($(this).attr("y"));
            rmin = ridx < rmin ? ridx : rmin;
            rmax = ridx > rmax ? ridx : rmax;
            var cidx = Number($(this).attr("x"));
            cmin = cidx < cmin ? cidx : cmin;
            cmax = cidx > cmax ? cidx : cmax;
        });
        rnum = rmax - rmin + 1;
        cnum = cmax - cmin + 1;
        if(hasSpan){
            alert("不支持重复合并");
            return;
        }
        var index = 0;
        $("th,td", $t).filter("." + sigSel).each(function(){
            if(index == 0){
                $(this).attr({rowspan: rnum, colspan: cnum});
            }else {
                $(this).addClass(sigDel);//隐藏合并的单元格
            }
            ++index;
        });
        $("th,td", $t).remove("." + sigDel);
        return false;
    });
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
    //布局模板
    var index=0;
    var layoutList = [];
    var table = document.getElementById("t")
    var childtr = table.getElementsByTagName("tr")
    var l=childtr.length;
    if(l==0){$.messager.alert('提示','请天添加布局', 'info');return false;}
    for(var i=0;i<l;i++){
        var childtd = childtr[i].getElementsByTagName("td");
        for(var j=0;j<childtd.length;j++){
            var w1=1,h1=1;
            var td = childtd[j];
            if(td.style.display==='none') continue;
            var rowspan=td.getAttribute('rowspan');//高
            var colspan=td.getAttribute('colspan');//宽
            if(rowspan!=null && typeof(rowspan) != "undefined"){
                h1=parseInt(rowspan);
            }
            if(colspan!= null && typeof(colspan) != "undefined"){
                w1=parseInt(colspan);
            }
            layoutList[index++]={x:parseInt(td.getAttribute('x')),y:td.getAttribute('y'),w:w1,h:h1};
        }
    }
    var size =$('#heightLayout').val()+"*"+$('#widthLayout').val();
    $("#recNum").val(layoutList.length);
    a={"layoutsize":size,"layoutdetails":layoutList};
    $("#layout").val($.toJSON(a));
	$.ajax({
		cache : true,
		type : "POST",
		url : "/v1/tcmalladmin/templatemanager/add",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
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
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
            code : {
				required : true,
			},
            widthLayout : {
                required : true,
                digits : true
			},
            heightLayout : {
				required : true,
                digits : true
			},

		},
		messages : {

			name : {
				required : icon + "请输入模板名称"
			},
			code : {
				required : icon + "请输入模板Code"
			},
            width : {
				required : icon + "请输入模板宽度"
			},
            height : {
				required : icon + "请输入模板高度"
			},
		}
	})
}

function autocreate() {
    //创建table表格
    var table = document.createElement("table");
    table.setAttribute("id", "t");
    table.setAttribute("border", "1");
    //获取行数值
    var line = $('#widthLayout').val();
    //获取列数值
    var list =$('#heightLayout').val();
    var colgroup = document.createElement("colgroup");
    for (var j = 1; j <= list; j++){
        var col= document.createElement("col");
        col.style.width="30px";
        col.style.height="30px";
        colgroup.appendChild(col);
    }
    table.appendChild(colgroup);

    for (var i = 1; i <= line; i++) {
        var tr = document.createElement("tr");
        for (var j = 1; j <= list; j++) {
            var td = document.createElement("td");
            td.setAttribute("x",j-1);
            td.setAttribute("y",i-1);
            td.style.width="30px";
            td.style.height="30px";
            td.innerHtml = i + "*" + j;
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
    $("#div_tablecontains").html('');
    document.getElementById("div_tablecontains").appendChild(table);
    $("#t").selectable();
    return false;
}