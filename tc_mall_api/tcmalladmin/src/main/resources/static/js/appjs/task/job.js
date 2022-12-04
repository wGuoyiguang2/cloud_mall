$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: "/task/job/list",
                showRefresh : true,
                // showToggle : true,
                showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                // search : true, // 是否显示搜索框
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
                // "server"
                queryParams: function (params) {
                    var jobName = $("#searchName").val();
                    return {
                        jobName: jobName,
                        limit: params.limit,
                        offset: params.offset
                    };
                },
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'id',
                        title: 'id'
                    },
                    {
                        field: 'serviceName',
                        title: '服务名称'
                    },
                    {
                        field: 'jobName',
                        title: '任务名称'
                    },
                    {
                        field: 'jobGroup',
                        title: '任务分组'
                    },
                    {
                        field: 'jobParam',
                        title: '任务参数'
                    },
                    {
                        field: 'cronExpression',
                        title: 'cron表达式'
                    },
                    {
                        field: 'description',
                        title: '任务描述'
                    },

                    {
                        visible: false,
                        field: 'ctime',
                        title: '创建时间'
                    },


                    {
                        visible: false,
                        field: 'utime',
                        title: '更新时间'
                    },
                    {
                        field: 'jobStatus',
                        title: '停起操作',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-success btn-xs" href="#" mce_href="#" title="点击开启" onclick="changeStatus(\''
                                + row.id + '\',\'' + row.jobStatus
                                + '\')"><i class="fa fa-hourglass-start"></i>开启</a> ';
                            var f = '<a class="btn btn-danger btn-xs" href="#" mce_href="#" title="点击关闭" onclick="changeStatus(\''
                                + row.id + '\',\'' + row.jobStatus
                                + '\')"><i class="fa fa-square-o">关闭</i></a> ';
                            if (row.jobStatus == 0) {
                                return e;
                            } else {
                                return f;
                            }

                        }
                    },
                    {
                        field: 'resultStatus',
                        title: '上一次执行状态',
                        formatter: function (value, row, index) {
                            var success = '<button class="btn btn-success btn-xs">成功</button> ';
                            var failed = '<button class="btn btn-danger btn-xs">失败</button> ';
                            if (row.jobStatus == 0) {
                                return failed;
                            } else {
                                return success;
                            }

                        }
                    },
                    {
                        field: 'resultMsg',
                        title: '上一次执行信息'
                    },
                    {
                        field: 'lastStartTime',
                        title: '上一次执行开始时间'
                    },
                    {
                        field: 'lastEndTime',
                        title: '上一次执行结束时间'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id + '\',\'' + row.jobStatus
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="执行"  mce_href="#" onclick="run(\''
                                + row.id
                                + '\')"><i class="fa fa-play"></i></a> ';
                            return e + d + f;
                        }
                    }]
            });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/task/job/add' // iframe的url
    });
}

function edit(id, status) {
    if (status == '1') {
        layer.alert('修改之前请先停止任务');
        return;
    }
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['50%', '60%'],
        content: '/task/job/edit/' + id // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/task/job/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function changeStatus(id, status) {
    var actCh;
    var cmd;
    if (status == 0) {
        cmd = 'start';
        actCh = "确认要开启任务吗？";
    } else {
        cmd = 'stop';
        actCh = "确认要停止任务吗？";
    }
    layer.confirm(actCh, {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/task/job/changeJobStatus",
            type: "get",
            data: {
                'id': id,
                'cmd': cmd
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: '/task/job/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}

function run(id) {
    layer.confirm("确认立即执行任务？", {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/task/job/run",
            type: "get",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}